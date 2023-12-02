package com.projektsemv.clubmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import com.projektsemv.clubmanagement.UserInfo.UserType;

public class ChangeController {
    static Stage stage;
    public static void changeScene(ActionEvent actionEvent, String fxmlFile, String title, UserType userType){
        Parent root = null;
        try{
            root = FXMLLoader.load(ChangeController.class.getResource(userType.name() +"/"+ fxmlFile));
        }catch (IOException e){
                    e.printStackTrace();
        }

        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Club management | " + title);
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public static void changeSceneLabel(MouseEvent mouseEvent, String fxmlFile, String title, UserType userType){
        Parent root = null;
        try{
            root = FXMLLoader.load(ChangeController.class.getResource(userType.name() +"/"+ fxmlFile));
        }catch (IOException e){
            e.printStackTrace();
        }
        stage = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setTitle("Club management | " + title);
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();
    }

    public static void singUpUser(ActionEvent event, String username,
                                String password, String email){
        //łączenie z bazą danych
        //sprawdzenie czy użytkownik istnieje
        //("SELECT * FROM users WHERE username = ?")
        //jeśli istnieje zwracamy ERROR
        //else INSERT INTO users (...) VALUES (?,?,...)

    }

    public static void logInUser(ActionEvent event, String username,
                                 String password){
        //łączenie z bazą
        //pobranie hasła dla danego loginu
        //jeśli nie ERROR
    }


}
