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

            stmt.executeUpdate("ALTER TABLE user_post ADD COLUMN comment_count INT NOT NULL DEFAULT 0;");

            System.out.println("Столбец likes_count успешно добавлен.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
