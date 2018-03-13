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


            Aihe aihe1 = new Aihe("Kissan hoito");
            Keskustelu keskustelu1 = new Keskustelu(aihe1, "Pitkäkarvaisen turkin hoito", "KissojenKaveri", "Kuinka usein harjaatte pitkäkarvaisten kissojenne turkkeja ja millaisia harjoja teillä on ollut käytössä? ");
            Viesti viesti1 = new Viesti("PersialaisetRules", "Meillä on ollut käytössä Furminator-harja, voin suositella. Pyrimme harjaamaan turkkia ainakin kerran kuussa, keväällä karvanlähtöaikaan useammin", keskustelu1);
            keskustelu1.lisaaViestiListaan(viesti1);

            Aihe aihe2 = new Aihe("Kissan tarvikkeet ja varusteet");
            Keskustelu keskustelu2 = new Keskustelu(aihe2, "Paras kissanvessa", "GrumpyCat", "Mikä on paras kissanvessa? Onko avoin vai katettu hyvä?");
            Viesti viesti2 = new Viesti("Siamilainen", "Katettu, ehdottomasti. Pysyy hiekat ja hajut paremmin kurissa.", keskustelu2);
            keskustelu2.lisaaViestiListaan(viesti2);


            viestiRepo.saveAll(keskustelu1.getListaViesteista());
            viestiRepo.saveAll(keskustelu2.getListaViesteista());

        };
    }


    public static void main(String[] args) {
        SpringApplication.run(MiniProjekti2Application.class, args);
    }
}
