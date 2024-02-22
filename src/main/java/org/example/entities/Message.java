package org.example.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Message_ID")
    private int messageId;

    @Column(name = "Status")
    private String status;

    @Column(name = "Datum")
    private LocalDate datum;

    @Column(name = "Message")
    private String message;

    @Column(name = "seen", nullable = false, columnDefinition = "boolean default false")
    private boolean seen;

    @ManyToOne
    @JoinColumn(name = "Mitarbeiter_personalNummer", nullable = false)
    private Mitarbeiter mitarbeiter;


    @Transient
    private String messageDateFormatted;

    @Transient
    private String icon;


    //Getters und Setters
    public int getMessageId() {
        return messageId;
    }
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDatum() {
        return datum;
    }
    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSeen() {return seen;}
    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }
    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public String getMessageDateFormatted() {
        return messageDateFormatted;
    }
    public void setMessageDateFormatted(String messageDateFormatted) {this.messageDateFormatted = messageDateFormatted;}

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
}