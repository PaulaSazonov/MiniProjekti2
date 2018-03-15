
package fi.academy.kontrolleri;

import fi.academy.entityt.Aihe;
import fi.academy.entityt.Keskustelu;
import fi.academy.entityt.Viesti;
import fi.academy.repositoryt.AiheRepo;
import fi.academy.repositoryt.KeskusteluRepo;
import fi.academy.repositoryt.ViestiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
            lisaaUusiKeskustelu(aiheenNimi, model);
            return "uusikeskustelu";
        }
        model.addAttribute("aiheenNimi", aiheenNimi);
        model.addAttribute("keskustelulista", keskustelulista);
        return "keskustelukokoelmat";
    }

    @GetMapping("foorumi/{aiheenNimi}/uusikeskustelu")
    public String lisaaUusiKeskustelu(@PathVariable String aiheenNimi, Model model) {

        Optional<Aihe> aihe = aiheRepo.findById(aiheenNimi);

        Keskustelu uusiKeskustelu = new Keskustelu();
        uusiKeskustelu.setAihealueJohonKuuluu(aihe.get());

        Viesti uusiAloitusviesti = new Viesti();
        uusiAloitusviesti.setKeskusteluJohonViestiKuuluu(uusiKeskustelu);

        uusiKeskustelu.setAloitusviesti(uusiAloitusviesti);

        model.addAttribute("aihe", aiheenNimi);
        model.addAttribute("lomake", uusiKeskustelu);

        return "uusikeskustelu";
    }

    @PostMapping("/uusikeskustelu")
    //@Transactional
    public String uudenKeskustelunKasittelija(Keskustelu keskustelu, Model model) {

        keskustelu.getAloitusviesti().setKeskusteluJohonViestiKuuluu(keskustelu);

        // TARKISTATAAN ONKO VIESTIN KENTÄT TYHJÄT
        Viesti viesti = keskustelu.getAloitusviesti();
        if (!viesti.getKirjoittaja().equals("") || !viesti.getTeksti().equals("") || !keskustelu.getKeskustelunotsikko().equals("")) {
            keskusteluRepo.save(keskustelu);
        } else {
            model.addAttribute("virhe", "Täytäthän kaikki tarvittavat tiedot!");
            String aiheenNimi = keskustelu.getAihealueJohonKuuluu().getAiheenNimi();
            return lisaaUusiKeskustelu(aiheenNimi, model);
        }

        String aiheenNimi = keskustelu.getAihealueJohonKuuluu().getAiheenNimi();
        int id = keskustelu.getId();

        return naytaYksiKeskustelu(aiheenNimi, id, model); // pysyy keskustelun sivulla mihin lisäsi viestin
    }

    @GetMapping("/foorumi/{aiheenNimi}/{id}")
    public String naytaYksiKeskustelu(@PathVariable("aiheenNimi") String aiheenNimi, @PathVariable("id") int id, Model model) {
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
        uusiViesti.setKeskusteluJohonViestiKuuluu(optkesk.get());
        model.addAttribute("lomake", uusiViesti);

        return "keskustelut";
    }

    @PostMapping("/uusiviesti")
    //@Transactional
    public String uudenViestinKasittelija(Viesti viesti, Model model) {

        String aiheenNimi;
        Optional<Keskustelu> keskustelu = keskusteluRepo.findById(viesti.getKeskusteluJohonViestiKuuluu().getId());
        aiheenNimi = keskustelu.get().getAihealueJohonKuuluu().getAiheenNimi();
        int id = viesti.getKeskusteluJohonViestiKuuluu().getId();

        // tarkistetaan onko viestin kentät tyhjiä
        if (!viesti.getKirjoittaja().equals("") || !viesti.getTeksti().equals("")) {
            Viesti talletettuViesti = viestiRepo.save(viesti);
        } else {
            model.addAttribute("virhe", "Täytäthän kaikki tarvittavat tiedot!");
        }

        return naytaYksiKeskustelu(aiheenNimi, id, model); // pysyy keskustelun sivulla mihin lisäsi viestin
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
        System.out.println(sana.getSana());
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



