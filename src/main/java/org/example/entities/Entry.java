package org.example.entities;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "entry")
public class Entry {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private int entryId;

    @Expose
    @Column(name = "status")
    private String status;

    @Expose
    @Column(name = "startDate")
    private Date startDate;

    @Expose
    @Column(name = "endDate")
    private Date endDate;

    @Expose
    @Column(name = "startTime")
    private String startTime;

    @Expose
    @Column(name = "endTime")
    private String endTime;

    @Expose
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "Mitarbeiter_personalNummer", nullable = false)
    private Mitarbeiter mitarbeiter;

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
    }

    public void setEndDate(Date date) {
        this.endDate = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }


    // getters and setters
}