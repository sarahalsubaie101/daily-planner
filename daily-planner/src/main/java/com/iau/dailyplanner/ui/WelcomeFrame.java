package com.iau.dailyplanner.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.iau.dailyplanner.ui.user.RegisterFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

public class WelcomeFrame extends BaseFrame {
    
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton loginButton;
    private JButton registerButton;
    
    public WelcomeFrame() {
        super(); // call the constructor of the BaseFrame superclass
        setTitle("Daily Planner System");
        
        // Create components
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 50, 50)); // add padding
        
        // Title and logo
        JPanel headerPanel = new JPanel(new BorderLayout(0, 25)); // Add space between logo and title
        headerPanel.setBorder(new EmptyBorder(0, 0, 30, 0)); // Add space below header
        
        // Load and display the logo image
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));
        Image scaledImage = logoIcon.getImage().getScaledInstance(380, 180, Image.SCALE_SMOOTH); // Slightly smaller logo
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center logo
     
        headerPanel.add(logoLabel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 0, 20)); // More space between buttons
        
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        
        // Style buttons
        Dimension buttonSize = new Dimension(220, 45); // Slightly larger buttons
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);
        
        loginButton.setFont(new Font("Arial", Font.BOLD, 16)); // Larger font
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Make buttons look better
        loginButton.setFocusPainted(false);
        registerButton.setFocusPainted(false);
        
        // Add margins inside buttons
        loginButton.setMargin(new Insets(10, 20, 10, 20));
        registerButton.setMargin(new Insets(10, 20, 10, 20));
        
        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginFrame();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegisterFrame();
            }
        });
        
        // Add buttons to panel
        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setLayout(new BoxLayout(buttonWrapper, BoxLayout.Y_AXIS));
        
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        
        // Center buttons
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonWrapper.add(Box.createVerticalGlue());
        buttonWrapper.add(buttonPanel);
        buttonWrapper.add(Box.createVerticalGlue());
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonWrapper, BorderLayout.CENTER);
        
        // Add main panel to frame
        getContentPane().add(mainPanel);
        
        // Set frame size
        setSize(500, 480);
        setLocationRelativeTo(null); // center the frame on the screen
    }
    
    private void openLoginFrame() {
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
        this.dispose(); // close the current frame
    }
    
    private void openRegisterFrame() {
        RegisterFrame registerFrame = new RegisterFrame();
        registerFrame.setVisible(true);
        this.dispose(); // close the current frame
    }
}