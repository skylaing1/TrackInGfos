package org.example;

import org.example.database.MitarbeiterTransaction;
import org.example.entities.Days;
import org.example.entities.Entries;
import org.example.entities.Mitarbeiter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MitarbeiterRefresh implements Runnable {
    @Override
    public void run() {
        // Für Datatables
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();



        List<Mitarbeiter> mitarbeiterList = MitarbeiterTransaction.fetchAllMitarbeiterForMitarbeiterRefresh();
        for (Mitarbeiter mitarbeiter : mitarbeiterList) {
            mitarbeiter.setPresent(0);
            int weekHoursProgress = 0;
            List<Days> daysList = mitarbeiter.getDays();
            for (Days day : daysList) {
                // if Day is in the current week
                if (ServletUtil.isInCurrentWeek(day.getDate().toLocalDate(), date)) {
                    weekHoursProgress += (day.getPresentDuration() + day.getSickDuration())/ 60;
                    if (day.getStatus().equals("Urlaub"))  {
                        weekHoursProgress += 8;
                    }
                    if (date.equals(day.getDate().toLocalDate())) {
                        List<Entries> entriesList = day.getEntries();
                        for (Entries entry : entriesList) {
                            LocalTime entryStartTime = LocalTime.parse(entry.getStartTime());
                            LocalTime entryEndTime = LocalTime.parse(entry.getEndTime());
                            if (entry.getState().equals("Anwesend") || entry.getState().equals("Dienstreise")) {
                                if (entryStartTime.isBefore(time) && entryEndTime.isAfter(time)) {
                                    mitarbeiter.setPresent(1);
                                    continue;
                                }
                                mitarbeiter.setPresent(2);
                            } else {
                                if (mitarbeiter.getPresent() != 1 && mitarbeiter.getPresent() != 2) {
                                    mitarbeiter.setPresent(0);
                                }
                            }
                        }
                    } else {
                        if (mitarbeiter.getPresent() != 1 && mitarbeiter.getPresent() != 2) {
                            mitarbeiter.setPresent(0);
                        }

                    }
                }

            }
            mitarbeiter.setWeekHoursProgress(weekHoursProgress);
            MitarbeiterTransaction.updateMitarbeiter(mitarbeiter);

        }
    }
}