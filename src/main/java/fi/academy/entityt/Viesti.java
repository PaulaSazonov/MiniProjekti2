
package fi.academy.entityt;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Viesti {
    @Id
    private int id;
    private Kayttaja kirjoittaja;
    private String otsikko;
    private String teksti;
    private LocalDate aikaleima;
    private Keskustelu keskusteluJohonViestiKuuluu;



}

