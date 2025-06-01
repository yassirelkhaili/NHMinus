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
            showLoginWindow();

            // Close current main window
            currentStage.close();
        }
    }

    /**
     * Show the login window
     */
    public void showLoginWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/de/hitec/nhplus/AuthView.fxml"));
            AnchorPane loginPane = loader.load();

            Scene loginScene = new Scene(loginPane);

            Stage loginStage = new Stage();
            loginStage.setTitle("NHPlus - Anmeldung");
            loginStage.setScene(loginScene);
            loginStage.setResizable(false);
            loginStage.centerOnScreen();

            // Handle close request for login window
            loginStage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });

            loginStage.show();

        } catch (IOException e) {
            System.err.println("Fehler beim Öffnen des Login-Fensters: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle application exit
     */
    @FXML
    private void handleExit() {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Anwendung beenden");
        confirmAlert.setHeaderText("Möchten Sie die Anwendung wirklich beenden?");

        if (confirmAlert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
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