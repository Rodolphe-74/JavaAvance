package com.rodolphe.restservice.RestApp.web.controller;

import com.rodolphe.restservice.RestApp.DAO.PersonnageDAO;
import com.rodolphe.restservice.RestApp.model.Personnage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonnageController {

    @Autowired
    private PersonnageDAO personnageDAO;


    @Operation(summary = "Affiche la liste des personnages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste trouvée",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personnage.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)})
    @RequestMapping(value="/Personnages", method= RequestMethod.GET)
    public List<Personnage> listePersonnages(){
        return personnageDAO.findAll();
    }


    @Operation(summary = "Affiche un personnage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnage trouvé",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personnage.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)})
    @GetMapping(value="/Personnages/{id}")
    public void afficherPersonnage(@PathVariable int id){
        personnageDAO.findById(id);
    }


    @Operation(summary = "Ajoute un personnage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ajout effectué",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personnage.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)})
    @PostMapping(value= "/Personnages")
    public void ajouterPersonnage(@RequestBody Personnage personnage){
        personnageDAO.save(personnage);
    }


    @Operation(summary = "Modifie un personnage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnage supprimé",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personnage.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)})
    @PutMapping(value= "/Personnages/{id}")
    public Personnage modifierPersonnages(@PathVariable int id, @RequestBody Personnage personnage){
        Personnage perso = personnageDAO.findById(id);
        perso.setName(personnage.getName());
        perso.setType(personnage.getType());
        return perso;
    }


    @Operation(summary = "Supprime un personnage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnage supprimé",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personnage.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content)})
    @DeleteMapping(value= "/Personnages/{id}")
    public void supressionPersonnages(@PathVariable int id){
        personnageDAO.delete(id);
    }
}
