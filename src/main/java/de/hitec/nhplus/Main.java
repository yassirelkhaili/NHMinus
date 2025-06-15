package de.hitec.nhplus;

import de.hitec.nhplus.controller.LoginController;
import de.hitec.nhplus.datastorage.ConnectionBuilder;
import de.hitec.nhplus.utils.SetUpDB;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // Initialize database
        SetUpDB.setUpDb();
        
        // Start with login window
        this.showLoginWindow();
    }

    public void showLoginWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/AuthView.fxml"));
            AnchorPane loginPane = loader.load();
            
            LoginController loginController = loader.getController();
            loginController.setMainApp(this);
            
            // Create a new stage for login to keep primaryStage available for main window
            Stage loginStage = new Stage();
            Scene loginScene = new Scene(loginPane);
            loginStage.setTitle("NHPlus - Anmeldung");
            loginStage.setScene(loginScene);
            loginStage.setResizable(false);
            loginStage.centerOnScreen();
            
            loginStage.setOnCloseRequest(event -> {
                ConnectionBuilder.closeConnection();
                Platform.exit();
            });
            
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainWindow() {
        try {
            // Make sure primaryStage is not null
            if (this.primaryStage == null) {
                this.primaryStage = new Stage();
            }
            
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/de/hitec/nhplus/MainWindowView.fxml"));
            this.mainLayout = loader.load();
            
            Scene scene = new Scene(this.mainLayout);
            this.primaryStage.setTitle("NHPlus - Nursing Home Management");
            this.primaryStage.setScene(scene);
            this.primaryStage.setResizable(false);
            this.primaryStage.centerOnScreen();
            
            this.primaryStage.setOnCloseRequest(event -> {
                ConnectionBuilder.closeConnection();
                Platform.exit();
            });
            
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mainWindow() {
        showMainWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}