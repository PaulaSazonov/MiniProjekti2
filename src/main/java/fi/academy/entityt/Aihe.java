
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

    public Aihe() {
    }

    public Aihe(String aiheenNimi) {
        this.aiheenNimi = aiheenNimi;
        this.keskustelutAiheesta = keskustelutAiheesta;
    }

    public String getAiheenNimi() {
        return aiheenNimi;
    }

    public void setAiheenNimi(String aiheenNimi) {
        this.aiheenNimi = aiheenNimi;
    }

    public List<Keskustelu> getKeskustelutAiheesta() {
        return keskustelutAiheesta;
    }

    public void setKeskustelutAiheesta(List<Keskustelu> keskustelutAiheesta) {
        this.keskustelutAiheesta = keskustelutAiheesta;
    }
}

