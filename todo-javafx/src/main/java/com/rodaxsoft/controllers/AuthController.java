package com.rodaxsoft.controllers;

import com.rodaxsoft.Main;
import com.rodaxsoft.models.ApplicationUser;
import com.rodaxsoft.models.Credentials;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthController {

    // --- Éléments du formulaire de Connexion (Login) ---
    @FXML private TextField emailLogin;
    @FXML private PasswordField passwordLogin; // Masque les caractères

    // --- Éléments du formulaire d'Inscription (Signup) ---
    @FXML private TextField nameSignup;
    @FXML private TextField emailSignup;
    @FXML private PasswordField passwordSignup;

    @FXML
    public void handleLogin() {
        Credentials creds = new Credentials();
        creds.setEmail(emailLogin.getText());
        creds.setPassword(passwordLogin.getText());

        try {
            // Appel au service
            Main.service.login(creds);

            // Si la ligne précédente ne lève pas d'erreur, on est connecté !
            System.out.println("Login successful!");

            // On redirige vers la vue principale
            Main.showView("/com/rodaxsoft/views/TaskView.fxml", "Task Manager");

        } catch (Exception e) {
            e.printStackTrace();
            showError("Login Failed", "Vérifiez vos identifiants ou l'état du serveur.\n" + e.getMessage());
        }
    }

    @FXML
    public void handleSignup() {
        ApplicationUser newUser = new ApplicationUser();
        newUser.setName(nameSignup.getText());
        newUser.setEmail(emailSignup.getText());
        newUser.setPassword(passwordSignup.getText());

        try {
            // Appel au service (attention à la majuscule de Signup définie dans votre service)
            Main.service.Signup(newUser);

            System.out.println("Signup successful!");

            // On redirige vers la vue principale
            Main.showView("/com/rodaxsoft/views/TaskView.fxml", "Task Manager");

        } catch (Exception e) {
            e.printStackTrace();
            showError("Signup Failed", "Impossible de créer le compte.\n" + e.getMessage());
        }
    }

    // Méthode utilitaire pour afficher les erreurs pop-up
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}