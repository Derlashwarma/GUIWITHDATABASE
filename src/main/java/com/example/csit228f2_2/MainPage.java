package com.example.csit228f2_2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    private static String username;
    @FXML
    Label welcome_message;
    private static Scene scene;
    private static Stage stage;
    @FXML
    Button return_login;
    @FXML
    HBox welcome_Hbox;
    @FXML
    Label username_box;
    @FXML
    VBox main_page_container;

    public static void setUsername(String username){
        MainPage.username = username;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String initial_message = welcome_message.getText();
        welcome_message.setText(initial_message+username);
        return_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    Stage stage = (Stage)return_login.getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("login-page.fxml"))));
                    stage.show();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        ResultSet resultSet;
        try(Connection connection = MYSQLConnection.getConnection()) {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users;";
            resultSet = statement.executeQuery(query);
            int counter = 1;
            while(resultSet.next()) {
                Label label = new Label(resultSet.getString("username").toString());
                HBox hbox = new HBox();
                hbox.getChildren().add(label);
                Button button = new Button("This is the Button for user with user id " + resultSet.getInt("id"));
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("Hello");
                        //RemoveUser(resultSet.getInt("id"));
                    }
                });
                hbox.getChildren().add(button);
                main_page_container.getChildren().add(hbox);
                counter++;
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
