package com.example.csit228f2_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class HelloApplication extends Application{

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent loader = FXMLLoader.load(getClass().getResource("login-page.fxml"));
        Scene scene = new Scene(loader);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        CreateTable.create_users_table();
        CreateTable.create_user_logs();
    }
}