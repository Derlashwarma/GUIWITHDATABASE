package com.example.csit228f2_2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class HelloController {
    public ToggleButton tbNight;
    @FXML
    private Label welcomeText;

    @FXML
    private void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void onNightModeClick() {
        if (tbNight.isSelected()) {
            // night mode
            tbNight.getScene().getStylesheets().add(
                    getClass().getResource("styles.css").toExternalForm());
        } else {
            tbNight.getScene().getStylesheets().clear();
        }
    }


}