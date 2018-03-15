
package fi.academy.kontrolleri;

import fi.academy.entityt.Viesti;
import fi.academy.repositoryt.ViestiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ViestiKontrolleri {

    private ViestiRepo viestiRepo;

    public ViestiKontrolleri(@Autowired ViestiRepo viestiRepo) {
        this.viestiRepo = viestiRepo;
    }

    @GetMapping("/foorumi/haku")
    public String hakulomake (Model model){
        Hakusana hakusana = new Hakusana();
        hakusana.setSana("");
        model.addAttribute("hakusana", hakusana);
        return "hakulomake";
    }

    @PostMapping("/foorumi/hakutulos")
    public String haekeskusteluista(Hakusana sana, Model model) {
        System.out.println(sana.getSana());
        if (sana == null || sana.getSana() == null || sana.getSana().trim().isEmpty())
            return "redirect:/";
        List<Viesti> haetut = viestiRepo.etsi(sana.getSana());
        if(haetut.isEmpty()){
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

