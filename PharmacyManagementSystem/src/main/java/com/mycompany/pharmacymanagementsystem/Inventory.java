/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacymanagementsystem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alima
 */
public class Inventory {
    private static Inventory instance;
    private final List<PharmacyItem> items;

    private Inventory() {
        items = new ArrayList<>();
    }

    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }

    public boolean addItem(PharmacyItem newItem) {
        for (PharmacyItem item : items) {
        if (item.getName().equalsIgnoreCase(newItem.getName())) {
            System.out.println("Item with name '" + newItem.getName() + "' already exists.");
            return false;
        }  
       }
        return items.add(newItem);
    }

    public boolean removeItem(int itemId) {
        return items.removeIf(item -> item.getItemID() == itemId);
    }

    public PharmacyItem getItem(int itemId) {
        for (PharmacyItem item : items) {
            if (item.getItemID() == itemId) {
                return item;
            }
        }
        return null;
    }

    public List<PharmacyItem> getItems() {
        return new ArrayList<>(items);
    }
    
    public boolean updateInventory(PharmacyItem updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            PharmacyItem current = items.get(i);
            if (current.getItemID() == updatedItem.getItemID()) {
                items.set(i, updatedItem);
                return true;
            }
        }
        return false;
    }
    
    public List<PharmacyItem> getLowStockItems() {
    List<PharmacyItem> lowStockItems = new ArrayList<>();
    for (PharmacyItem item : items) {
        if (item.isLowStock()) {
            lowStockItems.add(item);
        }
    }
    return lowStockItems;
    }
}
