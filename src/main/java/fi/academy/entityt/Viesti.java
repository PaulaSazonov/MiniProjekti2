
package fi.academy.entityt;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Viesti {
    @Id
    @GeneratedValue
    private int id;
    private LocalDateTime aikaleima;
    private String kirjoittaja;
    private String teksti;
    @ManyToOne
    @JoinColumn
    private Keskustelu keskusteluJohonViestiKuuluu;

    public Viesti() {
    }

    public Viesti(String kirjoittaja, String teksti, Keskustelu keskusteluJohonViestiKuuluu) {
        this.aikaleima = LocalDateTime.now();
        this.kirjoittaja = kirjoittaja;
        this.teksti = teksti;
        this.keskusteluJohonViestiKuuluu = keskusteluJohonViestiKuuluu;
    }

    public Viesti(String kirjoittaja, String teksti) {
        this.aikaleima = LocalDateTime.now();
        this.kirjoittaja = kirjoittaja;
        this.teksti = teksti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getAikaleima() {
        return aikaleima;
    }

    public void setAikaleima(LocalDateTime aikaleima) {
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

    @Override
    public String toString() {
        return kirjoittaja + ", " + aikaleima + "\n" + teksti;
    }
}

