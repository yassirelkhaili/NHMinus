package de.hitec.nhplus.controller;

import de.hitec.nhplus.datastorage.CaregiverDao;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.model.Caregiver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AllCaregiverController {

    @FXML private TableView<Caregiver> tableView;
    @FXML private TableColumn<Caregiver, Long> colID;
    @FXML private TableColumn<Caregiver, String> colSurname;
    @FXML private TableColumn<Caregiver, String> colFirstName;
    @FXML private TableColumn<Caregiver, String> colTelephone;
    @FXML private TableColumn<Caregiver, String> colQualification;
    @FXML private TextField txfSurname;
    @FXML private TextField txfFirstname;
    @FXML private TextField txfTelephone;
    @FXML private TextField txfQualification;
    @FXML private Button btnAdd;
    @FXML private Button btnDelete;
    @FXML private Button btnAssignPatients;
    
    private ObservableList<Caregiver> caregivers = FXCollections.observableArrayList();
    private CaregiverDao dao;

    @FXML
    public void initialize() {
        readAllAndShowInTableView();
        
        this.colID.setCellValueFactory(new PropertyValueFactory<>("cid"));
        this.colSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());
        this.colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        this.colTelephone.setCellFactory(TextFieldTableCell.forTableColumn());
        this.colQualification.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        this.colQualification.setCellFactory(TextFieldTableCell.forTableColumn());
        
        this.tableView.setItems(this.caregivers);
        this.tableView.setEditable(true);
        
        this.btnAdd.setOnAction(event -> handleAdd());
        this.btnDelete.setOnAction(event -> handleDeactivate());
        this.btnAssignPatients.setOnAction(event -> handleAssignPatients());
    }
    
    /**
     * Reads all caregivers from the database and shows them in the TableView
     */
    private void readAllAndShowInTableView() {
        this.caregivers.clear();
        this.dao = DaoFactory.getDaoFactory().createCaregiverDao();
        try {
            List<Caregiver> allCaregivers = this.dao.readAll();
            this.caregivers.addAll(allCaregivers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void doUpdate(Caregiver caregiver) {
        try {
            this.dao.update(caregiver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleAdd() {
        try {
            Caregiver caregiver = new Caregiver(
                this.txfFirstname.getText(),
                this.txfSurname.getText(),
                this.txfTelephone.getText(),
                this.txfQualification.getText()
            );
            this.dao.create(caregiver);
            readAllAndShowInTableView();
            clearTextFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void clearTextFields() {
        this.txfSurname.clear();
        this.txfFirstname.clear();
        this.txfTelephone.clear();
        this.txfQualification.clear();
    }
    
    @FXML
    public void handleDeactivate() {
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                selectedItem.setActive(false);
                this.dao.update(selectedItem);
                readAllAndShowInTableView();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showNoSelectionAlert();
        }
    }
    
    private void showNoSelectionAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Keine Auswahl");
        alert.setHeaderText("Keine Pflegekraft ausgewählt");
        alert.setContentText("Bitte wählen Sie eine Pflegekraft aus.");
        alert.showAndWait();
    }
    
    @FXML
    public void handleAssignPatients() {
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            showPatientAssignmentView(selectedItem);
        } else {
            showNoSelectionAlert();
        }
    }
    
    private void showPatientAssignmentView(Caregiver caregiver) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/de/hitec/nhplus/PatientAssignmentView.fxml"));
            AnchorPane page = loader.load();
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Patienten zuordnen");
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            
            PatientAssignmentController controller = loader.getController();
            controller.setCaregiver(caregiver);
            controller.setDialogStage(dialogStage);
            
            dialogStage.showAndWait();
            readAllAndShowInTableView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
