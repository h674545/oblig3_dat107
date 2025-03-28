package Oving_JPA;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Table(name = "Avdeling", schema = "oving_jpa")
@Entity
public class Avdeling {
	
	@Id
	@Column(name="avdelingID", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int avdelingID;
	
	@Column(name="avdelingNavn")
	private String avdelingNavn;
	
	@ManyToOne
	@JoinColumn(name = "ansattSjef", referencedColumnName = "ansattid")
	private Ansatt ansattSjef; // ansattid
	
	@OneToMany(mappedBy = "avdeling")  
	private List<Ansatt> ansatte;
	
	public Avdeling() {
		
	}
	
	public Avdeling(int avdelingID, String avdelingNavn, Ansatt ansattSjef) {
		this.setAvdelingNavn(avdelingNavn);
		this.setAnsattSjef(ansattSjef);
	}

	public String getAvdelingNavn() {
		return avdelingNavn;
	}

	public void setAvdelingNavn(String avdelingNavn) {
		this.avdelingNavn = avdelingNavn;
	}

	public Ansatt getAnsattSjef() {
		return ansattSjef;
	}

	public void setAnsattSjef(Ansatt ansattSjef) {
		this.ansattSjef = ansattSjef;
	}
	
	@Override
    public String toString() {
        return "Avdeling{" +
                "avdelingID=" + avdelingID +
                ", avdelingNavn='" + avdelingNavn + '\'' +
                ", ansattSjef=" + ansattSjef +
                '}';
    }
}
