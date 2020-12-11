package com.esiea.sondage.web.controller;

import com.esiea.sondage.dao.SalleDao;
import com.esiea.sondage.dao.SondageDao;
import com.esiea.sondage.dao.UtilisateurDao;
import com.esiea.sondage.model.SalleSondage;
import com.esiea.sondage.model.Sondage;
import com.esiea.sondage.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SalleController {
    @Autowired
    SalleDao salleDao;
    @Autowired
    UtilisateurDao utilisateurDao;
    @Autowired
    SondageDao sondageDao;

    /**
     *
     * @param userid
     * @return dans une liste toutes les salles auxquelles l'utilisateur a acces
     */
    @GetMapping(value = "/salles")
    public List<SalleSondage> listSalles(@RequestParam("userid") int userid) {
        List<SalleSondage> listSalles = new ArrayList<SalleSondage>();

        System.out.println("################ Userid: " + userid);

        Utilisateur utilisateur = null;
        utilisateur = utilisateurDao.findById(userid);

        if(utilisateur != null) {
            for(int idsalle : utilisateurDao.findById(userid).getListSalles())
                listSalles.add(salleDao.findById(idsalle));
        }

        return listSalles;
    }

    /**
     *
     * @param id
     * @param userid
     * @return tous les sondages de la salle dans une liste si l'utilisateur possede un acces a cette salle
     */
    @GetMapping(value = "/salles/{id}")
    public List<Sondage> getSondagesOfSalle(@PathVariable int id, @RequestParam("userid") int userid) {
        List<Sondage> listSondages = new ArrayList<Sondage>();

        if(utilisateurDao.findById(userid).getListSalles().contains(id) == true) {
            for(int idSondage : salleDao.findById(id).getListSondage())
                listSondages.add(sondageDao.findById(idSondage));
        }

        return listSondages;
    }

    /**
     * Pemret a un utilisateur de creer une salle, il en devient le peoprietaire
     * @param salleSondage
     * @param userid
     * @return
     */
    @PostMapping(value = "/salles")
    public ResponseEntity<Void> addSalle(@Valid @RequestBody SalleSondage salleSondage, @RequestParam("userid") int userid) {
        salleSondage.setIdProprietaire(userid);
        SalleSondage salleAdded = salleDao.save(salleSondage);

        if(salleAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salleAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
