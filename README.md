# ResolveIt - Complaint & Feedback Management System

## Project Overview

ResolveIt is a **Java GUI-based (Swing/JavaFX) desktop application** for efficiently managing customer complaints and feedback. It provides a centralized platform for users to submit complaints, track their status, and enables administrators to manage and resolve issues effectively.

**Technology Stack:**
- Language: Java SE
- GUI Framework: Swing (Java AWT/Swing)
- Database: MySQL
- Database Connectivity: JDBC
- IDE: IntelliJ IDEA / Eclipse / NetBeans

## Features

### User Features:
- **User Registration & Login**: Secure authentication system
- **Submit Complaints**: Submit new complaints with category, priority, and description
- **Track Complaints**: View submitted complaints and their current status (OPEN, IN_PROGRESS, RESOLVED, CLOSED)
- **Provide Feedback**: Add ratings and comments after complaint resolution
- **View Complaint Details**: See complete information including resolution notes

### Admin Features:
- **Admin Login**: Separate admin authentication
- **Dashboard**: View all complaints with filtering by status
- **Complaint Management**: Assign complaints, update status, and add resolution remarks
- **Analytics**: View complaint statistics, count by status, and average resolution time
- **Search & Filter**: Find complaints by ID, date range, or priority

## Database Design

### Tables:
1. **users**
   - user_id (PK)
   - name, email, password_hash, role (USER/ADMIN)
   - created_at

2. **complaints**
   - complaint_id (PK)
   - user_id (FK)
   - category, title, description, priority
   - status (OPEN, IN_PROGRESS, RESOLVED, CLOSED)
   - assigned_to (admin user_id)
   - created_at, updated_at, resolution_note

3. **feedback**
   - feedback_id (PK)
   - complaint_id (FK)
   - rating (1-5), comments
   - created_at

## Project Structure

```
ResolveitProjectJava/
├── src/
│   ├── com/resolveit/ui/           # GUI Frames and Panels
│   │   ├── LoginFrame.java
│   │   ├── UserDashboard.java
│   │   ├── AdminDashboard.java
│   │   └── ...
│   ├── com/resolveit/model/        # Model Classes
│   │   ├── User.java
│   │   ├── Complaint.java
│   │   ├── Feedback.java
│   │   └── ...
│   ├── com/resolveit/dao/          # Data Access Objects (JDBC)
│   │   ├── UserDAO.java
│   │   ├── ComplaintDAO.java
│   │   ├── FeedbackDAO.java
│   │   └── DatabaseConnection.java
│   ├── com/resolveit/util/         # Utility Classes
│   │   └── ValidationUtil.java
│   └── com/resolveit/Main.java     # Application Entry Point
├── database/
│   └── resolveit_schema.sql        # Database schema and sample data
├── lib/                            # External libraries (MySQL JDBC)
├── .gitignore
├── README.md
└── LICENSE
```

## Setup Instructions

### Prerequisites:
1. Java JDK 8 or higher installed
2. MySQL Server installed and running
3. MySQL JDBC Driver (mysql-connector-java-*.jar)

### Steps:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/udanuj07/ResolveitProjectJava.git
   cd ResolveitProjectJava
   ```

2. **Set up the database:**
   - Open MySQL command line or MySQL Workbench
   - Run the SQL script to create database and tables:
   ```sql
   source database/resolveit_schema.sql;
   ```
   - Or manually create the database and import the schema

3. **Update database connection details:**
   - Open `src/com/resolveit/dao/DatabaseConnection.java`
   - Update connection parameters (URL, username, password)

4. **Add MySQL JDBC Driver:**
   - Place `mysql-connector-java-*.jar` in the `lib/` folder
   - Add it to your project's classpath in your IDE

5. **Compile and Run:**
   - Compile all Java files
   - Run `com.resolveit.Main` class to start the application

## Usage

### For Users:
1. Launch the application → Click "Register" to create a new account
2. Login with your credentials
3. Click "Submit Complaint" to file a new complaint
4. View your complaints in "My Complaints" section
5. Track complaint status in real-time
6. Provide feedback once complaint is resolved

### For Admins:
1. Login with admin credentials
2. View all complaints on the dashboard
3. Filter complaints by status (OPEN, IN_PROGRESS, RESOLVED, CLOSED)
4. Click on a complaint to view details and assign to a handler
5. Update complaint status and add resolution remarks
6. View analytics and statistics

## Key Classes

### Model Classes:
- `User.java` - Represents a user (with role: USER or ADMIN)
- `Complaint.java` - Represents a complaint entity
- `Feedback.java` - Represents user feedback for resolved complaints

### DAO Classes (JDBC):
- `UserDAO.java` - Handles user login/registration
- `ComplaintDAO.java` - CRUD operations for complaints
- `FeedbackDAO.java` - CRUD operations for feedback
- `DatabaseConnection.java` - Manages database connections

### UI Frames:
- `LoginFrame.java` - Login/Register interface
- `UserDashboard.java` - User dashboard with complaint submission
- `AdminDashboard.java` - Admin dashboard with complaint management

## Code Quality Features:
- Exception handling with try-catch blocks
- Prepared statements for SQL injection prevention
- MVC-like architecture (Model, DAO, View)
- Separation of concerns (UI, Business Logic, Data Access)
- Input validation and error messages in dialog boxes

## Future Enhancements:
- Email notification integration
- Complaint export to PDF/CSV
- Advanced search with multiple filters
- Dashboard analytics with charts
- Bulk operations for admins
- Attachment support for complaints
- Multi-language support

## Contributors:
- Udan Ujjawal (udanuj07)

## License:
This project is open source and available under the MIT License.

## Contact:
For issues or suggestions, please open an issue on GitHub.
