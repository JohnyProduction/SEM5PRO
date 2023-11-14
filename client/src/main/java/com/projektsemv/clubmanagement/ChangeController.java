package com.projektsemv.clubmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeController {
    public static void changeScene(ActionEvent event, String fxmlFile,
                                   String title, String username){
        Parent root = null;
//        if(username != null) {
//            try {
//                FXMLLoader loader =
//                        new FXMLLoader(DBUtils.class.getResource(fxmlFile));
//                root = loader.load();
//                LoginPanelController loginPanelController = loader.getController();
//                //loginPanelController.setUserInformation(username);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else{
                try{
                    root = FXMLLoader.load(ChangeController.class.getResource(fxmlFile));
                }catch (IOException e){
                    e.printStackTrace();
                }

        //}
        Stage stage = (Stage)((Node) event.getSource())
                .getScene().getWindow();
        stage.setTitle(title);
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
