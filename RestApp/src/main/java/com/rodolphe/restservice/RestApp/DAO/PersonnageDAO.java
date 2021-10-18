package com.rodolphe.restservice.RestApp.DAO;

import com.rodolphe.restservice.RestApp.model.Personnage;

import java.util.List;

public interface PersonnageDAO {
    public List<Personnage> findAll();
    public Personnage findById(int id);
    public void save(Personnage personnage);
    public void delete(int id);
 //   public Personnage add(Personnage personnage);
}
