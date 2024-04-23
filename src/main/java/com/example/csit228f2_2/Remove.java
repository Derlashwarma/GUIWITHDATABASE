package com.example.csit228f2_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Remove {
    public static String removeLog(int id) {
        try(Connection connection = MYSQLConnection.getConnection()){
            connection.setAutoCommit(false);
            String remove_query = "UPDATE user_logs SET is_active = 0 WHERE log_id = ?";
            PreparedStatement statement = connection.prepareStatement(remove_query);
            statement.setInt(1,id);
            int reslt_query = statement.executeUpdate();

            if(reslt_query > 0) {
                connection.commit();
                return "Delete successful";
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
            return "Error has occured";
        }
        return "User not found";
    }
}
