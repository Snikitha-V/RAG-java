package org.example.controller;

import org.example.RetrievalService;
import org.example.QueryResult;
import org.example.Config;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

import java.util.Map;
import java.util.HashMap;

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

    // Basic query endpoint: POST { "query": "..." }
    @PostMapping("/query")
    public ResponseEntity<?> query(
            @RequestHeader(value = "x-api-key", required = false) String apiKey,
            @RequestBody Map<String,String> body) {
        
        // Check API key
        ResponseEntity<?> authError = validateApiKey(apiKey);
        if (authError != null) return authError;
        try {
            String q = body.getOrDefault("query", "");
            
            // Call retrieval service with full pipeline
            QueryResult result = retrievalService.askWithMetadata(q);
            
            // Build structured response
            Map<String, Object> payload = new HashMap<>();
            payload.put("answer", result.getAnswer());
            payload.put("sources", result.getSources());
            payload.put("retrieval_chain", result.getRetrievalChain());
            payload.put("confidence", result.getConfidence());
            
            return ResponseEntity.ok(payload);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() {
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}

