package com.projektsemv.clubmanagement.fan;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.Message;
import com.projektsemv.clubmanagement.UserFunction.News;
import com.projektsemv.clubmanagement.member.MessagesPanelControllerMember;
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

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.FAN;

public class MessagesPanelControllerFan implements Initializable {

    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut;
    @FXML
    private ListView<News> messagesList;
    @FXML
    private TextArea messagePreview, messageTextArena;
    @FXML
    private Label username;

    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MessagesPanelControllerFan.ReadFromServer = Client.ReadFromServer;
        MessagesPanelControllerFan.SendToServer = Client.SendToServer;
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
                String selectedItem = String.valueOf(messagesList.getSelectionModel().getSelectedItem());
                messagePreview.setText(selectedItem);
                messagePreview.setEditable(false);
            }
        });

    }
    private void preparePage() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {

                    // Perform time-consuming operations (e.g., reading from the server) here
                    //String serverResponse = ReadFromServer.readLine();

                    message.sendGetNewsPage(SendToServer, "FAN");
                    //System.out.println(serverResponse);
                    Platform.runLater(() -> username.setText("test"));
                    // Update the UI on the JavaFX application thread
                    String newsResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = newsResponse.split("\\|");
                        //System.out.println(newsResponse);
                        if(values[0].equals("NEWS")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 2) {
                                // Set values to the respective labels
                                ObservableList<News> messages = FXCollections.observableArrayList();
                                for(int i=1;i<values.length;i+=2){
                                    messages.add(new News(values[i],values[i+1]));
                                }
                                messagesList.setItems(messages);
                                // Handle the case where there are not enough values
                            }
                            else {
                                System.out.println("Invalid data received from the server");
                            }
                        }else{
                            System.out.println("Error getting news data");
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
