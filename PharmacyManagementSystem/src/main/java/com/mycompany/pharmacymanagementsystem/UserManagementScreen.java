/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javafx.scene.text.Font;

public class UserManagementScreen {

    public VBox getView() {
        TableView<User> userTable = new TableView<>();
        ObservableList<User> users = FXCollections.observableArrayList(generateSampleUsers());

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

        Label title = new Label("User Management");
        title.setFont(Font.font("Arial", 16));
        title.setStyle("-fx-padding: 0.5em 0 0 0.5em;");
        
        return new VBox(10, title, userTable);
    }

    private List<User> generateSampleUsers() {
        Admin admin1 = new Admin("4", "alice", "password1", "Alice Smith", 123456789);
        Admin admin2 = new Admin("5", "bob", "securebob", "Bob Johnson", 987654321);
        return Arrays.asList(admin1, admin2);
    }
}
