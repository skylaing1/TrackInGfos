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

    @Column(name = "onetimepassword")
    private String onetimepassword;

    @OneToOne(mappedBy = "mitarbeiter", cascade = CascadeType.ALL)
    private LoginData loginData;

    public Mitarbeiter() {
    }
    public int getPersonalNummer() {return personalNummer;}

    public Date getGeburtsdatum() {
        return this.geburtsdatum;
    }

    public String getName(){
        return this.name;
    }

    public String getOnetimepassword() {
        return this.onetimepassword;
    }

}