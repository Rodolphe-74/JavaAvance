package com.rodolphe.restservice.RestApp.web.controller;

import com.rodolphe.restservice.RestApp.DAO.PersonnageDAO;
import com.rodolphe.restservice.RestApp.model.Personnage;
import com.rodolphe.restservice.RestApp.model.PersonnageDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonnageController {

    @Autowired
    private PersonnageDAO personnageDAO;

    @RequestMapping(value="/Personnages", method= RequestMethod.GET)
    public List<Personnage> listePersonnages(){
        return personnageDAO.findAll();
    }

    @GetMapping(value="/Personnages/{id}")
    public Personnage afficherPersonnage(@PathVariable int id){
        return personnageDAO.findById(id);
    }
}
