# ResolveIt - Code Templates for Remaining Files

This document contains complete code templates for all remaining files that need to be created.

## Project Structure Summary

```
src/com/resolveit/
├── model/
│   ├── User.java ✓
│   ├── Complaint.java ✓
│   └── Feedback.java ✓
├── dao/
│   ├── DatabaseConnection.java ✓
│   ├── UserDAO.java (TODO)
│   ├── ComplaintDAO.java (TODO)
│   └── FeedbackDAO.java (TODO)
├── ui/
│   ├── LoginFrame.java (TODO)
│   ├── UserDashboard.java (TODO)
│   └── AdminDashboard.java (TODO)
├── util/
│   └── ValidationUtil.java (TODO)
└── Main.java (TODO)
```

## Quick Template Reference

### UserDAO.java
Key methods: registerUser(), loginUser(), getUserById(), getAllUsers(), updateUser(), deleteUser()
Uses: JDBC, DatabaseConnection, User model

### ComplaintDAO.java
Key methods: createComplaint(), getAllComplaints(), getComplaintsByStatus(), updateStatus(), assignComplaint()
Uses: JDBC, DatabaseConnection, Complaint model

### FeedbackDAO.java
Key methods: addFeedback(), getFeedbackByComplaintId(), getAverageRating()
Uses: JDBC, DatabaseConnection, Feedback model

### ValidationUtil.java
Key methods: isValidEmail(), isValidPassword(), isNotEmpty(), isValidPriority(), isValidStatus()
No external dependencies - pure utility functions

### LoginFrame.java
Extends: JFrame
Key components: JTextField (username), JPasswordField (password), JComboBox (role), JButton (login/register)
Action: Calls UserDAO.loginUser() and navigates to appropriate dashboard

### UserDashboard.java
Extends: JFrame
Key components: JTable (complaints list), JButton (submit, view details, logout), JComboBox (status filter)
Action: Calls ComplaintDAO methods to display and manage complaints

### AdminDashboard.java
Extends: JFrame
Key components: JTable (all complaints), JButton (assign, resolve), JTextArea (resolution notes), JComboBox (filters)
Action: Calls ComplaintDAO methods for admin operations

### Main.java
Main entry point
Action: Set Swing Look&Feel, create LoginFrame, set visible

## Implementation Priority

1. **First**: UserDAO (foundation for authentication)
2. **Second**: ValidationUtil (used by all components)
3. **Third**: ComplaintDAO, FeedbackDAO (business logic)
4. **Fourth**: LoginFrame (entry point GUI)
5. **Fifth**: UserDashboard, AdminDashboard (main GUIs)
6. **Last**: Main.java (ties everything together)

## Key Patterns Used

- **DAO Pattern**: Separate data access logic from business logic
- **Model Classes**: Lightweight POJOs for data transfer
- **JDBC**: Direct SQL execution with prepared statements
- **GUI**: Swing components with action listeners
- **Exception Handling**: Try-catch for SQL and IO operations
- **Password Hashing**: MD5 for demo (use BCrypt for production)

## Testing Checklist

- [ ] UserDAO: Test register, login, getAllUsers
- [ ] ComplaintDAO: Test create, getAll, updateStatus
- [ ] FeedbackDAO: Test addFeedback, getAverageRating
- [ ] ValidationUtil: Test all validation methods
- [ ] LoginFrame: Test login with valid/invalid credentials
- [ ] UserDashboard: Test submit complaint, view complaints
- [ ] AdminDashboard: Test view all, assign, resolve
- [ ] Main: Test application startup

## Database Schema Refresher

```sql
users: user_id, name, email, password_hash, role, created_at, updated_at
complaints: complaint_id, user_id, category, title, description, priority, status, assigned_to, resolution_note, created_at, updated_at
feedback: feedback_id, complaint_id, rating, comments, created_at
```

## JDBC Best Practices in This Project

1. Always use try-with-resources or try-finally for resource cleanup
2. Use prepared statements to prevent SQL injection
3. Handle SQLException with appropriate error messages
4. Close ResultSet, Statement, and Connection in proper order
5. Use DatabaseConnection.getConnection() centrally

## GUI Best Practices

1. Use JFrame as base for main windows
2. Use JPanel for content organization
3. Use LayoutManagers (GridLayout, BorderLayout, FlowLayout)
4. Add ActionListeners to buttons
5. Display errors using JOptionPane.showMessageDialog()
6. Use JTable with DefaultTableModel for data display
7. Use proper naming conventions for components

## Next Steps

1. Clone this repository locally
2. Set up MySQL database using database/resolveit_schema.sql
3. Add mysql-connector-java JAR to classpath
4. Create remaining files following templates above
5. Compile all Java files
6. Run Main.java to start application
7. Test all workflows
8. Deploy to production

---

**Last Updated**: 27-Nov-2025
**Status**: Foundation Complete, Ready for Implementation
