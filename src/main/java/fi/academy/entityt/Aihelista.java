package fi.academy.entityt;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;


public class Aihelista {

    private List<Aihe> aihelista; // ei linkitystä, koska listaa vain olemassa olevat aihealueet

    public Aihelista() {
    }

    public List<Aihe> getAihelista() {
        return aihelista;
    }

    public void setAihelista(List<Aihe> aihelista) {
        this.aihelista = aihelista;
    }
}
