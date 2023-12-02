package com.projektsemv.clubmanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPanel extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPanel.class.getResource("login-panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280.0, 720);
        stage.setTitle("Club management | Login Panel");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("appLogo.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}