package com.projektsemv.clubmanagement.member;

import com.projektsemv.clubmanagement.ChangeController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

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
    @FXML
    private TableView financeTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resultStatsChart.setData(resultStatsChartData());
        resultStatsChart.setLegendVisible(false);

        resultStatsChartLine.setData(resultStatsChartLineData());

        attendanceStatsChartLine.setData(attendanceStatsChartData());

        resultStatsChart.setVisible(true);
        resultStatsChartLine.setVisible(false);
        attendanceStatsChartLine.setVisible(false);
        //TODO: Wartość zwycięstw z bazy
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
                //TODO: Wartość zwycięstw z bazy
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
                //TODO: Wartość średniej z bazy
            }
        });
    }
}
