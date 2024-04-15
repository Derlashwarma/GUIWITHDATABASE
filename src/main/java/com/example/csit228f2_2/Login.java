package com.example.csit228f2_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    public static String Login(String username, String password) {
        try(Connection connection = MYSQLConnection.getConnection()){
            String login_query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(login_query);
            statement.setString(1,username);
            statement.setString(2,password);

            ResultSet result = statement.executeQuery();
            try{
                result.next();
                int id = result.getInt("id");
                if(id != 0){
                    return result.getString("username");
                }
            } catch (SQLException e) {
                return "Login Failed";
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "Login Failed";
    }
}
