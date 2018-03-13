package fi.academy.repositoryt;

import fi.academy.entityt.Aihe;
import org.springframework.data.repository.CrudRepository;

public interface AiheRepo extends CrudRepository<Aihe, Integer> {
}
