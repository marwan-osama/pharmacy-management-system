/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.pharmacymanagementsystem;


class Cashier extends User {
    private int sales;

    Cashier(String userID, String username, String password,
            String fullName, int phoneNumber) 
    {
        super("CSH".concat(userID), username, password, fullName, phoneNumber);
    }

    public int getSales() 
    {
        return sales;
    }

    public void setSales(int sales) 
    {
        this.sales = sales;
    }

    public void processSale(PharmacyItem item, int quantity) 
    {
        // Process the sale of the item
        // Update sales count
        this.sales += quantity;
    }
}