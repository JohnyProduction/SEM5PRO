package com.projektsemv.clubmanagement.fan;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.UserFunction.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.FAN;

public class TicketsPanelControllerFan implements Initializable {

    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut, buyTicketButton;

    @FXML
    private ListView<Tickets> messagesList;
    @FXML
    private TextArea messagePreview, messageTextArena;
    @FXML
    private TextField recipientUsername;
    @FXML
    private ChoiceBox<String> roleChoiceBox;
    @FXML
    private Label username, roleLabel, messageLabel, recipientUsernameLabel;
    @FXML
    private Label resultsClub1,resultsClub2,resultsDateOfMatch;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TicketsPanelControllerFan.ReadFromServer = Client.ReadFromServer;
        TicketsPanelControllerFan.SendToServer = Client.SendToServer;

        preparePage();

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
                String selectedItem = String.valueOf(messagesList.getSelectionModel().getSelectedItem());
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
    private void preparePage() {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Perform time-consuming operations (e.g., reading from the server) here
                    message.sendGetFanTickets(SendToServer);
                    String serverResponse = ReadFromServer.readLine();
                    // Update the UI on the JavaFX application thread
                    Platform.runLater(() -> username.setText(serverResponse));
                    String incomingResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {

                        // Split the received data into an array of values
                        String[] values = incomingResponse.split("\\|");
                        System.out.println(incomingResponse);
                        if(values[0].equals("INCOMING")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 3) {
                                resultsClub1.setText(values[1]);
                                resultsClub2.setText(values[2]);
                                resultsDateOfMatch.setText(values[3]);
                            }
                        }else{
                            System.out.println("Error getting user incoming match data");
                        }
                    });
                    String ticketsResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {

                        // Split the received data into an array of values
                        String[] values = ticketsResponse.split("\\|");
                        System.out.println(ticketsResponse);
                        if(values[0].equals("TICKETS")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 4) {
                                ObservableList<Tickets> tickets = FXCollections.observableArrayList();
                                for (int i = 1; i+3 < values.length; i += 4) {
                                    tickets.add(new Tickets(Integer.parseInt(values[i]), values[i + 1],values[i+2],values[i+3]));
                                }
                                messagesList.setItems(tickets);
                            }
                        }else{
                            System.out.println("Error getting user tickets data");
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
