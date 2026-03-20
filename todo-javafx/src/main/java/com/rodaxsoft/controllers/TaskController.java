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

    private final communicationHTTP service = Main.service;
    private static TaskController instance;

    public TaskController() {
        instance = this;
    }

    public static TaskController getInstance() {
        return instance;
    }

    public Task getSelectedTask() {
        if (taskTable != null) {
            return taskTable.getSelectionModel().getSelectedItem();
        }
        return null;
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDue.setCellValueFactory(new PropertyValueFactory<>("due"));
        handleRefresh();
    }

    @FXML
    public void handleRefresh() {
        try {
            statusLabel.setText("Loading");
            List<Task> tasks = service.GetAll();

            taskTable.setItems(FXCollections.observableArrayList(tasks));

            statusLabel.setText("Data refresh with success (" + tasks.size() + ")");
            statusLabel.setStyle("-fx-text-fill: green;");

        } catch (Exception e) {
            String errorMsg;
            if(e.getMessage()!=null){
                errorMsg=e.getMessage();
            }else{
                errorMsg="Server cant be reach (API closed)";
            }

            statusLabel.setText("Error : " + errorMsg);
            statusLabel.setStyle("-fx-text-fill: red;");

            System.err.println("Error detail : " + e.toString());
        }
    }
}