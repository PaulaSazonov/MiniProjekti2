package fi.academy.entityt;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Kayttaja {
    @Id
    private String tunnus;
    private String sahkoposti;
    private String salasana;
    @OneToMany
    private Viesti viesti;
    private String rooli;

    public Kayttaja() {
    }

    public Kayttaja(String tunnus, String sahkoposti, String salasana) {
        this.tunnus = tunnus;
        this.sahkoposti = sahkoposti;
        this.salasana = salasana;

    }

    public Kayttaja(String tunnus, String sahkoposti, String salasana, String rooli) {
        this.tunnus = tunnus;
        this.sahkoposti = sahkoposti;
        this.salasana = salasana;
        //this.viesti = viesti;
        this.rooli = rooli;
    }

    public String getTunnus() {
        return tunnus;
    }

    public void setTunnus(String tunnus) {
        this.tunnus = tunnus;
    }

    public String getSahkoposti() {
        return sahkoposti;
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = sahkoposti;
    }

    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public Viesti getViesti() {
        return viesti;
    }

    public void setViesti(Viesti viesti) {
        this.viesti = viesti;
    }

    public String getRooli() {
        return rooli;
    }

    public void setRooli(String rooli) {
        this.rooli = rooli;
    }
}
