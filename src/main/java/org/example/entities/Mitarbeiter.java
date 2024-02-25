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
    //TODO: PricturePath wird doppelt irgendwo gesetzt

    @Column(name = "WochenStundenFortschritt", columnDefinition = "int default 0")
    private int weekHoursProgress;

    @Column(name = "verbleibendeUrlaubstage", columnDefinition = "int default 28")
    private int verbleibendeUrlaubstage;

    @Column(name = "admin",columnDefinition = "boolean default false")
    private boolean admin;

    @Column(name = "onetimepassword")
    private String onetimepassword;


    @Column(name = "present" , columnDefinition = "int default 0")
    private int present;

    @OneToMany(mappedBy = "mitarbeiter", cascade = CascadeType.ALL)
    private List<Days> days;

    @OneToMany(mappedBy = "mitarbeiter", cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToOne(mappedBy = "mitarbeiter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LoginData loginData;


    @Transient
    private String geburtsdatumFormatted;

    @Transient
    private String einstellungsdatumFormatted;

    @Transient
    private String wochenstundenFormatted;

    @Transient
    private int wochenstundenProgressInPercent;


    //Getters und Setters
    public int getPersonalNummer() {return personalNummer;}
    public void setPersonalNummer(int personalNummer) {
        this.personalNummer = personalNummer;
    }

    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getVorname(){
        return vorname;
    }
    public void setVorname(String vorname) {this.vorname = vorname;}

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }
    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public LocalDate getEinstellungsdatum() {
        return einstellungsdatum;
    }
    public void setEinstellungsdatum(LocalDate einstellungsdatum) {
        this.einstellungsdatum = einstellungsdatum;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public int getWochenstunden() {
        return wochenstunden;
    }
    public void setWochenstunden(int wochenstunden) {
        this.wochenstunden = wochenstunden;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(String fileName) {this.profilePicture = fileName;}

    public int getWeekHoursProgress() {
        return weekHoursProgress;
    }
    public void setWeekHoursProgress(int weekHoursProgress) {
        this.weekHoursProgress = weekHoursProgress;
    }

    public int getVerbleibendeUrlaubstage() {
        return verbleibendeUrlaubstage;
    }
    public void setVerbleibendeUrlaubstage(int verbleibendeUrlaubstage) {this.verbleibendeUrlaubstage = verbleibendeUrlaubstage;}

    public boolean getAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getOnetimepassword() {
        return onetimepassword;
    }
    public void setOnetimepassword(String onetimepassword) {
        this.onetimepassword = onetimepassword;
    }

    public int getPresent() {return present;}
    public void setPresent(int present) {this.present = present;}

    public List<Days> getDays() {return days;}
    public void setDays(List<Days> days) {this.days = days;}

    public List<Message> getMessages() {return messages;}
    public void setMessages(List<Message> messages) {this.messages = messages;}

    public LoginData getLoginData() {return loginData;}
    public void setLoginData(LoginData loginData) {this.loginData = loginData;}

    public String getGeburtsdatumFormatted() {
        return geburtsdatumFormatted;
    }
    public void setGeburtsdatumFormatted(String geburtsdatumFormatted) {this.geburtsdatumFormatted = geburtsdatumFormatted;}

    public String getEinstellungsdatumFormatted() {
        return einstellungsdatumFormatted;
    }
    public void setEinstellungsdatumFormatted(String formattedDateEinstellungsDatum) {this.einstellungsdatumFormatted = formattedDateEinstellungsDatum;}

    public String getWochenstundenFormatted() {return wochenstundenFormatted;}
    public void setWochenstundenFormatted(String wochenstundenFormatted) {this.wochenstundenFormatted = wochenstundenFormatted;}

    public int getWochenstundenProgressInPercent() {return wochenstundenProgressInPercent;}
    public void setWochenstundenProgressInPercent(int wochenstundenProgressInPercent) {this.wochenstundenProgressInPercent = wochenstundenProgressInPercent;}
}