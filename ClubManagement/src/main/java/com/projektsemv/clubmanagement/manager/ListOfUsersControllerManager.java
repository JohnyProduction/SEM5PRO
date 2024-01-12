package com.projektsemv.clubmanagement.manager;

import com.projektsemv.clubmanagement.ChangeController;
import com.projektsemv.clubmanagement.UserFunction.Client;
import com.projektsemv.clubmanagement.UserFunction.Roles;
import com.projektsemv.clubmanagement.UserFunction.Message;
import com.projektsemv.clubmanagement.UserFunction.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ChoiceBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

import static com.projektsemv.clubmanagement.UserFunction.UserInfo.UserType.*;

public class ListOfUsersControllerManager implements Initializable {
    /*Import JavaFX controls*/
    @FXML
    private Button buttonOption1, buttonOption2, buttonOption3, buttonOptions, buttonLogOut;
    @FXML
    private Label username;
    private static BufferedReader ReadFromServer;
    private static PrintWriter SendToServer;
    private static final Message message = new Message();
    @FXML
    private TextField userListUserID,userListUsername,userListPassword,userListEmail,userListName,userListSurname;
    @FXML
    private Button userListEdit,userListDelete;
    @FXML
    private ChoiceBox<Roles> userListRole;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, String> userListColUserID;
    @FXML
    private TableColumn<User, String> userListColUsername;
    @FXML
    private TableColumn<User, String> userListColPassword;
    @FXML
    private TableColumn<User, String> userListColName;
    @FXML
    private TableColumn<User, String> userListColSurname;
    @FXML
    private TableColumn<User, String> userListColEmail;
    @FXML
    private TableColumn<User, String> userListColRole;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ListOfUsersControllerManager.ReadFromServer = Client.ReadFromServer;
        ListOfUsersControllerManager.SendToServer = Client.SendToServer;

        preparePage();
        initializeTableView();

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Check for a single mouse click
                User selectedUser = tableView.getSelectionModel().getSelectedItem();

                if (selectedUser != null) {
                    // Now you have access to the values in the selected row
                    userListUserID.setText(selectedUser.getUserID());
                    userListUsername.setText(selectedUser.getUsername());
                    userListPassword.setText(selectedUser.getPassword());
                    userListEmail.setText(selectedUser.getEmail());
                    userListName.setText(selectedUser.getName());
                    userListSurname.setText(selectedUser.getSurname());

                    // Set the user role in the ChoiceBox
                    for (Roles role : userListRole.getItems()) {
                        if (role.getType().equals(selectedUser.getRole())) {
                            userListRole.getSelectionModel().select(role);
                            break;
                        }
                    }

                }
            }
        });
        buttonLogOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "login-panel.fxml", "Panel logowania", null);
            }
        });
        buttonOption1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "club-page-manager.fxml", "Strona klubu", MANAGER);
            }
        });

        buttonOption3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "messages-panel-manager.fxml", "Wiadomości", MANAGER);
            }
        });
        buttonOptions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ChangeController.changeScene(actionEvent, "settings-page-manager.fxml", "Ustawienia", MANAGER);
            }
        });
        userListDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tableView.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 1) { // Check for a single mouse click
                        User selectedUser = tableView.getSelectionModel().getSelectedItem();

                        if (selectedUser != null) {
                            // Now you have access to the values in the selected row
                            message.sendDeleteUser(SendToServer,selectedUser.getUserID());
                        }
                    }
                });

                ChangeController.changeScene(actionEvent, "list-of-users-manager.fxml", "Lista użytkowników", MANAGER);
            }
        });
        userListEdit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                message.sendUpdateUser(SendToServer,userListUserID.getText(),userListUsername.getText(),userListPassword.getText(),userListName.getText(),userListSurname.getText(),userListEmail.getText(), String.valueOf(Integer.parseInt(String.valueOf(userListRole.getSelectionModel().getSelectedIndex()))+1));
                ChangeController.changeScene(actionEvent, "list-of-users-manager.fxml", "Lista użytkowników", MANAGER);
            }
        });
    }
    private void preparePage() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    // Perform time-consuming operations (e.g., reading from the server) here
                    message.sendUserListPage(SendToServer);
                    String serverResponse = ReadFromServer.readLine();

                    // Update the UI on the JavaFX application thread
                    Platform.runLater(() -> username.setText(serverResponse));
                    String userRolesResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = userRolesResponse.split("\\|");
                        if(values[0].equals("USERROLES")){
                            System.out.println(userRolesResponse);
                            // Check if there are enough values to fill the labels
                            if (values.length >= 5) {
                                // Set values to the respective labels
                                ObservableList<Roles> roles = FXCollections.observableArrayList();
                                for (int i = 1; i < values.length; i += 2) {
                                    roles.add(new Roles(Integer.parseInt(values[i]), values[i + 1]));
                                }
                                userListRole.setItems(roles);

                            } else {
                                // Handle the case where there are not enough values
                                System.out.println("Invalid data received from the server");
                            }
                        }else{
                            System.out.println("Error getting user roles data");
                        }
                    });
                    String userListResponse = ReadFromServer.readLine();
                    Platform.runLater(() -> {
                        // Split the received data into an array of values
                        String[] values = userListResponse.split("\\|");
                        if(values[0].equals("USERLIST")){
                            System.out.println(userListResponse);
                            // Check if there are enough values to fill the labels
                            if (values.length >= 3) {
                                // Set values to the respective labels
                                ObservableList<User> data = FXCollections.observableArrayList();
                                for (int i = 1; i + 6 <= values.length; i += 7) {
                                    data.add(new User(values[i], values[i + 1], values[i +  2],values[i + 3], values[i + 4], values[i + 5],values[i + 6]));
                                }
                                tableView.setItems(data);
                            } else {
                                // Handle the case where there are not enough values
                                System.out.println("Invalid data received from the server");
                            }
                        }else{
                            System.out.println("Error getting user list data");
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        // Start the task in a new thread
        new Thread(task).start();
    }
    private void initializeTableView() {
        // Initialize columns
        userListColUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        userListColUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        userListColPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        userListColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        userListColSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        userListColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        userListColRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        // You may want to set the cell factory for each column if you want custom rendering or editing
    }
}