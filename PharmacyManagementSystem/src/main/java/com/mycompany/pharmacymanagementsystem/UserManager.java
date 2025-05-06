/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.pharmacymanagementsystem;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager instance = null;
    private final List<User> users;

    private UserManager() 
    {
        users = new ArrayList<>();
    }

    public static UserManager getInstance() 
    {
        if (instance == null) 
        {
            instance = new UserManager();
        }
        return instance;
    }

    public void addUser(User user) 
    {
        users.add(user);
    }

    public boolean removeUser(User requestingUser, User userToRemove) 
    {
        if (requestingUser instanceof Admin) {
            if (userToRemove instanceof Admin) {
                return false;
            }
            
            if (users.contains(userToRemove)) {
                users.remove(userToRemove);
                return true;
            }
        }
        return false;
    }
    
    public User getUser(String userID) 
    {
        for (User user : users) 
        {
            if (user.getUserID().equals(userID)) 
            {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers()
    {
        return new ArrayList<>(users);
    }
    
}