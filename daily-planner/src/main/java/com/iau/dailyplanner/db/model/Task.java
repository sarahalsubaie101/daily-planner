package com.iau.dailyplanner.db.model;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends BaseModel {
    
    private int taskId;
    private String title;
    private boolean completed;
    private LocalDateTime datetime;
    private String category;
    private String description;
    private String location;

    // fields for relationships
    private int userId;
    private User user;
    
    // Constructor for creating a new Task
    public Task(User user, String title, LocalDateTime datetime, String category, String description, String location) {
        super(); // call the constructor of the BaseModel superclass

        this.user = user;
        this.userId = user.getId();
        
        this.title = title;
        this.completed = false;
        this.datetime = datetime;
        this.category = category;
        this.description = description;
        this.location = location;
    }
    
    // Empty constructor
    public Task() {
        super();
    }
    
    // Getters and setters
    public int getTaskId() {
        return taskId;
    }
    
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public String getDatetimeFormated() {
        if (datetime != null) {
            // am/pm
            return datetime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd / hh:mma"));
        }
        return "";
    }
    
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public User getUser() { return user; }
    
    public void setUser(User user) { 
        this.user = user;
        if (user != null) {
            this.userId = user.getId();
        }
    }

    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getStatus() {
        // Return "Completed", "Late", or "Pending
        if (completed) {
            return "Completed";
        } else {
            if (datetime != null && datetime.isBefore(LocalDateTime.now())) {
                return "Late";
            } else {
                return "Pending";
            }
        }
    }
    
    @Override
    public boolean add() {
        // SQL query to insert Task into database
        String sql = "INSERT INTO task (title, datetime, category, description, location, user_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        // PreparedStatement with RETURN_GENERATED_KEYS to get the generated ID
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
            statement.setString(1, title);
            statement.setTimestamp(2, Timestamp.valueOf(datetime));
            statement.setString(3, category);
            statement.setString(4, description);
            statement.setString(5, location);
            statement.setInt(6, userId);
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) { // if the query is successful
                // Get the generated ID
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    this.taskId = generatedKeys.getInt(1); // get the generated ID and set it to the taskId
                }
                return true;
            }
            return false; // return false if the query is not successful
        } catch (SQLException e) {
            logSQLException(e, "Error adding task to database");
            return false;
        }
    }
    
    @Override
    public boolean update() {
        // SQL query to update Task in database
        String sql = "UPDATE task SET title = ?, completed = ?,  datetime = ?, category = ?, description = ?, location = ?, user_id = ? WHERE task_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);
            statement.setBoolean(2, completed);
            statement.setTimestamp(3, Timestamp.valueOf(datetime));
            statement.setString(4, category);
            statement.setString(5, description);
            statement.setString(6, location);
            statement.setInt(7, userId);
            statement.setInt(8, taskId);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logSQLException(e, "Error updating task in database");
            return false;
        }
    }
    
    @Override
    public boolean delete() {
        // SQL query to delete Task from database
        String sql = "DELETE FROM task WHERE task_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logSQLException(e, "Error deleting task from database");
            return false;
        }
    }

    public Task findById(int id) {
        // SQL query to find Task by ID
        String sql = "SELECT * FROM task WHERE task_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                this.taskId = resultSet.getInt("task_id");
                this.title = resultSet.getString("title");
                this.datetime = resultSet.getTimestamp("datetime").toLocalDateTime();
                this.category = resultSet.getString("category");
                this.description = resultSet.getString("description");
                this.location = resultSet.getString("location");

                // Load related User
                this.userId = resultSet.getInt("user_id");
                if (this.userId > 0) {
                    this.user = new User().findById(this.userId);
                }
                return this; // return the Task which is (this class itself)
            }

            return null; // return null if not found
        } catch (SQLException e) {
            logSQLException(e, "Error finding task by ID");
            return null;
        }
    }

    public Task findByTitle(String title) {
        // SQL query to find Task by title
        String sql = "SELECT * FROM task WHERE title = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, title);   
    
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                this.taskId = resultSet.getInt("task_id");
                this.title = resultSet.getString("title");
                this.datetime = resultSet.getTimestamp("datetime").toLocalDateTime();
                this.category = resultSet.getString("category");
                this.description = resultSet.getString("description");
                this.location = resultSet.getString("location");

                // Load related User
                this.userId = resultSet.getInt("user_id");
                if (this.userId > 0) {
                    this.user = new User().findById(this.userId);
                }
                return this;
            }

            return null; 
        } catch (SQLException e) {
            logSQLException(e, "Error finding task by title");
            return null;
        }
    }
        
    // Get all Task from database as a list
    public List<Task> findAll() {
        List<Task> taskList = new ArrayList<>();
        // SQL query to find all Task
        String sql = "SELECT * FROM task";
        
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            // loop through the result set and add each task to the list
            while (resultSet.next()) {
                User user = new User().findById(resultSet.getInt("user_id"));

                Task task = new Task(
                    user,
                    resultSet.getString("title"),
                    resultSet.getTimestamp("datetime").toLocalDateTime(),
                    resultSet.getString("category"),
                    resultSet.getString("description"),
                    resultSet.getString("location")
                );

                task.taskId = resultSet.getInt("task_id");
                task.completed = resultSet.getBoolean("completed");
                
                taskList.add(task);
            }
            
            return taskList;
        } catch (SQLException e) {
            logSQLException(e, "Error finding all task");
            return taskList;
        }
    }

    public List<Task> findByUser(User user, String category) {
        List<Task> taskList = new ArrayList<>();
        // SQL query to find Task by user ID
         String sql = """
            SELECT * FROM task
            WHERE user_id = ?
            AND (? IS NULL OR category = ?)
            ORDER BY completed ASC, datetime IS NULL ASC, datetime ASC
        """;
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            statement.setString(2, category);
            statement.setString(3, category);
            
            ResultSet resultSet = statement.executeQuery();
            
            // loop through the result set and add each task to the list
            while (resultSet.next()) {
                Task task = new Task(
                    user,
                    resultSet.getString("title"),
                    resultSet.getTimestamp("datetime").toLocalDateTime(),
                    resultSet.getString("category"),
                    resultSet.getString("description"),
                    resultSet.getString("location")
                );
                task.taskId = resultSet.getInt("task_id");
                task.completed = resultSet.getBoolean("completed");
                
                taskList.add(task);
            }
            
            return taskList;
        } catch (SQLException e) {
            logSQLException(e, "Error finding task by user ID");
            return taskList;
        }
    }

    public String[] getAllCategories() {
        List<String> categories = new ArrayList<>();
        // SQL query to get distinct categories from tasks
        String sql = "SELECT DISTINCT category FROM task";
        
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            // loop through the result set and add each category to the list
            while (resultSet.next()) {
                categories.add(resultSet.getString("category"));
            }
            
        } catch (SQLException e) {
            logSQLException(e, "Error getting all categories");
        }

        return categories.toArray(String[]::new);
    }
}