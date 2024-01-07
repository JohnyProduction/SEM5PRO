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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.io.PrintWriter;
import java.io.BufferedReader;
import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.*;

public class LoginPanelController implements Initializable {

    /*Import JavaFX controls*/
    @FXML
    private Button signInButton, registerButton;

    @FXML
    public  Label errorLabel;
    @FXML
    public Label loginTitleLabel;
    @FXML
    public Label username;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    public static boolean status;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    private static int userRole;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        LoginPanelController.ReadFromServer = Client.ReadFromServer;
        LoginPanelController.SendToServer = Client.SendToServer;

        status=false;
        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent,"register-panel.fxml","Panel rejestracji", null);
            }
        });

        signInButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {

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
                }
            }
        });


    }
    private void handleServerResponse(String response) {
        status = UserFunctions.switchLoginClient(response);
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
                System.out.println(userRole);
                handleServerResponse(ReadFromServer.readLine());
            }catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("Error reading message: " + ex.getMessage());
            }

        }
    }
}