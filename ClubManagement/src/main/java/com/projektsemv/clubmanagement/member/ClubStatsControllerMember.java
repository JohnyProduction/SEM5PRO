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
import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.MEMBER;

public class ClubStatsControllerMember implements Initializable {
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
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ClubStatsControllerMember.ReadFromServer = Client.ReadFromServer;
        ClubStatsControllerMember.SendToServer = Client.SendToServer;
        preparePage();


       //attendanceStatsChartLine.setData(attendanceStatsChartData());

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
                ChangeController.changeScene(actionEvent, "club-page-member.fxml", "Strona klubu", MEMBER);
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
                    message.sendGetStatisticsPage(SendToServer, "MEMBER");
                    String serverResponse = ReadFromServer.readLine();
                    //System.out.println(serverResponse);
                    // Update the UI on the JavaFX application thread
                    Platform.runLater(() -> username.setText(serverResponse));
                    // Perform time-consuming operations (e.g., reading from the server) here

                    // Update the UI on the JavaFX application thread
                    String winRatioResponse = ReadFromServer.readLine();
                    //System.out.println(winRatioResponse);
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = winRatioResponse.split("\\|");
                        if(values[0].equals("MEMBERCHART")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 3) {
                                statsInfoData.setText(String.valueOf((Math.round(Float.parseFloat(values[1]))*100.0) / 100.0)+"%");
                                resultStatsChart.setData(resultStatsData(Float.parseFloat(values[1]),Float.parseFloat(values[2])));
                                resultStatsChart.setLegendVisible(false);
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
                        if(values[0].equals("MEMBERCHARTLINE")){
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
