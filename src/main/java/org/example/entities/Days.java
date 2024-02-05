package org.example.entities;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

import java.sql.Date;

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
    @Column(name = "date")
    private Date date;

    @Expose
    @Transient
    private String color;

    @Expose
    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "Mitarbeiter_personalNummer", nullable = false)
    private Mitarbeiter mitarbeiter;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public String getStatus() {
        return status;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Object getDayId() {
        return daysId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // getters and setters
}