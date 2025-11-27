package com.resolveit.dao;

import com.resolveit.model.Feedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Feedback entity
 * Handles CRUD operations for feedback in the database
 */
public class FeedbackDAO {
    
    /**
     * Submit feedback for a complaint
     */
    public static boolean submitFeedback(int complaintId, int userId, String comment, int rating) {
        String query = "INSERT INTO feedback (complaint_id, user_id, comment, rating, created_at) VALUES (?, ?, ?, ?, NOW())";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, complaintId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, comment);
            pstmt.setInt(4, rating);
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error submitting feedback: " + e.getMessage());
        }
        return false;
    }
    
    /**
     * Get all feedback for a specific complaint
     */
    public static List<Feedback> getComplaintFeedback(int complaintId) {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM feedback WHERE complaint_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, complaintId);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("id"));
                feedback.setComplaintId(rs.getInt("complaint_id"));
                feedback.setUserId(rs.getInt("user_id"));
                feedback.setComment(rs.getString("comment"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setCreatedAt(rs.getTimestamp("created_at"));
                feedbackList.add(feedback);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching feedback: " + e.getMessage());
        }
        return feedbackList;
    }
    
    /**
     * Get average rating for a complaint
     */
    public static double getAverageRating(int complaintId) {
        String query = "SELECT AVG(rating) as avg_rating FROM feedback WHERE complaint_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, complaintId);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                double avgRating = rs.getDouble("avg_rating");
                return Double.isNaN(avgRating) ? 0.0 : avgRating;
            }
        } catch (SQLException e) {
            System.err.println("Error calculating average rating: " + e.getMessage());
        }
        return 0.0;
    }
    
    /**
     * Delete feedback
     */
    public static boolean deleteFeedback(int feedbackId) {
        String query = "DELETE FROM feedback WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, feedbackId);
            
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting feedback: " + e.getMessage());
        }
        return false;
    }
}
