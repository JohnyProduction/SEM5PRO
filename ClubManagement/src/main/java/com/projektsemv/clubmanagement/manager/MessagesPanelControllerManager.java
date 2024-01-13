package com.projektsemv.clubmanagement.manager;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.Message;
import com.projektsemv.clubmanagement.UserFunction.Roles;
import com.projektsemv.clubmanagement.UserFunction.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.*;

public class MessagesPanelControllerManager implements Initializable {

    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut, sendButton;

    @FXML
    private ListView<String> messagesList;
    @FXML
    private TextArea messagePreview, messageTextArena;
    @FXML
    private ChoiceBox<Roles> roleChoiceBox;
    @FXML
    private Label username, roleLabel, messageLabel;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    ObservableList<String> messages = FXCollections.observableArrayList(
            "Admin1" + " ┃ " + "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem ipsum dolor sit ameta." ,
            "Marek05" + " ┃ " + "Nam tempor consectetur diam, non blandit leo lacinia interdum. " ,
            "Antek11" + " ┃ " + "Morbi condimentum dictum neque, ac elementum justo efficitur non. " ,
            "Piłkarz18" + " ┃ " + "Aenean aliquam cursus sem congue efficitur. Vestibulum rhoncus tristique" ,
            "Piłkarz20" + " ┃ " + " mollis. Praesent lacinia arcu in massa faucibus faucibus. " ,
            "Szefuńvcio12" + " ┃ " + "Donec nibh tortor, lacinia sit amet orci at, " ,
            "Kibic1" + " ┃ " + "iaculis condimentum est. Donec gravida ultrices diam a aliquet. "
    );
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MessagesPanelControllerManager.ReadFromServer = Client.ReadFromServer;
        MessagesPanelControllerManager.SendToServer = Client.SendToServer;

        messagesList.setItems(messages);

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
        buttonOptions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "settings-page-manager.fxml", "Ustawienia", MANAGER);
            }
        });
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                message.sendNews(SendToServer,messageTextArena.getText(),String.valueOf(roleChoiceBox.getSelectionModel().getSelectedItem()));
                ChangeController.changeScene(actionEvent, "messages-page-manager.fxml", "Wiadomości", MANAGER);
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

    }

    private void preparePage() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Perform time-consuming operations (e.g., reading from the server) here
                    message.sendUserListPage(SendToServer);
                    String serverResponse = ReadFromServer.readLine();

                    // Update the UI on the JavaFX application thread
                    Platform.runLater(() -> username.setText(serverResponse));
                    String userRolesResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = userRolesResponse.split("\\|");
                        if(values[0].equals("USERROLES")){
                            System.out.println(userRolesResponse);
                            // Check if there are enough values to fill the labels
                            if (values.length >= 5) {
                                // Set values to the respective labels
                                ObservableList<Roles> roles = FXCollections.observableArrayList();
                                for (int i = 1; i < values.length; i += 2) {
                                    roles.add(new Roles(Integer.parseInt(values[i]), values[i + 1]));
                                }
                                roleChoiceBox.setItems(roles);

                            } else {
                                // Handle the case where there are not enough values
                                System.out.println("Invalid data received from the server");
                            }
                        }else{
                            System.out.println("Error getting user roles data");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        // Start the task in a new thread
        new Thread(task).start();
    }
}
