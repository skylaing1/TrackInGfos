-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema trackindatabase
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema trackindatabase
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `trackindatabase` DEFAULT CHARACTER SET utf8mb3 ;
USE `trackindatabase` ;

-- -----------------------------------------------------
-- Table `trackindatabase`.`mitarbeiter`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trackindatabase`.`mitarbeiter` (
`personalNummer` INT NOT NULL,
`name` VARCHAR(50) NULL DEFAULT NULL,
`vorname` VARCHAR(50) NULL,
`geburtsdatum` DATE NULL,
`onetimepassword` VARCHAR(255) NULL DEFAULT NULL,
`admin` TINYINT(1) NULL DEFAULT '0',
`profile_picture` VARCHAR(255) NULL DEFAULT '../resources/img/avatars/default.jpeg',
`einstellungsdatum` DATE NULL DEFAULT '1900-01-01',
`position` VARCHAR(80) NULL DEFAULT 'Keine Positio  n',
`wochenstunden` TINYINT NULL DEFAULT 1,
`WochenStundenFortschritt` TINYINT NULL DEFAULT 0,
`verbleibendeUrlaubstage` TINYINT NULL DEFAULT 28,
`present` TINYINT NULL DEFAULT '0',
PRIMARY KEY (`personalNummer`),
UNIQUE INDEX `personalNummer_UNIQUE` (`personalNummer` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `trackindatabase`.`days`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trackindatabase`.`days` (
`days_id` INT NOT NULL AUTO_INCREMENT,
`status` VARCHAR(30) NULL DEFAULT NULL,
`date` DATE NOT NULL,
`Mitarbeiter_personalNummer` INT NOT NULL,
`description` VARCHAR(255) NULL DEFAULT NULL,
`presentDuration` SMALLINT NULL DEFAULT 0,
`sickDuration` SMALLINT NULL DEFAULT 0,
PRIMARY KEY (`days_id`),
INDEX `Mitarbeiter_personalNummer` (`Mitarbeiter_personalNummer` ASC) VISIBLE,
CONSTRAINT `days_ibfk_1`
FOREIGN KEY (`Mitarbeiter_personalNummer`)
REFERENCES `trackindatabase`.`mitarbeiter` (`personalNummer`)
ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 382
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `trackindatabase`.`entries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trackindatabase`.`entries` (
`entry_id` INT NOT NULL AUTO_INCREMENT,
`startTime` VARCHAR(10) NULL DEFAULT NULL,
`endTime` VARCHAR(10) NULL DEFAULT NULL,
`description` VARCHAR(255) NULL DEFAULT NULL,
`state` VARCHAR(30) NULL DEFAULT NULL,
`days_id` INT NOT NULL,
`entryDuration` SMALLINT NULL DEFAULT NULL,
PRIMARY KEY (`entry_id`),
INDEX `days_id` (`days_id` ASC) VISIBLE,
CONSTRAINT `entries_ibfk_1`
FOREIGN KEY (`days_id`)
REFERENCES `trackindatabase`.`days` (`days_id`)
ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 680
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `trackindatabase`.`logindata`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trackindatabase`.`logindata` (
`credentials_id` INT NOT NULL AUTO_INCREMENT,
`email` VARCHAR(60) NULL DEFAULT NULL,
`passwort` VARCHAR(255) NULL DEFAULT NULL,
`Mitarbeiter_personalNummer` INT NOT NULL,
PRIMARY KEY (`credentials_id`, `Mitarbeiter_personalNummer`),
UNIQUE INDEX `credentials_id_UNIQUE` (`credentials_id` ASC) VISIBLE,
UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
INDEX `fk_LoginData_Mitarbeiter_idx` (`Mitarbeiter_personalNummer` ASC) VISIBLE,
CONSTRAINT `fk_LoginData_Mitarbeiter`
FOREIGN KEY (`Mitarbeiter_personalNummer`)
REFERENCES `trackindatabase`.`mitarbeiter` (`personalNummer`)
ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 55
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `trackindatabase`.`message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trackindatabase`.`message` (
`Message_ID` INT NOT NULL AUTO_INCREMENT,
`status` VARCHAR(30) NULL DEFAULT NULL,
`Datum` DATE NULL DEFAULT NULL,
`Message` VARCHAR(255) NULL DEFAULT NULL,
`Mitarbeiter_personalNummer` INT NOT NULL,
`seen` TINYINT(1) NULL DEFAULT NULL,
PRIMARY KEY (`Message_ID`),
INDEX `Mitarbeiter_personalNummer` (`Mitarbeiter_personalNummer` ASC) VISIBLE,
CONSTRAINT `message_ibfk_1`
FOREIGN KEY (`Mitarbeiter_personalNummer`)
REFERENCES `trackindatabase`.`mitarbeiter` (`personalNummer`)
ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 151
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `trackindatabase`.`token`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trackindatabase`.`token` (
`Token_ID` INT NOT NULL AUTO_INCREMENT,
`token_content` VARCHAR(255) NOT NULL,
`token_timestamp` DATETIME(6) NULL DEFAULT NULL,
`credentials_id` INT NOT NULL,
PRIMARY KEY (`Token_ID`),
INDEX `fk_Token_LoginData_idx` (`credentials_id` ASC) VISIBLE,
CONSTRAINT `fk_Token_LoginData`
FOREIGN KEY (`credentials_id`)
REFERENCES `trackindatabase`.`logindata` (`credentials_id`)
ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 65
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
