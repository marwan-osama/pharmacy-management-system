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

    public boolean processSale(PharmacyItem item, int quantity) {
    if (item == null) {
        System.out.println("Item not found.");
        return false;
    }
    
    if (item.getQuantity() < quantity) {
        System.out.println("Not enough stock available for " + item.getName());
        return false;
    }
    
    // Calculate the sale amount
    double saleAmount = item.getPrice() * quantity;
    
    // Update inventory
    item.setQuantity(item.getQuantity() - quantity);
    Inventory.getInstance().updateInventory(item);
    
    // Update the cashier's sales
    this.sales += saleAmount;
    
    return true;
    }
}