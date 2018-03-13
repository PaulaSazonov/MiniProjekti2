package fi.academy;

import fi.academy.entityt.Aihe;
import fi.academy.entityt.Keskustelu;
import fi.academy.entityt.Viesti;
import fi.academy.repositoryt.ViestiRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MiniProjekti2Application {


    @Bean
    CommandLineRunner alusta(ViestiRepo viestiRepo) {
        return (args) -> {


            Aihe aihe = new Aihe("Kissa-aiheiset keskustelut");
            Keskustelu keskustelu = new Keskustelu(aihe, "Keskustelun otsikko", "Keskustelun aloittaja", "Keskustelun aloittajan viesti plaa plaa plaa");

            Viesti viesti1 = new Viesti("Kirjoittaja", "Viestiteksti plaa plaa plaa", keskustelu);
            Viesti viesti2 = new Viesti("Kalle", "Tämä on kolmas teksti", keskustelu);

            keskustelu.lisaaViestiListaan(viesti1);
            keskustelu.lisaaViestiListaan(viesti2);


            viestiRepo.saveAll(keskustelu.getListaViesteista());
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(MiniProjekti2Application.class, args);
    }
}
