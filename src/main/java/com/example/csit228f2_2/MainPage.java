package com.example.csit228f2_2;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MainPage implements Initializable {
    private static String username;
    private static int user_id;
    @FXML
    Label welcome_message;
    @FXML
    Button return_login;
    @FXML
    HBox welcome_Hbox;
    @FXML
    Label username_box;
    @FXML
    VBox main_page_container;
    @FXML
    Label status_box;
    @FXML
    TextArea log;
    @FXML
    Button insert_log_btn;
    public static void setUsername(String username){
        MainPage.username = username;
        MainPage.user_id = MYSQLConnection.getUserId(username);
        System.out.println("User ID is: " + user_id);
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
        insert_log_btn.setOnAction(event -> {
            String logText = log.getText();
            String insert_status = InsertRecord.insertRecord(logText,user_id);
            status_box.setText(insert_status);
            if(insert_status.contains("Successfully")){
                status_box.setTextFill(Paint.valueOf("green"));
            }
            else {
                status_box.setTextFill(Paint.valueOf("red"));
            }
            log.clear();
            reload(main_page_container, status_box);
        });

        reload(main_page_container,status_box);
    }
    private static void reload(VBox main_page_container, Label status_box) {
        main_page_container.getChildren().clear();
        try (Connection connection = MYSQLConnection.getConnection()) {
            String query = "SELECT * FROM user_logs WHERE user_id = ? AND is_active = 1;";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,user_id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    final String message = resultSet.getString("log");
                    final int log_id = resultSet.getInt("log_id");

                    Platform.runLater(() -> {
                        TextArea label = new TextArea(message);
                        label.setWrapText(true);
                        label.setPrefWidth(450);
                        label.prefHeightProperty().bind(label.heightProperty());

                        HBox hbox = new HBox();
                        Button remove_button = new Button("Del");
                        remove_button.setOnAction(event -> {
                            String res = Remove.removeLog(log_id);
                            status_box.setText(res);
                            if(res.contains("successful")) {
                                status_box.setTextFill(Paint.valueOf("green"));
                            }
                            else {
                                status_box.setTextFill(Paint.valueOf("red"));
                            }
                            reload(main_page_container,status_box);
                        });

                        Button update_button = new Button("Update");
                        update_button.setOnAction(event -> {
                            String res = Update.updateLog(label,log_id);
                            status_box.setText(res);
                            if(res.contains("success")) {
                                status_box.setTextFill(Paint.valueOf("green"));
                            }
                            else {
                                status_box.setTextFill(Paint.valueOf("red"));
                            }
                            reload(main_page_container, status_box);
                        });
                        hbox.setAlignment(Pos.CENTER);
                        hbox.setMargin(update_button,new Insets(5));
                        hbox.getChildren().addAll(label,update_button,remove_button);
                        main_page_container.getChildren().add(hbox);
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
