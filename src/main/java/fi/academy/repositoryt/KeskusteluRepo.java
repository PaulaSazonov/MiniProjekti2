package fi.academy.repositoryt;

import fi.academy.entityt.Keskustelu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KeskusteluRepo extends CrudRepository<Keskustelu, Integer> {

    @Query("SELECT k FROM Keskustelu k WHERE k.aihealueJohonKuuluu LIKE CONCAT(:aiheenNimi, '%') ")
    List<Keskustelu> haeKeskustelutAiheella (@Param("aiheenNimi") String aiheenNimi);
}
