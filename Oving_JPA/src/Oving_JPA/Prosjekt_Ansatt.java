package Oving_JPA;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "prosjekt_ansatt", schema = "oving_jpa")
public class Prosjekt_Ansatt {
	
	@EmbeddedId
	private ProsjektAnsattId id;
	
	@ManyToOne
	@MapsId("prosjektid") 
    @JoinColumn(name = "prosjektid", referencedColumnName = "prosjektid")
    private Prosjekt prosjekt;
	
	@ManyToOne
	@MapsId("ansattid")
    @JoinColumn(name = "ansattid", referencedColumnName = "ansattid")
    private Ansatt ansatt;
	
	@Column(name = "prosjektrolle")
	private String prosjektrolle;
	
	@Column(name = "prosjekttimer")
	private int prosjekttimer;
	
	public Prosjekt_Ansatt() {
		
	}
	
	public Prosjekt_Ansatt(Prosjekt prosjekt, Ansatt ansatt, String prosjektrolle, int prosjekttimer) {
		this.id = new ProsjektAnsattId(prosjekt.getProsjektid(), ansatt.getAnsattId());
		this.prosjektrolle = prosjektrolle;
		this.prosjekttimer = prosjekttimer;
	}

	public ProsjektAnsattId getId() {
		return id;
	}

	public void setId(ProsjektAnsattId id) {
		this.id = id;
	}

	public Prosjekt getProsjekt() {
		return prosjekt;
	}

	public void setProsjekt(Prosjekt prosjekt) {
		this.prosjekt = prosjekt;
	}

	public Ansatt getAnsatt() {
		return ansatt;
	}

	public void setAnsatt(Ansatt ansatt) {
		this.ansatt = ansatt;
	}

	public String getProsjektrolle() {
		return prosjektrolle;
	}

	public void setProsjektrolle(String prosjektrolle) {
		this.prosjektrolle = prosjektrolle;
	}

	public int getProsjekttimer() {
		return prosjekttimer;
	}

	public void setProsjekttimer(int prosjekttimer) {
		this.prosjekttimer = prosjekttimer;
	}

	@Override
	public String toString() {
		return "Prosjekt_Ansatt{" +
				"prosjekt=" + prosjekt.getProsjektnavn() +
				", ansatt=" + ansatt.getFornavn() +
				", rolle='" + prosjektrolle + '\'' +
				", timer=" + prosjekttimer +
				'}';
	}
}

