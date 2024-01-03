package com.projektsemv.clubmanagement;

import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.UserFunction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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


                if(status){
                    ChangeController.changeScene(actionEvent,"login-panel.fxml","Panel logowania!", null);
                }else{
                    errorLabel.setText("Błąd rejestracji");
                    errorLabel.setStyle("-fx-text-fill: RED;");
                }
            }
        });

    }
    private void handleServerResponse(String response) {
        status = Client.switchLoginClient(response);
    }

}