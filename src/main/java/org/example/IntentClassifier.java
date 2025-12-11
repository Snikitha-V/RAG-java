package org.example;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Tiny heuristic intent classifier for Level-7 routing.
 * Returns FACTUAL, SEMANTIC, or MIXED.
 */
public class IntentClassifier {

    private static final Pattern FACTUAL_PATTERN = Pattern.compile("\\b(when|date|how many|count|which|list|what\\s+is\\s+the\\s+date|earliest|latest|first|last|what.*courses|what.*topics|all\\s+courses|all\\s+topics)\\b", Pattern.CASE_INSENSITIVE);
    private static final Pattern SEMANTIC_PATTERN = Pattern.compile("\\b(why|explain|explanation|compare|recommend|pros|cons|benefit|how\\s+to)\\b", Pattern.CASE_INSENSITIVE);

    public enum Intent { FACTUAL, SEMANTIC, MIXED }

    public static Intent classify(String query) {
        if (query == null) return Intent.SEMANTIC;
        String q = query.trim().toLowerCase(Locale.ROOT);

        boolean factual = FACTUAL_PATTERN.matcher(q).find();
        boolean semantic = SEMANTIC_PATTERN.matcher(q).find();

        if (factual && semantic) return Intent.MIXED;
        if (factual) return Intent.FACTUAL;
        return Intent.SEMANTIC;
    }

    /**
     * Check if query is a simple greeting (no RAG needed).
     */
    public static boolean isGreeting(String query) {
        if (query == null) return false;
        String q = query.trim().toLowerCase(Locale.ROOT);
        return q.equals("hi") || q.equals("hello") || q.equals("hey") || q.equals("yo") 
            || q.equals("hi!") || q.equals("hello!") || q.equals("hey!") 
            || q.startsWith("hi ") || q.startsWith("hello ") || q.startsWith("hey ")
            || q.equals("good morning") || q.equals("good afternoon") || q.equals("good evening");
    }
}
