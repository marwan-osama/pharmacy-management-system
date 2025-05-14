/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacymanagementsystem;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author alima
 */
public class Order {
    private int orderID;
    private Cashier cashier;
    private List<PharmacyItem> items;
    private LocalDate orderDate;
    private double totalAmount;
    
    public Order(int orderID, Cashier cashier, List<PharmacyItem> items, LocalDate orderDate) {
        this.orderID = orderID;
        this.cashier = cashier;
        this.items = items;
        this.orderDate = orderDate;
        calculateTotalAmount();
    }

    public int getOrderID() {
        return orderID;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public List<PharmacyItem> getItems() {
        return items;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void calculateTotalAmount() {
        totalAmount = 0;
        for (PharmacyItem item : items) {
            totalAmount += item.getPrice(); // assuming getPrice() in item class
        }
    }

    @Override
    public String toString() {
        return "Order ID: " + orderID +
                "\nCashier: " + cashier +
                "\nOrder Date: " + orderDate +
                "\nTotal Amount: " + totalAmount;
    }
}
