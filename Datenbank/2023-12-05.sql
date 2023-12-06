-- Erstellt die Datenbank
CREATE DATABASE IF NOT EXISTS TrackInDatabase;
USE TrackInDatabase;

-- Erstellt die Tabelle für Benutzerinformationen
CREATE TABLE IF NOT EXISTS benutzer (
    benutzer_id INT AUTO_INCREMENT PRIMARY KEY,
    vorname VARCHAR(25),
    nachname VARCHAR(25),
    UNIQUE KEY unique_benutzer (vorname, nachname)
);

-- Erstellt die Tabelle für Passwörter
CREATE TABLE IF NOT EXISTS passwoerter (
    passwort_id INT AUTO_INCREMENT PRIMARY KEY,
    benutzer_id INT,
    passwort VARCHAR(255),
    FOREIGN KEY (benutzer_id) REFERENCES benutzer(benutzer_id)
);

-- Beispieldaten
INSERT INTO benutzer (vorname, nachname) VALUES
    ('Max', 'Mustermann'),
    ('Anna', 'Musterfrau');

INSERT INTO passwoerter (benutzer_id, passwort) VALUES
    (1, 'geheimespasswort1'),
    (2, 'geheimespasswort2');