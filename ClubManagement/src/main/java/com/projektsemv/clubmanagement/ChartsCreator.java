package com.projektsemv.clubmanagement;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

class FinanceChartsCreator {
    public enum FinanceType {
        PRZYCHODY, WYDATKI, SUMA
    }

    ;
    static private double incomings, incomingsLast, outgoings, outgoingsLast, transactionsSum, transactionsSumLast;
    static private BarChart barChart;
    static private CategoryAxis xAxis;
    static private NumberAxis yAxis;
    public static void updateTwoMonthsData() {
        //TODO: Pobieranie danych z klasy pobierającej z bazy

            incomings = 52000;
            incomingsLast = 37000;

            outgoings = -38000;
            outgoingsLast = -38000;

            transactionsSum = 850;
            transactionsSumLast = -1450;

    }

    public static XYChart.Series<String, Number> chartFinances(FinanceType financeType) {
        XYChart.Series<String, Number> barChartData = new XYChart.Series<>();
        updateTwoMonthsData();

        switch(financeType){
            case PRZYCHODY:

                barChartData = new XYChart.Series<>();
                barChartData.getData().add(new XYChart.Data<>("Ten miesiąc", incomings));
                barChartData.getData().add(new XYChart.Data<>("Ostatni miesiąc", incomingsLast));
                return barChartData;
            case WYDATKI:
                barChartData = new XYChart.Series<>();
                barChartData.getData().add(new XYChart.Data<>("Ten miesiąc", outgoings));
                barChartData.getData().add(new XYChart.Data<>("Ostatni miesiąc", outgoingsLast));
                return barChartData;
            case SUMA:
                barChartData = new XYChart.Series<>();
                barChartData.getData().add(new XYChart.Data<>("Ten miesiąc", transactionsSum));
                barChartData.getData().add(new XYChart.Data<>("Ostatni miesiąc", transactionsSumLast));
                return barChartData;
        }

        // Tworzenie osi
        //xAxis = new CategoryAxis();
        //yAxis = new NumberAxis();

        // Tworzenie BarChart
        //BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Tworzenie serii danych

        // Dodanie serii danych do wykresu
        //barChart.getData().add(barChartData);

        return null;
    }
}

