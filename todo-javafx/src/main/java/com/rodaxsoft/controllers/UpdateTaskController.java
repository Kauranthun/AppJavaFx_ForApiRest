package com.rodaxsoft.controllers;

import com.rodaxsoft.Main;
import com.rodaxsoft.models.Task;
import com.rodaxsoft.models.TaskStatus;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;

public class UpdateTaskController {

    @FXML private TextField Name;
    @FXML private TextField Description;
    @FXML private DatePicker Date;
    @FXML private ComboBox<TaskStatus> statusComboBox; // NOUVEAU : La liste déroulante pour le statut
    @FXML private Button updateButton;

    private String currentTaskId;

    // Cette méthode s'exécute automatiquement au chargement de la vue
    @FXML
    public void initialize() {
        // On remplit la liste déroulante avec toutes les valeurs possibles de ton enum TaskStatus
        statusComboBox.setItems(FXCollections.observableArrayList(TaskStatus.values()));
    }

    public void initData(Task task) {
        this.currentTaskId = task.getId();

        Name.setText(task.getTitle());
        Description.setText(task.getDescription());

        // Pré-sélectionner le bon statut dans la liste déroulante
        if (task.getStatus() != null) {
            statusComboBox.setValue(task.getStatus());
        }

        // Pré-sélectionner la bonne date
        if (task.getDue() != null) {
            LocalDate localDate = task.getDue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Date.setValue(localDate);
        }
    }

    @FXML
    public void sendUpdateRequest() {
        // 1. On prépare la tâche avec les NOUVELLES valeurs tapées par l'utilisateur
        Task updatedTask = new Task();
        updatedTask.setTitle(Name.getText());
        updatedTask.setDescription(Description.getText());

        // Récupération du statut choisi dans la liste déroulante
        updatedTask.setStatus(statusComboBox.getValue());

        LocalDate localDate = Date.getValue();
        if (localDate != null) {
            java.util.Date dateObject = java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            updatedTask.setDue(dateObject);
        }

        try {
            // 2. Appel de l'API
            Main.service.update(currentTaskId, updatedTask);

            // 3. Rafraîchir le tableau principal
            TaskController.getInstance().handleRefresh();

            // 4. Fermer la fenêtre Pop-up
            Stage stage = (Stage) updateButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("La mise à jour a échoué !");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}