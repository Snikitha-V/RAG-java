package org.example.controller;

import org.example.RetrievalService;
import org.example.QueryResult;
import org.example.Config;
import org.example.dto.QueryRequest;
import org.example.dto.QueryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class QueryController {

    private final RetrievalService retrievalService;

    @Autowired
    public QueryController(RetrievalService retrievalService) {
        this.retrievalService = retrievalService;
    }

    /**
     * Validate API key if configured. Returns error response if invalid, null if OK.
     */
    private ResponseEntity<?> validateApiKey(String apiKeyHeader) {
        String expectedKey = Config.API_KEY;
        // If API_KEY not set, auth is disabled (dev mode)
        if (expectedKey == null || expectedKey.isBlank()) {
            return null; // auth disabled
        }
        if (apiKeyHeader == null || !apiKeyHeader.equals(expectedKey)) {
            return ResponseEntity.status(401).body(Map.of("error", "Unauthorized: Invalid or missing API key"));
        }
        return null; // valid
    }

    /**
     * Canonical query endpoint.
     * POST /api/v1/query
     * Request:  { "query": "..." }
     * Response: QueryResponse with answer, intent, route, confidence, latencyMs, sources, retrievalChain, sql
     */
    @PostMapping("/query")
    public ResponseEntity<?> query(
            @RequestHeader(value = "x-api-key", required = false) String apiKey,
            @RequestBody QueryRequest request) {

        // Check API key
        ResponseEntity<?> authError = validateApiKey(apiKey);
        if (authError != null) return authError;

        long start = System.currentTimeMillis();
        try {
            String q = request.getQuery();
            if (q == null || q.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("error", "query must not be blank"));
            }

            // Call retrieval service with full pipeline
            QueryResult result = retrievalService.askWithMetadata(q, request.getHistory());

            long latencyMs = System.currentTimeMillis() - start;

            // Build canonical response DTO
            QueryResponse response = QueryResponse.from(result, latencyMs);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            long latencyMs = System.currentTimeMillis() - start;
            return ResponseEntity.status(500).body(Map.of(
                    "error", e.getMessage(),
                    "latencyMs", latencyMs
            ));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}

