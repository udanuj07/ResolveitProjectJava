package com.resolveit.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Feedback Model Class
 * Represents feedback given by users for resolved complaints
 */
public class Feedback implements Serializable {
    private int feedbackId;
    private int complaintId;
    private int rating;  // 1-5 scale
    private String comments;
    private LocalDateTime createdAt;

    // Constructor
    public Feedback() {
    }

    public Feedback(int complaintId, int rating, String comments) {
        this.complaintId = complaintId;
        this.rating = rating;
        this.comments = comments;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // Helper Methods
    public String getRatingStars() {
        return "★".repeat(rating) + "☆".repeat(5 - rating);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId=" + feedbackId +
                ", complaintId=" + complaintId +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
