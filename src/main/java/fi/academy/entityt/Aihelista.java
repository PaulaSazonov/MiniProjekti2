package fi.academy.entityt;

import javax.persistence.Entity;
import java.util.List;

@Entity
public class Aihelista {
    private List<Aihe> aihelista;

    public Aihelista() {
    }

    public List<Aihe> getAihelista() {
        return aihelista;
    }

    public void setAihelista(List<Aihe> aihelista) {
        this.aihelista = aihelista;
    }
}
