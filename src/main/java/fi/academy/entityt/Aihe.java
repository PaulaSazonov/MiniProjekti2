
package fi.academy.entityt;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Aihe {
    @Id
    private String aiheenNimi;
    @OneToMany(mappedBy = "aihealueJohonKuuluu", cascade = CascadeType.ALL)
    private List<Keskustelu> keskustelutAiheesta;

    public Aihe() {
    }

    public Aihe(String aiheenNimi) {
        this.aiheenNimi = aiheenNimi;
        this.keskustelutAiheesta = new ArrayList<>();
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

    public void lisaaKeskusteluAiheenListaan(Keskustelu keskustelu) {
        this.keskustelutAiheesta.add(keskustelu);
    }
}

