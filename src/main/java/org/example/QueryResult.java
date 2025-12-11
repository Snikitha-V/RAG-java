package org.example;

import java.util.List;
import java.util.Map;

/**
 * Structured response from the RAG pipeline containing answer and metadata.
 */
public class QueryResult {
    private String answer;
    private List<String> sources;           // chunk IDs used as context
    private String sql;                      // SQL query used (if any)
    private List<Map<String, Object>> retrievalChain;  // candidate IDs + scores
    private String confidence;               // low/medium/high
    private String intent;                   // FACTUAL/SEMANTIC/MIXED

    public QueryResult() {}

    public QueryResult(String answer) {
        this.answer = answer;
    }

    // Getters and setters
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public List<String> getSources() { return sources; }
    public void setSources(List<String> sources) { this.sources = sources; }

    public String getSql() { return sql; }
    public void setSql(String sql) { this.sql = sql; }

    public List<Map<String, Object>> getRetrievalChain() { return retrievalChain; }
    public void setRetrievalChain(List<Map<String, Object>> retrievalChain) { this.retrievalChain = retrievalChain; }

    public String getConfidence() { return confidence; }
    public void setConfidence(String confidence) { this.confidence = confidence; }

    public String getIntent() { return intent; }
    public void setIntent(String intent) { this.intent = intent; }
}
