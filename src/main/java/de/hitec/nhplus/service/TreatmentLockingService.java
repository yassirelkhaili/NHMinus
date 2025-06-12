package de.hitec.nhplus.service;

import de.hitec.nhplus.datastorage.DaoFactory;
import de.hitec.nhplus.datastorage.TreatmentDao;
import de.hitec.nhplus.model.Treatment;
import de.hitec.nhplus.model.Status;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TreatmentLockingService {

    public void lockFinishedTreatments() {
        TreatmentDao treatmentDao = DaoFactory.getDaoFactory().createTreatmentDao();
        try {
            List<Treatment> allTreatments = treatmentDao.readAll();
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();

            for (Treatment treatment : allTreatments) {
                LocalDate date = LocalDate.parse(treatment.getDate());
                LocalTime endTime = LocalTime.parse(treatment.getEnd());

                boolean treatmentIsPast = date.isBefore(today) || (date.isEqual(today) && endTime.isBefore(now));
                boolean notYetLocked = !treatment.getStatus().equals(Status.LOCKED.name());

                if (treatmentIsPast && notYetLocked) {
                    treatment.setStatus(Status.LOCKED);
                    treatment.setBlockDate(today.toString());
                    treatmentDao.update(treatment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
