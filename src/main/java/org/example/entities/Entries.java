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

    @ManyToOne
    @JoinColumn(name = "days_id", nullable = false)
    private Days day;

    // getters and setters
}