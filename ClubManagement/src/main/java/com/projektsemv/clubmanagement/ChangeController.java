package com.projektsemv.clubmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType;

public class ChangeController {
    static Stage stage;
    public static void changeScene(ActionEvent actionEvent, String fxmlFile, String title, UserType userType){
        Parent root = null;
        try{
            if(userType != null) {
                root = FXMLLoader.load(ChangeController.class.getResource(userType.name() + "/" + fxmlFile));
            }else{
                root = FXMLLoader.load(ChangeController.class.getResource(fxmlFile));
            }
        }catch (IOException e){
                    e.printStackTrace();
        }

        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        if(userType != null) {
            stage.setTitle("Club management | " + title + " | " + userType.name());
        }else{
            stage.setTitle("Club management | " + title + "|");
        }
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public static void changeSceneLabel(MouseEvent mouseEvent, String fxmlFile, String title, UserType userType){
        Parent root = null;
        try{
            if(userType != null) {
                root = FXMLLoader.load(ChangeController.class.getResource(userType.name() + "/" + fxmlFile));
            }else{
                root = FXMLLoader.load(ChangeController.class.getResource(fxmlFile));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();

        if(userType != null) {
            stage.setTitle("Club management | " + title + " | " + userType.name());
        }else{
            stage.setTitle("Club management | " + title);
        }

        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }
}
