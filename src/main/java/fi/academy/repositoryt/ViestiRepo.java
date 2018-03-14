package fi.academy.repositoryt;

import fi.academy.entityt.Viesti;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ViestiRepo extends CrudRepository<Viesti, Integer> {
    List<Viesti> findViestiByTekstiContaining(String hakusana);
}
