
CREATE SCHEMA oving_jpa;
SET search_path TO oving_jpa;

CREATE TABLE ansatt
(
	brukernavn CHAR(4),
	fornavn VARCHAR(50),
	etternavn VARCHAR(50),
	ansettelsesdato DATE,
	stilling VARCHAR(50),
	maanedslonn INTEGER,
	ansattid INTEGER PRIMARY KEY,
	avdelingansatt CHAR (50),
	CONSTRAINT avdelingansattFN FOREIGN KEY (avdelingansatt) REFERENCES avdeling (avdelingNavn)
)

CREATE TABLE avdeling 
(
	avdelingID INTEGER PRIMARY KEY,
	avdelingNavn CHAR(50) UNIQUE,
	ansattSjef VARCHAR(50),
	CONSTRAINT sjefIdFN FOREIGN KEY (ansattSjef) REFERENCES ansatt (ansattid)
)

CREATE TABLE prosjekt
(
	prosjektID INTEGER PRIMARY KEY,
	prosjektNavn VARCHAR(50) UNIQUE,
	prosjektBeskrivelse VARCHAR(50)
)
CREATE TABLE Prosjekt_Ansatt (
    prosjektid INTEGER,
    ansattid INTEGER,
    prosjektrolle VARCHAR(50),  
    prosjekttimer INTEGER,       
    PRIMARY KEY (prosjektid, ansattid),  
    FOREIGN KEY prosjektid REFERENCES Prosjekt(prosjektid),
    FOREIGN KEY ansattid REFERENCES Ansatt(ansattid)
);