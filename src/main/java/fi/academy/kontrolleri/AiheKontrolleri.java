
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
public class AiheKontrolleri {

    private AiheRepo aiheRepo;
    private KeskusteluRepo keskusteluRepo;
    private ViestiRepo viestiRepo;


    public AiheKontrolleri(@Autowired AiheRepo aiheRepo, @Autowired KeskusteluRepo keskusteluRepo, @Autowired ViestiRepo viestiRepo) {
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
        if (keskustelulista.isEmpty()){
            return "eiHakutuloksia";
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

        model.addAttribute("lomake", uusiKeskustelu);

        return "uusikeskustelu";
    }

    @PostMapping("/uusikeskustelu")
    //@Transactional
    public String uudenKeskustelunKasittelija(Keskustelu keskustelu, Model model) {

        keskustelu.getAloitusviesti().setKeskusteluJohonViestiKuuluu(keskustelu);
        keskusteluRepo.save(keskustelu);

        String aiheenNimi = keskustelu.getAihealueJohonKuuluu().getAiheenNimi();
        int id = keskustelu.getId();

        return naytaYksiKeskustelu(aiheenNimi, id, model); // pysyy keskustelun sivulla mihin lisäsi viestin
    }

    @GetMapping("/foorumi/{aiheenNimi}/{id}")
    public String naytaYksiKeskustelu(@PathVariable("aiheenNimi") String aiheenNimi, @PathVariable("id") int id, Model model) {
        List<Keskustelu> keskustelut = keskusteluRepo.haeKeskustelutAiheella(aiheenNimi);
        if (keskustelut.isEmpty()){
            return "eiHakutuloksia";
        }
        List<Viesti> listaaViestit = viestiRepo.listaaViestit(id);
        if (listaaViestit.isEmpty()){
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
        Viesti talletettuViesti = viestiRepo.save(viesti);
        // Keskustelu keskustelu = keskusteluRepo.findById(viesti.getKeskusteluJohonViestiKuuluu());

        String aiheenNimi;
        Optional<Keskustelu> keskustelu = keskusteluRepo.findById(viesti.getKeskusteluJohonViestiKuuluu().getId());
        aiheenNimi = keskustelu.get().getAihealueJohonKuuluu().getAiheenNimi();
        int id = viesti.getKeskusteluJohonViestiKuuluu().getId();

        return naytaYksiKeskustelu(aiheenNimi, id, model); // pysyy keskustelun sivulla mihin lisäsi viestin
    }
}


