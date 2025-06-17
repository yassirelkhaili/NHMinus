package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.model.Caregiver;
import de.hitec.nhplus.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.sql.SQLException;

public class PatientAssignmentController {

    @FXML private ListView<Patient> listViewAssigned;
    @FXML private ListView<Patient> listViewAvailable;
    @FXML private Button btnClose;
    
    private Stage dialogStage;
    private Caregiver caregiver;
    private ObservableList<Patient> assignedPatients = FXCollections.observableArrayList();
    private ObservableList<Patient> availablePatients = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
        listViewAssigned.setItems(assignedPatients);
        listViewAvailable.setItems(availablePatients);
        
        listViewAssigned.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewAvailable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
        loadPatients();
    }
    
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    private void loadPatients() {
        try {
            assignedPatients.clear();
            availablePatients.clear();
            
            var caregiverDao = DaoFactory.getDaoFactory().createCaregiverDao();
            var patientDao = DaoFactory.getDaoFactory().createPatientDao();
            
            var allPatients = patientDao.readAll();
            var assigned = caregiverDao.getPatientsForCaregiver(caregiver.getCid());
            
            assignedPatients.addAll(assigned);
            
            for (Patient patient : allPatients) {
                if (!assigned.contains(patient)) {
                    availablePatients.add(patient);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleAssignPatients() {
        try {
            var caregiverDao = DaoFactory.getDaoFactory().createCaregiverDao();
            for (Patient patient : listViewAvailable.getSelectionModel().getSelectedItems()) {
                caregiverDao.assignPatient(caregiver.getCid(), patient.getPid());
            }
            loadPatients();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void handleRemovePatients() {
        try {
            var caregiverDao = DaoFactory.getDaoFactory().createCaregiverDao();
            for (Patient patient : listViewAssigned.getSelectionModel().getSelectedItems()) {
                caregiverDao.removePatientAssignment(caregiver.getCid(), patient.getPid());
            }
            loadPatients();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleClose() {
        // Get the stage from the button
        Stage stage = (Stage)  btnClose.getScene().getWindow();
        // Close the window
        stage.close();
    }
}
