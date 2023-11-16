package com.projektsemv.clubmanagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ManegerListOfUsersController implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOption4, buttonOptions, buttonLogOut;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "login-panel.fxml", "Panel logowania", null);
            }
        });
        buttonOption1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "maneger-club-page.fxml", "Strona klubu", null);
            }
        });

    }
}