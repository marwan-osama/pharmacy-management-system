/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javafx.scene.text.Font;

public class UserManagementScreen {

    private ObservableList<User> users = FXCollections.observableArrayList();
    
    private boolean isAdmin;
    
    public UserManagementScreen(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public VBox getView() {
        TableView<User> userTable = new TableView<>();
        users.setAll(generateSampleUsers());

        // Columns
        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getUsername()));

        TableColumn<User, String> fullNameCol = new TableColumn<>("Full Name");
        fullNameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getFullName()));

        TableColumn<User, Integer> phoneCol = new TableColumn<>("Phone Number");
        phoneCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getPhoneNumber()).asObject());

        TableColumn<User, LocalDate> regDateCol = new TableColumn<>("Registered");
        regDateCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getRegistrationDate()));

        userTable.getColumns().addAll(usernameCol, fullNameCol, phoneCol, regDateCol);
        userTable.setItems(users);

        // Buttons for actions
        Button viewButton = new Button("View");
        viewButton.setMinWidth(60.0);
        Button editButton = new Button("Edit");
        editButton.setMinWidth(60.0);
        Button deleteButton = new Button("Delete");
        deleteButton.setMinWidth(60.0);
        Button addButton = new Button("Add");
        addButton.setMinWidth(60.0);

        // Action handlers
        viewButton.setOnAction(e -> {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                showUserDetails(selectedUser);
            } else {
                showAlert("No Selection", "Please select a user to view.");
            }
        });

        editButton.setOnAction(e -> {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                editUser(selectedUser, userTable);
            } else {
                showAlert("No Selection", "Please select a user to edit.");
            }
        });

        deleteButton.setOnAction(e -> {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                users.remove(selectedUser);
                showAlert("Success", "User deleted successfully.");
            } else {
                showAlert("No Selection", "Please select a user to delete.");
            }
        });

        addButton.setOnAction(e -> addUser(userTable));

        // Layout
        HBox buttonBox;
        
        if (this.isAdmin) {
            buttonBox = new HBox(10, viewButton, editButton, deleteButton, addButton);
        } else {
            buttonBox = new HBox(10, viewButton);
        }

        buttonBox.setPadding(new Insets(10));
        
        Label title = new Label("User Management");
        title.setFont(Font.font("Arial", 16));
        title.setStyle("-fx-padding: 0.5em 0 0 0.5em;");

        return new VBox(10, title, userTable, buttonBox);
    }

    private List<User> generateSampleUsers() {
        Admin admin1 = new Admin("4", "alice", "password1", "Alice Smith", 123456789);
        Admin admin2 = new Admin("5", "bob", "securebob", "Bob Johnson", 987654321);
        return Arrays.asList(admin1, admin2);
    }

    private void showUserDetails(User user) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Details");
        alert.setHeaderText("Details for " + user.getUsername());

        StringBuilder details = new StringBuilder();
        details.append("Username: ").append(user.getUsername()).append("\n");
        details.append("Full Name: ").append(user.getFullName()).append("\n");
        details.append("Phone Number: ").append(user.getPhoneNumber()).append("\n");
        details.append("Registration Date: ").append(user.getRegistrationDate()).append("\n");

        if (user instanceof Admin) {
            details.append("User Type: Admin\n");
        } else if (user instanceof Cashier) {
            details.append("User Type: Cashier\n");
        }

        alert.setContentText(details.toString());
        alert.getDialogPane().setMinWidth(300);
        alert.showAndWait();
    }

    private void editUser(User user, TableView<User> table) {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Edit User");
        dialog.setHeaderText("Edit the user details:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField usernameField = new TextField(user.getUsername());
        TextField fullNameField = new TextField(user.getFullName());
        TextField phoneField = new TextField(String.valueOf(user.getPhoneNumber()));
        TextField passwordField = new TextField(user.getPassword());

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Full Name:"), 0, 1);
        grid.add(fullNameField, 1, 1);
        grid.add(new Label("Phone Number:"), 0, 2);
        grid.add(phoneField, 1, 2);
        grid.add(new Label("Password:"), 0, 3);
        grid.add(passwordField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == saveButton) {
                user.setUsername(usernameField.getText());
                user.setFullName(fullNameField.getText());
                user.setPhoneNumber(Integer.parseInt(phoneField.getText()));
                user.setPassword(passwordField.getText());
                table.refresh();
                showAlert("Success", "User edited successfully.");
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void addUser(TableView<User> table) {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Add User");
        dialog.setHeaderText("Enter new user details:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ToggleGroup userTypeGroup = new ToggleGroup();
        RadioButton adminRadio = new RadioButton("Admin");
        RadioButton cashierRadio = new RadioButton("Cashier");
        adminRadio.setToggleGroup(userTypeGroup);
        cashierRadio.setToggleGroup(userTypeGroup);
        adminRadio.setSelected(true);
        grid.add(new Label("User Type:"), 0, 0);
        grid.add(adminRadio, 1, 0);
        grid.add(cashierRadio, 2, 0);

        TextField usernameField = new TextField();
        TextField fullNameField = new TextField();
        TextField phoneField = new TextField();
        TextField passwordField = new TextField();

        grid.add(new Label("Username:"), 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(new Label("Full Name:"), 0, 2);
        grid.add(fullNameField, 1, 2);
        grid.add(new Label("Phone Number:"), 0, 3);
        grid.add(phoneField, 1, 3);
        grid.add(new Label("Password:"), 0, 4);
        grid.add(passwordField, 1, 4);

        dialog.getDialogPane().setContent(grid);

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == saveButton) {
                String id = String.valueOf(users.size() + 1);
                String username = usernameField.getText();
                String password = passwordField.getText();
                String fullName = fullNameField.getText();
                int phone = Integer.parseInt(phoneField.getText());

                User newUser;
                if (adminRadio.isSelected()) {
                    newUser = new Admin(id, username, password, fullName, phone);
                } else {
                    newUser = new Cashier(id, username, password, fullName, phone);
                }
                users.add(newUser);
                table.refresh();
                showAlert("Success", "User added successfully.");
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}