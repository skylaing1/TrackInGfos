package org.example.entities;

import jakarta.persistence.*;

import java.util.List;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mitarbeiter_personalNummer", referencedColumnName = "personalNummer")
    private Mitarbeiter mitarbeiter;

    @OneToMany(mappedBy = "loginData")
    private List<Token> tokens;


    //Getters und Setters
    public int getCredentialsId() {return credentialsId;}
    public void setCredentialsId(int credentialsId) {this.credentialsId = credentialsId;}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }
    public void setPasswort(String password) {
        this.passwort = password;
    }

    public Mitarbeiter getMitarbeiter() {return mitarbeiter;}
    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public Token[] getTokens() {
        return tokens.toArray(new Token[0]);
    }
    public void setTokens(List<Token> tokens) {this.tokens = tokens;}
}
