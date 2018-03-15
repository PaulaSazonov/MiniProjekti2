package fi.academy.entityt;

import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Keskustelu {

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne // keskustelu voi kuulua vain yhteen aihealueeseen
    @JoinColumn
    private Aihe aihealueJohonKuuluu;
    private String keskustelunotsikko;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Viesti aloitusviesti;
    @OneToMany(mappedBy = "keskusteluJohonViestiKuuluu", cascade = CascadeType.ALL)
    // fetch = FetchType.EAGER) > määritetään metodeissa, että ei tule turhia hakuja
    private List<Viesti> kommentit;


    public Keskustelu() {
    }

    public Keskustelu(Aihe aihealueJohonKuuluu, String keskustelunotsikko) {
        this.aihealueJohonKuuluu = aihealueJohonKuuluu;
        this.keskustelunotsikko = keskustelunotsikko;
        this.kommentit = new ArrayList<>();
//        this.kommentit.add(aloitusviesti);

    }


//    public Keskustelu(Aihe aihealueJohonKuuluu, String keskustelunotsikko, Viesti aloitusviesti) {
//        this.aihealueJohonKuuluu = aihealueJohonKuuluu;
//        this.keskustelunotsikko = keskustelunotsikko;
//        this.aloitusviesti = aloitusviesti;
//        this.kommentit = new ArrayList<>();
////        this.kommentit.add(aloitusviesti);
//
//    }

    public void lisaaViestiListaan(Viesti viesti) {
        kommentit.add(viesti);
    }

    @Override
    public String toString() {
        return aihealueJohonKuuluu.getAiheenNimi() + ", " + keskustelunotsikko + "\n" + aloitusviesti + "\n" + kommentit;
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

    public String getKeskustelunotsikko() {
        return keskustelunotsikko;
    }

    public void setKeskustelunotsikko(String keskustelunotsikko) {
        this.keskustelunotsikko = keskustelunotsikko;
    }

    public Viesti getAloitusviesti() {
        return aloitusviesti;
    }

    public void setAloitusviesti(Viesti aloitusviesti) {
        this.aloitusviesti = aloitusviesti;
    }

    public List<Viesti> getKommentit() {
        return kommentit;
    }

    public void setKommentit(List<Viesti> kommentit) {
        this.kommentit = kommentit;
    }

    public String muotoiltuaikaleima(LocalDateTime aikaleima) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm.ss, dd.MM.yyyy");
        String aika = aikaleima.format(formatter);
        return aika;
    }


}
