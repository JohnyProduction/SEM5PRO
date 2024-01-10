package com.projektsemv.clubmanagement.member;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.Match;
import com.projektsemv.clubmanagement.UserFunction.Message;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.MEMBER;

public class SettingsPageControllerMember implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut, settingsEditButton, settingsDeleteButton;
    @FXML
    TextField settingsUsername, settingsName, settingsSurname, settingsPassword, settingsEmail;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    @FXML
    private Label username;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SettingsPageControllerMember.ReadFromServer = Client.ReadFromServer;
        SettingsPageControllerMember.SendToServer = Client.SendToServer;

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
                ChangeController.changeScene(actionEvent, "club-page-member.fxml", "Strona klubu", MEMBER);
            }
        });
        buttonOption2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "list-of-users-member.fxml", "Lista użytkowników", MEMBER);
            }
        });
        buttonOption3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "messages-panel-member.fxml", "Wiadomości", MEMBER);
            }
        });


    }
    private void preparePage() {
        settingsUsername = new TextField();
        settingsName= new TextField();
        settingsSurname= new TextField();
        settingsPassword= new TextField();
        settingsEmail= new TextField();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Perform time-consuming operations (e.g., reading from the server) here
                    message.sendGetSettings(SendToServer);
                    String serverResponse = ReadFromServer.readLine();
                    //System.out.println(serverResponse);

                    // Update the UI on the JavaFX application thread
                    Platform.runLater(() -> username.setText(serverResponse));
                    String settingsResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        System.out.println(settingsResponse);
                        // Split the received data into an array of values
                        String[] values = settingsResponse.split("\\|");
                        if(values[0].equals("USERSETTINGS")){
                            System.out.println(values);
                            // Check if there are enough values to fill the labels
                            if (values.length >= 4) {
                                settingsUsername.setText(values[1]);
                                settingsName.setText(values[2]);
                                settingsSurname.setText(values[3]);
                                settingsPassword.setText(values[4]);
                                settingsEmail.setText(values[5]);
                            } else {
                                // Handle the case where there are not enough values
                                System.out.println("Invalid data received from the server");
                            }
                        }else{
                            System.out.println("Error getting match table data");
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