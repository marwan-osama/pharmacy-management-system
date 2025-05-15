/*
 * Click nfs://nbsp/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nfs://nbsp/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacymanagementsystem;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

        // Title label
        Label titleLabel = new Label("Pharmacy Login");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");

        // Username field
        Label userLabel = new Label("Username:");
        userLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 14px;");
        username = new TextField();
        username.setPromptText("Enter your username");
        username.setStyle("-fx-background-color: #ffffff; -fx-border-color: #34495e; -fx-border-radius: 5; -fx-background-radius: 5; -fx-font-size: 14px;");
        username.setMaxWidth(300);

        // Password field
        Label passLabel = new Label("Password:");
        passLabel.setStyle("-fx-text-fill: #2c3e50; -fx-font-size: 14px;");
        password = new PasswordField();
        password.setPromptText("Enter your password");
        password.setStyle("-fx-background-color: #ffffff; -fx-border-color: #34495e; -fx-border-radius: 5; -fx-background-radius: 5; -fx-font-size: 14px;");
        password.setMaxWidth(300);

        // Login button
        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: #34495e; -fx-text-fill: #ffffff; -fx-font-size: 14px; -fx-background-radius: 5; -fx-padding: 10 20;");
        loginBtn.setOnAction(e -> handleLogin());
        loginBtn.setMaxWidth(150);

        // Layout
        VBox layout = new VBox(20, titleLabel, userLabel, username, passLabel, password, loginBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #34495e); -fx-padding: 40; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        layout.setMaxWidth(400);
        layout.setMaxHeight(450);
        layout.setStyle(layout.getStyle() + " -fx-background-color: #ffffff;");

        // Scene setup
        Scene scene = new Scene(layout, 800, 600);
        scene.setFill(javafx.scene.paint.Color.web("#2c3e50"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pharmacy Login");
        primaryStage.show();
    }

    public void handleLogin() {
        String userName = username.getText();
        String passWord = password.getText();
        UserManager userManager = UserManager.getInstance();

        for (User user : userManager.getUsers()) {
            if (user.getUsername().equals(userName) && user.getPassword().equals(passWord)) {
                new MainLayout(stage, user.getFullName());
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