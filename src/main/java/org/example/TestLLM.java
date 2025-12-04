package org.example;

/**
 * Quick test for LLMClient connection to llama-server.
 */
public class TestLLM {
    public static void main(String[] args) throws Exception {
        System.out.println("Testing LLM connection...");
        
        LLMClient llm = new LLMClient();
        
        // Use Mistral Instruct format: [INST] prompt [/INST]
        String prompt = "[INST] Hello! How can you help me today? [/INST]";
        String response = llm.generate(prompt, 50);
        
        System.out.println("LLM Response: " + response);
        System.out.println("\n✅ LLM is working!");
    }
}
