package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private void initialize() {
        // Check user role and hide caregiver management if not admin
        if (!LoginController.CurrentUserSession.isAdmin()) {
            // Hide caregiver management options
            hideCaregiversManagement();
        }
    }

    /**
     * Handle user logout - return to login screen
     */
    @FXML
    private void handleLogout() {
        // Confirm logout with user
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Abmelden");
        confirmAlert.setHeaderText("Möchten Sie sich wirklich abmelden?");
        confirmAlert.setContentText("Sie werden zur Anmeldung zurückgeleitet.");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {

            // Clear current user session
            LoginController.CurrentUserSession.logout();

            // Get current stage using the mainBorderPane
            Stage currentStage = (Stage) mainBorderPane.getScene().getWindow();

            // Show login window again
            new Main().showLoginWindow();

            // Close current main window
            currentStage.close();
        }
    }

    /**
     * Show users management (add this method for user management)
     */
    @FXML
    private void handleShowAllUsers(ActionEvent event) {
        // You can create a UserView.fxml for user management later
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllUserView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            // If UserView doesn't exist yet, show a placeholder or message
            System.out.println("User management view not implemented yet");
            exception.printStackTrace();
        }
    }

    /**
     * Hides caregiver management options for non-admin users
     */
    private void hideCaregiversManagement() {
        // Find and hide the caregiver button in the sidebar
        for (javafx.scene.Node node : mainBorderPane.getLeft().lookupAll("Button")) {
            if (node instanceof javafx.scene.control.Button) {
                javafx.scene.control.Button button = (javafx.scene.control.Button) node;
                if ("Pflegekräfte".equals(button.getText())) {
                    button.setVisible(false);
                    button.setManaged(false);
                }
            }
        }
        
        // Hide the menu item
        javafx.scene.control.MenuBar menuBar = (javafx.scene.control.MenuBar) mainBorderPane.getTop();
        for (javafx.scene.control.Menu menu : menuBar.getMenus()) {
            if ("Ansicht".equals(menu.getText())) {
                for (javafx.scene.control.MenuItem item : menu.getItems()) {
                    if ("Pflegekräfte".equals(item.getText())) {
                        item.setVisible(false);
                    }
                }
            }
        }
    }

    /**
     * Handle showing all caregivers - only accessible to admins
     */
    @FXML
    private void handleShowAllCaregivers(ActionEvent event) {
        // Check if user has admin privileges
        if (LoginController.CurrentUserSession.isAdmin()) {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllCaregiverView.fxml"));
            try {
                mainBorderPane.setCenter(loader.load());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            // Show access denied message
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Zugriff verweigert");
            alert.setHeaderText("Keine Berechtigung");
            alert.setContentText("Sie haben keine Berechtigung, auf die Pflegekräfteverwaltung zuzugreifen.");
            alert.showAndWait();
        }
    }

    /**
     * Handle showing all patients
     */
    @FXML
    private void handleShowAllPatient(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllPatientView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Handle showing all treatments
     */
    @FXML
    private void handleShowAllTreatments(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
