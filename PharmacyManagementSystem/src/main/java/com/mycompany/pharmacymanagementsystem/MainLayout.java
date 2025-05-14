package com.mycompany.pharmacymanagementsystem;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainLayout {
    private BorderPane root;

    private Label welcomeLabel;

    public MainLayout(Stage stage, String username) {
        root = new BorderPane();

        // Header (Top)
        HBox header = new HBox(10);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white;");
        Image userIconImage = new Image(getClass().getResource("/images/user_icon.png").toExternalForm());
        ImageView userIcon = new ImageView(userIconImage);
        userIcon.setFitHeight(40);
        userIcon.setFitWidth(40);
        welcomeLabel = new Label("Hello " + username + " !");
        welcomeLabel.setFont(Font.font("Arial", 18));
        welcomeLabel.setStyle("-fx-text-fill: white;");
        header.getChildren().addAll(userIcon, welcomeLabel);
        root.setTop(header);

        // Sidebar (Right)
        VBox sideNav = new VBox(10);
        sideNav.setPadding(new Insets(10));
        sideNav.setStyle("-fx-background-color: #34495e;");
        sideNav.setAlignment(Pos.TOP_CENTER);

        Button inventoryBtn = new Button("Inventory");
        Button ordersBtn = new Button("Orders");
        Button usersBtn = new Button("Users");

        inventoryBtn.setMaxWidth(Double.MAX_VALUE);
        ordersBtn.setMaxWidth(Double.MAX_VALUE);
        usersBtn.setMaxWidth(Double.MAX_VALUE);

        // Navigation actions
        inventoryBtn.setOnAction(e -> setContent(new InventoryManagementScreen().getView()));
        ordersBtn.setOnAction(e -> setContent(new OrdersViewScreen().getView()));
        usersBtn.setOnAction(e -> setContent(new UserManagementScreen().getView()));

        sideNav.getChildren().addAll(inventoryBtn, ordersBtn, usersBtn);
        root.setRight(sideNav);

        // Default center content
        setContent(new InventoryManagementScreen().getView());

        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Pharmacy Management System");
        stage.show();
    }

    public void setContent(Node content) {
        root.setCenter(content);
    }
}
