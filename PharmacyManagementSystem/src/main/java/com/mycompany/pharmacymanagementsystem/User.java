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
public abstract class User {
    private final String userID;
    private String username;
    private String password;
    private String fullName;
    private int phoneNumber;
    private final LocalDate registrationDate;
    
    User(String userID, String username,String password,
            String fullName,int phoneNumber)
    {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = LocalDate.now();
    }
    
    public String getUserID()
    {
        return this.userID;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return this.password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getFullName()
    {
        return this.fullName;
    }
    
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public int getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getRegistrationDate()
    {
        return this.registrationDate;
    }

    public boolean login(String username, String password)
    {
        return this.username.equals(username) && this.password.equals(password);
    }

    public boolean logout()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return "User{" + "userID=" + userID + ", phoneNumber=" + phoneNumber + ", registrationDate=" + registrationDate + '}';
    }
}
