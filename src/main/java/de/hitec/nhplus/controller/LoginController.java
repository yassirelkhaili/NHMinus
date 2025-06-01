package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.UserDao;
import de.hitec.nhplus.model.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for the login view
 * Handles user authentication and navigation to main application
 */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label statusLabel;

    private UserDao userDao;
    private Main mainApp;

    /**
     * Initialize the controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Initialize DAO
            userDao = DaoFactory.getDaoFactory().createUserDao();

            // Clear status message
            statusLabel.setText("");

            // Set focus to username field
            usernameField.requestFocus();

            // Set default button
            loginButton.setDefaultButton(true);

        } catch (Exception e) {
            showError("Fehler beim Initialisieren der Anwendung: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Set reference to main application
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Handle login button click
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validate input
        if (username.isEmpty()) {
            showError("Bitte geben Sie einen Benutzernamen ein.");
            usernameField.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            showError("Bitte geben Sie ein Passwort ein.");
            passwordField.requestFocus();
            return;
        }

        // Show loading message
        statusLabel.setText("Anmeldung läuft...");
        statusLabel.setTextFill(javafx.scene.paint.Color.BLUE);

        try {
            // Authenticate user
            User authenticatedUser = userDao.authenticate(username, password);

            if (authenticatedUser != null) {
                // Check if user is active
                if (!authenticatedUser.isActive()) {
                    showError("Ihr Benutzerkonto ist deaktiviert. Bitte wenden Sie sich an den Administrator.");
                    return;
                }

                // Success - store current user and show main window
                CurrentUserSession.setCurrentUser(authenticatedUser);

                statusLabel.setText("Anmeldung erfolgreich!");
                statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);

                // Show success message
                showSuccessAlert("Willkommen " + authenticatedUser.getFirstName() + " " +
                        authenticatedUser.getSurname() + "!");

                // Close login window and show main window
                if (mainApp != null) {
                    mainApp.showMainWindow();
                    closeLoginWindow();
                }

            } else {
                showError("Ungültige Anmeldedaten. Bitte versuchen Sie es erneut.");
                passwordField.clear();
                usernameField.requestFocus();
            }

        } catch (SQLException e) {
            showError("Datenbankfehler: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showError("Unerwarteter Fehler: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle cancel button click
     */
    @FXML
    private void handleCancel() {
        System.exit(0);
    }

    /**
     * Show error message
     */
    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setTextFill(javafx.scene.paint.Color.RED);
    }

    /**
     * Show success alert
     */
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Anmeldung erfolgreich");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Close the login window
     */
    private void closeLoginWindow() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Utility class to store current user session
     */
    public static class CurrentUserSession {
        private static User currentUser;

        public static void setCurrentUser(User user) {
            currentUser = user;
        }

        public static User getCurrentUser() {
            return currentUser;
        }

        public static boolean isLoggedIn() {
            return currentUser != null;
        }

        public static void logout() {
            currentUser = null;
        }

        public static boolean hasRole(String role) {
            return currentUser != null && role.equals(currentUser.getRole());
        }

        public static boolean isAdmin() {
            return hasRole("ADMIN");
        }

        public static boolean isMedicalStaff() {
            return currentUser != null &&
                    (hasRole("DOCTOR") || hasRole("NURSE"));
        }
    }
}