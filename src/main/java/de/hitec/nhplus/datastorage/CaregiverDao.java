package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.Caregiver;
import de.hitec.nhplus.model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CaregiverDao extends DaoImp<Caregiver> {

    public CaregiverDao(Connection connection) {
        super(connection);
    }

    @Override
    protected PreparedStatement getCreateStatement(Caregiver caregiver) {
        PreparedStatement statement = null;
        try {
            final String SQL = "INSERT INTO caregiver (firstname, surname, telephone, qualification, is_active) VALUES (?, ?, ?, ?, ?)";
            statement = this.connection.prepareStatement(SQL);
            statement.setString(1, caregiver.getFirstName());
            statement.setString(2, caregiver.getSurname());
            statement.setString(3, caregiver.getTelephone());
            statement.setString(4, caregiver.getQualification());
            statement.setBoolean(5, caregiver.isActive());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    @Override
    protected PreparedStatement getReadByIDStatement(long id) {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM caregiver WHERE cid = ?";
            statement = this.connection.prepareStatement(SQL);
            statement.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM caregiver";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Caregiver caregiver) {
        PreparedStatement statement = null;
        try {
            final String SQL = "UPDATE caregiver SET firstname = ?, surname = ?, telephone = ?, qualification = ?, is_active = ? WHERE cid = ?";
            statement = this.connection.prepareStatement(SQL);
            statement.setString(1, caregiver.getFirstName());
            statement.setString(2, caregiver.getSurname());
            statement.setString(3, caregiver.getTelephone());
            statement.setString(4, caregiver.getQualification());
            statement.setBoolean(5, caregiver.isActive());
            statement.setLong(6, caregiver.getCid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    @Override
    protected PreparedStatement getDeleteStatement(long id) {
        PreparedStatement statement = null;
        try {
            final String SQL = "UPDATE caregiver SET is_active = 0 WHERE cid = ?";
            statement = this.connection.prepareStatement(SQL);
            statement.setLong(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    @Override
    protected Caregiver getInstanceFromResultSet(ResultSet result) throws SQLException {
        long cid = result.getLong("cid");
        String firstName = result.getString("firstname");
        String surname = result.getString("surname");
        String telephone = result.getString("telephone");
        String qualification = result.getString("qualification");
        boolean isActive = result.getBoolean("is_active");
        return new Caregiver(cid, firstName, surname, telephone, qualification, isActive);
    }

    @Override
    protected ArrayList<Caregiver> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Caregiver> list = new ArrayList<>();
        while (result.next()) {
            list.add(getInstanceFromResultSet(result));
        }
        return list;
    }

    /**
     * Assigns a caregiver to a patient
     */
    public void assignPatient(long caregiverId, long patientId) throws SQLException {
        String sql = "INSERT INTO caregiver_patient (caregiver_id, patient_id) VALUES (?, ?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, caregiverId);
            statement.setLong(2, patientId);
            statement.executeUpdate();
        }
    }

    /**
     * Removes a caregiver-patient assignment
     */
    public void removePatientAssignment(long caregiverId, long patientId) throws SQLException {
        String sql = "DELETE FROM caregiver_patient WHERE caregiver_id = ? AND patient_id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, caregiverId);
            statement.setLong(2, patientId);
            statement.executeUpdate();
        }
    }

    /**
     * Gets all patients assigned to a caregiver
     */
    public List<Patient> getPatientsForCaregiver(long caregiverId) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT p.* FROM patient p JOIN caregiver_patient cp ON p.pid = cp.patient_id WHERE cp.caregiver_id = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, caregiverId);
            ResultSet rs = statement.executeQuery();

            PatientDao patientDao = DaoFactory.getDaoFactory().createPatientDao();
            while (rs.next()) {
                long pid = rs.getLong("pid");
                Patient patient = patientDao.read(pid);
                if (patient != null) {
                    patients.add(patient);
                }
            }
        }

        return patients;
    }

    /**
     * Gets all caregivers assigned to a patient
     */
    public List<Caregiver> getCaregiversForPatient(long patientId) throws SQLException {
        List<Caregiver> caregivers = new ArrayList<>();
        String sql = "SELECT c.* FROM caregiver c JOIN caregiver_patient cp ON c.cid = cp.caregiver_id WHERE cp.patient_id = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setLong(1, patientId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                caregivers.add(getInstanceFromResultSet(rs));
            }
        }

        return caregivers;
    }
}