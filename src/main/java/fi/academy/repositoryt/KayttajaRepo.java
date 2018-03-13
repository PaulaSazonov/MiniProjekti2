package fi.academy.repositoryt;

import fi.academy.entityt.Kayttaja;
import org.springframework.data.repository.CrudRepository;

public interface KayttajaRepo extends CrudRepository <Kayttaja, String> {
}
