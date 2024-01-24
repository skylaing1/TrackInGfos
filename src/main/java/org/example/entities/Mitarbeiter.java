package org.example.entities;

import java.sql.Date;
import java.time.LocalDate;

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
    private LocalDate geburtsdatum;

    @Column(name = "profile_picture", nullable = false, columnDefinition = "varchar(255) default 'default.jpeg'")
    private String profilePicture;

    @Column(name = "admin", nullable = false, columnDefinition = "boolean default false")
    private boolean admin;

    @Column(name = "onetimepassword")
    private String onetimepassword;

    @Transient
    private String geburtsdatumFormatted;

    @OneToOne(mappedBy = "mitarbeiter", cascade = CascadeType.ALL)
    private LoginData loginData;

    public Mitarbeiter() {
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public int getPersonalnummer() {
        return personalNummer;
    }

    public int getPersonalNummer() {return personalNummer;}

    public LocalDate getGeburtsdatum() {
        return this.geburtsdatum;
    }

    public String getName(){
        return this.name;
    }

    public String getVorname(){
        return this.vorname;
    }


    public String getOnetimepassword() {
        return this.onetimepassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public void setPersonalNummer(int personalNummer) {
        this.personalNummer = personalNummer;
    }

    public boolean isAdmin() {
        return admin;
    }





    public void setProfilePicture(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            this.profilePicture = "default.jpeg";
        } else {
            this.profilePicture = fileName;
        }
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setGeburtsdatumFormatted(String geburtsdatumFormatted) {
        this.geburtsdatumFormatted = geburtsdatumFormatted;
    }
    public String getGeburtsdatumFormatted() {
        return geburtsdatumFormatted;
    }


}

//TODO: If für Sidebar Managment anhand von Admin