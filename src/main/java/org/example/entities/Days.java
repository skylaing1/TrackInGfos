package org.example.entities;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.DayOfWeek;
import java.util.List;

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
    private int presentDuration;

    @Expose
    @Column(name = "description")
    private String description;

    @Column(name = "sickDuration")
    private int sickDuration;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Entries> entries;

    @ManyToOne
    @JoinColumn(name = "Mitarbeiter_personalNummer", nullable = false)
    private Mitarbeiter mitarbeiter;


    @Expose
    @Transient
    private String color;

    @Expose
    @Transient
    private int sickHours;

    @Expose
    @Transient
    private int presentHours;


    //Methoden
    public DayOfWeek getDayOfWeek() {
        return date.toLocalDate().getDayOfWeek();
    }


    //Getters und Setters
    public int getDayId() {
        return daysId;
    }
    public void setDayId(int dayId) {this.daysId = dayId;}

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getPresentDuration() {
        return presentDuration;
    }
    public void setPresentDuration(int presentDuration) {
        this.presentDuration = presentDuration;
    }

    public String getDescription() {return description;}
    public void setDescription(String description) {
        this.description = description;
    }

    public int getSickDuration() {
        return sickDuration;
    }
    public void setSickDuration(int i) {
        this.sickDuration = i;
    }

    public List<Entries> getEntries() {return entries; }
    public void setEntries(List<Entries> entries) {this.entries = entries;}

    public Mitarbeiter getMitarbeiter() {return mitarbeiter; }
    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public String getColor() {return color;}
    public void setColor(String color) {
        this.color = color;
    }

    public int getSickHours() {
        return sickHours;
    }
    public void setSickHours(int sickHours) {
        this.sickHours = sickHours;
    }

    public int getPresentHours() {
        return presentHours;
    }
    public void setPresentHours(int i) {
        this.presentHours = i;
    }
}