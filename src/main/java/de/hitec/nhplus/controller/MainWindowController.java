package de.hitec.nhplus.controller;

import de.hitec.nhplus.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import de.hitec.nhplus.Main;
import java.io.IOException;

public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private void handleShowAllPatient(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllPatientView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void handleShowAllTreatments(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException exception) {
            exception.printStackTrace();
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
}