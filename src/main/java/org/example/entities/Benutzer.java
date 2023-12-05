package org.example.entities;

import jakarta.persistence.*;


@Entity
@Table(name = "benutzer")
public class Benutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "benutzer_id")
    private Long benutzerId;

    @Column(name = "vorname")
    private String vorname;

    @Column(name = "nachname")
    private String nachname;


}
