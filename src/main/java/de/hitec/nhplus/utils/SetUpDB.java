package de.hitec.nhplus.utils;

import de.hitec.nhplus.datastorage.ConnectionBuilder;
import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.PatientDao;
import de.hitec.nhplus.datastorage.TreatmentDao;
import de.hitec.nhplus.datastorage.UserDao;
import de.hitec.nhplus.model.Patient;
import de.hitec.nhplus.model.Status;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static de.hitec.nhplus.utils.DateConverter.convertStringToLocalDate;
import static de.hitec.nhplus.utils.DateConverter.convertStringToLocalTime;

/**
 * Call static class provides to static methods to set up and wipe the database.
 * It uses the class ConnectionBuilder
 * and its path to build up the connection to the database. The class is
 * executable. Executing the class will build
 * up a connection to the database and calls setUpDb() to wipe the database,
 * build up a clean database and fill the
 * database with some test data.
 */
public class SetUpDB {

    /**
     * This method wipes the database by dropping the tables. Then the method calls
     * DDL statements to build it up from
     * scratch and DML statements to fill the database with hard coded test data.
     */
    public static void setUpDb() {
        Connection connection = ConnectionBuilder.getConnection();
        SetUpDB.wipeDb(connection);
        SetUpDB.setUpTablePatient(connection);
        SetUpDB.setUpTableTreatment(connection);
        SetUpDB.setUpTableUser(connection);
        SetUpDB.setUpPatients();
        SetUpDB.setUpTreatments();
        //SetUpDB.setUpUsers();
    }

    /**
     * This method wipes the database by dropping the tables.
     */
    public static void wipeDb(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE patient");
            statement.execute("DROP TABLE treatment");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void setUpTablePatient(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS patient (" +
                "   pid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   firstname TEXT NOT NULL, " +
                "   surname TEXT NOT NULL, " +
                "   dateOfBirth TEXT NOT NULL, " +
                "   carelevel TEXT NOT NULL, " +
                "   roomnumber TEXT NOT NULL, " +
                "   assets TEXt NOT NULL," +
                "   status TEXT NOT NULL, " +
                "   blockDate TEXT NOT NULL" +
                ");";
        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void setUpTableTreatment(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS treatment (" +
                "   tid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   pid INTEGER NOT NULL, " +
                "   treatment_date TEXT NOT NULL, " +
                "   begin TEXT NOT NULL, " +
                "   end TEXT NOT NULL, " +
                "   description TEXT NOT NULL, " +
                "   remark TEXT NOT NULL," +
                "   status TEXT NOT NULL, " +
                "   blockDate TEXT NOT NULL," +
                "   FOREIGN KEY (pid) REFERENCES patient (pid) ON DELETE CASCADE " +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void setUpPatients() {
        try {
            PatientDao dao = DaoFactory.getDaoFactory().createPatientDao();
            dao.create(
                    new Patient("Seppl", "Herberger", convertStringToLocalDate("1945-12-01"), "4", "202", "vermögend", Status.ACTIVE, convertStringToLocalDate("2025-01-01")));
            dao.create(new Patient("Martina", "Gerdsen", convertStringToLocalDate("1954-08-12"), "5", "010", "arm", Status.ACTIVE, convertStringToLocalDate("2025-01-01")));
            dao.create(new Patient("Gertrud", "Franzen", convertStringToLocalDate("1949-04-16"), "3", "002", "normal", Status.ACTIVE, convertStringToLocalDate("2025-01-01")));
            dao.create(new Patient("Ahmet", "Yilmaz", convertStringToLocalDate("1941-02-22"), "3", "013", "normal", Status.ACTIVE, convertStringToLocalDate("2025-01-01")));
            dao.create(new Patient("Hans", "Neumann", convertStringToLocalDate("1955-12-12"), "2", "001", "sehr vermögend", Status.ACTIVE, convertStringToLocalDate("2025-01-01")));
            dao.create(new Patient("Elisabeth", "Marouane", convertStringToLocalDate("1958-03-07"), "5", "110", "arm", Status.ACTIVE, convertStringToLocalDate("2025-01-01")));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void setUpTreatments() {
        try {
            TreatmentDao dao = DaoFactory.getDaoFactory().createTreatmentDao();
            dao.create(new Treatment(1, 1, convertStringToLocalDate("2023-06-03"), convertStringToLocalTime("11:00"),
                    convertStringToLocalTime("15:00"), "Gespräch",
                    "Der Patient hat enorme Angstgefühle und glaubt, er sei überfallen worden. Ihm seien alle Wertsachen gestohlen worden.\nPatient beruhigt sich erst, als alle Wertsachen im Zimmer gefunden worden sind.",
                    Status.ACTIVE, convertStringToLocalDate("2025-06-06")));
            dao.create(new Treatment(2, 1, convertStringToLocalDate("2023-06-05"), convertStringToLocalTime("11:00"),
                    convertStringToLocalTime("12:30"), "Gespräch",
                    "Patient irrt auf der Suche nach gestohlenen Wertsachen durch die Etage und bezichtigt andere Bewohner des Diebstahls.\nPatient wird in seinen Raum zurückbegleitet und erhält Beruhigungsmittel.",
                    Status.ACTIVE, convertStringToLocalDate("2025-06-06")));
            dao.create(new Treatment(3, 2, convertStringToLocalDate("2023-06-04"), convertStringToLocalTime("07:30"),
                    convertStringToLocalTime("08:00"), "Waschen",
                    "Patient mit Waschlappen gewaschen und frisch angezogen. Patient gewendet.",
                    Status.ACTIVE, convertStringToLocalDate("2025-06-06")));
            dao.create(new Treatment(4, 1, convertStringToLocalDate("2023-06-06"), convertStringToLocalTime("15:10"),
                    convertStringToLocalTime("16:00"), "Spaziergang",
                    "Spaziergang im Park, Patient döst  im Rollstuhl ein",
                    Status.ACTIVE, convertStringToLocalDate("2025-06-06")));
            dao.create(new Treatment(8, 1, convertStringToLocalDate("2023-06-08"), convertStringToLocalTime("15:00"),
                    convertStringToLocalTime("16:00"), "Spaziergang",
                    "Parkspaziergang; Patient ist heute lebhafter und hat klare Momente; erzählt von seiner Tochter",
                    Status.ACTIVE, convertStringToLocalDate("2025-06-06")));
            dao.create(new Treatment(9, 2, convertStringToLocalDate("2023-06-07"), convertStringToLocalTime("11:00"),
                    convertStringToLocalTime("11:30"), "Waschen",
                    "Waschen per Dusche auf einem Stuhl; Patientin gewendet;",
                    Status.ACTIVE, convertStringToLocalDate("2025-06-06")));
            dao.create(new Treatment(12, 5, convertStringToLocalDate("2023-06-08"), convertStringToLocalTime("15:00"),
                    convertStringToLocalTime("15:30"), "Physiotherapie",
                    "Übungen zur Stabilisation und Mobilisierung der Rückenmuskulatur",
                    Status.ACTIVE, convertStringToLocalDate("2025-06-06")));
            dao.create(new Treatment(14, 4, convertStringToLocalDate("2023-08-24"), convertStringToLocalTime("09:30"),
                    convertStringToLocalTime("10:15"), "KG", "Lympfdrainage",
                    Status.ACTIVE, convertStringToLocalDate("2025-06-06")));
            dao.create(new Treatment(16, 6, convertStringToLocalDate("2023-08-31"), convertStringToLocalTime("13:30"),
                    convertStringToLocalTime("13:45"), "Toilettengang",
                    "Hilfe beim Toilettengang; Patientin klagt über Schmerzen beim Stuhlgang. Gabe von Iberogast",
                    Status.ACTIVE, convertStringToLocalDate("2025-06-06")));
            dao.create(new Treatment(17, 6, convertStringToLocalDate("2023-09-01"), convertStringToLocalTime("16:00"),
                    convertStringToLocalTime("17:00"), "KG",
                    "Massage der Extremitäten zur Verbesserung der Durchblutung",
                    Status.ACTIVE, convertStringToLocalDate("2025-06-06")));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void setUpTableUser(Connection connection) {
        final String SQL = "CREATE TABLE IF NOT EXISTS users (" +
                "   uid INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "   first_name TEXT NOT NULL, " +
                "   surname TEXT NOT NULL, " +
                "   username TEXT UNIQUE NOT NULL, " +
                "   email TEXT UNIQUE NOT NULL, " +
                "   password_hash TEXT NOT NULL, " +
                "   role TEXT NOT NULL DEFAULT 'USER', " +
                "   is_active BOOLEAN DEFAULT 1, " +
                "   created_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                "   updated_at DATETIME DEFAULT CURRENT_TIMESTAMP " +
                ");";

        try (Statement statement = connection.createStatement()) {
            statement.execute(SQL);
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void setUpUsers() {
        try {
            UserDao dao = DaoFactory.getDaoFactory().createUserDao();

            // Admin user
            dao.create(new User(1, "John", "Doe", "admin", "john.doe@nhplus.com",
                    HashPassword.hashPassword("admin123"), "ADMIN", true,
                    LocalDateTime.now(), LocalDateTime.now()));

            // Nurse users
            dao.create(new User(2, "Jane", "Smith", "jsmith", "jane.smith@nhplus.com",
                    HashPassword.hashPassword("nurse123"), "NURSE", true,
                    LocalDateTime.now(), LocalDateTime.now()));

            dao.create(new User(3, "Maria", "Garcia", "mgarcia", "maria.garcia@nhplus.com",
                    HashPassword.hashPassword("nurse456"), "NURSE", true,
                    LocalDateTime.now(), LocalDateTime.now()));

            // Doctor users
            dao.create(new User(4, "Dr. Michael", "Johnson", "mjohnson", "michael.johnson@nhplus.com",
                    HashPassword.hashPassword("doctor123"), "DOCTOR", true,
                    LocalDateTime.now(), LocalDateTime.now()));

            dao.create(new User(5, "Dr. Sarah", "Wilson", "swilson", "sarah.wilson@nhplus.com",
                    HashPassword.hashPassword("doctor456"), "DOCTOR", true,
                    LocalDateTime.now(), LocalDateTime.now()));

            // Caregiver users
            dao.create(new User(6, "Anna", "Mueller", "amueller", "anna.mueller@nhplus.com",
                    HashPassword.hashPassword("care123"), "CAREGIVER", true,
                    LocalDateTime.now(), LocalDateTime.now()));

            dao.create(new User(7, "Thomas", "Weber", "tweber", "thomas.weber@nhplus.com",
                    HashPassword.hashPassword("care456"), "CAREGIVER", true,
                    LocalDateTime.now(), LocalDateTime.now()));

            // Reception/Administrative users
            dao.create(new User(8, "Lisa", "Hoffmann", "lhoffmann", "lisa.hoffmann@nhplus.com",
                    HashPassword.hashPassword("reception123"), "RECEPTION", true,
                    LocalDateTime.now(), LocalDateTime.now()));

            dao.create(new User(9, "Peter", "Schulz", "pschulz", "peter.schulz@nhplus.com",
                    HashPassword.hashPassword("admin456"), "ADMIN", true,
                    LocalDateTime.now(), LocalDateTime.now()));

            // Inactive user (for testing)
            dao.create(new User(10, "Former", "Employee", "femployee", "former.employee@nhplus.com",
                    HashPassword.hashPassword("inactive123"), "NURSE", false,
                    LocalDateTime.now().minusMonths(6), LocalDateTime.now().minusMonths(1)));

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SetUpDB.setUpDb();
    }
}
