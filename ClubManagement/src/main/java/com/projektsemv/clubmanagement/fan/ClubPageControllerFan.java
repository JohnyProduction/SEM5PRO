package com.projektsemv.clubmanagement.fan;

import com.projektsemv.clubmanagement.ChangeController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.FAN;

public class ClubPageControllerFan implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut;
    @FXML
    private Label username, labelNameOfClub, labelLeague, labelManagerName, labelPhoneNumber, labelAddress, labelFinance, labelResultStats;

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
                ChangeController.changeScene(actionEvent, "tickets-panel-fan.fxml", "Lista biletów", FAN);
            }
        });
        buttonOption3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "messages-panel-fan.fxml", "Wiadomości", FAN);
            }
        });
        buttonOptions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "settings-page-fan.fxml", "Ustawienia", FAN);
            }
        });
//        labelFinance.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                ChangeController.changeSceneLabel(mouseEvent, "finances-stats-manager.fxml", "Finanse", FAN);
//            }
//        });
        labelResultStats.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ChangeController.changeSceneLabel(mouseEvent, "club-stats-fan.fxml", "Statystyki klubowe", FAN);
            }
        });
    }
}