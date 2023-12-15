package com.projektsemv.clubmanagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserInfo.UserType.*;

public class RegisterPanelController implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button signInButton, registerButton;

    @FXML
    private Label errorLabel, loginTitleLabel;

    @FXML
    private TextField usernameTextField, emailTextField;

    @FXML
    private PasswordField passwordTextField, passwordConfirmedTextField;
    public static boolean status;
    protected void onRegisterButtonClick(){


    }
    protected void createAccount(){

    }

    protected void onSignInButtonClick() {
        errorLabel.setText("Podane dane są błędne!");
        errorLabel.setStyle("-fx-text-fill: GREEN;");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                sendRegisterDataToServer(usernameTextField.getText(),passwordTextField.getText(),emailTextField.getText());
                if(status){
                    ChangeController.changeScene(actionEvent,"login-panel.fxml","Panel logowania!", null);
                }else{
                    errorLabel.setText("Błąd rejestracji");
                    errorLabel.setStyle("-fx-text-fill: RED;");
                }
            }
        });

    }
    private static void handleServerResponse(String response) {
        if ("SUCCESS".equals(response)) {
            status = true;
        } else {
            status= false;
        }
    }
    private static void sendRegisterDataToServer(String username, String password, String email) {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Wysłanie danych do serwera w określonym formacie, na przykład:
            writer.println("REGISTER|" + username + "|" + password +"|"+email);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            System.out.println(response);
            handleServerResponse(response);

            reader.close();
            writer.close();
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}