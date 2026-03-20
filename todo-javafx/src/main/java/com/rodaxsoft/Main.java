package com.rodaxsoft;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.rodaxsoft.services.communicationHTTP;
import java.io.IOException;

public class Main extends Application {
    public static communicationHTTP service = new communicationHTTP();
    private static Stage primaryStage; // Garder la référence

    @Override
    public void start(Stage stage) throws Exception {

        primaryStage = stage;
        showView("/com/rodaxsoft/views/Auth.fxml", "Task Manager");
    }

    // Méthode statique pour changer de vue n'importe où
    public static void showView(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
        Parent root = loader.load();
        primaryStage.setTitle(title);
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }

    // Dans votre fichier Main.java
    public static void main(String[] args) {
        launch(args);
    }
}