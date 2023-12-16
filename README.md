MYSQL ROOT= G9#xYp$2zLs!vT7*



//DB MySql
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema trackindatabase
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema trackindatabase
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS trackindatabase DEFAULT CHARACTER SET utf8 ;
USE trackindatabase ;

-- -----------------------------------------------------
-- Table trackindatabase.Mitarbeiter
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS trackindatabase.Mitarbeiter (
personalNummer INT UNSIGNED NOT NULL,
name VARCHAR(30) NOT NULL,
vorname VARCHAR(30) NOT NULL,
geburtsdatum DATE NOT NULL,
PRIMARY KEY (personalNummer))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table trackindatabase.LoginData
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS trackindatabase.LoginData (
credentials_id INT NOT NULL,
email VARCHAR(45) NULL,
passwort VARCHAR(45) NULL,
mitarbeiter_personalNummer INT UNSIGNED NOT NULL,
PRIMARY KEY (credentials_id, mitarbeiter_personalNummer),
INDEX fk_login_data_Mitarbeiter_idx (mitarbeiter_personalNummer ASC) VISIBLE,
UNIQUE INDEX email_UNIQUE (email ASC) VISIBLE,
CONSTRAINT fk_login_data_Mitarbeiter
FOREIGN KEY (mitarbeiter_personalNummer)
REFERENCES trackindatabase.Mitarbeiter (personalNummer)
ON DELETE NO ACTION
ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;