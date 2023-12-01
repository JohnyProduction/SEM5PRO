package com.projektsemv.clubmanagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.FinanceChartsCreator.FinanceType.*;
import static com.projektsemv.clubmanagement.FinanceChartsCreator.chartFinances;


public class ManagerFinanceStatsController implements Initializable {
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut;
    @FXML
    private Button allTransactionsButton, incomesButton, outcomesButton;
    @FXML
    private Label username, sumOfMoneyLabel, mainFinanceLabel;
    @FXML
    private BarChart barChart;
    @FXML
    private TableView financeTable;
    FinanceChartsCreator.FinanceType tempFinanceType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        barChart.getData().add(chartFinances(PRZYCHODY));
        barChart.setLegendVisible(false);
        tempFinanceType = PRZYCHODY;
        mainFinanceLabel.setText("Statystyki przychodów");
        //TODO:sumOfMoneyLabel
        //TODO:financeTable


        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "login-panel.fxml", "Panel logowania");
            }
        });
        buttonOption1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "manager-club-page.fxml", "Strona klubu");
            }
        });
        buttonOption2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "manager-list-of-users.fxml", "Lista użytkowników");
            }
        });

        buttonOption3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "manager-messages-panel.fxml", "Wiadomości");
            }
        });
        allTransactionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                barChart.getData().remove(0);
                barChart.getData().add(chartFinances(SUMA));
                tempFinanceType = SUMA;
                mainFinanceLabel.setText("Statystyki sumaryczne");
                //sumOfMoneyLabel =
            }
        });
        incomesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                barChart.getData().remove(0);
                barChart.getData().add(chartFinances(PRZYCHODY));
                tempFinanceType = PRZYCHODY;
                mainFinanceLabel.setText("Statystyki przychodów");
                //sumOfMoneyLabel =
            }
        });
        outcomesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                barChart.getData().remove(0);
                barChart.getData().add(chartFinances(WYDATKI));
                tempFinanceType = WYDATKI;
                mainFinanceLabel.setText("Statystyki wydatków");
                //sumOfMoneyLabel =
            }
        });
    }
}


