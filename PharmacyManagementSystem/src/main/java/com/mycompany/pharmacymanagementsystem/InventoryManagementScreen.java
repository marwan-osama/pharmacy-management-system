/*
 * Click nfs://nbsp/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nfs://nbsp/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import java.util.List;
import javafx.scene.text.Font;

public class InventoryManagementScreen {

    private ObservableList<PharmacyItem> inventory = FXCollections.observableArrayList();

    public VBox getView() {
        TableView<PharmacyItem> inventoryTable = new TableView<>();
        inventory.setAll(generateSampleInventory());

        // Columns
        TableColumn<PharmacyItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));

        TableColumn<PharmacyItem, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getItemCategory()));

        TableColumn<PharmacyItem, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getQuantity()).asObject());

        TableColumn<PharmacyItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getPrice()).asObject());
        
        TableColumn<PharmacyItem, String> manufacturerCol = new TableColumn<>("Manufacturer");
        manufacturerCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getManufacturer()));

        inventoryTable.getColumns().addAll(nameCol, categoryCol, qtyCol, priceCol, manufacturerCol);
        inventoryTable.setItems(inventory);

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
            PharmacyItem selectedItem = inventoryTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                showItemDetails(selectedItem);
            } else {
                showAlert("No Selection", "Please select an item to view.");
            }
        });

        editButton.setOnAction(e -> {
            PharmacyItem selectedItem = inventoryTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                editItem(selectedItem, inventoryTable);
            } else {
                showAlert("No Selection", "Please select an item to edit.");
            }
        });

        deleteButton.setOnAction(e -> {
            PharmacyItem selectedItem = inventoryTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                inventory.remove(selectedItem);
                showAlert("Success", "Item deleted successfully.");
            } else {
                showAlert("No Selection", "Please select an item to delete.");
            }
        });

        addButton.setOnAction(e -> addItem(inventoryTable));

        // Layout
        HBox buttonBox = new HBox(10, viewButton, editButton, deleteButton, addButton);
        buttonBox.setPadding(new Insets(10));
        
        Label title = new Label("Inventory Management");
        title.setFont(Font.font("Arial", 16));
        title.setStyle("-fx-padding: 0.5em 0 0 0.5em;");

        return new VBox(10, title, inventoryTable, buttonBox);
    }

    private List<PharmacyItem> generateSampleInventory() {
        PharmacyItem item1 = new Medicine(1, "Paracetamol", 5.0, 10, "PharmaCorp", LocalDate.of(2025, 12, 1));
        PharmacyItem item2 = new MedicalSupply(2, "Bandage", 2.5, 20, "MediSafe", 5.0, "Cotton", MedicalSupply.Size.medium);
        return List.of(item1, item2);
    }

    private void showItemDetails(PharmacyItem item) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Item Details");
        alert.setHeaderText("Details for " + item.getName());

        StringBuilder details = new StringBuilder();
        details.append("Name: ").append(item.getName()).append("\n");
        details.append("Quantity: ").append(item.getQuantity()).append("\n");
        details.append("Price: $").append(String.format("%.2f", item.getPrice())).append("\n");
        details.append("Manufacturer: ").append(item.getManufacturer()).append("\n");

        if (item instanceof Medicine) {
            Medicine medicine = (Medicine) item;
            details.append("Expiry Date: ").append(medicine.getExpiryDate()).append("\n");
            details.append("User Instructions: ").append(medicine.getUsageInstructions()).append("\n");

        } else if (item instanceof MedicalSupply) {
            MedicalSupply supply = (MedicalSupply) item;
            details.append("MaterialType: ").append(supply.getMaterialType()).append("\n");
            details.append("Size: ").append(supply.getSize()).append("\n");
        }

        alert.setContentText(details.toString());
        alert.getDialogPane().setMinWidth(300);
        alert.showAndWait();
    }

    private void editItem(PharmacyItem item, TableView<PharmacyItem> table) {
        Dialog<PharmacyItem> dialog = new Dialog<>();
        dialog.setTitle("Edit Item");
        dialog.setHeaderText("Edit the item details:");

        // Set up the fields based on item type
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField(item.getName());
        TextField quantityField = new TextField(String.valueOf(item.getQuantity()));
        TextField priceField = new TextField(String.valueOf(item.getPrice()));
        TextField manufacturerField = new TextField(item.getManufacturer());
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Quantity:"), 0, 1);
        grid.add(quantityField, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(new Label("Manufacturer:"), 0, 3);
        grid.add(manufacturerField, 1, 3);

        if (item instanceof Medicine) {
            Medicine medicine = (Medicine) item;
            TextField usageInstructionsField = new TextField(medicine.getUsageInstructions());
            DatePicker expiryDateField = new DatePicker(medicine.getExpiryDate());
            grid.add(new Label("Usage Instructions:"), 0, 4);
            grid.add(usageInstructionsField, 1, 4);
            grid.add(new Label("Expiry Date:"), 0, 5);
            grid.add(expiryDateField, 1, 5);
        } else if (item instanceof MedicalSupply) {
            MedicalSupply supply = (MedicalSupply) item;
            TextField materialTypeField = new TextField(supply.getMaterialType());
            ChoiceBox<MedicalSupply.Size> sizeField = new ChoiceBox<>();
            sizeField.getItems().addAll(MedicalSupply.Size.values());
            sizeField.setValue(supply.getSize());
            grid.add(new Label("Material Type:"), 0, 4);
            grid.add(materialTypeField, 1, 4);
            grid.add(new Label("Size:"), 0, 5);
            grid.add(sizeField, 1, 5);
        }

        dialog.getDialogPane().setContent(grid);

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == saveButton) {
                item.setName(nameField.getText());
                item.setQuantity(Integer.parseInt(quantityField.getText()));
                item.setPrice(Double.parseDouble(priceField.getText()));
                item.setManufacturer(manufacturerField.getText());
                if (item instanceof Medicine) {
                    Medicine medicine = (Medicine) item;
                    medicine.setUsageInstructions(((TextField) grid.getChildren().get(8)).getText());
                    medicine.setExpiryDate(((DatePicker) grid.getChildren().get(10)).getValue());
                } else if (item instanceof MedicalSupply) {
                    MedicalSupply supply = (MedicalSupply) item;
                    supply.setMaterialType(((TextField) grid.getChildren().get(8)).getText());
                    supply.setSize(((ChoiceBox<MedicalSupply.Size>) grid.getChildren().get(10)).getValue());
                }
                table.refresh();
                showAlert("Success", "Item edited successfully.");
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void addItem(TableView<PharmacyItem> table) {
        Dialog<PharmacyItem> dialog = new Dialog<>();
        dialog.setTitle("Add Item");
        dialog.setHeaderText("Enter new item details:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ToggleGroup itemTypeGroup = new ToggleGroup();
        RadioButton medicineRadio = new RadioButton("Medicine");
        RadioButton supplyRadio = new RadioButton("Medical Supply");
        medicineRadio.setToggleGroup(itemTypeGroup);
        supplyRadio.setToggleGroup(itemTypeGroup);
        medicineRadio.setSelected(true);
        grid.add(new Label("Item Type:"), 0, 0);
        grid.add(medicineRadio, 1, 0);
        grid.add(supplyRadio, 2, 0);

        TextField nameField = new TextField();
        TextField quantityField = new TextField();
        TextField priceField = new TextField();
        TextField manufacturerField = new TextField();
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Quantity:"), 0, 2);
        grid.add(quantityField, 1, 2);
        grid.add(new Label("Price:"), 0, 3);
        grid.add(priceField, 1, 3);
        grid.add(new Label("Manufactureer:"), 0, 4);
        grid.add(manufacturerField, 1, 4);

        // Medicine fields
        TextField usageInstructionsField = new TextField();
        DatePicker expiryDateField = new DatePicker();
        grid.add(new Label("Usage Instructions:"), 0, 5);
        grid.add(usageInstructionsField, 1, 5);
        grid.add(new Label("Expiry Date:"), 0, 6);
        grid.add(expiryDateField, 1, 6);

        // Medical Supply fields
        TextField materialTypeField = new TextField();
        ChoiceBox<MedicalSupply.Size> sizeField = new ChoiceBox<>();
        sizeField.getItems().addAll(MedicalSupply.Size.values());
        sizeField.setValue(MedicalSupply.Size.small);
        grid.add(new Label("Material Type:"), 0, 7);
        grid.add(materialTypeField, 1, 7);
        grid.add(new Label("Size:"), 0, 8);
        grid.add(sizeField, 1, 8);

        // Toggle visibility based on selection
        materialTypeField.setVisible(false);
        sizeField.setVisible(false);
        itemTypeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            boolean isMedicine = medicineRadio.isSelected();
            usageInstructionsField.setVisible(isMedicine);
            expiryDateField.setVisible(isMedicine);
            materialTypeField.setVisible(!isMedicine);
            sizeField.setVisible(!isMedicine);
        });

        dialog.getDialogPane().setContent(grid);

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == saveButton) {
                int id = inventory.size() + 1;
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());
                if (medicineRadio.isSelected()) {
                    String manufacturer = manufacturerField.getText();
                    LocalDate expiryDate = expiryDateField.getValue();
                    PharmacyItem newItem = new Medicine(id, name, price, quantity, manufacturer, expiryDate);
                    inventory.add(newItem);
                } else {
                    String materialType = materialTypeField.getText();
                    String manufacturer = manufacturerField.getText();
                    MedicalSupply.Size size = sizeField.getValue();
                    PharmacyItem newItem = new MedicalSupply(id, name, price, quantity, manufacturer, materialType, size);
                    inventory.add(newItem);
                }
                table.refresh();
                showAlert("Success", "Item added successfully.");
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