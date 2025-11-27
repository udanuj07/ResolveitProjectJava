package com.resolveit;

import com.resolveit.ui.LoginFrame;
import javax.swing.SwingUtilities;

/**
 * Main entry point for the ResolveIt application
 * Initializes the application and displays the login screen
 */
public class Main {
    
    public static void main(String[] args) {
        // Start the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and display the login frame
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}
