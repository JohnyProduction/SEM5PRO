package com.projektsemv.clubmanagement.manager;

import com.projektsemv.clubmanagement.ChangeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.*;

public class SettingsPageControllerManager implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut, settingsEditButton, settingsDeleteButton;
    @FXML
    TextField settingsUsername, settingsName, settingsSurname, settingsPassword, settingsEmail;
    @FXML
    ChoiceBox leagueChoiceBox, clubChoiceBox;
    @FXML
    private Label username;
    ObservableList<String> teamsChoice = FXCollections.observableArrayList("FCA", "FCB", "FCC"); //Przykładowa lista klubów, aby zaprezentować działanie choiceBoxów
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leagueChoiceBox.setItems(teamsChoice);
        clubChoiceBox.setItems(teamsChoice);
        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "login-panel.fxml", "Panel logowania", null);
            }
        });
        buttonOption1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "club-page-manager.fxml", "Strona klubu", MANAGER);
            }
        });
        buttonOption2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "list-of-users-manager.fxml", "Lista użytkowników", MANAGER);
            }
        });
        buttonOption3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "messages-panel-manager.fxml", "Wiadomości", MANAGER);
            }
        });

    }
}