package com.projektsemv.clubmanagement.member;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.UserFunction.SocketClient;
import com.projektsemv.clubmanagement.UserFunction.UserFunctions;
import com.projektsemv.clubmanagement.UserFunction.UserInfo.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.MEMBER;

public class ClubPageControllerMember implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut;
    @FXML
    private Label username, labelNameOfClub, labelLeague, labelManagerName, labelPhoneNumber, labelAddress, labelFinance, labelMoreStats;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SocketClient socketClient = SocketClient.getInstance("localhost", 12345);
        System.out.println(socketClient.receiveMessage());

        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "login-panel.fxml", "Panel logowania", null);
            }
        });
        buttonOption3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "messages-panel-member.fxml", "Wiadomości", MEMBER);
            }
        });
        buttonOptions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "settings-page-member.fxml", "Ustawienia", MEMBER);
            }
        });
        labelMoreStats.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ChangeController.changeSceneLabel(mouseEvent, "club-stats-member.fxml", "Statystyki klubowe", MEMBER);
            }
        });
    }
}