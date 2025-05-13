/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacymanagementsystem;

import java.time.LocalDate;

/**
 *
 * @author alima
 */
public class Payment {
    private int paymentID;
    private Order order;
    private LocalDate paymentDate;
    private double amountPaid;
    private String paymentMethod;

    public int getPaymentID() {
        return paymentID;
    }

    public Order getOrder() {
        return order;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean processPayment() {
        if (order != null && amountPaid >= order.getTotalAmount()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Payment ID: " + paymentID +
                "\nOrder ID: " + (order != null ? order.getOrderID() : "None") +
                "\nPayment Date: " + paymentDate +
                "\nAmount Paid: " + amountPaid +
                "\nPayment Method: " + paymentMethod;
    }
}
