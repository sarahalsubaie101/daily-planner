package com.iau.dailyplanner.db.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.iau.dailyplanner.db.PasswordEncryptor;


public class User extends BaseUser {
    
   // constructor to create user
    public User(String firstName, String lastName, String username, String password) {
        super(firstName, lastName, username, password);
    }
    
    // Empty constructor
    public User() {
        super();
    }
    
    @Override
    public boolean add() {
        // SQL query to insert User into database
        String sql = "INSERT INTO user (fname, lname, username, password) VALUES (?, ?, ?, ?)";
        
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
                    this.id = generatedKeys.getInt(1); // get the generated ID and set it to the userId
                }
                return true;
            }
            return false; // return false if the query is not successful

        } catch (SQLException e) {
            logSQLException(e, "Error adding user to database");
            return false;
        }
    }
    
    @Override
    public boolean update() {
        // SQL query to update User in database
        String sql = "UPDATE user SET fname = ?, lname = ?, username = ? WHERE user_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, username);
            statement.setInt(4, id);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logSQLException(e, "Error updating user in database");
            return false;
        }
    }

    public boolean updatePassword(String newPassword) {
        // SQL query to update User password in database
        String sql = "UPDATE user SET password = ? WHERE user_id = ?";

        String encryptedPassword = PasswordEncryptor.encrypt(newPassword);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, encryptedPassword);
            statement.setInt(2, id);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logSQLException(e, "Error updating user password in database");
            return false;
        }
    }
    
    @Override
    public boolean delete() {
        // SQL query to delete User from database
        String sql = "DELETE FROM user WHERE user_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logSQLException(e, "Error deleting user from database");
            return false;
        }
    }
    
    @Override
    public boolean login(String username, String password) {
        // SQL query to authenticate User
        String sql = "SELECT * FROM user WHERE username = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                // Load user data from result set
                this.id = resultSet.getInt("user_id");
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
            logSQLException(e, "Error authenticating user");
            return false;
        }
    }
    
    public User findById(int id) {
        // SQL query to find User by ID
        String sql = "SELECT * FROM user WHERE user_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                this.id = resultSet.getInt("user_id");
                this.firstName = resultSet.getString("fname");
                this.lastName = resultSet.getString("lname");
                this.username = resultSet.getString("username");
                this.password = resultSet.getString("password");
                return this; // return the User which is (this class itself)
            }
            
            return null; // return null if not found
        } catch (SQLException e) {
            logSQLException(e, "Error finding user by ID");
            return null;
        }
    }
    
    // SQL query to find User by username
    public User findByUsername(String username) {
        // SQL query to find User by username
        String sql = "SELECT * FROM user WHERE username = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                this.id = resultSet.getInt("user_id");
                this.firstName = resultSet.getString("fname");
                this.lastName = resultSet.getString("lname");
                this.username = resultSet.getString("username");
                this.password = resultSet.getString("password");
                return this;
            }
            
            return null;
        } catch (SQLException e) {
            logSQLException(e, "Error finding user by username");
            return null;
        }
    }
    
    // return a list of all Users
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        // SQL query to find all Users
        String sql = "SELECT * FROM user";
        
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            // loop through the result set and add each user to the list
            while (resultSet.next()) {
                User user = new User(
                    resultSet.getString("fname"),
                    resultSet.getString("lname"),
                    resultSet.getString("username"),
                    resultSet.getString("password")
                );
                user.setId(resultSet.getInt("user_id"));
                users.add(user);
            }
            
            return users;
        } catch (SQLException e) {
            logSQLException(e, "Error finding all users");
            return users;
        }
    }
}