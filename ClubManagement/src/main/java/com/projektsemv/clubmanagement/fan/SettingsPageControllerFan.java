package com.projektsemv.clubmanagement.fan;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.UserFunction.*;
import com.projektsemv.clubmanagement.manager.SettingsPageControllerManager;
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

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.FAN;

public class SettingsPageControllerFan implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut, settingsEditButton;
    @FXML
    TextField settingsUsername, settingsName, settingsSurname, settingsPassword, settingsEmail;
    @FXML
    ChoiceBox<League> leagueChoiceBox;
    @FXML
    ChoiceBox<Club> clubChoiceBox;
    @FXML
    private Label username;
    private int userID;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SettingsPageControllerFan.ReadFromServer = Client.ReadFromServer;
        SettingsPageControllerFan.SendToServer = Client.SendToServer;

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
        leagueChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                message.sendGetFanClubs(SendToServer,String.valueOf((leagueChoiceBox.getSelectionModel().getSelectedIndex()+1)));
            }
        });
        settingsEditButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                message.sendUpdateFanSettings(SendToServer,
                        String.valueOf(userID),
                        settingsUsername.getText(),
                        settingsName.getText(),
                        settingsSurname.getText(),
                        settingsPassword.getText(),
                        settingsEmail.getText(),
                        String.valueOf((leagueChoiceBox.getSelectionModel().getSelectedIndex()+1)),
                        String.valueOf((clubChoiceBox.getSelectionModel().getSelectedIndex()+1))
                        );
                ChangeController.changeScene(actionEvent, "settings-page-fan.fxml", "Ustawienia", FAN);
            }
        });

    }

    private void preparePage() {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Perform time-consuming operations (e.g., reading from the server) here
                    message.sendGetFanSettings(SendToServer);
                    String serverResponse = ReadFromServer.readLine();
                    // Update the UI on the JavaFX application thread
                    Platform.runLater(() -> username.setText(serverResponse));
                    String leaguesResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {

                        // Split the received data into an array of values
                        String[] values = leaguesResponse.split("\\|");
                        System.out.println(leaguesResponse);
                        if(values[0].equals("USERLEAGUES")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 4) {
                                ObservableList<League> leagues = FXCollections.observableArrayList();
                                for (int i = 1; i < values.length; i += 2) {
                                    leagues.add(new League(Integer.parseInt(values[i]), values[i + 1]));
                                }
                                leagueChoiceBox.setItems(leagues);
                            }
                        }else{
                            System.out.println("Error getting user leagues settings data");
                        }
                    });
                    String settingsResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {

                        // Split the received data into an array of values
                        String[] values = settingsResponse.split("\\|");
                        System.out.println(settingsResponse);
                        if(values[0].equals("USERSETTINGS")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 4) {
                                    userID = Integer.parseInt(values[1]);
                                    settingsUsername.setText(values[2]);
                                    settingsName.setText(values[3]);
                                    settingsSurname.setText(values[4]);
                                    settingsPassword.setText(values[5]);
                                    settingsEmail.setText(values[6]);
                                // Set the user role in the ChoiceBox
                                for (League league : leagueChoiceBox.getItems()) {
                                    if (league.getName().equals(values[8])) {
                                        leagueChoiceBox.getSelectionModel().select(league);
                                        message.sendGetFanClubs(SendToServer,String.valueOf(league.getLeagueID()));
                                        break;
                                    }
                                }
                            }
                        }else{
                            System.out.println("Error getting user settings data");
                        }
                    });
                    String clubsResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {

                        // Split the received data into an array of values
                        String[] values = clubsResponse.split("\\|");
                        System.out.println(clubsResponse);
                        if(values[0].equals("USERCLUBS")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 2) {
                                ObservableList<Club> clubs = FXCollections.observableArrayList();
                                for (int i = 1; i < values.length; i += 2) {
                                    clubs.add(new Club(Integer.parseInt(values[i]), values[i + 1]));
                                }
                                clubChoiceBox.setItems(clubs);
                            }
                        }else{
                            System.out.println("Error getting user clubs settings data");
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