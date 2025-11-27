package com.resolveit.ui;

import com.resolveit.dao.ComplaintDAO;
import com.resolveit.dao.FeedbackDAO;
import com.resolveit.model.Complaint;
import com.resolveit.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Admin Dashboard - Management interface for administrators
 * Allows admins to view all complaints and update their status
 */
public class AdminDashboard extends JFrame {
    private User currentAdmin;
    private JTable complaintsTable;
    private DefaultTableModel tableModel;
    
    public AdminDashboard(User user) {
        this.currentAdmin = user;
        initializeUI();
        loadAllComplaints();
    }
    
    private void initializeUI() {
        setTitle("ResolveIt - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.add(new JLabel("Admin: " + currentAdmin.getUsername()));
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
        String[] columnNames = {"ID", "User ID", "Title", "Category", "Status", "Rating", "Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        complaintsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(complaintsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton updateStatusButton = new JButton("Update Status");
        updateStatusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUpdateStatusDialog();
            }
        });
        buttonPanel.add(updateStatusButton);
        
        JButton addFeedbackButton = new JButton("Add Feedback");
        addFeedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddFeedbackDialog();
            }
        });
        buttonPanel.add(addFeedbackButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAllComplaints();
            }
        });
        buttonPanel.add(refreshButton);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void loadAllComplaints() {
        tableModel.setRowCount(0);
        List<Complaint> complaints = ComplaintDAO.getAllComplaints();
        
        for (Complaint complaint : complaints) {
            double avgRating = FeedbackDAO.getAverageRating(complaint.getId());
            tableModel.addRow(new Object[]{
                complaint.getId(),
                complaint.getUserId(),
                complaint.getTitle(),
                complaint.getCategory(),
                complaint.getStatus(),
                String.format("%.2f", avgRating),
                complaint.getCreatedAt()
            });
        }
    }
    
    private void showUpdateStatusDialog() {
        if (complaintsTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a complaint first");
            return;
        }
        
        String[] statuses = {"PENDING", "IN_PROGRESS", "RESOLVED", "CLOSED"};
        String newStatus = (String) JOptionPane.showInputDialog(
            this,
            "Select new status:",
            "Update Complaint Status",
            JOptionPane.QUESTION_MESSAGE,
            null,
            statuses,
            statuses[0]
        );
        
        if (newStatus != null) {
            int complaintId = (int) tableModel.getValueAt(complaintsTable.getSelectedRow(), 0);
            if (ComplaintDAO.updateComplaintStatus(complaintId, newStatus)) {
                JOptionPane.showMessageDialog(this, "Status updated successfully!");
                loadAllComplaints();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update status");
            }
        }
    }
    
    private void showAddFeedbackDialog() {
        if (complaintsTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a complaint first");
            return;
        }
        
        JDialog dialog = new JDialog(this, "Add Feedback", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea commentArea = new JTextArea();
        JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
        
        panel.add(new JLabel("Comment:"));
        panel.add(new JScrollPane(commentArea));
        panel.add(new JLabel("Rating (1-5):"));
        panel.add(ratingSpinner);
        
        JButton submitButton = new JButton("Submit Feedback");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int complaintId = (int) tableModel.getValueAt(complaintsTable.getSelectedRow(), 0);
                String comment = commentArea.getText();
                int rating = (int) ratingSpinner.getValue();
                
                if (!comment.isEmpty()) {
                    if (FeedbackDAO.submitFeedback(complaintId, currentAdmin.getId(), comment, rating)) {
                        JOptionPane.showMessageDialog(dialog, "Feedback submitted successfully!");
                        loadAllComplaints();
                        dialog.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Please enter a comment");
                }
            }
        });
        panel.add(submitButton);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
}
