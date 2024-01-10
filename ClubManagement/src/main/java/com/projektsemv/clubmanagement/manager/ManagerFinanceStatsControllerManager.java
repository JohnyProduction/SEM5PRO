package com.projektsemv.clubmanagement.manager;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.ChartsCreator;
import com.projektsemv.clubmanagement.UserFunction.Client;
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
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.ChartsCreator.FinanceType.*;
import static com.projektsemv.clubmanagement.ChartsCreator.chartFinances;
import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.*;


public class ManagerFinanceStatsControllerManager implements Initializable {
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut;
    @FXML
    private Button allTransactionsButton, incomesButton, expensesButton;
    @FXML
    private Label username;
    @FXML
    private static Label sumOfMoneyLabel;
    @FXML
    private Label mainFinanceLabel;
    @FXML
    private BarChart allBarChart, incomesBarChart, expensesBarChart;
    @FXML
    private TableView financeTable;
    ChartsCreator.FinanceType tempFinanceType;

    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    private static ObservableList<XYChart.Series> incomesData;
    private static ObservableList<XYChart.Series> expensesData;
    private static ObservableList<XYChart.Series> sumData;
    private static float incomes;
    private static float expenses;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ManagerFinanceStatsControllerManager.ReadFromServer = Client.ReadFromServer;
        ManagerFinanceStatsControllerManager.SendToServer = Client.SendToServer;
        preparePage();

        allBarChart.setVisible(false);
        expensesBarChart.setVisible(false);
        incomesBarChart.setVisible(true);

        allBarChart.setLegendVisible(false);
        expensesBarChart.setLegendVisible(false);
        incomesBarChart.setLegendVisible(false);

        incomesBarChart.getData().add(chartFinances(PRZYCHODY));
        incomesBarChart.setLegendVisible(false);

        tempFinanceType = PRZYCHODY;
        mainFinanceLabel.setText("Statystyki przychodów");
        //TODO:sumOfMoneyLabel
        //TODO:financeTable


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
        allTransactionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                allBarChart.setVisible(true);
                expensesBarChart.setVisible(false);
                incomesBarChart.setVisible(false);

                allBarChart.getData().remove(0);

                allBarChart.getData().add(chartFinances(SUMA));

                incomesBarChart.getData().add(expensesData);
                tempFinanceType = SUMA;
                mainFinanceLabel.setText("Statystyki sumaryczne");
                //sumOfMoneyLabel =
            }
        });
        incomesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                allBarChart.setVisible(false);
                expensesBarChart.setVisible(false);
                incomesBarChart.setVisible(true);

                //Usuwanie danych z listy przed pobraniem raz jeszcze
                //incomesBarChart.getData().remove(0);
                //incomesBarChart.getData().add(incomesData);

                sumOfMoneyLabel.setText(String.valueOf((Math.round(incomes)*100.0) / 100.0)+" zł");
                mainFinanceLabel.setText("Statystyki przychodów");
                //sumOfMoneyLabel =
            }
        });
        expensesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                allBarChart.setVisible(false);
                expensesBarChart.setVisible(true);
                incomesBarChart.setVisible(false);

                expensesBarChart.getData().remove(0);
                expensesBarChart.getData().add(expensesData);

                sumOfMoneyLabel.setText(String.valueOf((Math.round(expenses)*100.0) / 100.0)+" zł");
                mainFinanceLabel.setText("Statystyki wydatków");
                //sumOfMoneyLabel =
            }
        });
    }
    private void preparePage() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Perform time-consuming operations (e.g., reading from the server) here
                    message.sendGetFinancePage(SendToServer, "MANAGER");

                    String serverResponse = ReadFromServer.readLine();
                    // Update the UI on the JavaFX application thread
                    Platform.runLater(() -> username.setText(serverResponse));
                    String incomesResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = incomesResponse.split("\\|");
                        if(values[0].equals("INCOMES")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 3) {
                               incomesData = resultData(values);
                                incomesBarChart.getData().add(incomesData);
                                incomes = Float.parseFloat(values[1]);
                            } else {
                                // Handle the case where there are not enough values
                                System.out.println("Invalid data received from the server");
                            }
                        }else{
                            System.out.println("Error getting incomes data");
                        }
                    });
                    String expensesResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = expensesResponse.split("\\|");
                        if(values[0].equals("EXPENSES")){
                            // Check if there are enough values to fill the labels
                            if (values.length >= 3) {
                                expensesData = resultData(values);
                                incomesBarChart.getData().add(expensesData);
                                expenses = Float.parseFloat(values[1]);
                            } else {
                                // Handle the case where there are not enough values
                                System.out.println("Invalid data received from the server");
                            }
                        }else{
                            System.out.println("Error getting expenses data");
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
    public static ObservableList<XYChart.Series> resultData(String[] values) {
        XYChart.Series actual = new XYChart.Series();
        actual.getData().add(new XYChart.Data<>("Ten miesiąc", Math.max(Float.parseFloat(values[1]), 0)));

        XYChart.Series last = new XYChart.Series();
        last.getData().add(new XYChart.Data<>("Ostatni miesiąc", Math.max(Float.parseFloat(values[2]), 0)));

        ObservableList<XYChart.Series> lineChartData = FXCollections.observableArrayList(actual, last);
        return lineChartData;
    }

}


