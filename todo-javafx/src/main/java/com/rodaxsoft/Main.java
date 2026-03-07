package com.rodaxsoft;

import com.rodaxsoft.models.ApplicationUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.rodaxsoft.services.communicationHTTP;
import com.rodaxsoft.models.Credentials;

public class Main extends Application {

    // On rend le service accessible pour que le contrôleur puisse l'utiliser
    public static communicationHTTP service = new communicationHTTP();

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            Credentials moi = new Credentials();
            moi.setEmail("corentin@gmail.com");
            moi.setPassword("CocoAlgebra");
            service.login(moi);
            System.out.println("Authentification automatique réussie !");
        } catch (Exception e) {
            System.err.println("Échec de l'auth au démarrage : " + e.getMessage());
        }

        // --- CHARGEMENT DE L'INTERFACE ---
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rodaxsoft/views/TaskView.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Todo App - Interoperability");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}