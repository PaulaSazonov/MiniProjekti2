
package fi.academy.kontrolleri;

import fi.academy.entityt.Aihe;
import fi.academy.repositoryt.AiheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class AiheKontrolleri {

    private AiheRepo aiheRepo;


    public AiheKontrolleri(@Autowired AiheRepo aiheRepo) {
        this.aiheRepo = aiheRepo;
    }

    @GetMapping("/")
    public String näytäAihe(Model model) {
        return "index";
    }

    @ModelAttribute
    public void annaKaikille(Model model) {
        Iterable<Aihe> optionalAihe = aiheRepo.findAll();
        model.addAttribute("aiheet", optionalAihe);
    }
}

