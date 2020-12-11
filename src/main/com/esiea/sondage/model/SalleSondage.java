package com.esiea.sondage.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class SalleSondage {
    @Id
    private int id;
    private String nom;
    private int idProprietaire;

    @ElementCollection
    private List<Integer> listSondage;

    public SalleSondage() {
    }

    public int getIdProprietaire() {
        return idProprietaire;
    }

    public void setIdProprietaire(int idProprietaire) {
        this.idProprietaire = idProprietaire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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
                ", listSondage=" + listSondage +
                '}';
    }
}
