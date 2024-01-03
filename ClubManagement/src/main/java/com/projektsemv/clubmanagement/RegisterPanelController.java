package com.projektsemv.clubmanagement;

<<<<<<< Updated upstream
import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.UserFunction;
=======
import com.projektsemv.clubmanagement.UserFunction.SocketClient;
import com.projektsemv.clubmanagement.UserFunction.UserFunctions;
>>>>>>> Stashed changes
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

    @FXML
    private Button signInButton, registerButton;

    @FXML
    private Label errorLabel, loginTitleLabel;

    @FXML
    private TextField usernameTextField, emailTextField;

    @FXML
    private PasswordField passwordTextField, passwordConfirmedTextField;

    public static boolean status;

    protected void onRegisterButtonClick() {

    }

    protected void createAccount() {

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
<<<<<<< Updated upstream


                if(status){
                    ChangeController.changeScene(actionEvent,"login-panel.fxml","Panel logowania!", null);
                }else{
=======
                sendRegisterDataToServer(usernameTextField.getText(), passwordTextField.getText(), emailTextField.getText());
                if (status) {
                    ChangeController.changeScene(actionEvent, "login-panel.fxml", "Panel logowania!", null);
                } else {
>>>>>>> Stashed changes
                    errorLabel.setText("Błąd rejestracji");
                    errorLabel.setStyle("-fx-text-fill: RED;");
                }
            }
        });

    }
<<<<<<< Updated upstream
    private void handleServerResponse(String response) {
        status = Client.switchLoginClient(response);
    }

}
=======

    private static void handleServerResponse(String response) {
        status = UserFunctions.SwitchLoginClient(response);
    }

    private static void sendRegisterDataToServer(String username, String password, String email) {
        SocketClient socketClient = SocketClient.getInstance("localhost", 12345);

        try {
            socketClient.sendMessage("REGISTER|" + username + "|" + password + "|" + email);
            handleServerResponse(socketClient.receiveMessage());
        } finally {
            socketClient.closeConnection();
        }
    }
}
>>>>>>> Stashed changes
