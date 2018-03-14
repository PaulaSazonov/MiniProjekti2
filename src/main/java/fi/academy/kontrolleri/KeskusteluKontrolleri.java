package fi.academy.kontrolleri;

import fi.academy.entityt.Keskustelu;
import fi.academy.repositoryt.AiheRepo;
import fi.academy.repositoryt.KeskusteluRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class KeskusteluKontrolleri {

    private KeskusteluRepo keskusteluRepo;
    private AiheRepo aiheRepo;

    public KeskusteluKontrolleri(@Autowired KeskusteluRepo keskusteluRepo, @Autowired AiheRepo aiheRepo) {
        this.keskusteluRepo = keskusteluRepo;
        this.aiheRepo = aiheRepo;
    }

    @GetMapping("/foorumi/{aiheenNimi}")
    public String naytaAiheenKeskustelut (@PathVariable ("aiheenNimi") String aiheenNimi, Model model){
        List<Keskustelu> keskustelulista = keskusteluRepo.haeKeskustelutAiheella(aiheenNimi);
        model.addAttribute("keskustelulista", keskustelulista);
        return "keskustelut";

    }

//    @GetMapping("/foorumi/{aiheenNimi}/{id}")
//    public String naytaYksiKeskustelu (@PathVariable ("aiheenNimi") String aiheenNimi, @PathVariable ("id") int id, Model model){
//        List<Keskustelu> keskustelulista = keskusteluRepo.haeKeskustelutAiheella(aiheenNimi);
//        model.addAttribute("keskustelulista", keskustelulista);
//        return "Keskustelut";
//
//    }
}
