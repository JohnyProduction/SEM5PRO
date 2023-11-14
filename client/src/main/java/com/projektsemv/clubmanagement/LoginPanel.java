package com.projektsemv.clubmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPanel extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPanel.class.getResource("login-panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280.0, 720);
        stage.setTitle("Login Panel");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}