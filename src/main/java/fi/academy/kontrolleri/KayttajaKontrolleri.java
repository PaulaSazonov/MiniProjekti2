package fi.academy.kontrolleri;

import fi.academy.entityt.Kayttaja;
import fi.academy.repositoryt.KayttajaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class KayttajaKontrolleri {

    private KayttajaRepo kayttajaRepo;

    public KayttajaKontrolleri(@Autowired KayttajaRepo kayttajaRepo) {
        this.kayttajaRepo = kayttajaRepo;
    }

    @GetMapping("/listaakaikki")
    public String kayttajat (Model model) {
        Iterable<Kayttaja> lista = kayttajaRepo.findAll();
        model.addAttribute("lista", lista);
        return "Index";
    }
}
