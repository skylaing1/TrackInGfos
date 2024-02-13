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

    @Column(name = "presentDuration")
    private int presentDuaration;

    @Expose
    @Transient
    private String color;

    @Expose
    @Column(name = "description")
    private String description;

    @Column(name = "sickDuration")
    private int sickDuration;

    @Expose
    @Transient
    private int sickHours;

    @Expose
    @Transient
    private int presentHours;


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

    public int getPresentDuration() {
        return presentDuaration;
    }

    public void setPresentDuration(int i) {
        this.presentDuaration = i;
    }

    public void setSickDuration(int i) {
        this.sickDuration = i;
    }

    public int getSickDuration() {
        return sickDuration;
    }

    public void setSickHours(int i) {
        this.sickHours = i;
    }

    public void setPresentHours(int i) {
        this.presentHours = i;
    }

    public int getPresentHours() {
        return presentHours;
    }

    public int getSickHours() {
        return sickHours;
    }

    public Date getDate() {
        return date;
    }


    // getters and setters
}