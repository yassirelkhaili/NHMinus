package de.hitec.nhplus;

import de.hitec.nhplus.controller.LoginController;
import de.hitec.nhplus.datastorage.ConnectionBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main JavaFX Application class
 * Handles application startup with login flow
 */
public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Start with login window
        this.showLoginWindow();
    }

        /**
     * Show the login window first
     */
    public void showLoginWindow() {
        try {
            // Load the login FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/de/hitec/nhplus/AuthView.fxml"));
            AnchorPane loginPane = loader.load();

            // Get the controller and set reference to main app
            LoginController loginController = loader.getController();
            loginController.setMainApp(this);  // Now this will work!

            // Create login scene
            Scene loginScene = new Scene(loginPane);
            
            // Configure login stage
            Stage loginStage = new Stage();
            loginStage.setTitle("NHPlus - Anmeldung");
            loginStage.setScene(loginScene);
            loginStage.setResizable(false);
            loginStage.centerOnScreen();
            
            // Handle close request
            loginStage.setOnCloseRequest(event -> {
                ConnectionBuilder.closeConnection();
                Platform.exit();
                System.exit(0);
            });
            
            loginStage.show();

        } catch (IOException exception) {
            System.err.println("Error loading login window: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * Show the main application window after successful login
     */
    public void showMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/MainWindowView.fxml"));
            BorderPane pane = loader.load();

            Scene scene = new Scene(pane);
            this.primaryStage.setTitle("NHPlus - Nursing Home Management");
            this.primaryStage.setScene(scene);
            this.primaryStage.setResizable(false);
            this.primaryStage.centerOnScreen();
            this.primaryStage.show();

            // Handle close request
            this.primaryStage.setOnCloseRequest(event -> {
                ConnectionBuilder.closeConnection();
                Platform.exit();
                System.exit(0);
            });

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Original main window method (kept for backward compatibility)
     */
    public void mainWindow() {
        showMainWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}