/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pharmacymanagementsystem;

public class MedicalSupply extends PharmacyItem implements Discountable{

    public enum Size {small, medium, large}

    private double discountedPrice = 0;
    private String materialType;
    private Size size;

    MedicalSupply(
            int itemID,
            String name,
            double price,
            int quantity,
            String manufacturer,
            double discountPercentage,
            String materialType,
            Size size
    )
    {
        super(itemID, name, price, quantity, manufacturer);
        applyDiscount(discountPercentage);
        this.materialType = materialType;
        this.size = size;
    }

    MedicalSupply(
            int itemID,
            String name,
            double price,
            int quantity,
            String manufacturer,
            String materialType,
            Size size
    )
    {
        super(itemID, name, price, quantity, manufacturer);
        applyDiscount();
        this.materialType = materialType;
        this.size = size;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    @Override
    public void applyDiscount(double discountPercentage) {
        discountedPrice = super.getPrice() * (1 - discountPercentage)/(double)100;
    }

    @Override
    public void applyDiscount() {
        discountedPrice = super.getPrice() * (1 - discountPercentage)/(double)100;
    }

    @Override
    public String getItemCategory() {
        return "Medical Supply";
    }

}
