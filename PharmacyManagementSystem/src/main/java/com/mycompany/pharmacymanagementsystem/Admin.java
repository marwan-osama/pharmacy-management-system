/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.pharmacymanagementsystem;

import java.util.List;

/**
 *
 * @author alima
 */
public class Admin extends User {

    Admin(String userID, String username, String password,
            String fullName, int phoneNumber) 
    {
        super("ADM".concat(userID), username, password, fullName, phoneNumber);
        UserManager.getInstance().addUser(this);
    }

    public void addUser(User user) 
    {
        UserManager.getInstance().addUser(user);
    }

    public boolean removeUser(User user) 
    {
        return UserManager.getInstance().removeUser(this, user);
    }

    public boolean addItem(PharmacyItem item)
    {
        
        return true;
    }

    public boolean removeItem(PharmacyItem item)
    {
        // Remove item from the system
        return true;
    }

    public boolean updateInventory(PharmacyItem item, int quantity) 
    {
        // Update inventory of the item
        return true;
    }

    public void generateReport() 
    {
        List<User> users = UserManager.getInstance().getUsers();
        System.out.println("Cashier Report:");
        System.out.println("======================================================================");
        System.out.println("Cashier ID\tCashier Name\t\tSales");
        System.out.println("======================================================================");
        for(User user : users) 
        {
            if(user instanceof Cashier) 
            {
                System.out.println(user.getUserID() + "\t\t" + user.getFullName() + "\t\t" + ((Cashier)user).getSales());
                System.out.println("======================================================================");
            }
        }
    }
       
}