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
import java.io.*;

import static com.projektsemv.clubmanagement.UserInfo.UserType.*;

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
    //    /* Make the label with the fx:id of 'errorLabel' change its text and color on Sign in Button click */
    protected void onSignInButtonClick() {
        errorLabel.setText("Podane dane są błędne!");
        errorLabel.setStyle("-fx-text-fill: RED;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
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
                sendLoginDataToServer(usernameTextField.getText(),passwordTextField.getText());
                if(status){
                    ChangeController.changeScene(actionEvent, "club-page-member.fxml", "Strona klubu", MEMBER);
                }else{
                    errorLabel.setText("Błąd logowania");
                    errorLabel.setStyle("-fx-text-fill: RED;");
                }
                //System.out.println(usernameTextField.getText());
                /*
                if(usernameTextField.getText().equals("fan")) {
                    ChangeController.changeScene(actionEvent, "club-page-fan.fxml", "Strona klubu", FAN);
                }else if((usernameTextField.getText().equals("m"))) {
                }else{
                    ChangeController.changeScene(actionEvent, "club-page-manager.fxml", "Strona klubu", MANAGER);

                }*/
            }
        });


    }
    private static void handleServerResponse(String response) {
        if ("SUCCESS".equals(response)) {
            status = true;
        } else {
           status = false;
        }
    }
    private static void sendLoginDataToServer(String username, String password) {
            try {
                Socket socket = new Socket("localhost", 12345);
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                // Wysłanie danych do serwera w określonym formacie, na przykład:
                writer.println("LOGIN|" + username + "|" + password);

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = reader.readLine();
                System.out.println(response);
                handleServerResponse(response);
                writer.close();
                reader.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
}