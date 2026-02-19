package com.iau.dailyplanner.db.model;

import com.iau.dailyplanner.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseModel {
    
    public Connection connection;
    
    public BaseModel() {
        // Get connection
        this.connection = DatabaseConnection.getConnection();
    }
    
    // (Add) return true if successful, false otherwise
    public abstract boolean add();
    
    // (Update) return true if successful, false otherwise
    public abstract boolean update();
    
    // (Delete) return true if successful, false otherwise
    public abstract boolean delete();
    
    // Method to print SQL error
    public void logSQLException(SQLException e, String message) {
        // Get subclass name then print error message
        String className = this.getClass().getSimpleName(); 
        System.out.println("["+className+"] " + message + ": " + e.getMessage());
    }
    
}