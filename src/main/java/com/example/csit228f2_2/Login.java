package com.example.csit228f2_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    public static String Login(String username, String password) {
        try (Connection connection = MYSQLConnection.getConnection()) {
            connection.setAutoCommit(false);
            String login_query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(login_query)) {
                statement.setString(1, username);
                statement.setString(2, password);

                String res = null;
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        res = result.getString("username");
                    } else {
                        res = null;
                    }
                    connection.commit();
                    return res;
                }
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
