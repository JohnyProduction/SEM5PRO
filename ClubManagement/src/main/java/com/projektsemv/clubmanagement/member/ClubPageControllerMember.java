package com.projektsemv.clubmanagement.member;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.LoginPanelController;
import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.MANAGER;
import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.MEMBER;

public class ClubPageControllerMember implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut;
    @FXML
    private Label username, labelNameOfClub, labelLeague, labelManagerName, labelPhoneNumber, labelAddress, labelFinance, labelMoreStats;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClubPageControllerMember.ReadFromServer = Client.ReadFromServer;
        ClubPageControllerMember.SendToServer = Client.SendToServer;
        preparePage();
        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "login-panel.fxml", "Panel logowania", null);
            }
        });
        buttonOption3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "messages-panel-member.fxml", "Wiadomości", MEMBER);
            }
        });
        buttonOptions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "settings-page-member.fxml", "Ustawienia", MEMBER);
            }
        });
        labelMoreStats.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ChangeController.changeSceneLabel(mouseEvent, "club-stats-member.fxml", "Statystyki klubowe", MEMBER);
            }
        });
    }
    private void preparePage() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Perform time-consuming operations (e.g., reading from the server) here
                    message.sendGetPage(SendToServer, "MEMBER");
                    String serverResponse = ReadFromServer.readLine();
                    System.out.println(serverResponse);

                    // Update the UI on the JavaFX application thread
                    Platform.runLater(() -> username.setText(serverResponse));
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