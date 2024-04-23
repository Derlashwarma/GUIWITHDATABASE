package com.example.csit228f2_2;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class MYSQLConnection {

    private static String url = "jdbc:mysql://localhost:3306/laurondb";
    private static String username ="root";
    private static String password = "";
    public static Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);
            System.out.println("CONNECTED TO DATABASE");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public static int getUserId(String username) {
        try(Connection conn = getConnection()) {
            String query = "SELECT id FROM users WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1,username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    return -1;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
