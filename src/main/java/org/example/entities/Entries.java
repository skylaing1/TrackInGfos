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

    @ManyToOne
    @JoinColumn(name = "days_id", nullable = false)
    private Days day;


    @Transient
    private String cardColor;

    @Transient
    private int entryWidth;


    //Getters und Setters
    public int getEntryId() {return entryId;}
    public void setEntryId(int entryId) {this.entryId = entryId;}

    public String getStartTime() {return startTime;}
    public void setStartTime(String time) {this.startTime = time;}

    public String getEndTime() {return endTime;}
    public void setEndTime(String time) {this.endTime = time;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getState() {return state;}
    public void setStatus(String state) {this.state = state;}

    public int getEntryDuration() {return entryDuration;}
    public void setEntryDuration(int entryDuration) {this.entryDuration = entryDuration;}

    public Days getDay() {return day;}
    public void setDay(Days day) {this.day = day;}

    public String getCardColor() {return cardColor;}
    public void setCardColor(String cardColor) {this.cardColor = cardColor;}

    public int getEntryWidth() {return entryWidth;}
    public void setEntryWidth(int entryWidth) {this.entryWidth = entryWidth;}
}