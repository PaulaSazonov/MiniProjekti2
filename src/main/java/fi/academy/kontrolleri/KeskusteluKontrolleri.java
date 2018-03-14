package fi.academy.kontrolleri;

import fi.academy.entityt.Keskustelu;
import fi.academy.entityt.Viesti;
import fi.academy.repositoryt.AiheRepo;
import fi.academy.repositoryt.KeskusteluRepo;
import fi.academy.repositoryt.ViestiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class KeskusteluKontrolleri {

    private KeskusteluRepo keskusteluRepo;
    private AiheRepo aiheRepo;
    private ViestiRepo viestirepo;

    public KeskusteluKontrolleri(@Autowired KeskusteluRepo keskusteluRepo, @Autowired AiheRepo aiheRepo, @Autowired ViestiRepo viestirepo) {
        this.keskusteluRepo = keskusteluRepo;
        this.aiheRepo = aiheRepo;
        this.viestirepo = viestirepo;
    }

    @GetMapping("/foorumi/{aiheenNimi}/{id}")
    public String naytaYksiKeskustelu(@PathVariable("aiheenNimi") String aiheenNimi, @PathVariable("id") int id, Model model) {
        Optional<Keskustelu> optkesk = keskusteluRepo.findById(id);
        model.addAttribute("keskustelu", optkesk);

        //Lomakkeen luonti
        Viesti uusiViesti = new Viesti();
        uusiViesti.setKeskusteluJohonViestiKuuluu(optkesk.get());
        model.addAttribute("lomake", uusiViesti);

        return "yksiKeskustelu";
    }

//    @GetMapping("/foorumi/haku")
//    public String haekeskusteluista(Hakusana sana, Model model) {
//        if (sana == null || sana.getHakusana() == null || sana.getHakusana().trim().isEmpty())
//            return "redirect:keskustelukokoelmat";
//
//        model.addAttribute("otsikko", "Sanonnat (filtterillä)");
//        model.addAttribute("sanonnat", sanonnat.getSanonnat(sana.getHakusana()));
//        model.addAttribute("hakusana", sana.getHakusana());
//        return "filtteroidyt";
//    }
//}
//class Hakusana {
//    private String hakusana;
//    public String getHakusana() {
//        return hakusana;
//    }
//    public void setHakusana(String hakusana) {
//        this.hakusana = hakusana;
//    }

    @PostMapping("/uusiviesti")
    @Transactional
    public String uudenViestinKasittelija(Viesti viesti) {
        viestirepo.save(viesti);
        return "redirect:foorumi"; // halutaan lopulta, että että palaa samaan keskusteluun - nyt ei toimi!
    }
}
