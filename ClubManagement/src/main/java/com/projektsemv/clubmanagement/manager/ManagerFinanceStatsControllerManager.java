package com.projektsemv.clubmanagement.manager;

import com.projektsemv.clubmanagement.ChangeController;

import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.Message;
import com.projektsemv.clubmanagement.UserFunction.Transactions;
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

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.*;


public class ManagerFinanceStatsControllerManager implements Initializable {
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut;
    @FXML
    private Label username;
    @FXML
    private Label sumOfMoneyLabel;
    @FXML
    private Label mainFinanceLabel;
    @FXML
    private BarChart incomesBarChart;
    @FXML
    private TableView<Transactions> financeTable;
    @FXML
    private TableColumn<Transactions, String> transactionID;

    @FXML
    private TableColumn<Transactions, String> transactionAmount;

    @FXML
    private TableColumn<Transactions, String> transactionDate;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ManagerFinanceStatsControllerManager.ReadFromServer = Client.ReadFromServer;
        ManagerFinanceStatsControllerManager.SendToServer = Client.SendToServer;
        preparePage();

        initializeTableView2();


        incomesBarChart.setVisible(true);
        incomesBarChart.setLegendVisible(false);

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
                        //System.out.println(incomesResponse);
                        if (values[0].equals("INCOMES")) {
                            // Check if there are enough values to fill the labels
                            if (values.length >= 3) {
                                ObservableList<Transactions> dataF = FXCollections.observableArrayList();
                                for (int i = 1; i + 2 <= values.length; i += 3) {
                                    dataF.add(new Transactions(values[i], values[i + 1], values[i + 2]));
                                }
                                financeTable.setItems(dataF);
                            } else {
                                // Handle the case where there are not enough values
                                System.out.println("Invalid data received from the server");
                            }
                        } else {
                            System.out.println("Error getting transaction data");
                        }
                    });
                    String financeResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = financeResponse.split("\\|");
                        //System.out.println(incomesResponse);
                        if (values[0].equals("CHART")) {
                            // Check if there are enough values to fill the labels
                            if (values.length >= 2) {
                                // Set categories for the X-axis
                                // Set categories for the X-axis
                                XYChart.Series<String, Number> series = new XYChart.Series<>();

                                // Convert Float to Integer to remove decimals
                                Integer incomeValue = Math.round(Float.parseFloat(values[1]));
                                Integer incomeValue2 = Math.round(Float.parseFloat(values[2]));
                                series.getData().add(new XYChart.Data<>("Przychody", incomeValue));
                                series.getData().add(new XYChart.Data<>("Wydatki", incomeValue2));
                                incomesBarChart.getData().add(series);
                                sumOfMoneyLabel.setText(values[3]);
                            } else {
                                // Handle the case where there are not enough values
                                System.out.println("Invalid data received from the server");
                            }
                        } else {
                            System.out.println("Error getting transaction data");
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

    private void initializeTableView2() {
        // Initialize columns
        transactionID.setCellValueFactory(new PropertyValueFactory<>("TransactionID"));
        transactionAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        transactionDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        // You may want to set the cell factory for each column if you want custom rendering or editing
    }



}

