/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacymanagementsystem;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author mo989
 */
public class LoginScreen {
    private TextField username;
    private PasswordField password;
    private Stage stage;

    public void start(Stage primaryStage) {
        this.stage = primaryStage;

        Label userLabel = new Label("Username:");
        username = new TextField();

        Label passLabel = new Label("Password:");
        password = new PasswordField();

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> handleLogin());

        VBox layout = new VBox(10, userLabel, username, passLabel, password, loginBtn);
        layout.setStyle("-fx-padding: 20");

        primaryStage.setScene(new Scene(layout, 300, 200));
        primaryStage.setTitle("Pharmacy Login");
        primaryStage.show();
    }

    public void handleLogin() {
        String userName = username.getText();
        String passWord = password.getText();
        UserManager userManager = UserManager.getInstance();

        for (User user : userManager.getUsers()) {
            if (user.getUsername().equals(userName) && user.getPassword().equals(passWord)) {
                new MainLayout(stage, user.getFullName()); // or username
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText("Invalid credentials");
        alert.setContentText("Username or password is incorrect.");
        alert.showAndWait();
    }
}
