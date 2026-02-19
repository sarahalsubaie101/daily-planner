package com.iau.dailyplanner.db.model;

import java.sql.*;

import com.iau.dailyplanner.db.PasswordEncryptor;

public class Admin extends BaseUser {
    
    // constructor to create Admin
    public Admin(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password); // call the constructor of the User superclass
    }
    
    // Empty constructor
    public Admin() {
        super(); // Call the constructor of the BaseModel superclass
    }
    
    @Override
    public boolean add() {
        // SQL query to insert Admin into database
        String sql = "INSERT INTO admin (fname, lname, username, password) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setString(4, PasswordEncryptor.encrypt(password));
            
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) { // if the query is successful
                // Get the generated ID
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getInt(1); // get the generated ID and set it to the adminId
                }
                return true;
            }

            return false; // return false if the query is not successful
        } catch (SQLException e) {
            logSQLException(e, "Error adding admin to database");
            return false;
        }
    }

    @Override
    public boolean update() {
        // SQL query to update Admin in database
        String sql = "UPDATE admin SET fname = ?, lname = ?, username = ? WHERE admin_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setInt(4, id);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logSQLException(e, "Error updating admin in database");
            return false;
        }
    }

    
    public boolean updatePassword(String newPassword) {
        // SQL query to update User password in database
        String sql = "UPDATE admin SET password = ? WHERE admin_id = ?";

        String encryptedPassword;
        try {
            encryptedPassword = PasswordEncryptor.encrypt(newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, encryptedPassword);
            statement.setInt(2, id);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logSQLException(e, "Error updating admin password in database");
            return false;
        }
    }
    
    @Override
    public boolean delete() {
        // SQL query to delete Admin from database
        String sql = "DELETE FROM admin WHERE admin_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logSQLException(e, "Error deleting admin from database");
            return false;
        }
    }
    
    @Override
    public boolean login(String username, String password) {
        // SQL query to authenticate Admin
        String sql = "SELECT * FROM admin WHERE username = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                // Load admin data from result set
                this.id = resultSet.getInt("admin_id");
                this.firstName = resultSet.getString("fname");
                this.lastName = resultSet.getString("lname");
                this.username = resultSet.getString("username");
                this.password = resultSet.getString("password");

                // Verify password
                String decryptedPassword = getPassword();
                if (decryptedPassword.equals(password)) {
                    return true;
                } else {
                    return false;
                }
            }
            
            return false;
        } catch (SQLException e) {
            logSQLException(e, "Error authenticating admin");
            return false;
        }
    }
  
}