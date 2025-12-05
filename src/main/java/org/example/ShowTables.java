package org.example;

import java.sql.*;

public class ShowTables {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/learning_db";
        String user = "postgres";
        String pass = System.getenv("DB_PASS");
        
        try (Connection c = DriverManager.getConnection(url, user, pass)) {
            System.out.println("=== SAMPLE DATA ===");
            
            // Show topics with their codes
            System.out.println("\n--- topics (first 5) ---");
            ResultSet rs = c.createStatement().executeQuery("SELECT id, code, title FROM topics LIMIT 5");
            while (rs.next()) {
                System.out.println("  id=" + rs.getInt("id") + " code=" + rs.getString("code") + " title=" + rs.getString("title"));
            }
            
            // Show classes with topic_id
            System.out.println("\n--- classes (first 5) ---");
            rs = c.createStatement().executeQuery("SELECT id, topic_id, title, learned_at FROM classes LIMIT 5");
            while (rs.next()) {
                System.out.println("  id=" + rs.getInt("id") + " topic_id=" + rs.getInt("topic_id") + " title=" + rs.getString("title") + " learned_at=" + rs.getTimestamp("learned_at"));
            }
            
            // Count
            rs = c.createStatement().executeQuery("SELECT COUNT(*) FROM classes");
            rs.next();
            System.out.println("\nTotal classes: " + rs.getInt(1));
        }
    }
}
