package com.projektsemv.clubmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;

public class ChartsCreator {
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
                barChartData.getData().add(new XYChart.Data<>("Ten miesiąc", outgoings * (-1)));
                barChartData.getData().add(new XYChart.Data<>("Ostatni miesiąc", outgoingsLast * (-1)));
                return barChartData;
            case SUMA:
                barChartData = new XYChart.Series<>();
                barChartData.getData().add(new XYChart.Data<>("Ten miesiąc", transactionsSum));
                barChartData.getData().add(new XYChart.Data<>("Ostatni miesiąc", transactionsSumLast));
                return barChartData;
        }
        return null;
        // Tworzenie osi
        //xAxis = new CategoryAxis();
        //yAxis = new NumberAxis();

        // Tworzenie BarChart
        //BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        // Tworzenie serii danych

        // Dodanie serii danych do wykresu
        //barChart.getData().add(barChartData);
    }

    public static ObservableList<PieChart.Data> resultStatsChartData(){
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Wygrane", 25),
                        new PieChart.Data("Porażki", 13));

        return pieChartData;
    }
    public static ObservableList<XYChart.Series>resultStatsChartLineData(){
        XYChart.Series wins = new XYChart.Series();
        wins.setName("Wygrane");

        XYChart.Series loses = new XYChart.Series();
        loses.setName("Przegrane");

        //TODO: Pobieranie z bazy dla każdego miesiąca
        wins.getData().add(new XYChart.Data("Jan", 2));
        wins.getData().add(new XYChart.Data("Feb", 3));
        wins.getData().add(new XYChart.Data("Mar", 1));
        wins.getData().add(new XYChart.Data("Apr", 1));
        wins.getData().add(new XYChart.Data("May", 3));
        wins.getData().add(new XYChart.Data("Jun", 4));
        wins.getData().add(new XYChart.Data("Jul", 6));
        wins.getData().add(new XYChart.Data("Aug", 1));
        wins.getData().add(new XYChart.Data("Sep", 7));
        wins.getData().add(new XYChart.Data("Oct", 2));
        wins.getData().add(new XYChart.Data("Nov", 0));
        wins.getData().add(new XYChart.Data("Dec", 1));

        //TODO: Pobieranie z bazy dla każdego miesiąca
        loses.getData().add(new XYChart.Data("Jan", 3));
        loses.getData().add(new XYChart.Data("Feb", 4));
        loses.getData().add(new XYChart.Data("Mar", 2));
        loses.getData().add(new XYChart.Data("Apr", 4));
        loses.getData().add(new XYChart.Data("May", 3));
        loses.getData().add(new XYChart.Data("Jun", 1));
        loses.getData().add(new XYChart.Data("Jul", 5));
        loses.getData().add(new XYChart.Data("Aug", 4));
        loses.getData().add(new XYChart.Data("Sep", 4));
        loses.getData().add(new XYChart.Data("Oct", 2));
        loses.getData().add(new XYChart.Data("Nov", 3));
        loses.getData().add(new XYChart.Data("Dec", 2));

        ObservableList<XYChart.Series> lineChartData =
                FXCollections.observableArrayList(wins,loses);

        return lineChartData;
    }
    public static ObservableList<XYChart.Series>attendanceStatsChartData(){
        XYChart.Series attendance = new XYChart.Series();
        attendance.setName("Frekwencja ostatnich spotkań");

        //TODO: Pobieranie z bazy dla każdego miesiąca
        attendance.getData().add(new XYChart.Data("Jan", 21010));
        attendance.getData().add(new XYChart.Data("Feb", 13012));
        attendance.getData().add(new XYChart.Data("Mar", 9009));
        attendance.getData().add(new XYChart.Data("Apr", 18000));
        attendance.getData().add(new XYChart.Data("May", 31445));
        attendance.getData().add(new XYChart.Data("Jun", 43242));
        attendance.getData().add(new XYChart.Data("Jul", 6234));
        attendance.getData().add(new XYChart.Data("Aug", 9988));
        attendance.getData().add(new XYChart.Data("Sep", 712));
        attendance.getData().add(new XYChart.Data("Oct", 2232));
        attendance.getData().add(new XYChart.Data("Nov", 18822));
        attendance.getData().add(new XYChart.Data("Dec", 13008));

        ObservableList<XYChart.Series> lineChartData =
                FXCollections.observableArrayList(attendance);

        return lineChartData;
    }
}

