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
    public static void create_user_logs() {
        try(Connection connection = MYSQLConnection.getConnection()){
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String create_user_image_table_query = "CREATE TABLE user_logs" +
                    "(log_id INT NOT NULL AUTO_INCREMENT ," +
                    " user_id INT NOT NULL , " +
                    "log VARCHAR(500) NOT NULL , " +
                    "is_active BOOLEAN NOT NULL DEFAULT TRUE , " +
                    "PRIMARY KEY (log_id)) ENGINE = InnoDB;";
            int create_result = statement.executeUpdate(create_user_image_table_query);

            if(create_result > 1) {
                System.out.println("TABLE CREATED SUCCESSFULLY");
                connection.commit();
            }
        }
        catch (SQLException e){
            System.out.println("TABLE HAS ALREADY EXISTED");
        }
    }
}
