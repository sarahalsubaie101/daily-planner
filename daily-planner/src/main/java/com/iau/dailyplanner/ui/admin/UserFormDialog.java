package com.iau.dailyplanner.ui.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.iau.dailyplanner.db.model.User;
import com.iau.dailyplanner.ui.BaseDialog;

public class UserFormDialog extends BaseDialog {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton actionButton;
    
    private boolean isEditMode;
    private User user;
    
    public UserFormDialog(JFrame parent, User user) {
        super(parent, (user == null ? "Add" : "Edit") + " User");
        
        // Check if edit mode or create mode
        isEditMode = (user != null);

        if (isEditMode) {
            this.user = user;
        } else {
            this.user = new User();
        }
        
        setupUI();
        
        if (isEditMode) {
            // Set existing values for edit mode
            firstNameField.setText(user.getFirstName());
            lastNameField.setText(user.getLastName());
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            confirmPasswordField.setText(user.getPassword());
            actionButton.setText("Update");
        } else {
            actionButton.setText("Add");
        }
        
        // Add action listeners
        actionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveUser();
            }
        });
    }
    
    private void setupUI() {
        // Create form panel with GridBagLayout for better control
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Create fonts
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);
        
        // Create labels
        JLabel idLabel = new JLabel("ID:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        
        // Set label fonts
        idLabel.setFont(labelFont);
        firstNameLabel.setFont(labelFont);
        lastNameLabel.setFont(labelFont);
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        confirmPasswordLabel.setFont(labelFont);
        
        // Create text fields
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        
        // Set field fonts
        firstNameField.setFont(fieldFont);
        lastNameField.setFont(fieldFont);
        usernameField.setFont(fieldFont);
        passwordField.setFont(fieldFont);
        confirmPasswordField.setFont(fieldFont);
        
        // Set field size
        Dimension fieldSize = new Dimension(250, 30);
        firstNameField.setPreferredSize(fieldSize);
        lastNameField.setPreferredSize(fieldSize);
        usernameField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        confirmPasswordField.setPreferredSize(fieldSize);
        
        // Add components with GridBagConstraints
        gbc.gridx = 0; // column
        gbc.gridy = 0; // row
        gbc.weightx = 0;
        formPanel.add(firstNameLabel, gbc);
        gbc.gridx = 1; // next column for the field
        gbc.weightx = 1; // make the field take the remaining space
        formPanel.add(firstNameField, gbc);
        
        gbc.gridx = 0; // column
        gbc.gridy = 1; // row
        gbc.weightx = 0;
        formPanel.add(lastNameLabel, gbc);
        gbc.gridx = 1; // next column for the field
        gbc.weightx = 1; // make the field take the remaining space
        formPanel.add(lastNameField, gbc);
        
        gbc.gridx = 0; // column
        gbc.gridy = 2; // row
        gbc.weightx = 0;
        formPanel.add(usernameLabel, gbc);
        gbc.gridx = 1; // next column for the field
        gbc.weightx = 1; // make the field take the remaining space
        formPanel.add(usernameField, gbc);
        
        gbc.gridx = 0; // column
        gbc.gridy = 3; // row
        gbc.weightx = 0;
        formPanel.add(passwordLabel, gbc);
        gbc.gridx = 1; // next column for the field
        gbc.weightx = 1; // make the field take the remaining space
        formPanel.add(passwordField, gbc);
        
        gbc.gridx = 0; // column
        gbc.gridy = 4; // row
        gbc.weightx = 0;
        formPanel.add(confirmPasswordLabel, gbc);
        gbc.gridx = 1; // next column for the field
        gbc.weightx = 1; // make the field take the remaining space
        formPanel.add(confirmPasswordField, gbc);
        
        // Create action button
        actionButton = new JButton();
        
        // Apply button style to action button
        setButtonStyle(actionButton);
        
        buttonPanel.add(actionButton);
        
        // Add form panel to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
    }
    
    private void saveUser() {
        // Get values from fields
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        // add all validation logic here
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "All fields are required!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // check if password and confirm password are the same
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // prevent username to be 'admin'
        if (username.equals("admin")) {
            JOptionPane.showMessageDialog(this,
                    "Username cannot be 'admin'",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            User foundUserByUsername = new User().findByUsername(username);

            if (isEditMode) { // edit mode

                // check if USERNAME is TAKEN by another user
                if (foundUserByUsername != null) {
                    if (foundUserByUsername.getId() != user.getId()) { // if not the same user, show error message
                        // Show error message
                        JOptionPane.showMessageDialog(this, 
                                "Username already taken by another user.", 
                                "Error", 
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
   
            } else { // add mode
                
                // check if USERNAME is TAKEN by another user
                if (foundUserByUsername != null) {
                    JOptionPane.showMessageDialog(this, 
                            "Username already exists in users!", 
                            "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }
            
            boolean success;

            // Set all fields
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            
            if (isEditMode) { // edit mode
                // update user in the database
                success = user.update();
                user.updatePassword(password);
            
            } else { // add mode
                // Create add new user to the database
                user.setPassword(password);
                success = user.add();
            }
            
            if (success) {
                // Show success message
                JOptionPane.showMessageDialog(this,
                        "User Saved Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                
                dispose(); // close the dialog
            } else {
                // Show error message
                JOptionPane.showMessageDialog(this,
                        "Failed to save user. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            // Show error message
            JOptionPane.showMessageDialog(this,
                    "Error saving user: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
} 