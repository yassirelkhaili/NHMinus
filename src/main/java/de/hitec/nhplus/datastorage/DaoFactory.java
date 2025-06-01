package de.hitec.nhplus.datastorage;

public class DaoFactory {

    private static DaoFactory instance;

    private DaoFactory() {
    }

    public static DaoFactory getDaoFactory() {
        if (DaoFactory.instance == null) {
            DaoFactory.instance = new DaoFactory();
        }
        return DaoFactory.instance;
    }

    public TreatmentDao createTreatmentDao() {
        return new TreatmentDao(ConnectionBuilder.getConnection());
    }

    public PatientDao createPatientDao() {
        return new PatientDao(ConnectionBuilder.getConnection());
    }

    public UserDao createUserDao() {
        return new UserDao(ConnectionBuilder.getConnection());
    }
}
