package com.esiea.sondage.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class SalleSondage {
    @Id
    private long id;
    private String nom;

    @ElementCollection
    private List<Integer> listUtilisateurs;

    @ElementCollection
    private List<Integer> listSondage;

    public SalleSondage() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Integer> getListUtilisateurs() {
        return listUtilisateurs;
    }

    public void setListUtilisateurs(List<Integer> listUtilisateurs) {
        this.listUtilisateurs = listUtilisateurs;
    }

    public List<Integer> getListSondage() {
        return listSondage;
    }

    public void setListSondage(List<Integer> listSondage) {
        this.listSondage = listSondage;
    }

    @Override
    public String toString() {
        return "SalleSondage{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", listUtilisateurs=" + listUtilisateurs +
                ", listSondage=" + listSondage +
                '}';
    }
}
