package com.esiea.sondage.web.controller;

import com.esiea.sondage.dao.SalleDao;
import com.esiea.sondage.model.SalleSondage;
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

    @GetMapping(value = "/Sondages")
    public List<SalleSondage> listSalles(@RequestParam("token") String token) {
        List<SalleSondage> listSalles = new ArrayList<SalleSondage>();

        for(SalleSondage salle : salleDao.findAll()) {
            if(salle.getListUtilisateurs().contains(token)) {
                listSalles.add(salle);
            }
        }

        return listSalles;
    }

    @PostMapping(value = "/Sondages")
    public ResponseEntity<Void> addSalle(@Valid @RequestBody SalleSondage salleSondage, @RequestParam("token") String token) {
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
