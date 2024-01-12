package com.projektsemv.clubmanagement.manager;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.Message;
import com.projektsemv.clubmanagement.member.SettingsPageControllerMember;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.*;

public class SettingsPageControllerManager implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut, settingsEditButton;
    @FXML
    TextField settingsUsername, settingsName, settingsSurname, settingsPassword, settingsEmail;
    @FXML
    private Label username;
    private int userID;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SettingsPageControllerManager.ReadFromServer = Client.ReadFromServer;
        SettingsPageControllerManager.SendToServer = Client.SendToServer;
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
        settingsEditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                message.sendUpdateSettingsUser(SendToServer,String.valueOf(userID),settingsUsername.getText(),settingsName.getText(),settingsSurname.getText(),settingsPassword.getText(),settingsEmail.getText());

                ChangeController.changeScene(actionEvent, "settings-page-manager.fxml", "Ustawienia", MANAGER);
            }
        });

    }
    private void preparePage() {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Perform time-consuming operations (e.g., reading from the server) here
                    message.sendGetSettings(SendToServer);
                    String serverResponse = ReadFromServer.readLine();
                    // Update the UI on the JavaFX application thread
                    Platform.runLater(() -> username.setText(serverResponse));
                    String settingsResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {

                        // Split the received data into an array of values
                        String[] values = settingsResponse.split("\\|");
                        System.out.println(settingsResponse);
                        if(values[0].equals("USERSETTINGS")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 4) {
                                for (int i=1;i<values.length;i++){
                                    userID = Integer.parseInt(values[1]);
                                    settingsUsername.setText(values[2]);
                                    settingsName.setText(values[3]);
                                    settingsSurname.setText(values[4]);
                                    settingsPassword.setText(values[5]);
                                    settingsEmail.setText(values[6]);
                                }
                            }
                        }else{
                            System.out.println("Error getting user settings data");
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