package Oving_JPA;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "prosjekt", schema = "oving_jpa")
public class Prosjekt {
	
	@Id
	@Column(name = "prosjektid")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int prosjektid;
	
	@Column(name = "prosjektnavn")
	private String prosjektnavn;
	
	@Column(name = "prosjektbeskrivelse")
	private String prosjektbeskrivelse;
	
    @OneToMany(mappedBy = "prosjekt", cascade = CascadeType.ALL)
    private List<Prosjekt_Ansatt> prosjektAnsattListe = new ArrayList<>();
	
	public List<Prosjekt_Ansatt> getProsjektAnsattListe() {
		return prosjektAnsattListe;
	}

	public void setProsjektAnsattListe(List<Prosjekt_Ansatt> prosjektAnsattListe) {
		this.prosjektAnsattListe = prosjektAnsattListe;
	}

	public Prosjekt() {
		
	}
	
	public Prosjekt(String prosjektnavn, String prosjektbeskrivelse) {
		this.prosjektnavn = prosjektnavn;
		this.prosjektbeskrivelse = prosjektbeskrivelse;
	}

	public int getProsjektid() {
		return prosjektid;
	}

	public void setProsjektid(int prosjektid) {
		this.prosjektid = prosjektid;
	}

	public String getProsjektnavn() {
		return prosjektnavn;
	}

	public void setProsjektnavn(String prosjektnavn) {
		this.prosjektnavn = prosjektnavn;
	}

	public String getProsjektbeskrivelse() {
		return prosjektbeskrivelse;
	}

	public void setProsjektbeskrivelse(String prosjektbeskrivelse) {
		this.prosjektbeskrivelse = prosjektbeskrivelse;
	}

	@Override 
	public String toString() {
		return String.format(prosjektnavn + " " + prosjektbeskrivelse);
	}
}
