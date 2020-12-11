package com.esiea.sondage.web.controller;

import com.esiea.sondage.dao.SalleDao;
import com.esiea.sondage.dao.SondageDao;
import com.esiea.sondage.dao.UtilisateurDao;
import com.esiea.sondage.model.Sondage;
import com.esiea.sondage.web.exceptions.NotFoundException;
import com.esiea.sondage.web.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SondageController {
    @Autowired
    SondageDao sondageDao;
    @Autowired
    UtilisateurDao utilisateurDao;
    @Autowired
    SalleDao salleDao;

    /**
     *
     * @return tous les sondages publics
     */
    @GetMapping(value = "/sondages")
    public List<Sondage> listSondages() {
        List<Sondage> sondageList = new ArrayList<Sondage>();

        for(Sondage sond : sondageDao.findAll()) {
            if(sond.isPublic() == true) {
                sondageList.add(sond);
            }
        }
        return sondageList;
    }

    /**
     *
     * @param id
     * @param userid
     * @return un sondage specifique en fonction de son id
     */
    @GetMapping(value = "/sondage/{id}")
    public Sondage getSondage(@PathVariable int id, @RequestParam("userid") int userid) {
        Sondage sondage = sondageDao.findById(id);
        if(sondage != null) {
            if(sondage.isPublic())
                return sondage;
            // sinon, si l'utilisateur n'existe pas
            else if(utilisateurDao.findById(userid) == null) {
                throw new UnauthorizedException("L'utilisateur " + userid + " n'existe pas");
            }
            // sinon, si l'utilisateur existe
            else if(utilisateurDao.findById(userid) != null) {
                // si l'utilisateur a acces a la salle ou est le sondage
                if(utilisateurDao.findById(userid).getListSalles().contains(sondage.getIdSalle()))
                    return sondage;
            }
            else {
                throw new NotFoundException("Le sondage d'id " + id + " n'existe pas");
            }
        }
        else {
            throw new NotFoundException("Le sondage d'id " + id + " n'existe pas");
        }
        return sondage;
    }
}
