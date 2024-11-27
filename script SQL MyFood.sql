-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
SET GLOBAL max_connections=2000;

-- -----------------------------------------------------
-- Schema MyFood
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `MyFood` ;

-- -----------------------------------------------------
-- Schema MyFood
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `MyFood` DEFAULT CHARACTER SET utf8 ;
USE `MyFood` ;

-- -----------------------------------------------------
-- Table `MyFood`.`Cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Cliente` (
  `idCliente` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `cognome` VARCHAR(45) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `dataNascita` DATETIME NULL,
  `professione` VARCHAR(45) NULL,
  `residenza` VARCHAR(100) NULL,
  `telefono` CHAR(10) NULL,
  `dataRegistrazione` DATETIME NOT NULL,
  `dataUltimoAccesso` DATETIME NOT NULL,
  `disabilitato` TINYINT NOT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Cucina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Cucina` (
  `idCucina` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `sede` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idCucina`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `sede_UNIQUE` (`sede` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Amministratore`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Amministratore` (
  `idAmministratore` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idAmministratore`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Ordine`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Ordine` (
  `idOrdine` INT NOT NULL AUTO_INCREMENT,
  `data` DATETIME NOT NULL,
  `importo` FLOAT NOT NULL,
  `stato` ENUM('NON_PAGATO', 'IN_LAVORAZIONE', 'PRONTO', 'ANNULLATO') NOT NULL,
  `salvato` TINYINT NOT NULL,
  `idCliente` INT NOT NULL,
  `idCucina` INT NOT NULL,
  PRIMARY KEY (`idOrdine`),
  INDEX `fk_Ordine_Cliente_idx` (`idCliente` ASC) VISIBLE,
  INDEX `fk_Ordine_Cucina1_idx` (`idCucina` ASC) VISIBLE,
  CONSTRAINT `fk_Ordine_Cliente`
    FOREIGN KEY (`idCliente`)
    REFERENCES `MyFood`.`Cliente` (`idCliente`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Ordine_Cucina1`
    FOREIGN KEY (`idCucina`)
    REFERENCES `MyFood`.`Cucina` (`idCucina`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Prodotto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Prodotto` (
  `idProdotto` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`idProdotto`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Ordine_has_Prodotto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Ordine_has_Prodotto` (
  `idOrdine` INT NOT NULL,
  `idProdotto` INT NOT NULL,
  PRIMARY KEY (`idOrdine`, `idProdotto`),
  INDEX `fk_Ordine_has_Prodotto_Prodotto1_idx` (`idProdotto` ASC) VISIBLE,
  INDEX `fk_Ordine_has_Prodotto_Ordine1_idx` (`idOrdine` ASC) VISIBLE,
  CONSTRAINT `fk_Ordine_has_Prodotto_Ordine1`
    FOREIGN KEY (`idOrdine`)
    REFERENCES `MyFood`.`Ordine` (`idOrdine`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Ordine_has_Prodotto_Prodotto1`
    FOREIGN KEY (`idProdotto`)
    REFERENCES `MyFood`.`Prodotto` (`idProdotto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Tipologia_Prodotto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Tipologia_Prodotto` (
  `idTipologiaP` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idTipologiaP`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`P_Singolo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`P_Singolo` (
  `idP_Singolo` INT NOT NULL,
  `prezzo` FLOAT NOT NULL,
  `disponibilita` TINYINT NOT NULL,
  `idTipologiaP` INT NOT NULL,
  PRIMARY KEY (`idP_Singolo`),
  INDEX `fk_P_Singolo_Tipologia_Prodotto1_idx` (`idTipologiaP` ASC) VISIBLE,
  INDEX `fk_P_Singolo_Prodotto1_idx` (`idP_Singolo` ASC) VISIBLE,
  CONSTRAINT `fk_P_Singolo_Tipologia_Prodotto1`
    FOREIGN KEY (`idTipologiaP`)
    REFERENCES `MyFood`.`Tipologia_Prodotto` (`idTipologiaP`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_P_Singolo_Prodotto1`
    FOREIGN KEY (`idP_Singolo`)
    REFERENCES `MyFood`.`Prodotto` (`idProdotto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Menu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Menu` (
  `idMenu` INT NOT NULL,
  `sconto` FLOAT NOT NULL,
  PRIMARY KEY (`idMenu`),
  INDEX `fk_Menu_Prodotto1_idx` (`idMenu` ASC) VISIBLE,
  CONSTRAINT `fk_Menu_Prodotto1`
    FOREIGN KEY (`idMenu`)
    REFERENCES `MyFood`.`Prodotto` (`idProdotto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Immagine`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Immagine` (
  `idImmagine` INT NOT NULL AUTO_INCREMENT,
  `nomeFile` VARCHAR(200) NOT NULL,
  `idProdotto` INT NOT NULL,
  PRIMARY KEY (`idImmagine`),
  INDEX `fk_Immagine_Prodotto1_idx` (`idProdotto` ASC) VISIBLE,
  CONSTRAINT `fk_Immagine_Prodotto1`
    FOREIGN KEY (`idProdotto`)
    REFERENCES `MyFood`.`Prodotto` (`idProdotto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Menu_has_Prodotto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Menu_has_Prodotto` (
  `idMenu` INT NOT NULL,
  `idProdotto` INT NOT NULL,
  PRIMARY KEY (`idMenu`, `idProdotto`),
  INDEX `fk_Menu_has_Prodotto_Prodotto1_idx` (`idProdotto` ASC) VISIBLE,
  INDEX `fk_Menu_has_Prodotto_Menu1_idx` (`idMenu` ASC) VISIBLE,
  CONSTRAINT `fk_Menu_has_Prodotto_Menu1`
    FOREIGN KEY (`idMenu`)
    REFERENCES `MyFood`.`Menu` (`idMenu`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Menu_has_Prodotto_Prodotto1`
    FOREIGN KEY (`idProdotto`)
    REFERENCES `MyFood`.`Prodotto` (`idProdotto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Commento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Commento` (
  `idCommento` INT NOT NULL AUTO_INCREMENT,
  `indiceGradimento` ENUM('UNO', 'DUE', 'TRE', 'QUATTRO', 'CINQUE') NOT NULL,
  `testo` TEXT NULL,
  `risposta` TEXT NULL,
  `idProdotto` INT NOT NULL,
  `idCliente` INT NOT NULL,
  `idAmministratore` INT NULL,
  PRIMARY KEY (`idCommento`),
  INDEX `fk_Commento_Prodotto1_idx` (`idProdotto` ASC) VISIBLE,
  INDEX `fk_Commento_Cliente1_idx` (`idCliente` ASC) VISIBLE,
  INDEX `fk_Commento_Amministratore1_idx` (`idAmministratore` ASC) VISIBLE,
  CONSTRAINT `fk_Commento_Prodotto1`
    FOREIGN KEY (`idProdotto`)
    REFERENCES `MyFood`.`Prodotto` (`idProdotto`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Commento_Cliente1`
    FOREIGN KEY (`idCliente`)
    REFERENCES `MyFood`.`Cliente` (`idCliente`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Commento_Amministratore1`
    FOREIGN KEY (`idAmministratore`)
    REFERENCES `MyFood`.`Amministratore` (`idAmministratore`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Produttore`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Produttore` (
  `idProduttore` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `partitaIVA` CHAR(11) NOT NULL,
  PRIMARY KEY (`idProduttore`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Distributore`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Distributore` (
  `idDistributore` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `partitaIVA` CHAR(11) NOT NULL,
  PRIMARY KEY (`idDistributore`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Tipologia_Ingrediente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Tipologia_Ingrediente` (
  `idTipologiaI` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idTipologiaI`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`Ingrediente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`Ingrediente` (
  `idIngrediente` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `idProduttore` INT NOT NULL,
  `idDistributore` INT NOT NULL,
  `idTipologiaI` INT NOT NULL,
  PRIMARY KEY (`idIngrediente`),
  INDEX `fk_Ingrediente_Produttore1_idx` (`idProduttore` ASC) VISIBLE,
  INDEX `fk_Ingrediente_Distributore1_idx` (`idDistributore` ASC) VISIBLE,
  INDEX `fk_Ingrediente_Tipologia_Ingrediente1_idx` (`idTipologiaI` ASC) VISIBLE,
  CONSTRAINT `fk_Ingrediente_Produttore1`
    FOREIGN KEY (`idProduttore`)
    REFERENCES `MyFood`.`Produttore` (`idProduttore`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Ingrediente_Distributore1`
    FOREIGN KEY (`idDistributore`)
    REFERENCES `MyFood`.`Distributore` (`idDistributore`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Ingrediente_Tipologia_Ingrediente1`
    FOREIGN KEY (`idTipologiaI`)
    REFERENCES `MyFood`.`Tipologia_Ingrediente` (`idTipologiaI`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MyFood`.`P_Singolo_has_Ingrediente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `MyFood`.`P_Singolo_has_Ingrediente` (
  `idProdottoS` INT NOT NULL,
  `idIngrediente` INT NOT NULL,
  PRIMARY KEY (`idProdottoS`, `idIngrediente`),
  INDEX `fk_P_Singolo_has_Ingrediente_Ingrediente1_idx` (`idIngrediente` ASC) VISIBLE,
  INDEX `fk_P_Singolo_has_Ingrediente_P_Singolo1_idx` (`idProdottoS` ASC) VISIBLE,
  CONSTRAINT `fk_P_Singolo_has_Ingrediente_P_Singolo1`
    FOREIGN KEY (`idProdottoS`)
    REFERENCES `MyFood`.`P_Singolo` (`idP_Singolo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_P_Singolo_has_Ingrediente_Ingrediente1`
    FOREIGN KEY (`idIngrediente`)
    REFERENCES `MyFood`.`Ingrediente` (`idIngrediente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `MyFood`.`Cliente`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Cliente` (`idCliente`, `nome`, `cognome`, `email`, `password`, `dataNascita`, `professione`, `residenza`, `telefono`, `dataRegistrazione`, `dataUltimoAccesso`, `disabilitato`) VALUES (1, 'Paolo', 'Rossi', 'paolo.rossi@gmail.com', '12345', '1985-07-12', 'carpentiere', 'Galatina (LE)', '3895561124', '2023-04-05', '2023-12-07', 0);
INSERT INTO `MyFood`.`Cliente` (`idCliente`, `nome`, `cognome`, `email`, `password`, `dataNascita`, `professione`, `residenza`, `telefono`, `dataRegistrazione`, `dataUltimoAccesso`, `disabilitato`) VALUES (2, 'Alessia', 'Bianchi', 'alessia.bianchi@gmail.com', '67890', '1997-02-28', 'studente', 'Leuca (LE)', '3562289477', '2023-06-08', '2023-12-04', 0);

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Cucina`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Cucina` (`idCucina`, `email`, `password`, `sede`) VALUES (1, 'cucinaLecce', 'cucinaLecce', 'Lecce');
INSERT INTO `MyFood`.`Cucina` (`idCucina`, `email`, `password`, `sede`) VALUES (2, 'cucinaBrindisi', 'cucinaBrindisi', 'Brindisi');

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Amministratore`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Amministratore` (`idAmministratore`, `email`, `password`) VALUES (1, 'admin', 'admin');

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Ordine`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Ordine` (`idOrdine`, `data`, `importo`, `stato`, `salvato`, `idCliente`, `idCucina`) VALUES (1, '2023-12-06', 7.50, 'PRONTO', 0, 1, 1);
INSERT INTO `MyFood`.`Ordine` (`idOrdine`, `data`, `importo`, `stato`, `salvato`, `idCliente`, `idCucina`) VALUES (2, '2023-12-07', 8.80, 'IN_LAVORAZIONE', 0, 2, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Prodotto`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (1, 'My Burger');
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (2, 'My Chicken Burger');
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (3, 'My Fish Burger');
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (4, 'My Salad');
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (5, 'Bevanda Piccola');
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (6, 'Bevanda Media');
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (7, 'Bevanda Grande');
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (8, 'My Patatine Piccola');
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (9, 'My Patatine Media');
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (10, 'My Patatine Grande');
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (11, 'My Burger Menu');
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (12, 'My Salad Menu');
INSERT INTO `MyFood`.`Prodotto` (`idProdotto`, `nome`) VALUES (13, 'My Burger Super Menu');

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Ordine_has_Prodotto`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Ordine_has_Prodotto` (`idOrdine`, `idProdotto`) VALUES (1, 1);
INSERT INTO `MyFood`.`Ordine_has_Prodotto` (`idOrdine`, `idProdotto`) VALUES (1, 6);
INSERT INTO `MyFood`.`Ordine_has_Prodotto` (`idOrdine`, `idProdotto`) VALUES (2, 11);

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Tipologia_Prodotto`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Tipologia_Prodotto` (`idTipologiaP`, `nome`) VALUES (1, 'panino');
INSERT INTO `MyFood`.`Tipologia_Prodotto` (`idTipologiaP`, `nome`) VALUES (2, 'insalata');
INSERT INTO `MyFood`.`Tipologia_Prodotto` (`idTipologiaP`, `nome`) VALUES (3, 'sfiziosità');
INSERT INTO `MyFood`.`Tipologia_Prodotto` (`idTipologiaP`, `nome`) VALUES (4, 'bevanda');
INSERT INTO `MyFood`.`Tipologia_Prodotto` (`idTipologiaP`, `nome`) VALUES (5, 'salsa');

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`P_Singolo`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`P_Singolo` (`idP_Singolo`, `prezzo`, `disponibilita`, `idTipologiaP`) VALUES (1, 3.50, 1, 1);
INSERT INTO `MyFood`.`P_Singolo` (`idP_Singolo`, `prezzo`, `disponibilita`, `idTipologiaP`) VALUES (2, 3.50, 1, 1);
INSERT INTO `MyFood`.`P_Singolo` (`idP_Singolo`, `prezzo`, `disponibilita`, `idTipologiaP`) VALUES (3, 4.00, 1, 1);
INSERT INTO `MyFood`.`P_Singolo` (`idP_Singolo`, `prezzo`, `disponibilita`, `idTipologiaP`) VALUES (4, 3.00, 1, 2);
INSERT INTO `MyFood`.`P_Singolo` (`idP_Singolo`, `prezzo`, `disponibilita`, `idTipologiaP`) VALUES (5, 2.50, 1, 4);
INSERT INTO `MyFood`.`P_Singolo` (`idP_Singolo`, `prezzo`, `disponibilita`, `idTipologiaP`) VALUES (6, 4.00, 1, 4);
INSERT INTO `MyFood`.`P_Singolo` (`idP_Singolo`, `prezzo`, `disponibilita`, `idTipologiaP`) VALUES (7, 5.00, 1, 4);
INSERT INTO `MyFood`.`P_Singolo` (`idP_Singolo`, `prezzo`, `disponibilita`, `idTipologiaP`) VALUES (8, 2.50, 1, 3);
INSERT INTO `MyFood`.`P_Singolo` (`idP_Singolo`, `prezzo`, `disponibilita`, `idTipologiaP`) VALUES (9, 3.50, 1, 3);
INSERT INTO `MyFood`.`P_Singolo` (`idP_Singolo`, `prezzo`, `disponibilita`, `idTipologiaP`) VALUES (10, 5.00, 1, 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Menu`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Menu` (`idMenu`, `sconto`) VALUES (11, 0.20);
INSERT INTO `MyFood`.`Menu` (`idMenu`, `sconto`) VALUES (12, 0.20);
INSERT INTO `MyFood`.`Menu` (`idMenu`, `sconto`) VALUES (13, 0.35);

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Immagine`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (1, 'id1_1.jpg', 1);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (2, 'id2_1.jpg', 2);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (3, 'id3_1.png', 3);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (4, 'id4_1.jpg', 4);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (5, 'id5_1.png', 5);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (6, 'id6_1.png', 6);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (7, 'id7_1.png', 7);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (8, 'id8_1.jpg', 8);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (9, 'id9_1.jpg', 9);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (10, 'id10_1.jpg', 10);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (11, 'id11_1.jpg', 11);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (12, 'id11_2.png', 11);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (13, 'id11_3.jpg', 11);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (14, 'id12_1.jpg', 12);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (15, 'id12_2.png', 12);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (16, 'id12_3.jpg', 12);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (17, 'id13_1.jpg', 13);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (18, 'id13_2.png', 13);
INSERT INTO `MyFood`.`Immagine` (`idImmagine`, `nomeFile`, `idProdotto`) VALUES (19, 'id13_3.jpg', 13);

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Menu_has_Prodotto`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Menu_has_Prodotto` (`idMenu`, `idProdotto`) VALUES (11, 1);
INSERT INTO `MyFood`.`Menu_has_Prodotto` (`idMenu`, `idProdotto`) VALUES (11, 6);
INSERT INTO `MyFood`.`Menu_has_Prodotto` (`idMenu`, `idProdotto`) VALUES (11, 9);
INSERT INTO `MyFood`.`Menu_has_Prodotto` (`idMenu`, `idProdotto`) VALUES (12, 4);
INSERT INTO `MyFood`.`Menu_has_Prodotto` (`idMenu`, `idProdotto`) VALUES (12, 6);
INSERT INTO `MyFood`.`Menu_has_Prodotto` (`idMenu`, `idProdotto`) VALUES (12, 9);
INSERT INTO `MyFood`.`Menu_has_Prodotto` (`idMenu`, `idProdotto`) VALUES (13, 11);
INSERT INTO `MyFood`.`Menu_has_Prodotto` (`idMenu`, `idProdotto`) VALUES (13, 1);
INSERT INTO `MyFood`.`Menu_has_Prodotto` (`idMenu`, `idProdotto`) VALUES (13, 9);

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Commento`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Commento` (`idCommento`, `indiceGradimento`, `testo`, `risposta`, `idProdotto`, `idCliente`, `idAmministratore`) VALUES (1, 'QUATTRO', 'molto buono', 'grazie mille!', 1, 1, 1);
INSERT INTO `MyFood`.`Commento` (`idCommento`, `indiceGradimento`, `testo`, `risposta`, `idProdotto`, `idCliente`, `idAmministratore`) VALUES (2, 'CINQUE', 'squisito', 'grazie!', 11, 2, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Produttore`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Produttore` (`idProduttore`, `nome`, `partitaIVA`) VALUES (1, 'Panificio Toma', '52115290158');
INSERT INTO `MyFood`.`Produttore` (`idProduttore`, `nome`, `partitaIVA`) VALUES (2, 'Macelleria Primiceri', '23759730700');
INSERT INTO `MyFood`.`Produttore` (`idProduttore`, `nome`, `partitaIVA`) VALUES (3, 'Che bevanda srl', '42242360701');
INSERT INTO `MyFood`.`Produttore` (`idProduttore`, `nome`, `partitaIVA`) VALUES (4, 'Gelasur surgelati srl', '23104640521');
INSERT INTO `MyFood`.`Produttore` (`idProduttore`, `nome`, `partitaIVA`) VALUES (5, 'Ortolano De Marco', '56203075723');
INSERT INTO `MyFood`.`Produttore` (`idProduttore`, `nome`, `partitaIVA`) VALUES (6, 'Mai senza salsa srl', '30427683152');

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Distributore`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Distributore` (`idDistributore`, `nome`, `partitaIVA`) VALUES (1, 'Burger food', '67109150150');
INSERT INTO `MyFood`.`Distributore` (`idDistributore`, `nome`, `partitaIVA`) VALUES (2, 'Ice distribution', '27008861523');

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Tipologia_Ingrediente`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Tipologia_Ingrediente` (`idTipologiaI`, `nome`) VALUES (1, 'carne');
INSERT INTO `MyFood`.`Tipologia_Ingrediente` (`idTipologiaI`, `nome`) VALUES (2, 'pesce');
INSERT INTO `MyFood`.`Tipologia_Ingrediente` (`idTipologiaI`, `nome`) VALUES (3, 'vegan');

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`Ingrediente`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`Ingrediente` (`idIngrediente`, `nome`, `idProduttore`, `idDistributore`, `idTipologiaI`) VALUES (1, 'pane', 1, 1, 3);
INSERT INTO `MyFood`.`Ingrediente` (`idIngrediente`, `nome`, `idProduttore`, `idDistributore`, `idTipologiaI`) VALUES (2, 'hamburger di manzo', 2, 1, 1);
INSERT INTO `MyFood`.`Ingrediente` (`idIngrediente`, `nome`, `idProduttore`, `idDistributore`, `idTipologiaI`) VALUES (3, 'hamburger di pollo', 2, 1, 1);
INSERT INTO `MyFood`.`Ingrediente` (`idIngrediente`, `nome`, `idProduttore`, `idDistributore`, `idTipologiaI`) VALUES (4, 'patatine', 4, 2, 3);
INSERT INTO `MyFood`.`Ingrediente` (`idIngrediente`, `nome`, `idProduttore`, `idDistributore`, `idTipologiaI`) VALUES (5, 'lattuga', 5, 1, 3);
INSERT INTO `MyFood`.`Ingrediente` (`idIngrediente`, `nome`, `idProduttore`, `idDistributore`, `idTipologiaI`) VALUES (6, 'ketchup', 6, 1, 3);
INSERT INTO `MyFood`.`Ingrediente` (`idIngrediente`, `nome`, `idProduttore`, `idDistributore`, `idTipologiaI`) VALUES (7, 'hamburger di tonno', 1, 1, 2);
INSERT INTO `MyFood`.`Ingrediente` (`idIngrediente`, `nome`, `idProduttore`, `idDistributore`, `idTipologiaI`) VALUES (8, 'maionese', 6, 1, 3);
INSERT INTO `MyFood`.`Ingrediente` (`idIngrediente`, `nome`, `idProduttore`, `idDistributore`, `idTipologiaI`) VALUES (9, 'coca cola', 3, 2, 3);
INSERT INTO `MyFood`.`Ingrediente` (`idIngrediente`, `nome`, `idProduttore`, `idDistributore`, `idTipologiaI`) VALUES (10, 'aranciata', 3, 2, 3);
INSERT INTO `MyFood`.`Ingrediente` (`idIngrediente`, `nome`, `idProduttore`, `idDistributore`, `idTipologiaI`) VALUES (11, 'pomodoro', 5, 1, 3);
INSERT INTO `MyFood`.`Ingrediente` (`idIngrediente`, `nome`, `idProduttore`, `idDistributore`, `idTipologiaI`) VALUES (12, 'olive', 5, 1, 3);

COMMIT;


-- -----------------------------------------------------
-- Data for table `MyFood`.`P_Singolo_has_Ingrediente`
-- -----------------------------------------------------
START TRANSACTION;
USE `MyFood`;
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (1, 1);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (1, 2);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (1, 4);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (1, 5);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (1, 6);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (2, 1);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (2, 3);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (2, 4);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (2, 5);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (2, 6);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (3, 1);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (3, 4);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (3, 5);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (3, 7);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (3, 8);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (5, 9);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (5, 10);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (6, 9);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (6, 10);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (7, 9);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (7, 10);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (8, 4);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (9, 4);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (10, 4);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (4, 11);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (4, 12);
INSERT INTO `MyFood`.`P_Singolo_has_Ingrediente` (`idProdottoS`, `idIngrediente`) VALUES (4, 5);

COMMIT;

