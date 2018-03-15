
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
        hakusana.setHakusana("");
        model.addAttribute("hakusana", hakusana.getHakusana());
        return "hakulomake";
    }

    @PostMapping("/foorumi/hakutulos")
    public String haekeskusteluista(Hakusana sana, Model model) {
        System.out.println(sana.getHakusana());
        if (sana == null || sana.getHakusana() == null || sana.getHakusana().trim().isEmpty())
            return "redirect:index";
        List<Viesti> haetut = viestiRepo.findViestiByTekstiContaining(sana.getHakusana());
        model.addAttribute("aiheenNimi", haetut.get(0).getKeskusteluJohonViestiKuuluu().getAihealueJohonKuuluu());
        model.addAttribute("keskustelulista", haetut);
        return "keskustelukokoelmat";
    }
}
class Hakusana {
    private String hakusana;

    public Hakusana() {
    }

    public Hakusana(String hakusana) {
        this.hakusana = hakusana;
    }

    public String getHakusana() {
        return hakusana;
    }
    public void setHakusana(String hakusana) {
        this.hakusana = hakusana;
    }
}

