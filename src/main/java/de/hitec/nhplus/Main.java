package de.hitec.nhplus;

import de.hitec.nhplus.controller.MainWindowController;
import de.hitec.nhplus.datastorage.ConnectionBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        new MainWindowController().showLoginWindow();
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