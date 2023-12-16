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
    @JoinColumn(name = "mitarbeiter_personalNummer")
    private Mitarbeiter mitarbeiter;


}
