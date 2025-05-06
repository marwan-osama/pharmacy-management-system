/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.pharmacymanagementsystem;

/**
 *
 * @author alima
 */
public interface Discountable {
    double discountPercentage = 10;
    void applyDiscount(double discountPercentage);
    void applyDiscount();
}
