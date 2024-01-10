package com.projektsemv.clubmanagement.manager;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.Match;
import com.projektsemv.clubmanagement.UserFunction.Message;
import com.projektsemv.clubmanagement.member.ClubPageControllerMember;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.*;

public class ClubPageControllerManager implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut;
    @FXML
    private Label username, labelNameOfClub, labelLeague, labelManagerName, labelPhoneNumber, labelAddress, labelFinance, labelMoreStats;
    @FXML
    private Label resultsClub1,resultsClub2,resultsOfMatch,resultsDateOfMatch;
    @FXML
    private TableView<Match> tableView;

    @FXML
    private TableColumn<Match, String> resultColumn;

    @FXML
    private TableColumn<Match, String> opponentColumn;

    @FXML
    private TableColumn<Match, String> dateColumn;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClubPageControllerManager.ReadFromServer = Client.ReadFromServer;
        ClubPageControllerManager.SendToServer = Client.SendToServer;
        initializeTableView();
        preparePage();

        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "login-panel.fxml", "Panel logowania", null);
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
        buttonOptions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "settings-page-manager.fxml", "Ustawienia", MANAGER);
            }
        });
        labelFinance.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ChangeController.changeSceneLabel(mouseEvent, "finances-stats-manager.fxml", "Finanse", MANAGER);
            }
        });
        labelMoreStats.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ChangeController.changeSceneLabel(mouseEvent, "club-stats-manager.fxml", "Statystyki klubowe", MANAGER);
            }
        });
    }

    private void preparePage() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Perform time-consuming operations (e.g., reading from the server) here
                    message.sendGetPage(SendToServer, "MANAGER");
                    String serverResponse = ReadFromServer.readLine();

                    // Update the UI on the JavaFX application thread
                    Platform.runLater(() -> username.setText(serverResponse));
                    String sidebarResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = sidebarResponse.split("\\|");
                        if(values[0].equals("MANAGERSIDEBAR")){

                            // Check if there are enough values to fill the labels
                            if (values.length >= 5) {
                                // Set values to the respective labels
                                labelNameOfClub.setText(values[1]);
                                labelLeague.setText(values[2]);
                                labelManagerName.setText(values[3]);
                                labelAddress.setText(values[4]);
                                labelPhoneNumber.setText(values[5]);
                            } else {
                                // Handle the case where there are not enough values
                                System.out.println("Invalid data received from the server");
                            }
                        }else{
                            System.out.println("Error getting sidebar data");
                        }
                    });
                    String lastMatchResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = lastMatchResponse.split("\\|");
                        if(values[0].equals("MANAGERLASTMATCH")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 5) {
                                // Set values to the respective labels
                                resultsClub1.setText(values[1]);
                                resultsClub2.setText(values[2]);
                                resultsOfMatch.setText(values[3]+" : "+values[4]);
                                resultsDateOfMatch.setText(values[5]);
                            } else {
                                // Handle the case where there are not enough values
                                System.out.println("Invalid data received from the server");
                            }
                        }else{
                            System.out.println("Error getting last match data");
                        }
                    });
                    String matchTableResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = matchTableResponse.split("\\|");
                        //System.out.println(matchTableResponse);
                        if(values[0].equals("MANAGERMATCHTABLE")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 4) {
                                ObservableList<Match> data = FXCollections.observableArrayList();
                                for (int i = 1; i + 2 <= values.length; i += 3) {
                                    data.add(new Match(values[i], values[i + 1], values[i +  2]));
                                }
                                tableView.setItems(data);
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
    private void initializeTableView() {
        // Initialize columns
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        opponentColumn.setCellValueFactory(new PropertyValueFactory<>("opponent"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        // You may want to set the cell factory for each column if you want custom rendering or editing
    }
}