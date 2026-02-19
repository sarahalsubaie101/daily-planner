package com.iau.dailyplanner.ui.admin;

import com.iau.dailyplanner.db.model.*;
import com.iau.dailyplanner.ui.BaseFrame;
import com.iau.dailyplanner.ui.TaskDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminFrame extends BaseFrame {
    
    private JTabbedPane tabbedPane;
    private JPanel taskPanel;
    private JPanel usersPanel;
    private JPanel feedbackPanel;
    private JButton editTaskButton;
    private JButton deleteTaskButton;
    private JButton addUserButton;
    private JButton editUserButton;
    private JButton deleteUserButton;
    private JButton logoutButton;

    private Admin admin;

    private JTable taskTable;
    private JTable usersTable;
    private JTable feedbackTable;

    private DefaultTableModel taskTableModel;
    private DefaultTableModel usersTableModel;
    private DefaultTableModel feedbackTableModel;

    private List<Task> taskList;
    private List<User> userList;
    private List<Feedback> feedbackList;
    
    public AdminFrame(Admin admin) {
        super(); // call the constructor of the BaseFrame superclass
        setTitle("Admin Dashboard - Daily Planner System");
        this.admin = admin;

        setSize(800, 600); // set the size of the frame
        
        // Setup UI
        setupUI();
    }
    
    private void setupUI() {
        // Setup main components
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 15));
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // ------------------ Create task tab ------------------ 
        taskPanel = new JPanel(new BorderLayout());
        JPanel taskListPanel = createTaskListPanel();
        JPanel taskButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // Action Buttons
        editTaskButton = new JButton("Edit Task");
        deleteTaskButton = new JButton("Delete Task");
        
        editTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditTaskDialog();
            }
        });
        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });

        taskButtonPanel.add(editTaskButton);
        taskButtonPanel.add(deleteTaskButton);
        taskPanel.add(taskListPanel, BorderLayout.CENTER);
        taskPanel.add(taskButtonPanel, BorderLayout.SOUTH);
        
        // ------------------ Create users tab ------------------
        usersPanel = new JPanel(new BorderLayout());
        JPanel usersListPanel = createUsersListPanel();
        JPanel usersButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        addUserButton = new JButton("Add User");
        editUserButton = new JButton("Edit User");
        deleteUserButton = new JButton("Delete User");
        
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddUserDialog();
            }
        });
        editUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditUserDialog();
            }
        });
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        usersButtonPanel.add(addUserButton);
        usersButtonPanel.add(editUserButton);
        usersButtonPanel.add(deleteUserButton);
        usersPanel.add(usersListPanel, BorderLayout.CENTER);
        usersPanel.add(usersButtonPanel, BorderLayout.SOUTH);
        
        // ------------------ Create feedback tab ------------------ 
        feedbackPanel = new JPanel(new BorderLayout());
        JPanel feedbackListPanel = createFeedbacksListPanel();
        feedbackPanel.add(feedbackListPanel, BorderLayout.CENTER);
        
        // ------------------------------------------------------------------------

        // Add tabs to tabbed pane
        tabbedPane.addTab("Tasks", taskPanel);
        tabbedPane.addTab("Users", usersPanel);
        tabbedPane.addTab("Feedbacks", feedbackPanel);
        
        // Create header panel with welcome text and logout button
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo panel (centered)
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));
        Image scaledImage = logoIcon.getImage().getScaledInstance(480, 180, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        logoPanel.add(logoLabel);
        headerPanel.add(logoPanel);

        // Panel for welcome message and logout button
        JPanel bottomRow = new JPanel(new BorderLayout());
        
        // Welcome message on the left
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel("Welcome, " + admin.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomePanel.add(welcomeLabel);
        bottomRow.add(welcomePanel, BorderLayout.WEST);
        
        // Logout button on the right
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        logoutPanel.add(logoutButton);
        bottomRow.add(logoutPanel, BorderLayout.EAST);

        headerPanel.add(bottomRow);

        // Style the buttons
        editTaskButton.setFont(new Font("Arial", Font.BOLD, 14));
        editTaskButton.setBackground(new Color(255, 152, 0)); // Orange
        deleteTaskButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteTaskButton.setBackground(new Color(244, 67, 54)); // Red
        addUserButton.setFont(new Font("Arial", Font.BOLD, 14));
        addUserButton.setBackground(new Color(33, 150, 243)); // Blue
        editUserButton.setFont(new Font("Arial", Font.BOLD, 14));
        editUserButton.setBackground(new Color(255, 152, 0)); // Orange
        deleteUserButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteUserButton.setBackground(new Color(244, 67, 54)); // Red

        // Set preferred size for all buttons
        Dimension buttonSize = new Dimension(200, 50);
        editTaskButton.setPreferredSize(buttonSize);
        deleteTaskButton.setPreferredSize(buttonSize);
        addUserButton.setPreferredSize(buttonSize);
        editUserButton.setPreferredSize(buttonSize);
        deleteUserButton.setPreferredSize(buttonSize);

        // Add components to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }
    
    // ================================= TASKs TAB =================================
    private JPanel createTaskListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Column names
        String[] columns = {"Title", "Category", "Date/Time", "Status", "Username"};
        
        // Create table with empty DefaultTableModel
        taskTableModel = new DefaultTableModel(columns, 0);
        taskTable = new JTable(taskTableModel);
        taskTable.setRowHeight(25);
        taskTable.setFont(new Font("Arial", Font.PLAIN, 14));
        taskTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(taskTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Load task data
        loadTaskData();
        
        return panel;
    }
    
    private void loadTaskData() {
        // Clear existing data
        taskTableModel.setRowCount(0);
        
        // Get task list from database
        taskList = new Task().findAll();
        
        // Add task to table
        for (Task task : taskList) {
            taskTableModel.addRow(new Object[] {
                task.getTitle(),
                task.getCategory(),
                task.getDatetimeFormated(),
                task.getStatus(),
                task.getUser().getUsername()
            });
        }
    }

    private void openEditTaskDialog() {
        // Get selected row of task from table
        int selectedRow = taskTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select task to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get task from the list
        Task task = taskList.get(selectedRow);

        // Open edit dialog with task data
        TaskDialog dialog = new TaskDialog(this, task.getUser(), task);
        dialog.setVisible(true);

        // refresh lists
        loadTaskData();
    }

    private void deleteTask() {
        // Get selected task from table
        int selectedRow = taskTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select task to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get task from the list
        Task task = taskList.get(selectedRow);
        
        // Show confirmation dialog
        int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete task: " + task.getTitle() + "?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            task.delete();

            // Refresh task list
            loadTaskData();

            // Show success message
            JOptionPane.showMessageDialog(this, 
                    "Task deleted successfully.", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ================================= USERS TAB =================================
    private JPanel createUsersListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Column names
        String[] columns = {"User ID", "First Name", "Last Name", "Username"};
        
        // Create table with empty DefaultTableModel
        usersTableModel = new DefaultTableModel(columns, 0);
        usersTable = new JTable(usersTableModel);
        usersTable.setRowHeight(25);
        usersTable.setFont(new Font("Arial", Font.PLAIN, 14));
        usersTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(usersTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Load user data
        loadUserData();
        
        return panel;
    }
    
    private void loadUserData() {
        // Clear existing data
        usersTableModel.setRowCount(0);
        
        // Get user list from database
        userList = new User().findAll();
        
        // Add users to table
        for (User user : userList) {
            usersTableModel.addRow(new Object[] {
                String.valueOf(user.getId()),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername()
            });
        }

        // also load task data to refresh username changes
        loadTaskData();
    }

    private void openAddUserDialog() {
        UserFormDialog dialog = new UserFormDialog(this, null);
        dialog.setVisible(true);

        // refresh lists
        loadUserData();
    }
    
    private void openEditUserDialog() {
        // Get selected user from table
        JPanel usersListPanel = (JPanel)usersPanel.getComponent(0);
        JScrollPane scrollPane = (JScrollPane)usersListPanel.getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get user from the list
        User user = userList.get(selectedRow);
        
        // Open edit dialog with user data
        UserFormDialog dialog = new UserFormDialog(this, user);
        dialog.setVisible(true);

        // refresh lists
        loadUserData();
    }

    private void deleteUser() {
        // Get selected user from table
        int selectedRow = usersTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get user from the list
        User user = userList.get(selectedRow);
        
        // Show confirmation dialog
        int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete user: " + user.getFullname() + "?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            // Delete user from database
            user.delete();
            
            // Refresh user list
            loadUserData();

            // Show success message
            JOptionPane.showMessageDialog(this, 
                    "User deleted successfully.", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ================================= FEEDBACKS TAB =================================
    private JPanel createFeedbacksListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] columns = {"Rating", "Comment", "Username"};
        
        // Create table with empty DefaultTableModel
        feedbackTableModel = new DefaultTableModel(columns, 0);
        feedbackTable = new JTable(feedbackTableModel);
        feedbackTable.setRowHeight(25);
        feedbackTable.setFont(new Font("Arial", Font.PLAIN, 14));
        feedbackTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Load feedback data
        loadFeedbacksData();
        
        return panel;
    }
    
    private void loadFeedbacksData() {
        // Clear existing data
        feedbackTableModel.setRowCount(0);
        
        // Get admin feedback from database
        feedbackList = new Feedback().findAll();
        
        // Add feedback to table
        for (Feedback feedback : feedbackList) {
            feedbackTableModel.addRow(new Object[] {
                feedback.getRating(),
                feedback.getComment(),
                feedback.getUser().getUsername()
            });
        }
    }
}