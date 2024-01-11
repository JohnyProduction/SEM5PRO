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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;

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
    @FXML
    private Label username;
    @FXML
    ChoiceBox leagueChoiceBox, clubChoiceBox;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    ObservableList<String> teamsChoice = FXCollections.observableArrayList("FCA", "FCB", "FCC"); //Przykładowa lista klubów, aby zaprezentować działanie choiceBoxów

    List<String> textFieldData = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        leagueChoiceBox.setItems(teamsChoice);
        clubChoiceBox.setItems(teamsChoice);
        SettingsPageControllerMember.ReadFromServer = Client.ReadFromServer;
        SettingsPageControllerMember.SendToServer = Client.SendToServer;
        preparePage();
        //System.out.println(textFieldData);
        /*
        settingsUsername.setText(textFieldData.get(1));
        settingsName.setText(textFieldData.get(2));
        settingsSurname.setText(textFieldData.get(3));
        settingsPassword.setText(textFieldData.get(4));
        settingsEmail.setText(textFieldData.get(5));
*/
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
                        System.out.println(settingsResponse);
                        // Split the received data into an array of values
                        String[] values = settingsResponse.split("\\|");
                        if(values[0].equals("USERSETTINGS")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 4) {
                                for (int i=1;i<values.length;i++){
                                    textFieldData.add(values[i].toString());
                                }
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