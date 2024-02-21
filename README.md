MYSQL ROOT= G9#xYp$2zLs!vT7*

Datenbank Regeln:

1. Um ein Datensatz in Logindata zu Erstellen muss der Datensatz in der Mitarbeiter Tabelle voll ausgefüllt werden
2. Emails werden klLein geschrieben in die Datenbank eingetragen und die Namen
3. Wir Benutzen Bcrypt zum verschlüsseln der Passwörter

//DB MySql
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema trackindatabase
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema trackindatabase
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `trackindatabase` DEFAULT CHARACTER SET utf8 ;
USE `trackindatabase` ;

-- -----------------------------------------------------
-- Table `trackindatabase`.`Mitarbeiter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trackindatabase`.`Mitarbeiter` (
`personalNummer` INT NOT NULL,
`name` VARCHAR(20) NOT NULL,
`vorname` VARCHAR(20) NOT NULL,
`geburtsdatum` DATE NOT NULL,
PRIMARY KEY (`personalNummer`),
UNIQUE INDEX `personalNummer_UNIQUE` (`personalNummer` ASC) VISIBLE
)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `trackindatabase`.`LoginData`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trackindatabase`.`LoginData` (
`credentials_id` INT NOT NULL AUTO_INCREMENT,
`email` VARCHAR(60) NULL,
`passwort` VARCHAR(60) NULL,
`Mitarbeiter_personalNummer` INT NOT NULL,
PRIMARY KEY (`credentials_id`, `Mitarbeiter_personalNummer`),
UNIQUE INDEX `credentials_id_UNIQUE` (`credentials_id` ASC) VISIBLE,
UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
INDEX `fk_LoginData_Mitarbeiter_idx` (`Mitarbeiter_personalNummer` ASC) VISIBLE,
CONSTRAINT `fk_LoginData_Mitarbeiter`
FOREIGN KEY (`Mitarbeiter_personalNummer`)
REFERENCES `trackindatabase`.`Mitarbeiter` (`personalNummer`)
ON DELETE CASCADE
ON UPDATE NO ACTION
)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


ALTER TABLE `trackindatabase`.`Mitarbeiter`
ADD COLUMN `onetimepassword` VARCHAR(255) DEFAULT NULL;

CREATE TABLE IF NOT EXISTS `trackindatabase`.`Token` (
`Token_ID` INT NOT NULL AUTO_INCREMENT,
`token_content` VARCHAR(255) NOT NULL,
`token_timestamp` TIMESTAMP NOT NULL,
`credentials_id` INT NOT NULL,
PRIMARY KEY (`Token_ID`),
INDEX `fk_Token_LoginData_idx` (`credentials_id` ASC) VISIBLE,
CONSTRAINT `fk_Token_LoginData`
FOREIGN KEY (`credentials_id`)
REFERENCES `trackindatabase`.`LoginData` (`credentials_id`)
ON DELETE CASCADE
ON UPDATE NO ACTION
)
ENGINE = InnoDB;


ALTER TABLE Mitarbeiter MODIFY Geburtsdatum DATE;

ALTER TABLE `trackindatabase`.`Mitarbeiter`
ADD COLUMN `admin` BOOLEAN DEFAULT FALSE;

ALTER TABLE `trackindatabase`.`Mitarbeiter`
ADD COLUMN `profile_picture` VARCHAR(255) NOT NULL DEFAULT '../resources/img/avatars/default.jpeg';


ALTER TABLE `trackindatabase`.`Mitarbeiter`
ADD COLUMN `Eintrittsdatum` DATE NOT NULL DEFAULT '1900-01-01',
ADD COLUMN `Position` VARCHAR(30) NOT NULL DEFAULT 'Keine Position',
ADD COLUMN `Wochenstunden` INT NOT NULL DEFAULT 1

ALTER TABLE `trackindatabase`.`Mitarbeiter`
CHANGE COLUMN `einstellungsdatum` `einstellungsdatum` DATE NOT NULL DEFAULT '1900-01-01',
CHANGE COLUMN `Position` `Position` VARCHAR(30) NOT NULL DEFAULT 'keine position';

CREATE TABLE IF NOT EXISTS `Days` (
`days_id` INT NOT NULL AUTO_INCREMENT,
`status` VARCHAR(40) NOT NULL,
`startDate` DATE NOT NULL,
`endDate` DATE NOT NULL,
`Mitarbeiter_personalNummer` INT NOT NULL,
FOREIGN KEY (`Mitarbeiter_personalNummer`) REFERENCES `trackindatabase`.`Mitarbeiter`(`personalNummer`) ON DELETE CASCADE,
PRIMARY KEY (`days_id`)
) ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `Entries` (
`entry_id` INT NOT NULL AUTO_INCREMENT,
`startTime` VARCHAR(5) NOT NULL,
`endTime` VARCHAR(5) NOT NULL,
`description` VARCHAR(255),
`state` VARCHAR(255),
`days_id` INT NOT NULL,
PRIMARY KEY (`entry_id`),
FOREIGN KEY (`days_id`) REFERENCES `Days`(`days_id`) ON DELETE CASCADE
) ENGINE = InnoDB;

ALTER TABLE Days
CHANGE COLUMN startDate date DATE NOT NULL,
DROP COLUMN endDate;

ALTER TABLE Days
ADD COLUMN description VARCHAR(255);

ALTER TABLE `Entries`
ADD COLUMN `entryDuration` INT;

ALTER TABLE Days
ADD COLUMN presentDuration INT;

ALTER TABLE Days
ADD COLUMN sickDuration INT;

ALTER TABLE Mitarbeiter
ADD COLUMN WochenStundenFortschritt INT DEFAULT 0;

ALTER TABLE Mitarbeiter
ADD COLUMN verbleibendeUrlaubstage INT DEFAULT 28;

CREATE TABLE IF NOT EXISTS `Message` (
`Message_ID` INT NOT NULL AUTO_INCREMENT,
`Status` VARCHAR(255) NOT NULL,
`Datum` DATE NOT NULL,
`Message` TEXT NOT NULL,
`Mitarbeiter_personalNummer` INT NOT NULL,
PRIMARY KEY (`Message_ID`),
FOREIGN KEY (`Mitarbeiter_personalNummer`) REFERENCES `Mitarbeiter`(`personalNummer`) ON DELETE CASCADE
) ENGINE = InnoDB;

ALTER TABLE Message MODIFY Status VARCHAR(50);