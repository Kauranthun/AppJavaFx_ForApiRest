package com.rodaxsoft.controllers;

import com.rodaxsoft.Main;
import com.rodaxsoft.models.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    public void openGetAllScreen() throws IOException {
        // Retour à la vue principale (le tableau)
        Main.showView("/com/rodaxsoft/views/TaskView.fxml", "Task Manager - List");
    }

    @FXML
    public void openPostScreen() throws IOException {
        // Vers votre future page de création
        Main.showView("/com/rodaxsoft/views/PostTaskView.fxml", "Create New Task");
    }

    @FXML
    public void DeleteTask() {
        // 1. On récupère le contrôleur de la vue principale
        TaskController taskController = TaskController.getInstance();

        if (taskController == null) {
            return; // Sécurité si on n'est pas sur la bonne page
        }

        // 2. On lui demande quelle tâche est sélectionnée
        Task selectedTask = taskController.getSelectedTask();

        if (selectedTask == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une tâche dans le tableau avant de cliquer sur Delete.");
            alert.showAndWait();
            return;
        }

        // 3. Confirmation
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmation de suppression");
        confirm.setHeaderText("Supprimer la tâche : " + selectedTask.getTitle() + " ?");

        if (confirm.showAndWait().orElse(null) == javafx.scene.control.ButtonType.OK) {
            try {
                // 4. Appel HTTP pour supprimer
                Main.service.delete(selectedTask.getId());

                // 5. Demander au TaskController de rafraîchir le tableau
                taskController.handleRefresh();

            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Impossible de supprimer la tâche.");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void UpdateTask() throws IOException {

        TaskController taskController = TaskController.getInstance();

        if (taskController == null) {
            return;
        }

        // On lui demande quelle tâche est sélectionnée
        Task selectedTask = taskController.getSelectedTask();

        if (selectedTask == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une tâche dans le tableau avant de cliquer sur Update.");
            alert.showAndWait();
            return;
        }

        // --- NOUVEAU CODE POUR CHARGER LA PAGE D'UPDATE ---

        // 1. On charge le FXML de la future page d'update
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/rodaxsoft/views/UpdateTask.fxml"));
        Parent root = loader.load();

        // 2. On récupère le contrôleur de cette nouvelle page
        UpdateTaskController updateController = loader.getController();

        // 3. On lui passe la tâche sélectionnée pour qu'il pré-remplisse les champs
        updateController.initData(selectedTask);

        // 4. On affiche cette vue dans une nouvelle fenêtre (Pop-up)
        Stage stage = new Stage();
        stage.setTitle("Update Task : " + selectedTask.getTitle());
        stage.setScene(new Scene(root, 400, 450)); // Taille adaptée à un formulaire
        stage.show();
    }

    @FXML
    public void handleLogout(){
        try{
            Main.service.logout();
            System.out.println("Logout successful!");
            Main.showView("/com/rodaxsoft/views/Auth.fxml", "Authentication");

        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Logout error");
            alert.setHeaderText(null);
            alert.setContentText("Error during logout\n"+e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void RefreshToken(){
        try{
            Main.service.refreshTokens();
            System.out.println("Logout successful!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}