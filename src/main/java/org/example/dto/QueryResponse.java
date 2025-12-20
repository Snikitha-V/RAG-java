package org.example.dto;

import java.util.List;
import java.util.Map;

/**
 * Canonical response DTO for the /api/v1/query endpoint.
 * Machine-consumable, API-grade structure.
 */
public class QueryResponse {

    /** The generated answer text */
    private String answer;

    /** Detected intent: FACTUAL, SEMANTIC, MIXED */
    private String intent;

    /** Route taken: SQL, RAG, or MIXED */
    private String route;

    /** Confidence level: low, medium, high */
    private String confidence;

    /** End-to-end latency in milliseconds */
    private long latencyMs;

    /** List of source chunk IDs used as context */
    private List<String> sources;

    /** Retrieval chain: ordered list of candidate IDs with scores */
    private List<Map<String, Object>> retrievalChain;

    /** SQL query executed (if any) */
    private String sql;

    public QueryResponse() {}

    // ----- Getters and Setters -----

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public long getLatencyMs() {
        return latencyMs;
    }

    public void setLatencyMs(long latencyMs) {
        this.latencyMs = latencyMs;
    }

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public List<Map<String, Object>> getRetrievalChain() {
        return retrievalChain;
    }

    public void setRetrievalChain(List<Map<String, Object>> retrievalChain) {
        this.retrievalChain = retrievalChain;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    // ----- Builder-style convenience -----

    public static QueryResponse from(org.example.QueryResult result, long latencyMs) {
        QueryResponse resp = new QueryResponse();
        resp.setAnswer(result.getAnswer());
        resp.setIntent(result.getIntent());
        resp.setConfidence(result.getConfidence());
        resp.setSources(result.getSources());
        resp.setRetrievalChain(result.getRetrievalChain());
        resp.setSql(result.getSql());
        resp.setLatencyMs(latencyMs);

        // Derive route from intent
        String intent = result.getIntent();
        if ("FACTUAL".equalsIgnoreCase(intent)) {
            resp.setRoute("SQL");
        } else if ("SEMANTIC".equalsIgnoreCase(intent)) {
            resp.setRoute("RAG");
        } else {
            resp.setRoute("MIXED");
        }

        return resp;
    }
}
