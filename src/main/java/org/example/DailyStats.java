package org.example;

import org.example.entities.Days;
import org.example.entities.Entries;

import java.util.ArrayList;
import java.util.List;

public class DailyStats {

    private String WeekDay;

    private int presentDuration;

    private int sickDuration;

    private int absentDuration;

    private int breakDuration;


    public static DailyStats createDailyStats(String WeekDay, List<Days> days) {
        int presentMinutes = 0;
        int sickMinutes = 0;
        int absentMinutes = 0;
        int breakMinutes = 0;

        DailyStats dailyStats = new DailyStats();
        dailyStats.setWeekDay(WeekDay);
        List<Entries> entriesOfDays = new ArrayList<>();

        for (Days day : days) {
            entriesOfDays.addAll(day.getEntries());
        }

        for (Entries entry : entriesOfDays) {

            switch (entry.getState()) {
                case "Anwesend":
                    presentMinutes += entry.getEntryDuration();
                    break;
                case "Krank":
                    sickMinutes += entry.getEntryDuration();
                    break;
                case "Abwesend":
                    absentMinutes += entry.getEntryDuration();
                    break;
                case "Pause":
                    breakMinutes += entry.getEntryDuration();
                    break;
            }
        }

        dailyStats.presentDuration = (presentMinutes / 60);
        dailyStats.sickDuration = (sickMinutes / 60);
        dailyStats.absentDuration = (absentMinutes / 60);
        dailyStats.breakDuration = (breakMinutes / 60);

        return dailyStats;
    }

    public void setWeekDay(String WeekDay) {
        this.WeekDay = WeekDay;
    }

    public String getWeekDay() {
        return WeekDay;
    }

    public int getPresentDuration() {
        return presentDuration;
    }

    public int getSickDuration() {
        return sickDuration;
    }

    public int getAbsentDuration() {
        return absentDuration;
    }

    public int getBreakDuration() {
        return breakDuration;
    }
}