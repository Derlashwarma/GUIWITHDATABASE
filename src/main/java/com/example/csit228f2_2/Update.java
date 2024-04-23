package com.example.csit228f2_2;

import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {
    public static String updateLog(TextArea textArea, int log_id) {
        try(Connection connection = MYSQLConnection.getConnection()) {
            connection.setAutoCommit(false);
            String newText = textArea.getText().toString();
            String update_query = "UPDATE user_logs SET log = ? WHERE log_id = ?";
            PreparedStatement statement = connection.prepareStatement(update_query);
            statement.setString(1,newText);
            statement.setInt(2,log_id);
            int result = statement.executeUpdate();
            if(result > 0) {
                connection.commit();
                return "success";
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return "Update Unsuccessful";
    }
}
