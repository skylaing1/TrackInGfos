package org.example.entities;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "einstellungsdatum",columnDefinition = "DATE default '1900-01-01'")
    private LocalDate einstellungsdatum;

    @Column(name = "Position", columnDefinition = "varchar(255) default 'keine position'")
    private String position;

    @Column(name = "Wochenstunden",columnDefinition = "int default 1")
    private int wochenstunden;

    @Column(name = "profile_picture", columnDefinition = "varchar(255) default '../resources/img/avatars/default.jpeg'")
    private String profilePicture;

    @Column(name = "WochenStundenFortschritt", columnDefinition = "int default 0")
    private int weekHoursProgress;

    @Column(name = "verbleibendeUrlaubstage", columnDefinition = "int default 28")
    private int verbleibendeUrlaubstage;

    @Column(name = "admin",columnDefinition = "boolean default false")
    private boolean admin;

    @Column(name = "onetimepassword")
    private String onetimepassword;

    @Transient
    private String geburtsdatumFormatted;

    @Transient
    private String einstellungsdatumFormatted;

    @Transient
    private String wochenstundenFormatted;

    @Transient
    private String picturePath;
    //TODO: PricturePath soll über Session übergeben und gesetzt werden

    @OneToMany(mappedBy = "mitarbeiter", cascade = CascadeType.ALL)
    private List<Days> days;

    @OneToMany(mappedBy = "mitarbeiter", cascade = CascadeType.ALL)
    private List<Message> messages;

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
            this.profilePicture = "../resources/img/avatars/default.jpeg";
        } else {
            this.profilePicture = "../resources/img/avatars/" + fileName;
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





    public void setWochenstundenFormatted(String wochenstundenFormatted) {
        this.wochenstundenFormatted = wochenstundenFormatted;
    }


    public String getWochenstundenFormatted() {
        return wochenstundenFormatted;
    }

    public int getWochenstunden() {
        return wochenstunden;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setWochenstunden(int wochenstunden) {
        this.wochenstunden = wochenstunden;
    }

    public void setOnetimepassword(String onetimepassword) {
        this.onetimepassword = onetimepassword;
    }


    public LocalDate getEinstellungsdatum() {
        return einstellungsdatum;
    }

    public String getEinstellungsdatumFormatted() {
        return einstellungsdatumFormatted;
    }
    public void setEinstellungsdatumFormatted(String formattedDateEinstellungsDatum) {
        this.einstellungsdatumFormatted = formattedDateEinstellungsDatum;
    }

    public void setEinstellungsdatum(LocalDate parse) {
        this.einstellungsdatum = parse;
    }

    public LoginData getLoginData() {
        return loginData;
    }

    public void setLoginData(LoginData loginData) {
        this.loginData = loginData;
    }

    public void setWeekHoursProgress(int i) {
        this.weekHoursProgress = i;
    }

    public int getWeekHoursProgress() {
        return weekHoursProgress;
    }

    public void setVerbleibendeUrlaubstage(int leftVacation) {
        this.verbleibendeUrlaubstage = leftVacation;
    }

    public int getVerbleibendeUrlaubstage() {
        return verbleibendeUrlaubstage;
    }
}

//TODO: If für Sidebar Managment anhand von Admin