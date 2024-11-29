package com.example.webapp1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


// IF SMTH BROKE
public class DatabaseUpdater {
    public static void main(String[] args) {
        //Example
        String url = "jdbc:postgresql://localhost:5432/usersdb";
        String user = "postgres";
        String password = "123";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("ALTER TABLE user_comment ALTER COLUMN id SET DATA TYPE bigint;\n" +
                    "ALTER TABLE user_comment_likes ALTER COLUMN user_comment_id SET DATA TYPE bigint;\n" +
                    "ALTER TABLE user_diary_posts ALTER COLUMN posts_id SET DATA TYPE bigint;\n" +
                    "ALTER TABLE user_post ALTER COLUMN id SET DATA TYPE bigint;\n" +
                    "ALTER TABLE user_post_comments ALTER COLUMN user_post_id SET DATA TYPE bigint;\n" +
                    "ALTER TABLE user_post_comments ALTER COLUMN comments_id SET DATA TYPE bigint;\n" +
                    "ALTER TABLE user_post_likes ALTER COLUMN user_post_id SET DATA TYPE bigint;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
