package fi.academy.repositoryt;

import fi.academy.entityt.Viesti;
import org.springframework.data.repository.CrudRepository;

public interface ViestiRepo extends CrudRepository<Viesti, Integer> {
}
