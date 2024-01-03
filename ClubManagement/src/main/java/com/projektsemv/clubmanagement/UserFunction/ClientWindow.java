package com.projektsemv.clubmanagement.UserFunction;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;

import static java.lang.System.exit;

public class ClientWindow extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        ClientWindow.primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/projektsemv/clubmanagement/login-panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image(String.valueOf(ClientWindow.class.getResource("/com/projektsemv/clubmanagement/appLogo.png")));
        stage.getIcons().add(icon);
        stage.setTitle("Club management");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    public void startWindow() {
        launch();
        System.out.println("Disconnected from server.");
        exit(0);
    }
}