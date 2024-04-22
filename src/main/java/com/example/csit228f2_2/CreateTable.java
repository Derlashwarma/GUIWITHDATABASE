package com.example.csit228f2_2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void create_users_table() {
        try(Connection connection = MYSQLConnection.getConnection()){
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            int res = statement.executeUpdate("CREATE TABLE IF NOT EXISTS users" +
                    "(id INT PRIMARY KEY AUTO_INCREMENT," +
                    " username VARCHAR(55) NOT NULL," +
                    " password VARCHAR(55) NOT NULL)");
            if(res > 0) {
                System.out.println("ADDED A NEW TABLE");
            }
            else {
                System.out.println("TABLE ALREADY EXIST");
            }
            connection.commit();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
