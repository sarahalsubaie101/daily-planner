package com.iau.dailyplanner.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseDialog extends JDialog {
    
    public JPanel mainPanel;
    public JPanel buttonPanel;
    public JButton cancelButton;
    
    public BaseDialog(JFrame parent, String title) {
        super(parent, title, true);
        
        // Set dialog properties
        setSize(400, 300); // size
        setLocationRelativeTo(parent); // center the dialog on the parent frame
        
        
        // Initialize panels
        mainPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // Create cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // close the dialog
            }
        });
        
        // Apply button style to cancel button
        setButtonStyle(cancelButton);
        
        buttonPanel.add(cancelButton);
        
        // Add panels to dialog
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // add the button panel to the bottom of the main panel
        getContentPane().add(mainPanel); // add the main panel to the dialog
    }
    
    public void setButtonStyle(JButton button) {
        // Make buttons wider and bigger
        Dimension buttonSize = new Dimension(120, 35);
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        
        // Make the font a bit larger
        Font newFont = new Font("Arial", Font.BOLD, 15);
        button.setFont(newFont);
    }
}