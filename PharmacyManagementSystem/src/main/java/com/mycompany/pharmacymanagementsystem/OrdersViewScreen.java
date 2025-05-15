/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.pharmacymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javafx.scene.text.Font;

public class OrdersViewScreen {

    public VBox getView() {
        TableView<Order> ordersTable = new TableView<>();
        ObservableList<Order> orders = FXCollections.observableArrayList(generateSampleOrders());

        // Columns
        TableColumn<Order, Integer> orderIdCol = new TableColumn<>("Order ID");
        orderIdCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getOrderID()).asObject());

        TableColumn<Order, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getOrderDate()));

        TableColumn<Order, Double> totalCol = new TableColumn<>("Total Amount");
        totalCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getTotalAmount()).asObject());

        ordersTable.getColumns().addAll(orderIdCol, dateCol, totalCol);
        ordersTable.setItems(orders);

        // Detail view
        Text details = new Text("Select an order to see details...");

        VBox detailsWrapper = new VBox(details);
        detailsWrapper.setPadding(new Insets(0, 0, 0, 10));

        ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Order ID: ").append(newSelection.getOrderID()).append("\n");
                sb.append("Date: ").append(newSelection.getOrderDate()).append("\n");
                sb.append("Cashier: ").append(newSelection.getCashier().getUsername()).append("\n");
                sb.append("Total: $").append(newSelection.getTotalAmount()).append("\n");
                sb.append("Items:\n");
                for (PharmacyItem item : newSelection.getItems()) {
                    sb.append(" - ").append(item.getName()).append(", Qty: ").append(item.getQuantity()).append("\n");
                }
                details.setText(sb.toString());
            }
        });
        
        Label title = new Label("Orders History");
        title.setFont(Font.font("Arial", 16));
        title.setStyle("-fx-padding: 0.5em 0 0 0.5em;");

        return new VBox(10, title, ordersTable, detailsWrapper);
    }

    private List<Order> generateSampleOrders() {
        PharmacyItem item1 = new Medicine(4, "Paracetamol", 5.0, 10, "PharmaCorp", LocalDate.of(2025, 12, 1));
        PharmacyItem item2 = new MedicalSupply(5, "Bandage", 2.5, 20, "MediSafe", 5, "Cotton", MedicalSupply.Size.medium);

        Cashier cashier = new Cashier("john123", "john", "pass", "john doe", 123456);

        Order order1 = new Order(1001, cashier, Arrays.asList(item1, item2), LocalDate.of(2025, 5, 10));
        order1.calculateTotalAmount();

        Order order2 = new Order(1002, cashier, List.of(item1), LocalDate.of(2025, 5, 12));
        order2.calculateTotalAmount();

        return Arrays.asList(order1, order2);
    }
}
