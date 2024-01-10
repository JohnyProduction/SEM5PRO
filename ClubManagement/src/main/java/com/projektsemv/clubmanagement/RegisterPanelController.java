package com.projektsemv.clubmanagement;

import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.Message;
import com.projektsemv.clubmanagement.UserFunction.UserFunctions;
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
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterPanelController implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button signInButton, registerButton;

    @FXML
    private Label errorLabel, loginTitleLabel;

    @FXML
    private TextField usernameTextField, emailTextField;
    @FXML
    private TextField nameTextField, surnameTextField;
    @FXML
    private PasswordField passwordTextField, passwordConfirmedTextField;
    public static boolean status;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RegisterPanelController.ReadFromServer = Client.ReadFromServer;
        RegisterPanelController.SendToServer = Client.SendToServer;
        registerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    checkInputs();
                }catch(IOException e) {
                    throw new RuntimeException(e);
                }

                if(status){
                    ChangeController.changeScene(actionEvent,"login-panel.fxml","Panel logowania!", null);
                }else{
                    errorLabel.setText("ERROR! Błąd rejestracji");
                    errorLabel.setStyle("-fx-text-fill: RED;");
                }
            }
        });

    }
    private void handleServerResponse(String response) {
        status = UserFunctions.switchLoginClient(response);
    }
    private void checkInputs() throws IOException {
        if (usernameTextField.getText().isEmpty() && passwordTextField.getText().isEmpty() && passwordConfirmedTextField.getText().isEmpty())
            errorLabel.setText("Username and Password can't be empty!");
        else if (!usernameTextField.getText().isEmpty() && passwordTextField.getText().isEmpty() && passwordConfirmedTextField.getText().isEmpty())
            errorLabel.setText("Password can't be empty!");
        else if (usernameTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty() && !passwordConfirmedTextField.getText().isEmpty())
            errorLabel.setText("Username can't be empty!");
        else {
            message.sendRegisterMessage(SendToServer, usernameTextField.getText(), passwordTextField.getText(),passwordConfirmedTextField.getText(), nameTextField.getText(),surnameTextField.getText());
            try{
                handleServerResponse(ReadFromServer.readLine());
            }catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("Error reading message: " + ex.getMessage());
            }

        }
    }
}