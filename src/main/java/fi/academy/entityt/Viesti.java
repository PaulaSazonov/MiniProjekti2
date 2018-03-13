
package fi.academy.entityt;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Viesti {
    @Id
    @GeneratedValue
    private int id;
    private LocalDate aikaleima;

    private String kirjoittaja;
    private String teksti;

    @ManyToOne  // viesti voi kuulua vain yhteen keskusteluun
    @JoinColumn
    private Keskustelu keskusteluJohonViestiKuuluu;

    public Viesti() {
    }

    public Viesti(String kirjoittaja, String teksti, Keskustelu keskusteluJohonViestiKuuluu) {
        this.aikaleima = LocalDate.now();
        this.kirjoittaja = kirjoittaja;
        this.teksti = teksti;
        this.keskusteluJohonViestiKuuluu = keskusteluJohonViestiKuuluu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getAikaleima() {
        return aikaleima;
    }

    public void setAikaleima(LocalDate aikaleima) {
        this.aikaleima = aikaleima;
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

    public Keskustelu getKeskusteluJohonViestiKuuluu() {
        return keskusteluJohonViestiKuuluu;
    }

    public void setKeskusteluJohonViestiKuuluu(Keskustelu keskusteluJohonViestiKuuluu) {
        this.keskusteluJohonViestiKuuluu = keskusteluJohonViestiKuuluu;
    }
}

