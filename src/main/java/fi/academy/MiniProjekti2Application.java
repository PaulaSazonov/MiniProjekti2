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
            Aihe aihe = new Aihe("Kissat");
            aiheRepo.save(aihe); // Koska CASCADE käytössä, ei tarvitse tallentaa viestejä ja keskusteluita erikseen repoon, vaan menevät aiheen tallennuksella

            // 1. KISSA-KESKUSTELU

            // Viesti aloitusviesti =

            Keskustelu keskustelu = new Keskustelu(aihe, "Pitkäkarvaisen turkin hoito");
            aihe.lisaaKeskusteluAiheenListaan(keskustelu);

            keskustelu.setAloitusviesti(new Viesti("KissojenKaveri", "Kuinka usein harjaatte pitkäkarvaisten kissojenne turkkeja ja millaisia harjoja teillä on ollut käytössä?", keskustelu));
            Viesti viesti1 = new Viesti("PersialaisetRules", "Meillä on ollut käytössä Furminator-harja, voin suositella. Pyrimme harjaamaan turkkia ainakin kerran kuussa, keväällä karvanlähtöaikaan useammin", keskustelu);
            Viesti viesti2 = new Viesti("Toinen", "Toinen vastaus", keskustelu);
            keskustelu.lisaaViestiListaan(viesti1);
            keskustelu.lisaaViestiListaan(viesti2);
            keskusteluRepo.save(keskustelu); // Koska CASCADE käytössä, ei tarvitse tallentaa viestejä ja keskusteluita erikseen repoon, vaan menevät aiheen tallennuksella


            // 2. KISSA-KESKUSTELU
            keskustelu = new Keskustelu(aihe, "Paras kissanvessa");
            aihe.lisaaKeskusteluAiheenListaan(keskustelu);

            keskustelu.setAloitusviesti(new Viesti("GrumpyCat", "Mikä on paras kissanvessa? Onko avoin vai katettu hyvä?",keskustelu));
            viesti1 = new Viesti("Siamilainen", "Katettu, ehdottomasti. Pysyy hiekat ja hajut paremmin kurissa.", keskustelu);
            viesti2 = new Viesti("Toinen", "Toinen vastaus", keskustelu);
            keskustelu.lisaaViestiListaan(viesti1);
            keskustelu.lisaaViestiListaan(viesti2);
            keskusteluRepo.save(keskustelu);

            // KOIRA-AIHEALUE
            aihe = new Aihe("Koirat");
            aiheRepo.save(aihe);

            // 1. KOIRAVIESTI
            keskustelu = new Keskustelu(aihe, "Koirani on ruma");
            aihe.lisaaKeskusteluAiheenListaan(keskustelu);

            keskustelu.setAloitusviesti(new Viesti("Pertti", "Hei. Onko teidän koiranne komeita?", keskustelu));
            viesti1 = new Viesti("Koiraihminen", "Kaikki koirat ovat kauniita!", keskustelu);
            viesti2 = new Viesti("Ihmiskoira", "Minunkin koirani on komea", keskustelu);
            Viesti viesti3 = new Viesti("Sipulimies", "Koirani on kaunis kuin sipuli", keskustelu);
            keskustelu.lisaaViestiListaan(viesti1);
            keskustelu.lisaaViestiListaan(viesti2);
            keskustelu.lisaaViestiListaan(viesti3);
            keskusteluRepo.save(keskustelu);

            // 2. KOIRAVIESTI
            keskustelu = new Keskustelu(aihe, "Osaatteko suositella ihmisille sopivaa koiranruokaa?");
            aihe.lisaaKeskusteluAiheenListaan(keskustelu);

            keskustelu.setAloitusviesti(new Viesti("Nälkäinen", "Olen huomannut, että koiranruoka on edullisempaa kuin ihmisen ruoka. Etsin siis suosituksia hyvästä ihmisille sopivasta koiranruoasta!", keskustelu));
            viesti1 = new Viesti("Kokkikoira", "Suosittelen luita", keskustelu);
            viesti2 = new Viesti("Pertti", "Ei ole Pedigren voittanutta!", keskustelu);
            viesti3 = new Viesti("Silja", "Hullu, syö mielummin kissanruokaa", keskustelu);
            keskustelu.lisaaViestiListaan(viesti1);
            keskustelu.lisaaViestiListaan(viesti2);
            keskustelu.lisaaViestiListaan(viesti3);
            keskusteluRepo.save(keskustelu);

            // LISÄÄ AIHEALUEITA
            aihe = new Aihe("Jyrsijät");
            aiheRepo.save(aihe);
            aihe = new Aihe("Linnut");
            aiheRepo.save(aihe);
            aihe = new Aihe("Kalat");
            aiheRepo.save(aihe);
            aihe = new Aihe("Matelijat");
            aiheRepo.save(aihe);
            aihe = new Aihe("Muut");
            aiheRepo.save(aihe);

        };
    }

}
