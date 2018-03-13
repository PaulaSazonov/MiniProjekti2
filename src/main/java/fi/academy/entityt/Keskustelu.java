package fi.academy.entityt;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Keskustelu {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne (cascade=CascadeType.ALL) // keskustelu voi kuulua vain yhteen aihealueeseen
    @JoinColumn
    private Aihe aihealueJohonKuuluu;

    @OneToMany(mappedBy = "keskusteluJohonViestiKuuluu")
    // fetch = FetchType.EAGER) > määritetään metodeissa, että ei tule turhia hakuja
    private List<Viesti> listaViesteista;

    private LocalDate aikaleima;
    private String keskustelunotsikko;
    private String kirjoittaja;
    private String teksti;

    public Keskustelu() {
    }

    public Keskustelu(Aihe aihealueJohonKuuluu, String keskustelunotsikko, String kirjoittaja, String teksti) {
        this.aihealueJohonKuuluu = aihealueJohonKuuluu;
        this.listaViesteista = new ArrayList<>();

        this.aikaleima = LocalDate.now();
        this.keskustelunotsikko = keskustelunotsikko;
        this.kirjoittaja = kirjoittaja;
        this.teksti = teksti;
    }

    public void lisaaViestiListaan(Viesti viesti) {
        listaViesteista.add(viesti);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Aihe getAihealueJohonKuuluu() {
        return aihealueJohonKuuluu;
    }

    public void setAihealueJohonKuuluu(Aihe aihealueJohonKuuluu) {
        this.aihealueJohonKuuluu = aihealueJohonKuuluu;
    }

    public List<Viesti> getListaViesteista() {
        return listaViesteista;
    }

    public void setListaViesteista(List<Viesti> listaViesteista) {
        this.listaViesteista = listaViesteista;
    }

    public LocalDate getAikaleima() {
        return aikaleima;
    }

    public void setAikaleima(LocalDate aikaleima) {
        this.aikaleima = aikaleima;
    }

    public String getKeskustelunotsikko() {
        return keskustelunotsikko;
    }

    public void setKeskustelunotsikko(String keskustelunotsikko) {
        this.keskustelunotsikko = keskustelunotsikko;
    }

    public String getKirjoittaja() {
        return kirjoittaja;
    }

    public void setKirjoittaja(String kirjoittaja) {
        this.kirjoittaja = kirjoittaja;
    }

    public String getTeksti() {
        return teksti;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti;
    }
}
