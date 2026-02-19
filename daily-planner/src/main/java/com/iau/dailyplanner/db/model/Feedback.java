package com.iau.dailyplanner.db.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Feedback extends BaseModel {

    private int feedbackId;
    private String comment;
    private int rating;
    
    // fields for relationships
    private int userId;
    private User user;

    // Constructor for creating a new Feedback
    public Feedback(User user, String comment, int rating) {
        super(); // call the constructor of the BaseModel superclass

        this.comment = comment;
        this.rating = rating;

        this.userId = user.getId();
        this.user = user;
    }

    // Empty constructor
    public Feedback() {
        super(); // call the constructor of the BaseModel superclass
    }

    // Getters and Setters
    public int getFeedbackId() {
        return feedbackId;
    }
    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            this.userId = user.getId();
        }
    }

    @Override
    public boolean add() {
        // SQL query to insert Feedback into database
        String sql = "INSERT INTO feedback (comment, rating, user_id) VALUES (?, ?, ?)";
        
        // PreparedStatement with RETURN_GENERATED_KEYS to get the generated ID
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 
            statement.setString(1, comment);
            statement.setInt(2, rating);
            statement.setInt(3, userId);
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) { // if the query is successful
                // Get the generated ID
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    this.feedbackId = generatedKeys.getInt(1); // get the generated ID and set it to the feedbackId
                }
                return true;
            }
            return false; // return false if the query is not successful
        } catch (SQLException e) {
            logSQLException(e, "Error adding feedback to database");
            return false;
        }
    }

    @Override
    public boolean update() {
        return false; // no need to update feedbacks
    }

    @Override
    public boolean delete() {
        return false; // no need to delete feedbacks
    }

    public List<Feedback> findAll() {
        List<Feedback> feedbackList = new ArrayList<>();
        // SQL query to find all Feedback
        String sql = "SELECT * FROM feedback";
        
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            // loop through the result set and add each feedback to the list
            while (resultSet.next()) {
                User user = new User().findById(resultSet.getInt("user_id"));

                Feedback feedback = new Feedback(
                    user,
                    resultSet.getString("comment"),
                    resultSet.getInt("rating")
                );

                feedback.feedbackId = resultSet.getInt("feedback_id");
                
                feedbackList.add(feedback);
            }
            
            return feedbackList;
        } catch (SQLException e) {
            logSQLException(e, "Error finding all feedback");
            return feedbackList;
        }
    }
   
    public Feedback findByUser(User user) {
        // SQL query to find Feedback by user ID
        String sql = "SELECT * FROM feedback WHERE user_id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Feedback feedback = new Feedback(
                    user,
                    resultSet.getString("comment"),
                    resultSet.getInt("rating")
                );

                feedback.feedbackId = resultSet.getInt("feedback_id");
                
                return feedback;
            }
            return null; // return null if no feedback is found
        } catch (SQLException e) {
            logSQLException(e, "Error finding feedback by user");
            return null;
        }
    }   
}
