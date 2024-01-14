package com.projektsemv.clubmanagement.fan;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.UserFunction.BuyTickets;
import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.Message;
import com.projektsemv.clubmanagement.UserFunction.Tickets;
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

public class BuyTicketPanelControllerFan implements Initializable {

    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut, buyTicketButton;

    @FXML
    private ListView<BuyTickets> messagesList;
    @FXML
    private TextArea messagePreview;
    @FXML
    private Label username;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BuyTicketPanelControllerFan.ReadFromServer = Client.ReadFromServer;
        BuyTicketPanelControllerFan.SendToServer = Client.SendToServer;

        preparePage();
        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "login-panel.fxml", "Panel logowania", null);
            }
        });
        buttonOption1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "club-page-fan.fxml", "Strona klubu", FAN);
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
        messagesList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Pobieranie do textArea zaznaczonego elementu
                String selectedItem =String.valueOf(messagesList.getSelectionModel().getSelectedItem());
                messagePreview.setText(selectedItem);
                messagePreview.setEditable(false);
            }
        });
        buyTicketButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Get the selected item from messagesList
                BuyTickets selectedBuyTicket = messagesList.getSelectionModel().getSelectedItem();

                if (selectedBuyTicket != null) {
                    String matchID = selectedBuyTicket.getMatchID();
                    //System.out.println("Selected MatchID: " + matchID);
                    message.sendBuyTickets(SendToServer,matchID);
                    // Now you can use the matchID as needed
                    // For example, you can pass it to another method or use it in your logic
                } else {
                    // No item selected, handle accordingly
                    System.out.println("No item selected");
                }
                ChangeController.changeScene(actionEvent, "tickets-panel-fan.fxml", "Ustawienia", FAN);
            }
        });

    }
    private void preparePage() {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Perform time-consuming operations (e.g., reading from the server) here
                    message.sendGetBuyTicketsPage(SendToServer);
                    String serverResponse = ReadFromServer.readLine();
                    // Update the UI on the JavaFX application thread
                    Platform.runLater(() -> username.setText(serverResponse));
                    String ticketsResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {

                        // Split the received data into an array of values
                        String[] values = ticketsResponse.split("\\|");
                        System.out.println(ticketsResponse);
                        if(values[0].equals("BUYTICKETLIST")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 4) {
                                ObservableList<BuyTickets> tickets = FXCollections.observableArrayList();
                                for (int i = 1; i+3 < values.length; i += 4) {
                                    tickets.add(new BuyTickets((values[i]), values[i + 1],values[i+2],values[i+3]));
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
