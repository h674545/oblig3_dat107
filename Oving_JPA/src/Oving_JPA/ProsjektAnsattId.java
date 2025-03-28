package Oving_JPA;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProsjektAnsattId implements Serializable {
	private static final long serialVersionUID = 1L;
	private int ansattid;
    private int prosjektid;

    public ProsjektAnsattId() {}

    public ProsjektAnsattId(int ansatt, int prosjekt) {
        this.ansattid = ansatt;
        this.prosjektid = prosjekt;
    }

    public int getAnsattid() { return ansattid; }
    public void setAnsattid(int ansatt) { this.ansattid = ansatt; }

    public int getProsjektid() { return prosjektid; }
    public void setProsjektid(int prosjekt) { this.prosjektid = prosjekt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProsjektAnsattId that = (ProsjektAnsattId) o;
        return ansattid == that.ansattid && prosjektid == that.prosjektid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ansattid, prosjektid);
    }

    @Override
    public String toString() {
        return "ProsjektAnsattId{" +
                "ansattid=" + ansattid +
                ", prosjektid=" + prosjektid +
                '}';
    }
}

