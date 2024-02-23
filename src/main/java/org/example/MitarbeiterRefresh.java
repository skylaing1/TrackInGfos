package org.example;

import org.example.database.MitarbeiterDAO;
import org.example.entities.Mitarbeiter;

import java.time.LocalDate;
import java.util.List;

public class MitarbeiterRefresh implements Runnable {
    @Override
    public void run() {
        List<Mitarbeiter> mitarbeiterList = MitarbeiterDAO.fetchAllMitarbeiterForTable();

        LocalDate currentDate = LocalDate.now();






    }
}
