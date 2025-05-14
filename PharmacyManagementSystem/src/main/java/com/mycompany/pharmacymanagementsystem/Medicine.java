/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacymanagementsystem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Medicine extends PharmacyItem implements Expirable{

    private LocalDate expiryDate;
    private String usageInstructions;

    Medicine(int itemID, String name, double price, int quantity, String manufacturer, LocalDate expiryDate) {
        super(itemID, name, price, quantity, manufacturer);
        this.expiryDate = expiryDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getUsageInstructions() {
        return usageInstructions;
    }

    public void setUsageInstructions(String usageInstructions) {
        this.usageInstructions = usageInstructions;
    }

    @Override
    public int compareTo(PharmacyItem item) {
        if(this.getQuantity() < item.getQuantity()){
            return -1;
        }
        if(this.getQuantity() > item.getQuantity()){
            return 1;
        }
        return 0;
    }

    @Override
    public boolean isExpired() {
        if(expiryDate.isBefore(LocalDate.now()))
            return true;
        return false;
    }

    @Override
    public long daysLeft(Medicine medicine) {
        return ChronoUnit.DAYS.between(expiryDate, LocalDate.now());
    }

    @Override
    public String getItemCategory() {
        return "Medicine";
    }

}
