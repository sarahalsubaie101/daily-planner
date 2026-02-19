package com.iau.dailyplanner.ui;

import javax.swing.*;

import com.iau.dailyplanner.db.model.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import com.github.lgooddatepicker.components.DateTimePicker;



public class TaskDialog extends BaseDialog {
    
    // Widgets
    private JButton submitButton;
    private JTextField titleField;
    private JComboBox<String> categoryComboBox;
    private JTextField descriptionField;
    private JTextField locationField;
    private DateTimePicker dateTimePicker;
    
    // Data
    private Task task;
    private boolean isEditMode;
    
    public TaskDialog(JFrame parent, User user, Task selectedTask) {
        super(parent, selectedTask == null ? "Create Task" : "Edit Task");
        
        // Check if edit mode or create mode
        isEditMode = (selectedTask != null);

        if (isEditMode) { 
            this.task = selectedTask;
        
        } else { // Create Mode
            this.task = new Task();
            this.task.setUser(user);
        }
        
        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField();

        // Category
        JLabel categoryLabel = new JLabel("Category:");
        setupCategoryComboBox();

        // Description
        JLabel descriptionLabel = new JLabel("Description: (optional)");
        descriptionField = new JTextField();

        // Location
        JLabel locationLabel = new JLabel("Location: (optional)");
        locationField = new JTextField();

       // Date & time selection
        JLabel dateLabel = new JLabel("Date & Time:");
        dateTimePicker = new DateTimePicker();
        
        // Add components to form panel
        formPanel.add(titleLabel);
        formPanel.add(titleField);
        formPanel.add(categoryLabel);
        formPanel.add(categoryComboBox);
        formPanel.add(descriptionLabel);
        formPanel.add(descriptionField);
        formPanel.add(locationLabel);
        formPanel.add(locationField);
        formPanel.add(dateLabel);
        formPanel.add(dateTimePicker);
        
        // Submit button
        submitButton = new JButton("Create Task");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTask();
            }
        });

        // Fill form if in edit mode
        if (isEditMode) {
            titleField.setText(task.getTitle());
            categoryComboBox.setSelectedItem(task.getCategory());
            descriptionField.setText(task.getDescription());
            locationField.setText(task.getLocation());
            dateTimePicker.setDateTimePermissive(task.getDatetime());
            submitButton.setText("Save Changes");
        }
        
        // Apply button style to submit button
        setButtonStyle(submitButton);
        Dimension buttonSize = new Dimension(150, 35);
        submitButton.setPreferredSize(buttonSize);
        submitButton.setMinimumSize(buttonSize);
        
        buttonPanel.add(submitButton);
        
        // Set preferred size for the dialog
        setSize(520, 300);
        
        // Add form panel to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
    }

    private void setupCategoryComboBox() {
        // Get all categories from database
        String[] categories = new Task().getAllCategories();
        categoryComboBox = new JComboBox<>(categories);
        categoryComboBox.setEditable(true);
    }
    
    private void saveTask() {
        // Get form data
        String title = titleField.getText().trim();
        String category = (String) categoryComboBox.getSelectedItem();
        String description = descriptionField.getText().trim();
        String location = locationField.getText().trim();
        LocalDateTime dateTime = dateTimePicker.getDateTimeStrict();
        
        // Validate all fields are filled
        if (title.isEmpty() || dateTime == null || (category == null || category.isEmpty())) {
            JOptionPane.showMessageDialog(this,
                    "Please fill Title, Date/Time and Category.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate date and time is not in the past
        if (dateTime.isBefore(LocalDateTime.now())) {
            JOptionPane.showMessageDialog(this,
                    "Cannot create a task for a past date and time.",
                    "Invalid Date/Time",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create and add task
        task.setTitle(title);
        task.setCategory(category);
        task.setDescription(description);
        task.setLocation(location);
        task.setDatetime(dateTime);

        if (isEditMode) {
            task.update();
            JOptionPane.showMessageDialog(this,
                    "Task updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close dialog
            return;
        }
        
        boolean success = task.add();
        
        if (success) {
            // Show success message
            JOptionPane.showMessageDialog(this,
                    "Task created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            
            dispose(); // Close dialog
        } else {
            // Show error message
            JOptionPane.showMessageDialog(this,
                    "Failed to create Task. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
  
    }

}