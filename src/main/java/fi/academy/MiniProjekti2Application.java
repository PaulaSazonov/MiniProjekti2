package fi.academy;

import fi.academy.entityt.Aihe;
import fi.academy.entityt.Keskustelu;
import fi.academy.entityt.Viesti;
import fi.academy.repositoryt.AiheRepo;
import fi.academy.repositoryt.KeskusteluRepo;
import fi.academy.repositoryt.ViestiRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MiniProjekti2Application {

    public static void main(String[] args) {
        SpringApplication.run(MiniProjekti2Application.class, args);
    }

    @Bean
    CommandLineRunner alusta(ViestiRepo viestiRepo, KeskusteluRepo keskusteluRepo, AiheRepo aiheRepo) {
        return (args) -> {

            // KISSA-AIHEALUE
            Aihe aihe = new Aihe("Kissa");
            aiheRepo.save(aihe); // Koska CASCADE käytössä, ei tarvitse tallentaa viestejä ja keskusteluita erikseen repoon, vaan menevät aiheen tallennuksella

            // 1. KISSA-KESKUSTELU
            Keskustelu keskustelu = new Keskustelu(aihe, "Pitkäkarvaisen turkin hoito", "KissojenKaveri", "Kuinka usein harjaatte pitkäkarvaisten kissojenne turkkeja ja millaisia harjoja teillä on ollut käytössä? ");
            aihe.lisaaKeskusteluAiheenListaan(keskustelu);

            Viesti viesti1 = new Viesti("PersialaisetRules", "Meillä on ollut käytössä Furminator-harja, voin suositella. Pyrimme harjaamaan turkkia ainakin kerran kuussa, keväällä karvanlähtöaikaan useammin", keskustelu);
            Viesti viesti2 = new Viesti("Toinen", "Toinen vastaus", keskustelu);
            keskustelu.lisaaViestiListaan(viesti1);
            keskustelu.lisaaViestiListaan(viesti2);

            keskusteluRepo.save(keskustelu); // Koska CASCADE käytössä, ei tarvitse tallentaa viestejä ja keskusteluita erikseen repoon, vaan menevät aiheen tallennuksella

            // 2. KISSA-KESKUSTELU
            keskustelu = new Keskustelu(aihe, "Paras kissanvessa", "GrumpyCat", "Mikä on paras kissanvessa? Onko avoin vai katettu hyvä?");

            viesti1 = new Viesti("Siamilainen", "Katettu, ehdottomasti. Pysyy hiekat ja hajut paremmin kurissa.", keskustelu);
            viesti2 = new Viesti("Toinen", "Toinen vastaus", keskustelu);
            keskustelu.lisaaViestiListaan(viesti1);
            keskustelu.lisaaViestiListaan(viesti2);

            keskusteluRepo.save(keskustelu);


        };
    }


}
