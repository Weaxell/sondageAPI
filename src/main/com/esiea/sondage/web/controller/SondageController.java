package com.esiea.sondage.web.controller;

import com.esiea.sondage.dao.SalleDao;
import com.esiea.sondage.dao.SondageDao;
import com.esiea.sondage.dao.UtilisateurDao;
import com.esiea.sondage.model.Sondage;
import com.esiea.sondage.web.exceptions.NotFoundException;
import com.esiea.sondage.web.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

        // si le sondage est public
        if(sondage != null) {
            if(sondage.isPublic())
                return sondage;
            // sinon, si l'utilisateur n'existe pas
            else if(utilisateurDao.findById(userid) == null) {
                throw new UnauthorizedException("L'utilisateur " + userid + " n'existe pas");
            }
            // sinon, si l'utilisateur existe et si l'utilisateur a acces a la salle ou est le sondage
            else if(utilisateurDao.findById(userid) != null && utilisateurDao.findById(userid).getListSalles().contains(sondage.getSalleId())) {
                return sondage;
            }
        }
        else {
            throw new NotFoundException("Le sondage d'id " + id + " n'existe pas");
        }
        return sondage;
    }

    /**
     * CREATION DE SONDAGE
     * Permet a un utilisateur de creer un sondage s'il est public ou s'il la creation se fait dans une salle à laquelle l'utilisateur a acces
     * @param sondage
     * @param userid
     */

    @PostMapping(value = "/sondages")
    public void addSondage(@Valid @RequestBody Sondage sondage, @RequestParam("userid") int userid) {
        sondage.setIdProprietaire(userid);
        if(sondage.isPublic())
            sondageDao.save(sondage);
        else if(salleDao.findById(sondage.getSalleId()) != null) {
            if(salleDao.findById(sondage.getSalleId()).getListUtilisateurs().contains(userid))
                sondageDao.save(sondage);
        }
        else
            throw new UnauthorizedException("Action non autorisee");
    }

    @PutMapping(value = "/sondages")
    public void modifierSondage(@Valid @RequestBody Sondage sondage, @RequestParam("userid") int userid) {
        sondage.setIdProprietaire(userid);
        if(sondage.isPublic())
            sondageDao.save(sondage);
        else if(utilisateurDao.findById(userid).getListSalles().contains(sondage.getSalleId())) {
            Sondage sond = sondageDao.save(sondage);
            salleDao.findById(sond.getSalleId()).addSondage(sond.getId());
        }
        else
            throw new UnauthorizedException("Action non autorisee");
    }
}
