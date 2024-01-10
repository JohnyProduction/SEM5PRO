package com.projektsemv.clubmanagement.fan;

import com.projektsemv.clubmanagement.ChangeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.FAN;

public class TicketsPanelControllerFan implements Initializable {

    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut, buyTicketButton;

    @FXML
    private ListView<String> messagesList;
    @FXML
    private TextArea messagePreview, messageTextArena;
    @FXML
    private TextField recipientUsername;
    @FXML
    private ChoiceBox<String> roleChoiceBox;
    @FXML
    private Label username, roleLabel, messageLabel, recipientUsernameLabel;
    ObservableList<String> messages = FXCollections.observableArrayList(
            "1" + " ┃ " + "2023-12-19" + " ┃ " + "50.00 PLN" + " ┃ " + "Asseco Resovia Rzeszów vs Zaksa Kędzierzyn-Koźle",
            "2" + " ┃ " + "2023-11-19" + " ┃ " + "40.00 PLN" + " ┃ " + "Asseco Resovia Rzeszów vs ZAKSA Strzelce Opolskie",
            "3" + " ┃ " + "2023-9-9" + " ┃ " + "45.00 PLN" + " ┃ " + "Asseco Resovia Rzeszów vs Trefl Gdańsk",
            "4" + " ┃ " + "2023-12-12" + " ┃ " + "40.00 PLN" + " ┃ " + "Asseco Resovia Rzeszów vs Indykpol AZS Olsztyn",
            "5" + " ┃ " + "2023-03-19" + " ┃ " + "45.00 PLN" + " ┃ " + "Asseco Resovia Rzeszów vs Jastrzębski Węgiel",
            "6" + " ┃ " + "2023-02-09" + " ┃ " + "55.00 PLN" + " ┃ " + "Asseco Resovia Rzeszów vs PGE Skra Bełchatów",
            "7" + " ┃ " + "2023-01-11" + " ┃ " + "60.00 PLN" + " ┃ " + "Asseco Resovia Rzeszów vs ZAKSA Strzelce Opolskie"
    );
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messagesList.setItems(messages);
        //roleChoiceBox.setItems(roles);
        buttonOption1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "club-page-fan.fxml", "Strona klubu", FAN);
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
        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "login-panel.fxml", "Panel logowania", null);
            }
        });
        messagesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Pobieranie do textArea zaznaczonego elementu
                String selectedItem = messagesList.getSelectionModel().getSelectedItem();
                messagePreview.setText("\n" + selectedItem);
                messagePreview.setEditable(false);
            }
        });
        buyTicketButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "buy-ticket-panel-fan.fxml", "Kupno biletów", FAN);
            }
        });


    }
}
