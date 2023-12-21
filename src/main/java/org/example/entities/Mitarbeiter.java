package org.example.entities;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "Mitarbeiter")
public class Mitarbeiter {

    @Id
    @Column(name = "personalNummer")
    private int personalNummer;

    @Column(name = "name")
    private String name;

    @Column(name = "vorname")
    private String vorname;

    @Column(name = "geburtsdatum")
    private Date geburtsdatum;

    @OneToOne(mappedBy = "mitarbeiter", cascade = CascadeType.ALL)
    private LoginData loginData;

    public Mitarbeiter() {
    }
    public int getPersonalNummer() {return personalNummer;}


}