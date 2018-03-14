
package fi.academy.kontrolleri;

import fi.academy.entityt.Aihe;
import fi.academy.repositoryt.AiheRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class AiheKontrolleri {

    private AiheRepo aiheRepo;


    public AiheKontrolleri(@Autowired AiheRepo aiheRepo) {
        this.aiheRepo = aiheRepo;
    }

    @GetMapping("/{id}")
    public String näytäAihe(@PathVariable (name = "aiheenNimi") String aiheenNimi, Model model) {
        Optional<Aihe> optionalAihe = aiheRepo.findById(aiheenNimi);
        model.addAttribute("aihe", optionalAihe);
        return "naytaaihe";



    }

}

