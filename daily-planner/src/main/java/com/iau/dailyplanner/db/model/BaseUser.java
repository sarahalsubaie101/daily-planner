package com.iau.dailyplanner.db.model;

import com.iau.dailyplanner.db.PasswordEncryptor;

public abstract class BaseUser extends BaseModel {
    
    public int id;
    public String firstName;
    public String lastName;
    public String username;
    public String password;
    
    public BaseUser(String firstName, String lastName, String username, String password) {
        super(); // Call the constructor of the BaseModel superclass
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public BaseUser () {
        super(); // Call the constructor of the BaseModel superclass
    }
    
    // ID getter and setter
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    // First name getter and setter
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    // Last name getter and setter
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    // Username getter and setter
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    // Password getter and setter
    public String getPassword() {
        return PasswordEncryptor.decrypt(password);
    }

    public void setPassword(String password) {
        this.password = PasswordEncryptor.encrypt(password);
    }
    
    // Get full name
    public String getFullname() {
        return firstName + " " + lastName;
    }
    
    // Authenticate a user, return true if authentication is successful, false otherwise
    // should be implemented by the subclasses
    public abstract boolean login(String username, String password);
}