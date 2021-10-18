package com.rodolphe.restservice.RestApp.model;

import com.rodolphe.restservice.RestApp.DAO.PersonnageDAO;
import com.rodolphe.restservice.RestApp.model.Personnage;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonnageDaoImpl implements PersonnageDAO {

    public static List<Personnage>personnages=new ArrayList<>();
    static {
        personnages.add(new Personnage(1, "Merlin", "Mage"));
        personnages.add(new Personnage(2, "Brutus", "Warrior"));
        personnages.add(new Personnage(3, "Achille", "Warrior"));
    }

    @Override
    public List<Personnage> findAll() {
        return personnages;
    }

    @Override
    public Personnage findById(int id) {
        for (Personnage personnage : personnages) {
            if(personnage.getId() ==id){
                return personnage;
            }
        }
        return null;
    }

    @Override
    public Personnage save(Personnage personnage) {
        personnages.add(personnage);
        return null;
    }

}
