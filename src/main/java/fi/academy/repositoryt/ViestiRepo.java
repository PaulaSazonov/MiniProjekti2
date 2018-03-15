package fi.academy.repositoryt;

import fi.academy.entityt.Viesti;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.List;

public interface ViestiRepo extends CrudRepository<Viesti, Integer> {
    List<Viesti> findViestiByTekstiContaining(String hakusana);

    @Query("SELECT v FROM Viesti v where v.keskusteluJohonViestiKuuluu.id= :id")
    List<Viesti> listaaViestit (@Param("id") Integer id);

    @Query("SELECT v FROM Viesti v where v.keskusteluJohonViestiKuuluu.keskustelunotsikko like concat ('%', :hakusana, '%') or v.teksti like concat('%', :hakusana, '%')")
    List<Viesti> etsi(@Param("hakusana") String sana);

//    @Query("SELECT k FROM Keskustelu k WHERE k.aihealueJohonKuuluu LIKE CONCAT(:aiheenNimi, '%') ")
//    List<Keskustelu> haeKeskustelutAiheella (@Param("aiheenNimi") String aiheenNimi);
}
