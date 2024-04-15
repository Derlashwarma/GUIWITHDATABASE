package com.example.csit228f2_2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

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
    Button delete_user;
    @FXML
    TextField delete_username;
    @FXML
    Label delete_status;

    public static void setUsername(String username){
        MainPage.username = username;
    }
    public static void setLogInScene(Scene scene, Stage stage){
        MainPage.scene = scene;
        MainPage.stage = stage;
        System.out.println(MainPage.scene.toString());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String initial_message = welcome_message.getText();
        welcome_message.setText(initial_message+username);

        return_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.setScene(scene);
                stage.show();
            }
        });
        try(Connection connection = MYSQLConnection.getConnection()){
            String show_query = "SELECT * FROM users";
            PreparedStatement show_statement = connection.prepareStatement(show_query);

            ResultSet result = show_statement.executeQuery();

            String usernames = "";
            int count = 0;
            while (result.next()) {
                String username = result.getString("username");
                int id = result.getInt("id");
                usernames = usernames + "\t" + "ID: " + id + " " + username;
                if (count % 4 == 3) {
                    usernames += "\n";
                }
                count++;
            }
            username_box.setText(usernames);
            delete_user.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String toDelete = delete_username.getText();
                    String deleteQuery = "DELETE FROM users WHERE username = ?";
                    try (Connection conn = MYSQLConnection.getConnection();PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery)) {
                        deleteStatement.setString(1, toDelete);
                        int rowsAffected = deleteStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            delete_status.setText("Delete Successful (needs refresh to see changes)");
                        } else {
                            delete_status.setText("No users found");
                        }
                    } catch (SQLException e) {
                        delete_status.setText("wala ko kahibaw asa ko nag kuwang");
                    }
                }
            });

        }catch (SQLException e){}
    }
}
