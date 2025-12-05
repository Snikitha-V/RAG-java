package org.example;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * SqlService executes parameterized queries for common factual intents
 * and builds a tiny DbChunk containing the SQL result so it can be injected
 * into RAG context.
 */
public class SqlService {
    private final String url;
    private final String user;
    private final String pass;

    public SqlService(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    private Connection getConn() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    /**
     * Helper to resolve topic code (e.g., "C1-T1") to topic_id.
     * Returns empty if not found.
     */
    private Optional<Integer> resolveTopicId(Connection c, String topicCode) throws SQLException {
        String sql = "SELECT id FROM topics WHERE UPPER(code) = UPPER(?)";
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, topicCode.trim());
            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getInt("id"));
                }
                return Optional.empty();
            }
        }
    }

    /**
     * Example: handle "when did I learn <topic_code>?" or "when did I learn C2-T3?"
     * Returns a small map with keys "earliest" and "latest" as ISO timestamps, or empty if none.
     * topicCode must be exact e.g., "C2-T3" (caller must extract / validate).
     */
    public Optional<Map<String, String>> queryLearnedAtRange(String topicCode) throws SQLException {
        try (Connection c = getConn()) {
            Optional<Integer> topicIdOpt = resolveTopicId(c, topicCode);
            if (topicIdOpt.isEmpty()) return Optional.empty();
            
            String sql = "SELECT MIN(learned_at) AS earliest, MAX(learned_at) AS latest FROM classes WHERE topic_id = ?;";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setInt(1, topicIdOpt.get());
                try (ResultSet rs = p.executeQuery()) {
                    if (!rs.next()) return Optional.empty();
                    Timestamp e = rs.getTimestamp("earliest");
                    Timestamp l = rs.getTimestamp("latest");
                    if (e == null && l == null) return Optional.empty();
                    Map<String,String> out = new LinkedHashMap<>();
                    if (e != null) out.put("earliest", e.toLocalDateTime().toString());
                    if (l != null) out.put("latest", l.toLocalDateTime().toString());
                    return Optional.of(out);
                }
            }
        }
    }

    /**
     * Example: count classes for a topic
     */
    public Optional<Integer> queryCountClassesForTopic(String topicCode) throws SQLException {
        try (Connection c = getConn()) {
            Optional<Integer> topicIdOpt = resolveTopicId(c, topicCode);
            if (topicIdOpt.isEmpty()) return Optional.empty();
            
            String sql = "SELECT COUNT(*) AS cnt FROM classes WHERE topic_id = ?;";
            try (PreparedStatement p = c.prepareStatement(sql)) {
                p.setInt(1, topicIdOpt.get());
                try (ResultSet rs = p.executeQuery()) {
                    if (!rs.next()) return Optional.of(0);
                    return Optional.of(rs.getInt("cnt"));
                }
            }
        }
    }

    /**
     * Example: list assignments for a class (returns up to limit rows)
     */
    public List<Map<String,String>> queryAssignmentsForClass(int classId, int limit) throws SQLException {
        String sql = "SELECT a.id AS assignment_id, a.title, a.due_date " +
                     "FROM assignments a " +
                     "JOIN assignment_topics at ON a.id = at.assignment_id " +
                     "JOIN classes c ON at.topic_id = c.topic_id " +
                     "WHERE c.id = ? ORDER BY a.due_date LIMIT ?;";
        try (Connection c = getConn(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, classId);
            p.setInt(2, limit);
            try (ResultSet rs = p.executeQuery()) {
                List<Map<String,String>> res = new ArrayList<>();
                while (rs.next()) {
                    Map<String,String> r = new LinkedHashMap<>();
                    r.put("assignment_id", String.valueOf(rs.getInt("assignment_id")));
                    r.put("title", rs.getString("title"));
                    Date d = rs.getDate("due_date");
                    r.put("due_date", d == null ? "" : d.toString());
                    res.add(r);
                }
                return res;
            }
        }
    }

    /**
     * Build a synthetic DbChunk representing SQL results.
     * The chunk id is prefixed with SQL- for easy detection.
     */
    public DbChunk buildSqlChunk(String idSuffix, String title, String body) {
        DbChunk c = new DbChunk();
        c.setChunkId("SQL-" + idSuffix);
        c.setChunkType("sql_result");
        c.setTitle(title);
        c.setText(body);
        return c;
    }

    // Helper to make a readable body for date range
    public String sqlDateRangeBody(String topicCode, Map<String,String> range) {
        StringBuilder sb = new StringBuilder();
        sb.append("SQL_RESULT for topic=").append(topicCode).append("\n");
        range.forEach((k,v)-> sb.append(k).append(": ").append(v).append("\n"));
        return sb.toString();
    }

    // Helper to make count body
    public String sqlCountBody(String topicCode, int cnt) {
        return String.format("SQL_RESULT for topic=%s\nTotal classes: %d\n", topicCode, cnt);
    }
}
