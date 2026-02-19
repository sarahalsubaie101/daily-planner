package com.iau.dailyplanner.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.iau.dailyplanner.db.model.*;
import com.iau.dailyplanner.ui.admin.AdminFrame;
import com.iau.dailyplanner.ui.user.UserFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.*;

public class LoginFrame extends BaseFrame {
    
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    
    public LoginFrame() {
        super(); // call the constructor of the superclass (BaseFrame)
        setTitle("Login - Daily Planner System");
        
        // Create components
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50)); // add padding to the main panel
        
        // Title
        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0)); // Add space below title
        
        // Form panel - using GridLayout for simple vertical stacking
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // More space between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Create fonts
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);
        
        // Create labels
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        
        // Create text fields
        usernameField = new JTextField();
        usernameField.setFont(fieldFont);
        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        
        // Make fields taller
        Dimension fieldSize = new Dimension(250, 35);
        usernameField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        
        // Add components to form panel with GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        formPanel.add(usernameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        formPanel.add(passwordField, gbc);
        
        // Center the form panel in the UI
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(formPanel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0)); // Add space above buttons
        
        loginButton = new JButton("Login");
        backButton = new JButton("Back");
        
        // Style buttons with larger font and size
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        loginButton.setFont(buttonFont);
        backButton.setFont(buttonFont);
        
        Dimension buttonSize = new Dimension(140, 40);
        loginButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
        
        // Make buttons look better
        loginButton.setFocusPainted(false);
        backButton.setFocusPainted(false);
        
        // Add margins inside buttons
        loginButton.setMargin(new Insets(8, 20, 8, 20));
        backButton.setMargin(new Insets(8, 20, 8, 20));
        
        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);
        
        // Add panels to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame and set a larger size
        getContentPane().add(mainPanel);
        setSize(500, 400);
        setLocationRelativeTo(null); // center the frame on the screen
    }
    
    private void login() {
        // get the username and password from the text fields
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        // make sure the username and password are not empty
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Username and password are required!",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Try to authenticate Admin or User
        Admin admin = new Admin();
        User user = new User();

        // login as admin
        if (admin.login(username, password)) {
            // Open admin dashboard with admin info
            AdminFrame adminFrame = new AdminFrame(admin);
            adminFrame.setVisible(true);
            this.dispose();
            return;

        // login as user
        } else if (user.login(username, password)) {
            // Open user dashboard with user info
            UserFrame userFrame = new UserFrame(user);
            userFrame.setVisible(true);
            this.dispose();
            return;

        // Authentication failed
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void goBack() {
        WelcomeFrame welcomeFrame = new WelcomeFrame();
        welcomeFrame.setVisible(true);
        this.dispose();
    }
}