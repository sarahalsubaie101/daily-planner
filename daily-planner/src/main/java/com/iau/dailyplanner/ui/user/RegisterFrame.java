package com.iau.dailyplanner.ui.user;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.iau.dailyplanner.db.model.*;
import com.iau.dailyplanner.ui.BaseFrame;
import com.iau.dailyplanner.ui.LoginFrame;
import com.iau.dailyplanner.ui.WelcomeFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.*;

public class RegisterFrame extends BaseFrame {
    
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton registerButton;
    private JButton backButton;
    
    public RegisterFrame() {
        super(); // call the constructor of the superclass (BaseFrame)
        setTitle("Register - Daily Planner System");
        
        // Create components
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Title
        JLabel titleLabel = new JLabel("Register New Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0)); // Add space below title
        
        // Form panel with GridBagLayout for vertical stacking
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10); // More space between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Create components with larger font
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        
        // Set font for all labels
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);
        confirmPasswordLabel.setFont(labelFont);
        firstNameLabel.setFont(labelFont);
        lastNameLabel.setFont(labelFont);
        
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        
        // Set font for all fields
        usernameField.setFont(fieldFont);
        passwordField.setFont(fieldFont);
        confirmPasswordField.setFont(fieldFont);
        firstNameField.setFont(fieldFont);
        lastNameField.setFont(fieldFont);
        
        // Set minimum size for all text fields
        Dimension fieldSize = new Dimension(300, 35);
        usernameField.setMinimumSize(fieldSize);
        passwordField.setMinimumSize(fieldSize);
        confirmPasswordField.setMinimumSize(fieldSize);
        firstNameField.setMinimumSize(fieldSize);
        lastNameField.setMinimumSize(fieldSize);
        
        // Also set preferred size
        usernameField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        confirmPasswordField.setPreferredSize(fieldSize);
        firstNameField.setPreferredSize(fieldSize);
        lastNameField.setPreferredSize(fieldSize);
        
        // Add components to form panel with GridBagLayout       
        // Username row
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        formPanel.add(usernameField, gbc);
        
        // Password row
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        formPanel.add(passwordField, gbc);
        
        // Confirm Password row
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(confirmPasswordLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        formPanel.add(confirmPasswordField, gbc);
        
        // First Name row
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        formPanel.add(firstNameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.7;
        formPanel.add(firstNameField, gbc);
        
        // Last Name row
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        formPanel.add(lastNameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0.7;
        formPanel.add(lastNameField, gbc);
        
        // Set consistent width for the form panel
        formPanel.setPreferredSize(new Dimension(500, 300));
        
        // Center the form panel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(formPanel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0)); // Add space above buttons
        
        registerButton = new JButton("Register");
        backButton = new JButton("Back");
        
        // Style buttons with larger font and size
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        registerButton.setFont(buttonFont);
        backButton.setFont(buttonFont);
        
        Dimension buttonSize = new Dimension(140, 40);
        registerButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
        
        // Make buttons look better
        registerButton.setFocusPainted(false);
        backButton.setFocusPainted(false);
        
        // Add margins inside buttons
        registerButton.setMargin(new Insets(8, 20, 8, 20));
        backButton.setMargin(new Insets(8, 20, 8, 20));
        
        // Add action listeners
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        
        // Add panels to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame and set a larger size
        getContentPane().add(mainPanel);
        setSize(650, 550);
        setLocationRelativeTo(null); // center the frame on the screen
    }
    
    private void register() {
        // Get input from the form
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        
        // Make sure all fields are filled
        if (username.isEmpty() || password.isEmpty() || 
            confirmPassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Make sure passwords match
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String passwordError = validatePassword(password);
if (passwordError != null) {
    JOptionPane.showMessageDialog(this, passwordError, "Weak Password", JOptionPane.ERROR_MESSAGE);
    return;
}

        
        User user = new User();

        // Check if username already taken
        if (user.findByUsername(username) != null) {
            JOptionPane.showMessageDialog(this, 
                    "Username already exists!", 
                    "Registration Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if username is equal to 'admin'
        if (username.equals("admin")) {
            JOptionPane.showMessageDialog(this, 
                    "Username cannot be 'admin'", 
                    "Registration Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

  
        // Create and add new user
        User newUser = new User(firstName, lastName, username, password);
        boolean success = newUser.add();
        
        if (success) {
            // Registration successful
            JOptionPane.showMessageDialog(this, 
                    "Registration successful! Please login.", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            // Open login page
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
            this.dispose(); // close the register frame
        
        } else {
            // Show error message
            JOptionPane.showMessageDialog(this, 
                    "Registration failed! Please try again.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
            

    }
    
    private void goBack() {
        WelcomeFrame welcomeFrame = new WelcomeFrame();
        welcomeFrame.setVisible(true);
        this.dispose();
    }
    
    
public String validatePassword(String password) {

    if (password.length() < 8) {
        return "Password must be at least 8 characters long.";
    }

    if (!password.matches(".*[A-Z].*")) {
        return "Password must contain at least one uppercase letter.";
    }

    if (!password.matches(".*[a-z].*")) {
        return "Password must contain at least one lowercase letter.";
    }

    if (!password.matches(".*\\d.*")) {
        return "Password must contain at least one digit.";
    }

    if (!password.matches(".*[^a-zA-Z0-9].*")) {
        return "Password must contain at least one special character.";
    }

    return null; 
}

}