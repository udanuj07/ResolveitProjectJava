package com.resolveit.dao;

import com.resolveit.model.Complaint;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Complaint entity
 * Handles CRUD operations for complaints in the database
 */
public class ComplaintDAO {
    
    /**
     * Submit a new complaint
     */
    public static boolean submitComplaint(int userId, String title, String description, String category) {
        String query = "INSERT INTO complaints (user_id, title, description, category, status, created_at) VALUES (?, ?, ?, ?, ?, NOW())";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, category);
            pstmt.setString(5, "PENDING");
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error submitting complaint: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get all complaints by user ID
     */
    public static List<Complaint> getUserComplaints(int userId) {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT * FROM complaints WHERE user_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, userId);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Complaint complaint = new Complaint();
                complaint.setId(rs.getInt("id"));
                complaint.setUserId(rs.getInt("user_id"));
                complaint.setTitle(rs.getString("title"));
                complaint.setDescription(rs.getString("description"));
                complaint.setCategory(rs.getString("category"));
                complaint.setStatus(rs.getString("status"));
                complaint.setCreatedAt(rs.getTimestamp("created_at"));
                complaints.add(complaint);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching user complaints: " + e.getMessage());
        }
        return complaints;
    }
    
    /**
     * Get all complaints (for admin)
     */
    public static List<Complaint> getAllComplaints() {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT * FROM complaints ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                Complaint complaint = new Complaint();
                complaint.setId(rs.getInt("id"));
                complaint.setUserId(rs.getInt("user_id"));
                complaint.setTitle(rs.getString("title"));
                complaint.setDescription(rs.getString("description"));
                complaint.setCategory(rs.getString("category"));
                complaint.setStatus(rs.getString("status"));
                complaint.setCreatedAt(rs.getTimestamp("created_at"));
                complaints.add(complaint);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all complaints: " + e.getMessage());
        }
        return complaints;
    }
    
    /**
     * Update complaint status
     */
    public static boolean updateComplaintStatus(int complaintId, String status) {
        String query = "UPDATE complaints SET status = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, status);
            pstmt.setInt(2, complaintId);
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating complaint status: " + e.getMessage());
        }
        return false;
    }
}
