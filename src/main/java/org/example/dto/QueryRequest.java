package org.example.dto;

/**
 * Canonical request DTO for the /api/v1/query endpoint.
 */
public class QueryRequest {

    private String query;

    public QueryRequest() {}

    public QueryRequest(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
