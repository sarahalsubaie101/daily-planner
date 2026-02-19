package com.iau.dailyplanner.ui;

import javax.swing.*;

public class BaseFrame extends JFrame {
    
    public BaseFrame() {
        // Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 600); // size
        setLocationRelativeTo(null); // center the frame on the screen
        
        // Set the look-and-feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        // Show confirmation dialog
        int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to logout?", 
                "Confirm Logout", 
                JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            // Navigate back to welcome page
            WelcomeFrame welcomeFrame = new WelcomeFrame();
            welcomeFrame.setVisible(true);
            this.dispose();
        }
    }
}