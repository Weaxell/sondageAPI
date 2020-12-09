package com.esiea.sondage.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Sondage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String titre;

    @ElementCollection
    private List<String> reponses;

    private boolean isPublic;

    @ElementCollection
    private List<Integer> listSalles;

    public Sondage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public List<String> getReponses() {
        return reponses;
    }

    public void setReponses(List<String> reponses) {
        this.reponses = reponses;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<Integer> getListSalles() {
        return listSalles;
    }

    public void setListSalles(List<Integer> listSalles) {
        this.listSalles = listSalles;
    }
}
