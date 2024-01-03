package com.projektsemv.clubmanagement;

<<<<<<< Updated upstream
import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.UserFunction;
import com.projektsemv.clubmanagement.UserFunction.Message;
=======
import com.projektsemv.clubmanagement.UserFunction.SocketClient;
import com.projektsemv.clubmanagement.UserFunction.UserFunctions;
import com.projektsemv.clubmanagement.UserFunction.UserInfo.*;
>>>>>>> Stashed changes
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

<<<<<<< Updated upstream
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.PrintWriter;
import java.io.BufferedReader;

import static com.projektsemv.clubmanagement.UserInfo.UserType.*;
=======
import java.net.URL;
import java.util.ResourceBundle;
>>>>>>> Stashed changes

public class LoginPanelController implements Initializable {

    @FXML
    private Button signInButton, registerButton;

    @FXML
    public Label errorLabel;
    @FXML
    public Label loginTitleLabel;
    @FXML
    public Label username;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    public static boolean status;
<<<<<<< Updated upstream
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    private static int userRole;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        LoginPanelController.ReadFromServer = Client.ReadFromServer;
        LoginPanelController.SendToServer = Client.SendToServer;

        status=false;
=======
    private SocketClient socketClient;
    protected void onSignInButtonClick() {
        errorLabel.setText("Podane dane są błędne!");
        errorLabel.setStyle("-fx-text-fill: RED;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        status = false;
        socketClient = SocketClient.getInstance("localhost", 12345);

>>>>>>> Stashed changes
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "register-panel.fxml", "Panel rejestracji", null);
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
<<<<<<< Updated upstream

                try {
                    checkInputs();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(status){
                    if(userRole == 1){
                        ChangeController.changeScene(actionEvent, "club-page-member.fxml", "Strona klubu", MEMBER);
                    } else if (userRole == 2) {
                        ChangeController.changeScene(actionEvent, "club-page-manager.fxml", "Strona klubu", MANAGER);
                    } else if (userRole == 3) {
                        ChangeController.changeScene(actionEvent, "club-page-fan.fxml", "Strona klubu", FAN);
                    }
                }else{
                    errorLabel.setText("ERROR! Błąd logowania!");
=======
                sendLoginDataToServer(usernameTextField.getText(), passwordTextField.getText());
                if (status) {
                    //System.out.println(socketClient.receiveMessage());
                    ChangeController.changeScene(actionEvent, "club-page-member.fxml", "Strona klubu", UserType.MEMBER);
                } else {
                    errorLabel.setText("Błąd logowania");
                    errorLabel.setStyle("-fx-text-fill: RED;");
>>>>>>> Stashed changes
                }
            }
        });
    }
<<<<<<< Updated upstream
    private void handleServerResponse(String response) {
        status = Client.switchLoginClient(response);
    }
    private void checkInputs()throws IOException{
        if (usernameTextField.getText().isEmpty() && passwordTextField.getText().isEmpty())
            errorLabel.setText("Username and Password can't be empty!");
        else if (!usernameTextField.getText().isEmpty() && passwordTextField.getText().isEmpty())
            errorLabel.setText("Password can't be empty!");
        else if (usernameTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty())
            errorLabel.setText("Username can't be empty!");
        else {
            message.sendLoginMessage(SendToServer, usernameTextField.getText(), passwordTextField.getText());
            try{
                userRole = Integer.parseInt(ReadFromServer.readLine());
                handleServerResponse(ReadFromServer.readLine());
            }catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("Error reading message: " + ex.getMessage());
            }

        }
    }

}
=======

    private static void handleServerResponse(String response) {
        status = UserFunctions.SwitchLoginClient(response);
    }

    private void sendLoginDataToServer(String username, String password) {


        try {
            socketClient.sendMessage("LOGIN|" + username + "|" + password);
            handleServerResponse(socketClient.receiveMessage());
        } finally {
            //socketClient.closeConnection();
        }
    }
}
>>>>>>> Stashed changes
