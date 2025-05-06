package com.mycompany.pharmacymanagementsystem;

public interface Expirable {
    boolean isExpired();
    long daysLeft(Medicine medicine);
}
