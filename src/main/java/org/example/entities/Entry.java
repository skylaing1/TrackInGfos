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
    private Time startTime;

    @Expose
    @Column(name = "endTime")
    private Time endTime;

    @Expose
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "Mitarbeiter_personalNummer", nullable = false)
    private Mitarbeiter mitarbeiter;

    // getters and setters
}