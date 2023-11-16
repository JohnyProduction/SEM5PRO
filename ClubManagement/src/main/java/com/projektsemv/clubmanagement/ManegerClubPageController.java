package com.projektsemv.clubmanagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class ManegerClubPageController implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "login-panel.fxml", "Panel logowania", null);
            }
        });

        buttonOption2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "maneger-list-of-users.fxml", "Lista użytkowników", null);
            }
        });
    }
}