package com.example.csit228f2_2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPage implements Initializable {
    @FXML
    Button forget_password;
    @FXML
    Button login_btn;
    @FXML
    Button register_btn;
    @FXML
    TextField username_login;
    @FXML
    PasswordField password_login;
    @FXML
    Label error_message;
    @FXML
    Button show_password_btn;
    @FXML
    HBox password_container;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextField textField = new TextField();
        textField.setVisible(false);

        show_password_btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                textField.setVisible(true);
                textField.setText(password_login.getText());
                password_container.getChildren().remove(password_login);
                textField.setPrefWidth(291);
                password_container.getChildren().add(textField);
            }
        });
        show_password_btn.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                password_container.getChildren().add(password_login);
                password_container.getChildren().remove(textField);
            }
        });

        forget_password.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("ForgotPassword.fxml"));
                    Stage stage = (Stage) forget_password.getScene().getWindow();

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        login_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String username = username_login.getText().toString();
                String password = password_login.getText().toString();
                String login = Login.Login(username,password);
                if(login == null){
                    error_message.setText("Login Failed");
                }
                else {
                    try {
                        MainPage.setUsername(username);
                        Parent root = FXMLLoader.load(getClass().getResource("main-page.fxml"));
                        Stage stage = (Stage) forget_password.getScene().getWindow();

                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        register_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
                    Stage stage = (Stage) forget_password.getScene().getWindow();

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
