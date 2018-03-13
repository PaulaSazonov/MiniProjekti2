package fi.academy.repositoryt;

import fi.academy.entityt.Keskustelu;
import org.springframework.data.repository.CrudRepository;

public interface KeskusteluRepo extends CrudRepository<Keskustelu, Integer> {
}
