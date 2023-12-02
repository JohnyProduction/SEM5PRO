package com.projektsemv.clubmanagement.manager;

import com.projektsemv.clubmanagement.ChangeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserInfo.UserType.*;

public class MessagesPanelController implements Initializable {

    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut;

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
            "Admin1" + " ┃ " + "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit ameta." ,
            "Marek05" + " ┃ " + "Nam tempor consectetur diam, non blandit leo lacinia interdum. " ,
            "Antek11" + " ┃ " + "Morbi condimentum dictum neque, ac elementum justo efficitur non. " ,
            "Piłkarz18" + " ┃ " + "Aenean aliquam cursus sem congue efficitur. Vestibulum rhoncus tristique" ,
            "Piłkarz20" + " ┃ " + " mollis. Praesent lacinia arcu in massa faucibus faucibus. " ,
            "Szefuńvcio12" + " ┃ " + "Donec nibh tortor, lacinia sit amet orci at, " ,
            "Kibic1" + " ┃ " + "iaculis condimentum est. Donec gravida ultrices diam a aliquet. "
    );
    ObservableList<String> roles = FXCollections.observableArrayList("Właściciel", "Gracz", "Kibic");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messagesList.setItems(messages);
        roleChoiceBox.setItems(roles);
        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "login-panel.fxml", "Panel logowania", MANAGER);
            }
        });
        buttonOption1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "manager-club-page.fxml", "Strona klubu", MANAGER);
            }
        });
        buttonOption2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "manager-list-of-users.fxml", "Lista użytkowników", MANAGER);
            }
        });
        messagesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Pobieranie do textArea zaznaczonego elementu
                String selectedItem = messagesList.getSelectionModel().getSelectedItem();
                messagePreview.setText(selectedItem);
                messagePreview.setEditable(false);
            }
        });
        //Ukryj etykietę po wybraniu opcji
        roleChoiceBox.setOnAction(event -> {
            roleLabel.setVisible(false);
        });
        //Ukryj etykietę po kliknięciu
        messageTextArena.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                messageLabel.setVisible(false); // Ukryj etykietę po kliknięciu w obszar tekstowy
                messageTextArena.setEditable(true);
            }else{
                messageTextArena.setEditable(false);
            }
            event.consume(); //zdarzenie obsłużone
        });

        recipientUsername.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                recipientUsername.setEditable(true);
                recipientUsernameLabel.setVisible(false);
            }else{
                recipientUsername.setEditable(false);
            }
            event.consume();
        });

    }
}
