package com.example.csit228f2_2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ForgotPassword implements Initializable {
    @FXML
    Button return_login;
    @FXML
    private TextField forgotUsername;
    @FXML
    private PasswordField forgotPassword;
    @FXML
    Label reset_message;
    @FXML
    Button reset_btn;

    public static Scene scene;

    public static void getLogInScene(Scene scene) {
        ForgotPassword.scene = scene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        return_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) reset_message.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        });

        reset_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String username = forgotUsername.getText();
                String password = forgotPassword.getText();
                if(username.isEmpty() || password.isEmpty()){
                    reset_message.setText("Please do not leave field/s blank");
                    return;
                }
                try(Connection conn = MYSQLConnection.getConnection()){
                    String searchUsernameQuery = "SELECT * FROM users WHERE username = ?";
                    PreparedStatement searchUsername = conn.prepareStatement(searchUsernameQuery);
                    searchUsername.setString(1,username);

                    ResultSet resultSet = searchUsername.executeQuery();
                    if(resultSet.next()){
                        String updatePasswordQuery = "UPDATE users SET password = ? WHERE username = ?";
                        PreparedStatement updateStatement = conn.prepareStatement(updatePasswordQuery);
                        updateStatement.setString(1,password);
                        updateStatement.setString(2,username);

                        int updateResult = updateStatement.executeUpdate();
                        if(updateResult > 0){
                            reset_message.setText("Password Change Successful");
                        }
                    }
                    else{
                        reset_message.setText("No username found");
                    }

                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
