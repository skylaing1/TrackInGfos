package org.example.entities;

import jakarta.persistence.*;
import org.example.entities.Mitarbeiter;

@Entity
@Table(name = "LoginData")
public class LoginData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credentials_id")
    private int credentialsId;

    @Column(name = "email")
    private String email;

    @Column(name = "passwort")
    private String passwort;

    @OneToOne
    @JoinColumn(name = "mitarbeiter_personalNummer", referencedColumnName = "personalNummer")
    private Mitarbeiter mitarbeiter;

    public LoginData() {
    }

    public String getPasswort() {
        return passwort;
    }

    public String getEmail() {
        return email;
    }



    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public void setPasswort(String password) {
        this.passwort = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
