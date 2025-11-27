package com.resolveit.ui;

import com.resolveit.dao.UserDAO;
import com.resolveit.model.User;
import com.resolveit.util.ValidationUtil;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login Frame - GUI for user authentication
 * Provides login and registration functionality
 */
public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    
    public LoginFrame() {
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("ResolveIt - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("ResolveIt - Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel);
        
        JPanel emailPanel = new JPanel(new BorderLayout(5, 0));
        emailPanel.add(new JLabel("Email:"), BorderLayout.WEST);
        emailField = new JTextField();
        emailPanel.add(emailField, BorderLayout.CENTER);
        panel.add(emailPanel);
        
        JPanel passwordPanel = new JPanel(new BorderLayout(5, 0));
        passwordPanel.add(new JLabel("Password:"), BorderLayout.WEST);
        passwordField = new JPasswordField();
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        panel.add(passwordPanel);
        
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setForeground(Color.RED);
        panel.add(statusLabel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        buttonPanel.add(loginButton);
        
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterDialog();
            }
        });
        buttonPanel.add(registerButton);
        panel.add(buttonPanel);
        
        add(panel);
    }
    
    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        
        if (!ValidationUtil.isValidEmail(email)) {
            statusLabel.setText("Invalid email format");
            return;
        }
        
        User user = UserDAO.authenticateUser(email, password);
        
        if (user != null) {
            statusLabel.setForeground(Color.GREEN);
            statusLabel.setText("Login successful!");
            
            if ("ADMIN".equals(user.getRole())) {
                AdminDashboard adminDashboard = new AdminDashboard(user);
                adminDashboard.setVisible(true);
            } else {
                UserDashboard userDashboard = new UserDashboard(user);
                userDashboard.setVisible(true);
            }
            
            this.dispose();
        } else {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Invalid credentials");
            passwordField.setText("");
        }
    }
    
    private void showRegisterDialog() {
        JDialog registerDialog = new JDialog(this, "Register", true);
        registerDialog.setSize(400, 300);
        registerDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextField usernameField = new JTextField();
        JTextField regEmailField = new JTextField();
        JPasswordField regPasswordField = new JPasswordField();
        
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Email:"));
        panel.add(regEmailField);
        panel.add(new JLabel("Password:"));
        panel.add(regPasswordField);
        
        JButton registerConfirmButton = new JButton("Register");
        registerConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String email = regEmailField.getText();
                String password = new String(regPasswordField.getPassword());
                
                if (ValidationUtil.isValidUsername(username) && ValidationUtil.isValidEmail(email) && ValidationUtil.isValidPassword(password)) {
                    if (UserDAO.registerUser(username, email, password)) {
                        JOptionPane.showMessageDialog(registerDialog, "Registration successful!");
                        registerDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(registerDialog, "Registration failed");
                    }
                } else {
                    JOptionPane.showMessageDialog(registerDialog, "Invalid input");
                }
            }
        });
        panel.add(registerConfirmButton);
        
        registerDialog.add(panel);
        registerDialog.setVisible(true);
    }
}
