package fi.academy.repositoryt;

import fi.academy.entityt.Viesti;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ViestiRepo extends CrudRepository<Viesti, Integer> {

    @Query("SELECT v FROM Viesti v where v.keskusteluJohonViestiKuuluu.id= :id")
    List<Viesti> listaaViestit (@Param("id") Integer id);

//    @Query("SELECT k FROM Keskustelu k WHERE k.aihealueJohonKuuluu LIKE CONCAT(:aiheenNimi, '%') ")
//    List<Keskustelu> haeKeskustelutAiheella (@Param("aiheenNimi") String aiheenNimi);
}
