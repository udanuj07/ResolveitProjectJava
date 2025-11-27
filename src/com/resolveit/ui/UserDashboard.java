package com.resolveit.ui;

import com.resolveit.dao.ComplaintDAO;
import com.resolveit.dao.FeedbackDAO;
import com.resolveit.model.Complaint;
import com.resolveit.model.Feedback;
import com.resolveit.model.User;
import com.resolveit.util.ValidationUtil;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * User Dashboard - Main interface for regular users
 * Allows users to submit and view complaints, and provide feedback
 */
public class UserDashboard extends JFrame {
    private User currentUser;
    private JTable complaintsTable;
    private DefaultTableModel tableModel;
    
    public UserDashboard(User user) {
        this.currentUser = user;
        initializeUI();
        loadComplaints();
    }
    
    private void initializeUI() {
        setTitle("ResolveIt - User Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.add(new JLabel("Welcome, " + currentUser.getUsername()));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        headerPanel.add(logoutButton);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Center - Complaints Table
        String[] columnNames = {"ID", "Title", "Category", "Status", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        complaintsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(complaintsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton submitComplaintButton = new JButton("Submit Complaint");
        submitComplaintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSubmitComplaintDialog();
            }
        });
        buttonPanel.add(submitComplaintButton);
        
        JButton viewFeedbackButton = new JButton("View Feedback");
        viewFeedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFeedbackDialog();
            }
        });
        buttonPanel.add(viewFeedbackButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadComplaints();
            }
        });
        buttonPanel.add(refreshButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void loadComplaints() {
        tableModel.setRowCount(0);
        List<Complaint> complaints = ComplaintDAO.getUserComplaints(currentUser.getId());
        
        for (Complaint complaint : complaints) {
            tableModel.addRow(new Object[]{
                complaint.getId(),
                complaint.getTitle(),
                complaint.getCategory(),
                complaint.getStatus(),
                complaint.getCreatedAt()
            });
        }
    }
    
    private void showSubmitComplaintDialog() {
        JDialog dialog = new JDialog(this, "Submit Complaint", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField titleField = new JTextField();
        JTextArea descriptionArea = new JTextArea();
        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"General", "Technical", "Payment", "Service"});
        
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(descriptionArea));
        panel.add(new JLabel("Category:"));
        panel.add(categoryCombo);
        
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionArea.getText();
                String category = (String) categoryCombo.getSelectedItem();
                
                if (ValidationUtil.isValidText(title, 3, 100) && ValidationUtil.isValidText(description, 10, 1000)) {
                    if (ComplaintDAO.submitComplaint(currentUser.getId(), title, description, category)) {
                        JOptionPane.showMessageDialog(dialog, "Complaint submitted successfully!");
                        loadComplaints();
                        dialog.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Invalid input");
                }
            }
        });
        panel.add(submitButton);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showFeedbackDialog() {
        if (complaintsTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a complaint first");
            return;
        }
        
        int complaintId = (int) tableModel.getValueAt(complaintsTable.getSelectedRow(), 0);
        List<Feedback> feedbackList = FeedbackDAO.getComplaintFeedback(complaintId);
        
        StringBuilder feedbackText = new StringBuilder();
        for (Feedback feedback : feedbackList) {
            feedbackText.append("Rating: ").append(feedback.getRating())
                    .append(" - ").append(feedback.getComment())
                    .append("\n");
        }
        
        JTextArea textArea = new JTextArea(feedbackText.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Feedback", JOptionPane.INFORMATION_MESSAGE);
    }
}
