package fi.academy;

import fi.academy.entityt.Kayttaja;
import fi.academy.repositoryt.KayttajaRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class MiniProjekti2Application {

	@Bean
	CommandLineRunner alusta (KayttajaRepo kayttajaRepo){
		return (args) -> {
			List<Kayttaja> kayttajat = new ArrayList<>();
			Kayttaja k = new Kayttaja("Taappana", "taappana@gmail.com", "taappana");
			Kayttaja a = new Kayttaja("Aku", "akuankka@gmail.com", "pulivari");
			kayttajat.add(k);
			kayttajat.add(a);
			kayttajaRepo.saveAll(kayttajat);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(MiniProjekti2Application.class, args);
	}
}
