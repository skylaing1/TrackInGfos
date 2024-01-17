package org.example.entities;

import jakarta.persistence.*;

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

    @OneToOne(mappedBy = "loginData", cascade = CascadeType.ALL)
    private Token token;

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

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }
}
