package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "Entries")
public class Entries {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private int entryId;


    @Column(name = "startTime")
    private String startTime;


    @Column(name = "endTime")
    private String endTime;


    @Column(name = "description")
    private String description;


    @Column(name = "state")
    private String state;

    @Column(name = "entryDuration")
    private int entryDuration;

    @Transient
    private String cardColor;


    @Transient
    private int entryWidth;

    @ManyToOne
    @JoinColumn(name = "days_id", nullable = false)
    private Days day;

    public void setDay(Days day) {
        this.day = day;
    }

    public void setStatus(String state) {
        this.state = state;
    }

    public void setStartTime(String time) {
        this.startTime = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndTime(String time) {
        this.endTime = time;
    }

    public String getState() {
        return state;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setCardColor(String s) {
        this.cardColor = s;
    }

    public String getDescription() {
        return description;
    }

    public String getCardColor() {
        return cardColor;
    }

    public int getEntryDuration() {
        return entryDuration;
    }

    public void setEntryDuration(int i) {
        this.entryDuration = i;
    }

    public void setEntryWidth(int v) {
        this.entryWidth = v;
    }

    public int getEntryId() {
        return entryId;
    }

    public int getEntryWidth() {
        return entryWidth;
    }

    // getters and setters
}