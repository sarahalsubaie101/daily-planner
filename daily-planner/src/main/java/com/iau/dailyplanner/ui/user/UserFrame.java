package com.iau.dailyplanner.ui.user;

import com.iau.dailyplanner.db.model.Task;
import com.iau.dailyplanner.db.model.Feedback;
import com.iau.dailyplanner.db.model.User;
import com.iau.dailyplanner.ui.BaseFrame;
import com.iau.dailyplanner.ui.TaskDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserFrame extends BaseFrame {
    
    private JTabbedPane tabbedPane;
    private JPanel taskPanel;
    private JButton addTaskButton;
    private JButton editTaskButton;
    private JButton deleteTaskButton;
    private JButton markCompletedButton;
    private JButton logoutButton;
    private JComboBox<String> categoryFilterComboBox;

    private JTable taskTable;
    private DefaultTableModel taskTableModel;

    private JTable completedTaskTable;
    private DefaultTableModel completedTaskTableModel;

    private User user;
    private List<Task> taskList;
    private String selectdCategory = null;
    
    public UserFrame(User user) {
        super(); // call the constructor of the BaseFrame superclass
        setTitle("User Dashboard - Daily Planner System");
        this.user = user;
        
        // Setup UI
        setupUI();
    }
    
    private void setupUI() {
        // ----------------------  Create header panel with welcome text and logout button ---------------------- 
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

        // Left side: Welcome message and filter stacked vertically
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Inner padding

        // Welcome message
        JPanel welcomePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFullname());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomePanel.add(welcomeLabel);
        welcomePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(welcomePanel);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        JLabel categoryLabel = new JLabel("Category: ");
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        filterPanel.add(categoryLabel);
        categoryFilterComboBox = new JComboBox<>();
        categoryFilterComboBox.setPreferredSize(new Dimension(200, 30));
        categoryFilterComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        setupCategoryComboBox();
        filterPanel.add(categoryFilterComboBox);
        filterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(filterPanel);

        bottomRow.add(leftPanel, BorderLayout.WEST);

        // Logout button on the right
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setPreferredSize(new Dimension(120, 40));
        logoutButton.setBackground(new Color(244, 67, 54)); // Red color
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        logoutPanel.add(logoutButton);
        bottomRow.add(logoutPanel, BorderLayout.EAST);

        headerPanel.add(bottomRow);

        // ----------------------  Create tabbed pane ---------------------- 
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.PLAIN, 15));
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create task tab
        taskPanel = new JPanel(new BorderLayout());
        JPanel taskListPanel = createTaskListPanel();
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        markCompletedButton = new JButton("Mark as Completed");
        markCompletedButton.setFont(new Font("Arial", Font.BOLD, 14));
        markCompletedButton.setPreferredSize(new Dimension(200, 50));
        markCompletedButton.setBackground(new Color(76, 175, 80));
        markCompletedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markTaskAsCompleted();
            }
        });

        addTaskButton = new JButton("Create Task");
        addTaskButton.setFont(new Font("Arial", Font.BOLD, 14));
        addTaskButton.setPreferredSize(new Dimension(200, 50));
        addTaskButton.setBackground(new Color(33, 150, 243));
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openTaskDialog();
            }
        });

        editTaskButton = new JButton("Edit Task");
        editTaskButton.setFont(new Font("Arial", Font.BOLD, 14));
        editTaskButton.setPreferredSize(new Dimension(200, 50));
        editTaskButton.setBackground(new Color(255, 152, 0));
        editTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEditTaskDialog();
            }
        });

        deleteTaskButton = new JButton("Delete Task");
        deleteTaskButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteTaskButton.setPreferredSize(new Dimension(200, 50));
        deleteTaskButton.setBackground(new Color(244, 67, 54));
        deleteTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });
        
        actionButtonPanel.add(addTaskButton);
        actionButtonPanel.add(markCompletedButton);
        actionButtonPanel.add(editTaskButton);
        actionButtonPanel.add(deleteTaskButton);
        taskPanel.add(taskListPanel, BorderLayout.CENTER);
        taskPanel.add(actionButtonPanel, BorderLayout.SOUTH);

        // ----------------------  Create Completed tabbed pane ---------------------- 
        JPanel completedTaskPanel = new JPanel(new BorderLayout());
        JPanel completedTaskListPanel = createCompletedTaskListPanel();
        completedTaskPanel.add(completedTaskListPanel, BorderLayout.CENTER);

        
        // Add tabs to tabbed pane
        tabbedPane.addTab("Tasks", taskPanel);
        tabbedPane.addTab("Completed Tasks", completedTaskPanel);
        
        
        // ---------------------- Add components to frame ---------------------- 
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // Customize table appearance
        customizeTable(taskTable);
        customizeTable(completedTaskTable);

        // Load task data
        loadTaskData();
    }
    
    private JPanel createTaskListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // add padding to the panel
        
        // Visible columns
        String[] columns = {"Title", "Category", "Date/Time", "Status", "Description", "Location"};
        
        // Create table with DefaultTableModel
        taskTableModel = new DefaultTableModel(columns, 0);
        
        taskTable = new JTable(taskTableModel);
        taskTable.setRowHeight(25);
        taskTable.setFont(new Font("Arial", Font.PLAIN, 14));
        taskTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(taskTable);
        panel.add(scrollPane, BorderLayout.CENTER);
       
        return panel;
    }

    private JPanel createCompletedTaskListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // add padding to the panel
        
        // Visible columns
        String[] columns = {"Title", "Category", "Date/Time", "Status", "Description", "Location"};
        
        // Create table with DefaultTableModel
        completedTaskTableModel = new DefaultTableModel(columns, 0);
        
        completedTaskTable = new JTable(completedTaskTableModel);
        completedTaskTable.setRowHeight(25);
        completedTaskTable.setFont(new Font("Arial", Font.PLAIN, 14));
        completedTaskTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(completedTaskTable);
        panel.add(scrollPane, BorderLayout.CENTER);
       
        return panel;
    }
    
    private void loadTaskData() {
        // ---- Refresh Category ComboBox items ----
        try {
            categoryFilterComboBox.removeAllItems();
        } catch (Exception e) {
            // ignore
        }
        categoryFilterComboBox.addItem("All Categories");
        String[] categories = new Task().getAllCategories();
        for (String category : categories) {
            categoryFilterComboBox.addItem(category);
        }

        // Restore selected category
        if (selectdCategory != null) {
            categoryFilterComboBox.setSelectedItem(selectdCategory);
        } else {
            categoryFilterComboBox.setSelectedIndex(0);
        }

        // Clear existing data
        taskTableModel.setRowCount(0);
        completedTaskTableModel.setRowCount(0);
        
        // Get task list from database
        taskList = new Task().findByUser(user, selectdCategory);
        
        for (Task task : taskList) {
            if (task.isCompleted()) {
                completedTaskTableModel.addRow(new Object[] {
                    task.getTitle(),
                    task.getCategory(),
                    task.getDatetimeFormated(),
                    task.getStatus(),
                    task.getDescription(),
                    task.getLocation()
                });
            } else {
                taskTableModel.addRow(new Object[] {
                    task.getTitle(),
                    task.getCategory(),
                    task.getDatetimeFormated(),
                    task.getStatus(),
                    task.getDescription(),
                    task.getLocation()
                });
            }

        }
    }

    private void openTaskDialog() {      
        TaskDialog dialog = new TaskDialog(this, user, null);
        dialog.setVisible(true);

        // refresh the task list
        loadTaskData(); 
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

    private void markTaskAsCompleted() {
        // Get selected task from table
        int selectedRow = taskTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select task first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Get task from the list
        Task task = taskList.get(selectedRow);
        
        // Show confirmation dialog
        int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to mark task: " + task.getTitle() + " as completed?", 
                "Confirm Completion", 
                JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            task.setCompleted(true);
            task.update();

            // Refresh task list
            loadTaskData();

            // Show success message
            JOptionPane.showMessageDialog(this, 
                    "Task completed successfully.", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);

            openFeedbackDialog();
        }
    }

    private void setupCategoryComboBox() {
        // Get all categories from database
        String[] categories = new Task().getAllCategories();
        categoryFilterComboBox.addItem("All Categories");
        for (String category : categories) {
            categoryFilterComboBox.addItem(category);
        }
        categoryFilterComboBox.setSelectedIndex(0);

        categoryFilterComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) categoryFilterComboBox.getSelectedItem();
                if (selected.equals("All Categories")) {
                    selectdCategory = null;
                } else {
                    selectdCategory = selected;
                }
                loadTaskData();
            }
        });
    }
   

    private void openFeedbackDialog() {
        Feedback oldFeedback = new Feedback().findByUser(user);
        if (oldFeedback != null) {
            // Feedback already submitted
            return;
        } else {
            // Open feedback dialog
            FeedbackDialog dialog = new FeedbackDialog(this, user);
            dialog.setVisible(true);
        }
    }

    private void customizeTable(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                        boolean isSelected, boolean hasFocus,
                                                        int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Reset default colors
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

                // Only change color for Status column (4th column, index 3)
                if (column == 3) {
                    String status = (String) value;

                    if ("Completed".equalsIgnoreCase(status)) {
                        c.setForeground(new Color(0, 128, 0)); // Green
                    } else if ("Late".equalsIgnoreCase(status)) {
                        c.setForeground(new Color(200, 0, 0)); // Red
                    } else if ("Pending".equalsIgnoreCase(status)) {
                        c.setForeground(new Color(205, 102, 0)); // Dark orange
                    }
                    
                    c.setFont(c.getFont().deriveFont(Font.BOLD)); // optional: make it bold
                }

                return c;
            }
        });
    }
}