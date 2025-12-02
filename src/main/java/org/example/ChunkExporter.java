package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ChunkExporter
 *
 * Run:
 *   set DB_URL=jdbc:postgresql://localhost:5432/learning_db
 *   set DB_USER=postgres
 *   set DB_PASS=YourPass
 *   mvn -Dexec.mainClass=org.example.ChunkExporter -Dexec.args="chunks.jsonl" exec:java
 *
 * Output: chunks.jsonl (JSONL file, one JSON object per line)
 */
public class ChunkExporter {
    // approx token -> char heuristic: 1 token ~= 4 chars for English; target ~300 tokens -> ~1200 chars
    private static final int TARGET_CHUNK_CHARS = 1200;
    private static final int CHUNK_OVERLAP_CHARS = 250; // overlap between adjacent chunks

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final ObjectMapper M = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        String outFile = args.length > 0 ? args[0] : "chunks.jsonl";
        String url = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/learning_db");
        String user = System.getenv().getOrDefault("DB_USER", "postgres");
        String pass = System.getenv().getOrDefault("DB_PASS", "postgres");

        System.out.println("Connecting to: " + url + " as " + user);
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            conn.setAutoCommit(false);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
                exportCourses(conn, writer);
                exportTopics(conn, writer);
                exportClasses(conn, writer);
                exportAssignments(conn, writer);
            }
            System.out.println("Chunks written to: " + outFile);
        }
    }

    // ---------- Course Summary chunks ----------
    private static void exportCourses(Connection conn, BufferedWriter writer) throws SQLException, IOException {
        String sql = "SELECT id, code, title, description, created_at FROM courses ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String code = rs.getString("code");
                String title = rs.getString("title");
                String desc = rs.getString("description");
                Timestamp created = rs.getTimestamp("created_at");

                // list topics for this course
                List<String> topics = new ArrayList<>();
                try (PreparedStatement tps = conn.prepareStatement(
                        "SELECT code, title FROM topics WHERE course_id = ? ORDER BY position")) {
                    tps.setInt(1, id);
                    try (ResultSet tr = tps.executeQuery()) {
                        while (tr.next()) {
                            topics.add(tr.getString("code") + " — " + tr.getString("title"));
                        }
                    }
                }

                StringBuilder body = new StringBuilder();
                body.append(desc == null ? "" : desc.trim());
                body.append("\n\nTopics: ");
                if (topics.isEmpty()) {
                    body.append("None");
                } else {
                    body.append(String.join("; ", topics));
                }
                body.append("\nTotal topics: ").append(topics.size());
                body.append("\nCreated at: ").append(created == null ? "" : created.toLocalDateTime().format(ISO));

                ObjectNode chunk = baseChunk();
                String chunkId = String.format("COURSE-%d", id);
                chunk.put("chunk_id", chunkId);
                chunk.put("chunk_type", "course");
                chunk.put("title", "Course: " + code + " — " + title);
                chunk.put("text", body.toString());

                ObjectNode meta = M.createObjectNode();
                meta.put("course_id", id);
                meta.put("course_code", code);
                meta.put("created_at", created == null ? "" : created.toLocalDateTime().format(ISO));
                meta.put("topic_count", topics.size());
                chunk.set("metadata", meta);

                writer.write(M.writeValueAsString(chunk));
                writer.newLine();
            }
        }
    }

    // ---------- Topic Summary & Topic Aggregate chunks ----------
    private static void exportTopics(Connection conn, BufferedWriter writer) throws SQLException, IOException {
        String sql = "SELECT id, course_id, code, title, description, position, created_at FROM topics ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int topicId = rs.getInt("id");
                int courseId = rs.getInt("course_id");
                String code = rs.getString("code");
                String title = rs.getString("title");
                String desc = rs.getString("description");
                int pos = rs.getInt("position");
                Timestamp created = rs.getTimestamp("created_at");

                // gather classes list and assignment list
                List<String> classLines = new ArrayList<>();
                try (PreparedStatement cps = conn.prepareStatement(
                        "SELECT class_number, title, learned_at FROM classes WHERE topic_id = ? ORDER BY class_number")) {
                    cps.setInt(1, topicId);
                    try (ResultSet cr = cps.executeQuery()) {
                        while (cr.next()) {
                            int cn = cr.getInt("class_number");
                            String ct = cr.getString("title");
                            Timestamp learned = cr.getTimestamp("learned_at");
                            String learnedS = learned == null ? "unknown" : learned.toLocalDateTime().format(ISO);
                            classLines.add(String.format("%d: %s — learned_at: %s", cn, ct, learnedS));
                        }
                    }
                }
                int totalClasses = classLines.size();

                List<String> assignments = new ArrayList<>();
                try (PreparedStatement aps = conn.prepareStatement(
                        "SELECT a.id, a.title FROM assignments a JOIN assignment_topics at ON a.id = at.assignment_id WHERE at.topic_id = ?")) {
                    aps.setInt(1, topicId);
                    try (ResultSet ar = aps.executeQuery()) {
                        while (ar.next()) {
                            assignments.add(ar.getString("title"));
                        }
                    }
                }
                int totalAssignments = assignments.size();

                // Topic summary chunk
                StringBuilder body = new StringBuilder();
                body.append(desc == null ? "" : desc.trim());
                body.append("\n\nTopic position: ").append(pos);
                body.append("\nCourse id: ").append(courseId);
                body.append("\nTotal classes: ").append(totalClasses);
                body.append("\nTotal assignments: ").append(totalAssignments);
                body.append("\nClasses:\n");
                for (String cl : classLines) body.append("- ").append(cl).append("\n");
                body.append("Assignments:\n");
                if (assignments.isEmpty()) body.append("- None\n");
                else for (String a : assignments) body.append("- ").append(a).append("\n");
                body.append("Created at: ").append(created == null ? "" : created.toLocalDateTime().format(ISO));

                ObjectNode chunk = baseChunk();
                String chunkId = String.format("TOPIC-%d", topicId);
                chunk.put("chunk_id", chunkId);
                chunk.put("chunk_type", "topic_summary");
                chunk.put("title", "Topic: " + code + " — " + title);
                chunk.put("text", body.toString());
                ObjectNode meta = M.createObjectNode();
                meta.put("topic_id", topicId);
                meta.put("topic_code", code);
                meta.put("course_id", courseId);
                meta.put("position", pos);
                meta.put("total_classes", totalClasses);
                meta.put("total_assignments", totalAssignments);
                chunk.set("metadata", meta);
                writer.write(M.writeValueAsString(chunk));
                writer.newLine();

                // Topic aggregate chunk (explicit earliest/latest learned_at)
                try (PreparedStatement agg = conn.prepareStatement(
                        "SELECT MIN(learned_at) AS first_learned, MAX(learned_at) AS last_learned FROM classes WHERE topic_id = ?")) {
                    agg.setInt(1, topicId);
                    try (ResultSet ar = agg.executeQuery()) {
                        if (ar.next()) {
                            Timestamp first = ar.getTimestamp("first_learned");
                            Timestamp last = ar.getTimestamp("last_learned");
                            String firstS = first == null ? "unknown" : first.toLocalDateTime().format(ISO);
                            String lastS = last == null ? "unknown" : last.toLocalDateTime().format(ISO);
                            String aggText = String.format("Topic aggregate for %s (%s)\nTotal classes: %d\nTotal assignments: %d\nEarliest learned_at: %s\nLatest learned_at: %s",
                                    code, title, totalClasses, totalAssignments, firstS, lastS);
                            ObjectNode aggregateChunk = baseChunk();
                            aggregateChunk.put("chunk_id", "TOPIC_AGG-" + topicId);
                            aggregateChunk.put("chunk_type", "topic_aggregate");
                            aggregateChunk.put("title", "Topic Aggregate: " + code + " — " + title);
                            aggregateChunk.put("text", aggText);
                            ObjectNode m2 = M.createObjectNode();
                            m2.put("topic_id", topicId);
                            m2.put("topic_code", code);
                            m2.put("course_id", courseId);
                            m2.put("total_classes", totalClasses);
                            m2.put("total_assignments", totalAssignments);
                            if (first != null) m2.put("first_learned", first.toLocalDateTime().format(ISO));
                            if (last != null) m2.put("last_learned", last.toLocalDateTime().format(ISO));
                            aggregateChunk.set("metadata", m2);
                            writer.write(M.writeValueAsString(aggregateChunk));
                            writer.newLine();
                        }
                    }
                }
            }
        }
    }

    // ---------- Class chunks (with resources) ----------
    private static void exportClasses(Connection conn, BufferedWriter writer) throws SQLException, IOException {
        String sql = "SELECT id, topic_id, title, content, class_number, learned_at, created_at FROM classes ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int classId = rs.getInt("id");
                int topicId = rs.getInt("topic_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                int classNumber = rs.getInt("class_number");
                Timestamp learned = rs.getTimestamp("learned_at");

                // get topic code and course code
                String topicCode = "";
                String courseCode = "";
                try (PreparedStatement tq = conn.prepareStatement("SELECT t.code AS tcode, c.code AS ccode FROM topics t JOIN courses c ON t.course_id = c.id WHERE t.id = ?")) {
                    tq.setInt(1, topicId);
                    try (ResultSet tr = tq.executeQuery()) {
                        if (tr.next()) {
                            topicCode = tr.getString("tcode");
                            courseCode = tr.getString("ccode");
                        }
                    }
                }

                // get resources for class
                List<String> resources = new ArrayList<>();
                try (PreparedStatement rps = conn.prepareStatement("SELECT kind, url, description FROM resources WHERE class_id = ?")) {
                    rps.setInt(1, classId);
                    try (ResultSet rr = rps.executeQuery()) {
                        while (rr.next()) {
                            String kind = rr.getString("kind");
                            String url = rr.getString("url");
                            String desc = rr.getString("description");
                            resources.add(String.format("%s: %s — %s", kind, url == null ? "[no-url]" : url, desc == null ? "" : desc));
                        }
                    }
                }

                String header = String.format("Class %s-%s #%d — %s\nLearned at: %s\n",
                        courseCode, topicCode, classNumber, title,
                        learned == null ? "unknown" : learned.toLocalDateTime().format(ISO));
                String body = (content == null ? "" : content.trim()) + "\n\nResources:\n";
                if (resources.isEmpty()) body += "- None\n";
                else for (String r : resources) body += "- " + r + "\n";

                String fullText = header + "\n" + body;

                // split into smaller chunks if too long
                List<String> pieces = splitToChunks(fullText, TARGET_CHUNK_CHARS, CHUNK_OVERLAP_CHARS);

                for (int i = 0; i < pieces.size(); i++) {
                    String piece = pieces.get(i);
                    ObjectNode chunk = baseChunk();
                    String chunkId = String.format("CLASS-%d-%d", classId, i+1);
                    chunk.put("chunk_id", chunkId);
                    chunk.put("chunk_type", "class");
                    chunk.put("title", "Class: " + title + (pieces.size() > 1 ? " (part " + (i+1) + "/" + pieces.size() + ")" : ""));
                    chunk.put("text", piece);
                    ObjectNode meta = M.createObjectNode();
                    meta.put("class_id", classId);
                    meta.put("topic_id", topicId);
                    meta.put("class_number", classNumber);
                    if (learned != null) meta.put("learned_at", learned.toLocalDateTime().format(ISO));
                    meta.put("resource_count", resources.size());
                    chunk.set("metadata", meta);
                    writer.write(M.writeValueAsString(chunk));
                    writer.newLine();
                }
            }
        }
    }

    // ---------- Assignment chunks ----------
    private static void exportAssignments(Connection conn, BufferedWriter writer) throws SQLException, IOException {
        String sql = "SELECT id, title, description, created_at, due_date FROM assignments ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int aid = rs.getInt("id");
                String title = rs.getString("title");
                String desc = rs.getString("description");
                Timestamp created = rs.getTimestamp("created_at");
                java.sql.Date due = rs.getDate("due_date");

                // related topics
                List<String> topicCodes = new ArrayList<>();
                try (PreparedStatement tps = conn.prepareStatement("SELECT t.code FROM topics t JOIN assignment_topics at ON t.id = at.topic_id WHERE at.assignment_id = ?")) {
                    tps.setInt(1, aid);
                    try (ResultSet tr = tps.executeQuery()) {
                        while (tr.next()) topicCodes.add(tr.getString("code"));
                    }
                }

                StringBuilder body = new StringBuilder();
                body.append(desc == null ? "" : desc.trim());
                body.append("\n\nRelated topics: ");
                if (topicCodes.isEmpty()) body.append("None");
                else body.append(String.join(", ", topicCodes));
                body.append("\nDue date: ").append(due == null ? "" : due.toString());
                body.append("\nCreated at: ").append(created == null ? "" : created.toLocalDateTime().format(ISO));

                ObjectNode chunk = baseChunk();
                String chunkId = "ASSIGN-" + aid;
                chunk.put("chunk_id", chunkId);
                chunk.put("chunk_type", "assignment");
                chunk.put("title", "Assignment: " + title);
                chunk.put("text", body.toString());
                ObjectNode meta = M.createObjectNode();
                meta.put("assignment_id", aid);
                meta.put("due_date", due == null ? "" : due.toString());
                meta.put("related_topic_count", topicCodes.size());
                chunk.set("metadata", meta);
                writer.write(M.writeValueAsString(chunk));
                writer.newLine();
            }
        }
    }

    // ---------- helpers ----------
    private static ObjectNode baseChunk() {
        return M.createObjectNode();
    }

    /**
     * Split text into overlapping chunks approximating targetChars size using sentence boundaries.
     */
    private static List<String> splitToChunks(String text, int targetChars, int overlapChars) {
        List<String> sentences = splitIntoSentences(text);
        List<String> chunks = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        for (int i = 0; i < sentences.size(); i++) {
            String s = sentences.get(i);
            if (cur.length() + s.length() <= targetChars || cur.length() == 0) {
                cur.append(s);
            } else {
                chunks.add(cur.toString().trim());
                // start new chunk with overlap
                StringBuilder next = new StringBuilder();
                int kept = 0;
                // append sentences from the end for overlap until we reach overlapChars
                for (int j = i - 1; j >= 0 && kept < overlapChars; j--) {
                    String prev = sentences.get(j);
                    if (next.length() + prev.length() > overlapChars) break;
                    next.insert(0, prev);
                    kept += prev.length();
                }
                // ensure new chunk starts with overlap content
                next.append(s);
                cur = next;
            }
        }
        if (cur.length() > 0) chunks.add(cur.toString().trim());
        // final sanity: if a chunk is too long, hard-split by characters
        List<String> finalChunks = new ArrayList<>();
        for (String c : chunks) {
            if (c.length() <= targetChars * 1.5) finalChunks.add(c);
            else {
                // hard cut into multiple pieces with overlap
                int idx = 0;
                while (idx < c.length()) {
                    int end = Math.min(c.length(), idx + targetChars);
                    finalChunks.add(c.substring(idx, end).trim());
                    idx = end - overlapChars;
                    if (idx < 0) idx = 0;
                }
            }
        }
        return finalChunks;
    }

    // Simple sentence splitter. Good-enough heuristic for English: split on [.?!]\s+
    private static List<String> splitIntoSentences(String text) {
        List<String> out = new ArrayList<>();
        Pattern p = Pattern.compile("[^.!?\\n]+[.!?\\n]*", Pattern.MULTILINE);
        Matcher m = p.matcher(text);
        while (m.find()) {
            out.add(m.group());
        }
        if (out.isEmpty()) out.add(text);
        return out;
    }
}
