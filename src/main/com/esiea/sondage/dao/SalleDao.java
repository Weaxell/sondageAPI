package com.esiea.sondage.dao;

import com.esiea.sondage.model.SalleSondage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalleDao extends JpaRepository<SalleSondage, Integer> {
    SalleSondage findById(int id);
}
