package com.esiea.sondage.dao;

import com.esiea.sondage.model.Sondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// <Sondage, Integer> correspond au type d'entite concernee, puis au type de l'id manipule
@Repository
public interface SondageDao extends JpaRepository<Sondage, Integer> {
    Sondage findById(int id);
    // List<Sondage> findAll(String recherche);
}
