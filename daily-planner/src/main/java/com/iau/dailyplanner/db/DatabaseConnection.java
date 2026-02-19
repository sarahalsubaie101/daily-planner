package com.iau.dailyplanner.db;

import java.sql.*;


// Class to create a single connection to the database and use it for all other classes
public class DatabaseConnection {

    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "daliy_planner_db";
    private static final String DB_USER = "IAU";
    private static final String DB_PASSWORD = "Tt12345678";
    
    // Connection object
    private static Connection connection;
    
    public static Connection getConnection() {
        // Load MySQL JDBC Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load MySQL JDBC Driver: " + e.getMessage());
        }

        // Create Database Connection
        try {
            // if there is no connection before or connection is closed, create new connection
            if (connection == null || connection.isClosed()) {  
                connection = DriverManager.getConnection(DB_URL + DB_NAME, DB_USER, DB_PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("Failed to create database connection: " + e.getMessage());
        }

        // return connection
        return connection;
    }

     public static void createDatabaseAndTables() {
        // SQL to create the database
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
    
        // SQL for creating tables
        String[] tableSQL = {
            
            // User table
            """
            CREATE TABLE IF NOT EXISTS user (
                user_id INT PRIMARY KEY AUTO_INCREMENT,
                fname VARCHAR(50) NOT NULL,
                lname VARCHAR(50) NOT NULL,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(100) NOT NULL
            );
            """,

            // Admin table
            """
            CREATE TABLE IF NOT EXISTS admin (
                admin_id INT PRIMARY KEY AUTO_INCREMENT,
                fname VARCHAR(50) NOT NULL,
                lname VARCHAR(50) NOT NULL,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(100) NOT NULL
            );
            """,

            // Task table
            """
            CREATE TABLE IF NOT EXISTS task (
                task_id INT PRIMARY KEY AUTO_INCREMENT,
                user_id INT,
                title VARCHAR(100) NOT NULL,
                completed BOOLEAN NOT NULL DEFAULT FALSE,
                datetime DATETIME,
                category VARCHAR(50),
                description TEXT,
                location VARCHAR(100),
                FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
            );
            """,

            // Feedback table
            """
            CREATE TABLE IF NOT EXISTS feedback (
                feedback_id INT PRIMARY KEY AUTO_INCREMENT,
                user_id INT,
                comment VARCHAR(100),
                rating INT NOT NULL,
                FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE
            );
            """,
        };

         try (
            // Create connections to MySQL server
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement()
            ) {

            // Create the database if it doesn't exist
            stmt.executeUpdate(createDatabaseSQL);
    
            // Use the database to create the tables
            String useDatabaseSQL = "USE " + DB_NAME;
            stmt.executeUpdate(useDatabaseSQL);
    
            // Create tables if they don't exist
            for (String sql : tableSQL) {
                stmt.executeUpdate(sql);
            }

            // Create Default admin if not exist
            boolean created = createDefaultAdmin(stmt);
            if (created) {
                // Create Dummy data
                createDummyData(stmt);
            }
    
        } catch (SQLException e) {
            System.err.println("Error when Creating the Database: " + e.getMessage());
        }
    }

    private static boolean createDefaultAdmin(Statement stmt) throws SQLException {
        // Try to insert the default admin (username: admin | password: admin)
        String encryptedPassword = PasswordEncryptor.encrypt("admin");
        String insertAdminSQL = "INSERT INTO admin (fname, lname, username, password) VALUES ('Administrator', 'User', 'admin', '" + encryptedPassword + "')";
        try {
            stmt.executeUpdate(insertAdminSQL);
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            // this error means the admin already exists
            return false;
        }
    }

    private static void createDummyData(Statement stmt) throws SQLException {
        try {
            // Default encrypted password for all users: 1234656
            String encryptedPassword = PasswordEncryptor.encrypt("1234656");

            // Insert Users
            stmt.executeUpdate(String.format("""
                INSERT INTO user (fname, lname, username, password) VALUES
                ('Taghreed', 'Alharbi', 'taghreed', '%s'),
                ('Sarah', 'Alsubaie', 'sarah', '%s'),
                ('Razan', 'Alzahrani', 'razan', '%s'),
                ('Maha', 'Aldhwaihi', 'maha', '%s'),
                ('Khadijah', 'Baaqeel', 'khadijah', '%s'),
                ('Elan', 'Alfowzan', 'elan', '%s')
            """, encryptedPassword, encryptedPassword, encryptedPassword, encryptedPassword, encryptedPassword, encryptedPassword));
            // Insert Tasks
            stmt.executeUpdate("""
                INSERT INTO task (user_id, title, completed, datetime, category, description, location) VALUES
                (1, 'Finish homework', FALSE, '2025-11-01 10:00:00', 'Study', 'Math exercises', 'Home'),
                (1, 'Submit report', TRUE, '2025-11-02 15:00:00', 'Work', 'Monthly report', 'Office'),
                (1, 'Grocery shopping', FALSE, '2025-11-05 12:00:00', 'Personal', 'Buy groceries', 'Supermarket'),

                (2, 'Yoga class', TRUE, '2025-11-02 07:00:00', 'Health', 'Morning session', 'Gym'),
                (2, 'Call mom', FALSE, '2025-11-03 18:00:00', 'Family', 'Weekly call', 'Home'),
                (2, 'Prepare presentation', FALSE, '2025-11-06 09:00:00', 'Work', 'Slides for meeting', 'Office'),

                (3, 'Read book', TRUE, '2025-11-01 20:00:00', 'Leisure', 'Finish novel', 'Home'),
                (3, 'Pay bills', FALSE, '2025-11-04 17:00:00', 'Finance', 'Electricity and water', 'Home'),
                (3, 'Plan trip', FALSE, '2025-11-05 14:00:00', 'Personal', 'Weekend getaway', 'Online'),

                (4, 'Doctor appointment', FALSE, '2025-11-03 10:30:00', 'Health', 'Routine checkup', 'Clinic'),
                (4, 'Laundry', TRUE, '2025-11-02 12:00:00', 'Home', 'Wash clothes', 'Home'),
                (4, 'Team meeting', FALSE, '2025-11-06 11:00:00', 'Work', 'Project discussion', 'Office'),

                (5, 'Cook dinner', TRUE, '2025-11-02 18:00:00', 'Home', 'Family dinner', 'Home'),
                (5, 'Online course', FALSE, '2025-11-04 20:00:00', 'Study', 'Finish module', 'Home'),
                (5, 'Clean room', FALSE, '2025-11-05 09:00:00', 'Home', 'Organize stuff', 'Home'),

                (6, 'Visit friend', FALSE, '2025-11-03 16:00:00', 'Personal', 'Catch up', 'Friend''s house'),
                (6, 'Write blog', TRUE, '2025-11-02 13:00:00', 'Leisure', 'New article', 'Home'),
                (6, 'Exercise', FALSE, '2025-11-06 07:00:00', 'Health', 'Morning workout', 'Gym')
            """);

            // Insert Feedback
            stmt.executeUpdate("""
                INSERT INTO feedback (user_id, comment, rating) VALUES
                (1, 'Great app, very helpful!', 5),
                (3, 'I love using this daily.', 5),
                (5, 'Excellent features and easy to use.', 5)
            """);

        } catch (SQLIntegrityConstraintViolationException e) {
            // Ignore duplicate entries
        }
    }

}