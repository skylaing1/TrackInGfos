package org.example.entities;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "Days")
public class Days {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "days_id")
    private int daysId;

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
    @Transient
    private String color;

    @ManyToOne
    @JoinColumn(name = "Mitarbeiter_personalNummer", nullable = false)
    private Mitarbeiter mitarbeiter;

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