package com.example.csit228f2_2;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class MYSQLConnection {

    private static String url = "jdbc:mysql://localhost:3306/javadb";
    private static String username ="ArdelTiocoJeffLauron";
    private static String password = "123456";
    public static Connection getConnection(){
        Connection connection = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);
            System.out.println("CONNECTED TO DATABASE");

        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
        catch (SQLException e){
            e.getMessage();
        }
        return connection;
    }

}
