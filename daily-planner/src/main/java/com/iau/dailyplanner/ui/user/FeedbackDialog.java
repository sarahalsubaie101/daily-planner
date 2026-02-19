package com.iau.dailyplanner.ui.user;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.iau.dailyplanner.db.model.Feedback;
import com.iau.dailyplanner.db.model.User;
import com.iau.dailyplanner.ui.BaseDialog;

public class FeedbackDialog extends BaseDialog{

    private JButton submitButton;
    private JComboBox<Integer> ratingComboBox;
    private JTextField commentField;

    private Feedback feedback;

    public FeedbackDialog(JFrame parent, User user) {
        super(parent, "Feedback");

        // Create feedback object
        this.feedback = new Feedback();
        this.feedback.setUser(user);

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Rating
        JLabel ratingLabel = new JLabel("Rating (1-5):");
        ratingComboBox = new JComboBox<>();
        for (int i = 1; i <= 5; i++) {
            ratingComboBox.addItem(i);
        }

        // Comment
        JLabel commentLabel = new JLabel("Comment: (optional)");
        commentField = new JTextField();

        // Add components to form panel
        formPanel.add(ratingLabel);
        formPanel.add(ratingComboBox);
        formPanel.add(commentLabel);
        formPanel.add(commentField);

        // Submit button
        submitButton = new JButton("Send");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submit();
            }
        });

        // Apply button style to submit button
        setButtonStyle(submitButton);
        buttonPanel.add(submitButton);

        // Add form panel to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
    }

    private void submit() {
        // Get data from fields
        int rating = (int) ratingComboBox.getSelectedItem();
        String comment = commentField.getText().trim();

        // Set data to feedback object
        feedback.setRating(rating);
        feedback.setComment(comment);

        // Add feedback to database
        feedback.add();

        // Show success message
        JOptionPane.showMessageDialog(this, 
            "Thank you for your feedback!", 
            "Success", 
            JOptionPane.INFORMATION_MESSAGE
        );

        // Close dialog
        dispose();
    }   

    
}
