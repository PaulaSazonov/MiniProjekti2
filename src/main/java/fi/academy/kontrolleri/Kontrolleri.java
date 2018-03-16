package fi.academy.kontrolleri;

import fi.academy.entityt.Aihe;
import fi.academy.entityt.Keskustelu;
import fi.academy.entityt.Viesti;
import fi.academy.repositoryt.AiheRepo;
import fi.academy.repositoryt.KeskusteluRepo;
import fi.academy.repositoryt.ViestiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class Kontrolleri {

    private AiheRepo aiheRepo;
    private KeskusteluRepo keskusteluRepo;
    private ViestiRepo viestiRepo;


    public Kontrolleri(@Autowired AiheRepo aiheRepo, @Autowired KeskusteluRepo keskusteluRepo, @Autowired ViestiRepo viestiRepo) {
        this.aiheRepo = aiheRepo;
        this.keskusteluRepo = keskusteluRepo;
        this.viestiRepo = viestiRepo;
    }

    @ModelAttribute
    public void annaKaikille(Model model) {
        Iterable<Aihe> aiheet = aiheRepo.findAll();
        model.addAttribute("aiheet", aiheet);
    }

    @GetMapping("/foorumi")
    public String näytäAihe(Model model) {
        return "index";
    }

    @GetMapping("/foorumi/{aiheenNimi}")
    public String naytaAiheenKeskustelut(@PathVariable String aiheenNimi, Model model) {
        List<Keskustelu> keskustelulista = keskusteluRepo.haeKeskustelutAiheella(aiheenNimi);
        if (keskustelulista.isEmpty()) {

            Keskustelu keskustelu = new Keskustelu();
            Viesti viesti = new Viesti();
            keskustelu = null;
            viesti = null;

            lisaaUusiKeskustelu(aiheenNimi, model, viesti, keskustelu);
            return "uusikeskustelu";
        }
        model.addAttribute("aiheenNimi", aiheenNimi);
        model.addAttribute("keskustelulista", keskustelulista);
        return "keskustelukokoelmat";
    }

    @GetMapping("foorumi/{aiheenNimi}/uusikeskustelu")
    public String lisaaUusiKeskustelu(@PathVariable String aiheenNimi, Model model, Viesti viesti, Keskustelu keskustelu) {

        Optional<Aihe> aihe = aiheRepo.findById(aiheenNimi);

        Keskustelu uusiKeskustelu = new Keskustelu();
        uusiKeskustelu.setAihealueJohonKuuluu(aihe.get());
        if (keskustelu != null) {// jos palataan virheilmoituksen kautta
            uusiKeskustelu.setKeskustelunotsikko(keskustelu.getKeskustelunotsikko()); // = keskustelu;
        }

        Viesti uusiAloitusviesti = new Viesti();
        uusiAloitusviesti.setKeskusteluJohonViestiKuuluu(uusiKeskustelu);
        if (viesti != null) { // jos palataan virheilmoituksen kautta
            uusiAloitusviesti = viesti;
        }

        uusiKeskustelu.setAloitusviesti(uusiAloitusviesti);

        model.addAttribute("aihe", aiheenNimi);
        model.addAttribute("lomake", uusiKeskustelu);

        return "uusikeskustelu";
    }

    @PostMapping("/uusikeskustelu")
    public String uudenKeskustelunKasittelija(Keskustelu keskustelu, Model model) {

        keskustelu.getAloitusviesti().setKeskusteluJohonViestiKuuluu(keskustelu);

        // palautus URLiin liittyvät kommentit
        String aiheenNimi = keskustelu.getAihealueJohonKuuluu().getAiheenNimi();
        // TARKISTATAAN ONKO VIESTIN KENTÄT TYHJÄT
        Viesti viesti = keskustelu.getAloitusviesti();

        // TARKISTA KAIKKI KENTÄT
        if (viesti.getKirjoittaja().isEmpty() || viesti.getTeksti().isEmpty() || keskustelu.getKeskustelunotsikko().isEmpty()) {
            model.addAttribute("virhe", "Täytäthän kaikki pakolliset tiedot");
            return lisaaUusiKeskustelu(aiheenNimi, model, viesti, keskustelu);
        } else {
            if (viesti.getTeksti().length() < 2000 & viesti.getKirjoittaja().length() < 255 & keskustelu.getKeskustelunotsikko().length() < 255) {
                keskusteluRepo.save(keskustelu);
                int id = keskustelu.getId();

                viesti = null;
                return naytaYksiKeskustelu(aiheenNimi, id, model, viesti); // pysyy keskustelun sivulla mihin lisäsi viestin
            } else {

                String virheilmoitus = "";
                if (viesti.getTeksti().length() > 2000)
                    virheilmoitus += "Viestin maksimipituus on 2000 merkkiä.\n ";
                if (viesti.getKirjoittaja().length() > 255)
                    virheilmoitus += "Nimen maksimipituus on 255 merkkiä. ";
                if (keskustelu.getKeskustelunotsikko().length() > 255)
                    virheilmoitus += "Otsikon maksimipituus on 255 merkkiä.";
                model.addAttribute("virhe", virheilmoitus);

                return lisaaUusiKeskustelu(aiheenNimi, model, viesti, keskustelu);
            }
        }
    }

    @GetMapping("/foorumi/{aiheenNimi}/{id}")
    public String naytaYksiKeskustelu(@PathVariable("aiheenNimi") String aiheenNimi, @PathVariable("id") int id, Model model, Viesti viesti) {
        List<Keskustelu> keskustelut = keskusteluRepo.haeKeskustelutAiheella(aiheenNimi);
        if (keskustelut.isEmpty()) {
            return "eiHakutuloksia";
        }
        List<Viesti> listaaViestit = viestiRepo.listaaViestit(id);
        if (listaaViestit.isEmpty()) {
            return "eiHakutuloksia";
        }
        String keskustelunotsikko = listaaViestit.get(0).getKeskusteluJohonViestiKuuluu().getKeskustelunotsikko();
        model.addAttribute("otsikko", keskustelunotsikko);
        model.addAttribute("keskustelut", keskustelut);
        model.addAttribute("listaaviestit", listaaViestit);

        Optional<Keskustelu> optkesk = keskusteluRepo.findById(id);

        Viesti uusiViesti = new Viesti();
        if (viesti != null) { // jos palataan virheilmoituksen kautta
            uusiViesti = viesti;
        }

        uusiViesti.setKeskusteluJohonViestiKuuluu(optkesk.get());
        model.addAttribute("lomake", uusiViesti);

        return "keskustelut";
    }

    @PostMapping("/uusiviesti")
    public String uudenViestinKasittelija(Viesti viesti, Model model) {

        String aiheenNimi;
        Optional<Keskustelu> keskustelu = keskusteluRepo.findById(viesti.getKeskusteluJohonViestiKuuluu().getId());
        aiheenNimi = keskustelu.get().getAihealueJohonKuuluu().getAiheenNimi();
        int id = viesti.getKeskusteluJohonViestiKuuluu().getId();

        // tarkistetaan onko viestin kentät tyhjiä
        if (viesti.getKirjoittaja().equals("") || viesti.getTeksti().equals("")) {
            model.addAttribute("virhe", "Täytäthän kaikki tarvittavat tiedot!");
            return naytaYksiKeskustelu(aiheenNimi, id, model, viesti);
        } else {
            if (viesti.getTeksti().length() < 2000 & viesti.getKirjoittaja().length() < 255) {
                Viesti talletettuViesti = viestiRepo.save(viesti);
                viesti = null;
                return naytaYksiKeskustelu(aiheenNimi, id, model, viesti); // pysyy keskustelun sivulla mihin lisäsi viestin
            } else {
                String virheilmoitus = "";
                if (viesti.getTeksti().length() > 2000)
                    virheilmoitus += "Viestin maksimipituus on 2000 merkkiä.\n ";
                if (viesti.getKirjoittaja().length() > 255)
                    virheilmoitus += "Nimen maksimipituus on 255 merkkiä. ";
                model.addAttribute("virhe", virheilmoitus);

                return naytaYksiKeskustelu(aiheenNimi, id, model, viesti);
            }
        }
    }

    @GetMapping("/foorumi/haku")
    public String hakulomake(Model model) {
        fi.academy.kontrolleri.Hakusana hakusana = new fi.academy.kontrolleri.Hakusana();
        hakusana.setSana("");
        model.addAttribute("hakusana", hakusana);
        return "hakulomake";
    }

    @PostMapping("/foorumi/hakutulos")
    public String haekeskusteluista(fi.academy.kontrolleri.Hakusana sana, Model model) {
        if (sana == null || sana.getSana() == null || sana.getSana().trim().isEmpty())
            return "eiHakutuloksia";
        List<Viesti> haetut = viestiRepo.etsi(sana.getSana());
        if (haetut.isEmpty()) {
            return "eiHakutuloksia";
        }
        model.addAttribute("viestilista", haetut);
        return "hakutulokset";
    }
}

class Hakusana {
    private String sana;

    public Hakusana() {
    }

    public Hakusana(String sana) {
        this.sana = sana;
    }

    public String getSana() {
        return sana;
    }

    public void setSana(String sana) {
        this.sana = sana;
    }
}