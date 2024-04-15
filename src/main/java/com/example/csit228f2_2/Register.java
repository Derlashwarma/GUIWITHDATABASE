package com.example.csit228f2_2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class Register implements Initializable {
    @FXML
    Button back_to_login_btn;
    @FXML
    Button registerAccount;

    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label error_message;

    public static Scene scene;

    public static void getLogInScene(Scene scene){
        Register.scene = scene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        back_to_login_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) password.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        });
        registerAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String u = username.getText();
                String p = password.getText();

                if(u.isEmpty() || p.isEmpty()){
                    error_message.setText("Please Enter Valid Input");
                    return;
                }

                try (Connection connection = MYSQLConnection.getConnection()) {
                    String search_username_query = "SELECT * FROM users WHERE username = ?";
                    PreparedStatement check_username = connection.prepareStatement(search_username_query);
                    check_username.setString(1, u);
                    ResultSet rs = check_username.executeQuery();

                    if (rs.next()) {
                        error_message.setText("Username is already taken");
                    } else {
                        String registerQuery = "INSERT INTO users(username, password) VALUES (?, ?)";
                        PreparedStatement register = connection.prepareStatement(registerQuery);
                        register.setString(1, u);
                        register.setString(2, p);

                        int register_res = register.executeUpdate();
                        if (register_res > 0) {
                            error_message.setText("Registration Successful");
                        }
                    }
                } catch (SQLException e) {}
            }
        });
    }

}
