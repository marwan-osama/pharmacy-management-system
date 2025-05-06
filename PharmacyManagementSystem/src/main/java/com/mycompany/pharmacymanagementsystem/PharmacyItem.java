/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.pharmacymanagementsystem;

public abstract class PharmacyItem implements Comparable<PharmacyItem> {

    private int itemID;
    private String name;
    private double price;
    private int quantity;
    private String manufacturer;
    private int lowStockThreshold;

    PharmacyItem(int itemID, String name, double price, int quantity, String manufacturer) {
        if(itemID < 0 || price < 0 || quantity < 0){
            System.out.println("Invalid item ID, price, or quantity");
        }
        else{
            this.itemID = itemID;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.manufacturer = manufacturer;
        }
    }
    PharmacyItem(){}

    public int getItemID() {
        return itemID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        else{
            this.price = price;
        }
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public int getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(int lowStockThreshold) {
        if (lowStockThreshold < 0) {
            throw new IllegalArgumentException("Low stock threshold cannot be negative");
        }
        else{
            this.lowStockThreshold = lowStockThreshold;
        }
    }

    @Override
    public int compareTo (PharmacyItem item) {
        if (this.getPrice() > item.getPrice()) {
            return 1;
        }
        else if (this.getPrice() < item.getPrice()) {
            return -1;
        }
        else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return ("Pharmacy Item ID: " + itemID + " Name: " + name + " Price: " + price + " Quantity: " + quantity);
    }

    public boolean isLowStock() {
        return quantity < lowStockThreshold;
    }

    public abstract String getItemCategory();

}