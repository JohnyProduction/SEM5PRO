package com.projektsemv.clubmanagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserInfo.UserType.*;

public class LoginPanelController implements Initializable {

    /*Import JavaFX controls*/
    @FXML
    private Button signInButton, registerButton;

    @FXML
    private Label errorLabel, loginTitleLabel, username;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    //    /* Make the label with the fx:id of 'errorLabel' change its text and color on Sign in Button click */
    protected void onSignInButtonClick() {
        errorLabel.setText("Podane dane są błędne!");
        errorLabel.setStyle("-fx-text-fill: GREEN;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent,"register-panel.fxml","Panel rejestracji", null);
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                sendLoginDataToServer(usernameTextField.getText(),passwordTextField.getText());
                System.out.println(usernameTextField.getText());
                if(usernameTextField.getText().equals("fan")) {
                    ChangeController.changeScene(actionEvent, "club-page-fan.fxml", "Strona klubu", FAN);
                }else if((usernameTextField.getText().equals("m"))) {
                    ChangeController.changeScene(actionEvent, "club-page-member.fxml", "Strona klubu", MEMBER);
                }else{
                    ChangeController.changeScene(actionEvent, "club-page-manager.fxml", "Strona klubu", MANAGER);

                }
            }
        });


    }
    private static void sendLoginDataToServer(String username, String password) {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Wysłanie danych do serwera w określonym formacie, na przykład:
            writer.println("LOGIN|" + username + "|" + password);

            writer.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}