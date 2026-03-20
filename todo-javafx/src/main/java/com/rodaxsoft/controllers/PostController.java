package com.rodaxsoft.controllers;

import com.rodaxsoft.Main;
import com.rodaxsoft.models.Task;
import com.rodaxsoft.models.TaskStatus; // N'oubliez pas d'importer votre enum !
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.ZoneId;

public class PostController {

    @FXML
    private TextField Name;

    @FXML
    private TextField Description;

    @FXML
    private DatePicker Date;

    @FXML
    public void sendPostRequest() {

        Task newTask = new Task();
        newTask.setTitle(Name.getText());
        newTask.setDescription(Description.getText());

        LocalDate localDate = Date.getValue();

        if (localDate != null) {
            java.util.Date dateObject = java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            newTask.setDue(dateObject);
        }

        try {
            // Envoi de la requête POST
            Main.service.create(newTask);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("Task created!");
            alert.setContentText("The task '" + newTask.getTitle() + "' was successfully saved!");
            alert.showAndWait();

            // Nettoyage de l'interface
            Name.clear();
            Description.clear();
            Date.setValue(null);

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("The task could not be posted!");
            alert.setContentText("Error details: " + e.getMessage());
            alert.showAndWait();
        }
    }
}