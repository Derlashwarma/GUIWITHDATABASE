package com.example.csit228f2_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertRecord {
    public static String insertRecord(String record, int user_id) {
        try(Connection connection = MYSQLConnection.getConnection()) {
            connection.setAutoCommit(false);
            String insert_query = "INSERT INTO user_logs(user_id, log) VALUES(?,?);";
            PreparedStatement statement = connection.prepareStatement(insert_query);
            statement.setInt(1,user_id);
            statement.setString(2,record);
            int result_query = statement.executeUpdate();
            if(result_query > 0) {
                connection.commit();
                return "Inserted Successfully";
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return "SQL EXCEPTION HAS OCCURED";
        }
        return "Insert Failed";
    }
}
