package Oving_JPA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ansatt", schema = "oving_jpa")
public class Ansatt {

    @Id
    @Column(name = "ansattid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private int ansattId; 
    
    @Column(length = 4, nullable = false, unique = true)
    private String brukernavn;
    
    @Column(name = "fornavn")
    private String fornavn;
    
    @Column(name = "etternavn")
    private String etternavn;
    
    @Column(name = "ansettelsesdato")
    private LocalDate ansettelsesdato;
    
    @Column(name="stilling")
    private String stilling;
    
    @Column(name = "maanedslonn")
    private int maanedslonn;
    
    @ManyToOne
    @JoinColumn(name = "avdelingansatt", referencedColumnName = "avdelingid")
    private Avdeling avdeling;
    
    @OneToMany(mappedBy = "ansatt", cascade = CascadeType.ALL)
    private List<Prosjekt_Ansatt> prosjektAnsattListe = new ArrayList<>();
    
    public Avdeling getAvdeling() {
		return avdeling;
	}


	public void setAvdeling(Avdeling avdeling) {
		this.avdeling = avdeling;
	}


	public List<Prosjekt_Ansatt> getProsjektAnsattListe() {
		return prosjektAnsattListe;
	}


	public void setProsjektAnsattListe(List<Prosjekt_Ansatt> prosjektAnsattListe) {
		this.prosjektAnsattListe = prosjektAnsattListe;
	}


	public void setAnsattId(int ansattId) {
		this.ansattId = ansattId;
	}


	public Ansatt() {}

 
    public Ansatt(String brukernavn, String fornavn, String etternavn, LocalDate ansettelsesdato, String stilling, int maanedslonn) {
        this.brukernavn = brukernavn;
        this.fornavn = fornavn;
        this.etternavn = etternavn;
        this.ansettelsesdato = ansettelsesdato;
        this.stilling = stilling;
        this.maanedslonn = maanedslonn;
    }
    
    public Integer getAnsattId() {
    	return ansattId;
    }
    
    public void setAnsattId(Integer ansattId) {
    	this.ansattId = ansattId;
    }

    public String getBrukernavn() {
        return brukernavn;
    }

    public void setBrukernavn(String brukernavn) {
        this.brukernavn = brukernavn;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEtternavn() {
        return etternavn;
    }

    public void setEtternavn(String etternavn) {
        this.etternavn = etternavn;
    }

    public String getStilling() {
        return stilling;
    }

    public void setStilling(String stilling) {
        this.stilling = stilling;
    }

    public LocalDate getAnsettelsesdato() {
        return ansettelsesdato;
    }

    public void setAnsettelsesdato(LocalDate ansettelsesdato) {
        this.ansettelsesdato = ansettelsesdato;
    }

    public int getMaanedslonn() {
        return maanedslonn;
    }

    public void setMaanedslonn(int maanedslonn) {
        this.maanedslonn = maanedslonn;
    }

    public Avdeling getAvdelingAnsatt() {
    	return avdeling;
    }
    
    public void setAvdelingAnsatt(Avdeling avdelingansatt) {
    	this.avdeling = avdelingansatt;
    }

	
	 @Override
	    public String toString() {
	    	 return String.format(fornavn + " " + etternavn); 
	 }
}	