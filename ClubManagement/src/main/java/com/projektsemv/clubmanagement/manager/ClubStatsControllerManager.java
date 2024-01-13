package com.projektsemv.clubmanagement.manager;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.Message;
import com.projektsemv.clubmanagement.member.ClubStatsControllerMember;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.ChartsCreator.*;
import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.*;

public class ClubStatsControllerManager implements Initializable {
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut;
    @FXML
    private Button attendanceStatsButton, resultsStatsButton;
    @FXML
    private Label username, statsInfoLabel, statsInfoData, mainStatsLabel;
    @FXML
    private BarChart barChart;
    @FXML
    private PieChart resultStatsChart;
    @FXML
    private LineChart resultStatsChartLine, attendanceStatsChartLine;
    @FXML
    private StackPane stackPaneStats;
    @FXML
    private TableView financeTable;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClubStatsControllerManager.ReadFromServer = Client.ReadFromServer;
        ClubStatsControllerManager.SendToServer = Client.SendToServer;

        preparePage();
        resultStatsChart.setVisible(true);
        resultStatsChartLine.setVisible(false);
        attendanceStatsChartLine.setVisible(false);


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
        buttonOptions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "settings-page-manager.fxml", "Ustawienia", MANAGER);
            }
        });
        resultsStatsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (attendanceStatsChartLine.isVisible()) {
                    attendanceStatsChartLine.setVisible(false);
                    resultStatsChart.setVisible(true);
                } else if (resultStatsChart.isVisible()) {
                    resultStatsChartLine.setVisible(true);
                    resultStatsChart.setVisible(false);
                } else if (resultStatsChartLine.isVisible()) {
                    resultStatsChartLine.setVisible(false);
                    resultStatsChart.setVisible(true);
                }
                mainStatsLabel.setText("Statystyki wyników");
                statsInfoLabel.setText("Procent zwycięstw");
            }
        });
        attendanceStatsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resultStatsChartLine.setVisible(false);
                resultStatsChart.setVisible(false);
                attendanceStatsChartLine.setVisible(true);

                mainStatsLabel.setText("Statystyki frekwencji");
                statsInfoLabel.setText("Średnia na mecz");
            }
        });
    }
    private void preparePage() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Perform time-consuming operations (e.g., reading from the server) here
                    message.sendGetStatisticsPage(SendToServer, "MANAGER");
                    String serverResponse = ReadFromServer.readLine();
                    // Update the UI on the JavaFX application thread
                    Platform.runLater(() -> username.setText(serverResponse));
                    String winRatioResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = winRatioResponse.split("\\|");
                        if(values[0].equals("MANAGERCHART")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 3) {
                                statsInfoData.setText(String.valueOf((Math.round(Float.parseFloat(values[1]))*100.0) / 100.0)+"%");
                                resultStatsChart.setData(resultStatsData(Float.parseFloat(values[1]),Float.parseFloat(values[2])));
                                resultStatsChart.setLegendVisible(true);
                            } else {
                                // Handle the case where there are not enough values
                                System.out.println("Invalid data received from the server");
                            }
                        }else{
                            System.out.println("Error getting sidebar data");
                        }
                    });
                    String winLoseResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = winLoseResponse.split("\\|");
                        System.out.println(winLoseResponse);
                        if(values[0].equals("MANAGERCHARTLINE")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 3) {
                                resultStatsChartLine.setData(resultStatsLineData(values));
                            } else {
                                // Handle the case where there are not enough values
                                System.out.println("Invalid data received from the server");
                            }
                        }else{
                            System.out.println("Error getting sidebar data");
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
    private ObservableList<PieChart.Data> resultStatsData(float value1, float value2) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        // Assuming you have data to fill the chart
        pieChartData.add(new PieChart.Data("Wygrane", value1));
        pieChartData.add(new PieChart.Data("Porażki",  value2));
        // Add more data as needed

        return pieChartData;
    }
    public static ObservableList<XYChart.Series>resultStatsLineData(String[] values){

        XYChart.Series wins = new XYChart.Series();
        wins.setName("Wygrane");
        XYChart.Series loses = new XYChart.Series();
        loses.setName("Przegrane");

        for (int i = 1; i + 2 <= values.length; i += 3) {
            wins.getData().add(new XYChart.Data(values[i], Integer.parseInt(values[i + 1])));
            loses.getData().add(new XYChart.Data(values[i], Integer.parseInt(values[i + 2])));
        }


        ObservableList<XYChart.Series> lineChartData =
                FXCollections.observableArrayList(wins,loses);

        return lineChartData;
    }
}
