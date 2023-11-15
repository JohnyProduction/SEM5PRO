package com.projektsemv.clubmanagement;

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

public class LoginPanelController implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button signInButton, registerButton;

    @FXML
    private Label errorLabel, loginTitleLabel;

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
                ChangeController.changeScene(actionEvent,"register-panel.fxml","Panel rejestracji",null);
            }
        });

        //TODO: wrzuciłem to co niżej no i wywala błędy
        signInButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent,"maneger-club-page.fxml","Lista użytkowników",null);
            }
        });


    }

}