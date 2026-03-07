package com.rodaxsoft.controllers;

import com.rodaxsoft.Main;
import com.rodaxsoft.models.Task;
import com.rodaxsoft.services.communicationHTTP;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TaskController {

    @FXML private TableView<Task> taskTable;
    @FXML private TableColumn<Task, String> colId;
    @FXML private TableColumn<Task, String> colTitle;
    @FXML private TableColumn<Task, String> colDescription;
    @FXML private TableColumn<Task, String> colStatus;
    @FXML private TableColumn<Task, String> colDue;
    @FXML private Label statusLabel;

    // On utilise ton service déjà prêt !
    private final communicationHTTP service = Main.service;

    @FXML
    public void initialize() {
        // Liaison des colonnes avec les attributs de la classe Task
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDue.setCellValueFactory(new PropertyValueFactory<>("due"));

        // Charger les données au démarrage
        handleRefresh();
    }

    @FXML
    private void handleRefresh() {
        try {
            statusLabel.setText("Chargement...");
            List<Task> tasks = service.GetAll();

            taskTable.setItems(FXCollections.observableArrayList(tasks));

            statusLabel.setText("Données actualisées avec succès (" + tasks.size() + ")");
            statusLabel.setStyle("-fx-text-fill: green;");

        } catch (Exception e) {
            String errorMsg = (e.getMessage() != null) ? e.getMessage() : "Serveur injoignable (API éteinte)";

            statusLabel.setText("Erreur : " + errorMsg);
            statusLabel.setStyle("-fx-text-fill: red;");

            System.err.println("Détail de l'erreur : " + e.toString());
        }
    }
}