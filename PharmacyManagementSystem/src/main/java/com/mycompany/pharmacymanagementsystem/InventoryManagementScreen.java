/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javafx.scene.text.Font;

public class InventoryManagementScreen {

    private ObservableList<PharmacyItem> inventory = FXCollections.observableArrayList();

    public VBox getView() {
        TableView<PharmacyItem> inventoryTable = new TableView<>();
        inventory.setAll(generateSampleInventory());
        applyDiscountsToMedicalSupplies();
        sortInventory();

        // Columns
        TableColumn<PharmacyItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));

        TableColumn<PharmacyItem, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getItemCategory()));

        TableColumn<PharmacyItem, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getQuantity()).asObject());

        // Improved price column with better formatting
        TableColumn<PharmacyItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(cell -> {
            PharmacyItem item = cell.getValue();
            if (item instanceof MedicalSupply) {
                MedicalSupply supply = (MedicalSupply) item;
                return new javafx.beans.property.SimpleDoubleProperty(supply.getDiscountedPrice()).asObject();
            }
            return new javafx.beans.property.SimpleDoubleProperty(item.getPrice()).asObject();
        });
        
        // Format the price column to show currency
        priceCol.setCellFactory(col -> new TableCell<PharmacyItem, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", price));
                }
            }
        });
        
        TableColumn<PharmacyItem, String> manufacturerCol = new TableColumn<>("Manufacturer");
        manufacturerCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getManufacturer()));

        inventoryTable.getColumns().addAll(nameCol, categoryCol, qtyCol, priceCol, manufacturerCol);
        inventoryTable.setItems(inventory);

        // Buttons
        Button viewButton = new Button("View");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button addButton = new Button("Add");
        Button checkExpiryButton = new Button("Check Expiry");

        // Action handlers
        viewButton.setOnAction(e -> {
            PharmacyItem selected = inventoryTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showItemDetails(selected);
            } else {
                showAlert("No Selection", "Please select an item to view.");
            }
        });

        editButton.setOnAction(e -> {
            PharmacyItem selected = inventoryTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                if (showEditDialog(selected, inventoryTable)) {
                    applyDiscountsToMedicalSupplies();
                    inventoryTable.refresh();
                    showAlert("Success", "Item updated successfully!");
                }
            } else {
                showAlert("No Selection", "Please select an item to edit.");
            }
        });

        deleteButton.setOnAction(e -> {
            PharmacyItem selected = inventoryTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                inventory.remove(selected);
                showAlert("Success", "Item deleted successfully.");
            } else {
                showAlert("No Selection", "Please select an item to delete.");
            }
        });

        addButton.setOnAction(e -> {
            if (showAddDialog(inventoryTable)) {
                applyDiscountsToMedicalSupplies();
                inventoryTable.refresh();
            }
        });

        checkExpiryButton.setOnAction(e -> checkMedicineExpiry());

        // Layout
        HBox buttonBox = new HBox(10, viewButton, editButton, deleteButton, addButton, checkExpiryButton);
        buttonBox.setPadding(new Insets(10));
        
        Label title = new Label("Inventory Management");
        title.setFont(Font.font("Arial", 16));
        title.setStyle("-fx-padding: 0.5em 0 0 0.5em;");

        return new VBox(10, title, inventoryTable, buttonBox);
    }

    private boolean showEditDialog(PharmacyItem item, TableView<PharmacyItem> table) {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Edit Item");
        dialog.setHeaderText("Edit " + item.getName());

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

        TextField usageField = new TextField();
        DatePicker expiryField = new DatePicker();
        TextField materialField = new TextField();
        ChoiceBox<MedicalSupply.Size> sizeField = new ChoiceBox<>();
        
        if (item instanceof Medicine) {
            Medicine medicine = (Medicine) item;
            usageField.setText(medicine.getUsageInstructions());
            expiryField.setValue(medicine.getExpiryDate());
            
            grid.add(new Label("Usage Instructions:"), 0, 4);
            grid.add(usageField, 1, 4);
            grid.add(new Label("Expiry Date:"), 0, 5);
            grid.add(expiryField, 1, 5);
            
            // Hide medical supply fields
            materialField.setVisible(false);
            sizeField.setVisible(false);
        } else if (item instanceof MedicalSupply) {
            MedicalSupply supply = (MedicalSupply) item;
            materialField.setText(supply.getMaterialType());
            sizeField.getItems().addAll(MedicalSupply.Size.values());
            sizeField.setValue(supply.getSize());
            
            grid.add(new Label("Material Type:"), 0, 4);
            grid.add(materialField, 1, 4);
            grid.add(new Label("Size:"), 0, 5);
            grid.add(sizeField, 1, 5);
            
            // Hide medicine fields
            usageField.setVisible(false);
            expiryField.setVisible(false);
        }

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    // Basic validation
                    if (nameField.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Name cannot be empty");
                    }
                    
                    int quantity = Integer.parseInt(quantityField.getText().trim());
                    double price = Double.parseDouble(priceField.getText().trim());
                    
                    if (quantity < 0) {
                        throw new IllegalArgumentException("Quantity cannot be negative");
                    }
                    if (price < 0) {
                        throw new IllegalArgumentException("Price cannot be negative");
                    }
                    
                    // Update the item
                    item.setName(nameField.getText());
                    item.setQuantity(quantity);
                    item.setPrice(price);
                    item.setManufacturer(manufacturerField.getText());

                    if (item instanceof Medicine) {
                        Medicine medicine = (Medicine) item;
                        medicine.setUsageInstructions(usageField.getText());
                        medicine.setExpiryDate(expiryField.getValue());
                    } else if (item instanceof MedicalSupply) {
                        MedicalSupply supply = (MedicalSupply) item;
                        supply.setMaterialType(materialField.getText());
                        supply.setSize(sizeField.getValue());
                        supply.applyDiscount(); // Reapply discount after changes
                    }
                    return true;
                } catch (NumberFormatException e) {
                    showAlert("Error", "Please enter valid numbers for quantity and price");
                    return false;
                } catch (IllegalArgumentException e) {
                    showAlert("Error", e.getMessage());
                    return false;
                } catch (Exception e) {
                    showAlert("Error", "An error occurred: " + e.getMessage());
                    return false;
                }
            }
            return false;
        });

        return dialog.showAndWait().orElse(false);
    }

    private boolean showAddDialog(TableView<PharmacyItem> table) {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Add Item");
        dialog.setHeaderText("Enter new item details");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ToggleGroup typeGroup = new ToggleGroup();
        RadioButton medicineRadio = new RadioButton("Medicine");
        RadioButton supplyRadio = new RadioButton("Medical Supply");
        medicineRadio.setToggleGroup(typeGroup);
        supplyRadio.setToggleGroup(typeGroup);
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
        grid.add(new Label("Manufacturer:"), 0, 4);
        grid.add(manufacturerField, 1, 4);

        // Medicine fields
        TextField usageField = new TextField();
        DatePicker expiryField = new DatePicker(LocalDate.now().plusMonths(6)); // Default expiry 6 months
        grid.add(new Label("Usage Instructions:"), 0, 5);
        grid.add(usageField, 1, 5);
        grid.add(new Label("Expiry Date:"), 0, 6);
        grid.add(expiryField, 1, 6);

        // Medical Supply fields
        TextField materialField = new TextField();
        ChoiceBox<MedicalSupply.Size> sizeField = new ChoiceBox<>();
        sizeField.getItems().addAll(MedicalSupply.Size.values());
        sizeField.setValue(MedicalSupply.Size.medium);
        grid.add(new Label("Material Type:"), 0, 7);
        grid.add(materialField, 1, 7);
        grid.add(new Label("Size:"), 0, 8);
        grid.add(sizeField, 1, 8);

        // Toggle visibility
        materialField.setVisible(false);
        sizeField.setVisible(false);
        grid.lookup(".label:nth-of-type(7)").setVisible(false);
        grid.lookup(".label:nth-of-type(8)").setVisible(false);
        
        typeGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            boolean isMedicine = medicineRadio.isSelected();
            usageField.setVisible(isMedicine);
            expiryField.setVisible(isMedicine);
            grid.lookup(".label:nth-of-type(5)").setVisible(isMedicine);
            grid.lookup(".label:nth-of-type(6)").setVisible(isMedicine);
            
            materialField.setVisible(!isMedicine);
            sizeField.setVisible(!isMedicine);
            grid.lookup(".label:nth-of-type(7)").setVisible(!isMedicine);
            grid.lookup(".label:nth-of-type(8)").setVisible(!isMedicine);
        });

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    // Basic validation
                    if (nameField.getText().trim().isEmpty()) {
                        throw new IllegalArgumentException("Name cannot be empty");
                    }
                    
                    int quantity;
                    double price;
                    
                    try {
                        quantity = Integer.parseInt(quantityField.getText().trim());
                        if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Please enter a valid number for quantity");
                    }
                    
                    try {
                        price = Double.parseDouble(priceField.getText().trim());
                        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Please enter a valid number for price");
                    }
                    
                    String name = nameField.getText().trim();
                    String manufacturer = manufacturerField.getText().trim();
                    int id = inventory.size() + 1;

                    if (medicineRadio.isSelected()) {
                        if (expiryField.getValue() == null) {
                            throw new IllegalArgumentException("Please select an expiry date");
                        }
                        
                        Medicine medicine = new Medicine(id, name, price, quantity, manufacturer, expiryField.getValue());
                        medicine.setUsageInstructions(usageField.getText());
                        inventory.add(medicine);
                    } else {
                        if (materialField.getText().trim().isEmpty()) {
                            throw new IllegalArgumentException("Material type cannot be empty");
                        }
                        
                        MedicalSupply supply = new MedicalSupply(id, name, price, quantity, manufacturer, 
                            materialField.getText(), sizeField.getValue());
                        inventory.add(supply);
                    }
                    return true;
                } catch (IllegalArgumentException e) {
                    showAlert("Error", e.getMessage());
                    return false;
                } catch (Exception e) {
                    showAlert("Error", "An error occurred: " + e.getMessage());
                    return false;
                }
            }
            return false;
        });

        return dialog.showAndWait().orElse(false);
    }

    private void applyDiscountsToMedicalSupplies() {
        for (PharmacyItem item : inventory) {
            if (item instanceof MedicalSupply) {
                ((MedicalSupply) item).applyDiscount();
            }
        }
    }

    private void checkMedicineExpiry() {
        StringBuilder sb = new StringBuilder();
        boolean hasMedicines = false;
        
        for (PharmacyItem item : inventory) {
            if (item instanceof Medicine) {
                hasMedicines = true;
                Medicine medicine = (Medicine) item;
                sb.append(medicine.getName()).append(": ");
                if (medicine.isExpired()) {
                    sb.append("EXPIRED (").append(Math.abs(medicine.daysLeft(medicine))).append(" days ago)\n");
                } else {
                    sb.append("Expires in ").append(medicine.daysLeft(medicine)).append(" days\n");
                }
            }
        }
        
        showAlert("Expiry Status", hasMedicines ? sb.toString() : "No medicines in inventory");
    }

    private void sortInventory() {
        FXCollections.sort(inventory);
    }

    private List<PharmacyItem> generateSampleInventory() {
        return List.of(
            new Medicine(1, "Paracetamol", 5.0, 10, "PharmaCorp", LocalDate.now().plusMonths(6)),
            new MedicalSupply(2, "Bandage", 2.5, 20, "MediSafe", "Cotton", MedicalSupply.Size.medium),
            new Medicine(3, "Amoxicillin", 12.0, 5, "MediCorp", LocalDate.now().plusMonths(3)),
            new MedicalSupply(4, "Surgical Gloves", 3.5, 50, "SafeGuard", "Latex", MedicalSupply.Size.small),
            new Medicine(5, "Ibuprofen", 4.5, 15, "PharmaCorp", LocalDate.now().plusMonths(12)),
            new MedicalSupply(6, "Face Mask", 1.5, 100, "MediSafe", "N95", MedicalSupply.Size.medium)
        );
    }

    private void showItemDetails(PharmacyItem item) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(item.getName() + " Details");
        alert.setHeaderText(null);

        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(item.getName()).append("\n")
          .append("Quantity: ").append(item.getQuantity()).append("\n");

        if (item instanceof MedicalSupply) {
            MedicalSupply supply = (MedicalSupply) item;
            sb.append("Original Price: $").append(String.format("%.2f", item.getPrice())).append("\n")
              .append("Discounted Price: $").append(String.format("%.2f", supply.getDiscountedPrice())).append("\n")
              .append("Material: ").append(supply.getMaterialType()).append("\n")
              .append("Size: ").append(supply.getSize()).append("\n");
        } else {
            sb.append("Price: $").append(String.format("%.2f", item.getPrice())).append("\n");
        }

        sb.append("Manufacturer: ").append(item.getManufacturer()).append("\n");

        if (item instanceof Medicine) {
            Medicine medicine = (Medicine) item;
            sb.append("Expiry: ").append(medicine.getExpiryDate()).append("\n")
              .append("Status: ").append(medicine.isExpired() ? "EXPIRED" : "Valid").append("\n")
              .append("Days Left: ").append(medicine.isExpired() ? 
                  Math.abs(medicine.daysLeft(medicine)) + " days ago" : 
                  medicine.daysLeft(medicine) + " days").append("\n")
              .append("Instructions: ").append(medicine.getUsageInstructions()).append("\n");
        }

        alert.setContentText(sb.toString());
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}