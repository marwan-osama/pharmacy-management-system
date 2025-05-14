// This is a skeleton of JavaFX GUI screens for the Pharmacy Management System
// Each screen is implemented as a separate class to maintain modularity.
package com.mycompany.pharmacymanagementsystem;

// App.java
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        UserManager userManager = UserManager.getInstance();
        Admin admin1 = new Admin("1", "admin", "admin", "marwan osama", 12345678);
        userManager.addUser(admin1);
        
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.start(primaryStage);
    }

    public static void main(String[] args) {
        launch();
    }
}
