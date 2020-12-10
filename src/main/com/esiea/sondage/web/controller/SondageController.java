package com.esiea.sondage.web.controller;

import com.esiea.sondage.dao.SondageDao;
import com.esiea.sondage.model.Sondage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SondageController {
    @Autowired
    SondageDao sondageDao;

    @GetMapping(value = "/Sondages")
    public List<Sondage> listSondages() {
        List<Sondage> sondageList = new ArrayList<Sondage>();

        for(Sondage sond : sondageDao.findAll()) {
            if(sond.isPublic() == true) {
                sondageList.add(sond);
            }
        }
        return sondageList;
    }
}
