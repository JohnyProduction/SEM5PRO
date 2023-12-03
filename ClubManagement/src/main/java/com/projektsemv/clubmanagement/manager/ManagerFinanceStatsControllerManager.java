package com.projektsemv.clubmanagement.manager;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.ChartsCreator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.ChartsCreator.FinanceType.*;
import static com.projektsemv.clubmanagement.ChartsCreator.chartFinances;
import static com.projektsemv.clubmanagement.UserInfo.UserType.*;


public class ManagerFinanceStatsControllerManager implements Initializable {
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
    ChartsCreator.FinanceType tempFinanceType;

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


