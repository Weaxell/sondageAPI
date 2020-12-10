package com.esiea.sondage.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Utilisateur {
    @Id
    private int id;
    private String pseudo;
    private String passwd;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
