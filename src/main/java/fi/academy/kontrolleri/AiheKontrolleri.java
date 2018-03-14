
package fi.academy.kontrolleri;

import fi.academy.entityt.Aihe;
import fi.academy.entityt.Keskustelu;
import fi.academy.repositoryt.AiheRepo;
import fi.academy.repositoryt.KeskusteluRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class AiheKontrolleri {

    private AiheRepo aiheRepo;
    private KeskusteluRepo keskusteluRepo;


    public AiheKontrolleri(@Autowired AiheRepo aiheRepo, @Autowired KeskusteluRepo keskusteluRepo) {
        this.aiheRepo = aiheRepo;
        this.keskusteluRepo = keskusteluRepo;
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
    public String naytaAiheenKeskustelut (@PathVariable String aiheenNimi, Model model){
        List<Keskustelu> keskustelulista = keskusteluRepo.haeKeskustelutAiheella(aiheenNimi);
        model.addAttribute("aiheenNimi", aiheenNimi);
        model.addAttribute("keskustelulista", keskustelulista);
        return "keskustelukokoelmat";
    }
}

