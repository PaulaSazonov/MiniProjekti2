
package fi.academy.entityt;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Aihe {
    @Id
    private String aiheenNimi;
    @OneToMany
    private List<Keskustelu> keskustelutAiheesta;


}

