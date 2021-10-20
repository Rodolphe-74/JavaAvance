package com.rodolphe.Thymeleaf.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.rodolphe.Thymeleaf.form.DeletePersonForm;
import com.rodolphe.Thymeleaf.form.PersonForm;
import com.rodolphe.Thymeleaf.form.UpdatePersonForm;
import com.rodolphe.Thymeleaf.model.Personnage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class MainController {

    // Injectez (inject) via application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model) {

        RestTemplate restTemplate = new RestTemplate();
        List<Personnage> personnage = restTemplate.getForObject("http://localhost:8081/Personnages", List.class);
        model.addAttribute("personnage", personnage);
        return "personList";
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
    public String showAddPersonPage(Model model) {

        PersonForm personForm = new PersonForm();
        model.addAttribute("personForm", personForm);

        return "addPerson";
    }

    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
    public String savePerson(Model model, @ModelAttribute("personForm") PersonForm personForm) throws URISyntaxException {

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

        URI url = new URI("http://localhost:8081/Personnages");

        int id = personForm.getId();
        String name = personForm.getName();
        String type = personForm.getType();

        if (name != null && name.length() > 0 && type != null && type.length() > 0) {
            Personnage newPersonnage = new Personnage(id, name, type);

//            HttpEntity<Personnage> request = new HttpEntity<>(newPersonnage,headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(url, newPersonnage, Personnage.class);

            return "redirect:/personList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addPerson";
    }

    @RequestMapping(value = { "/deletePerson" }, method = RequestMethod.GET)
    public String deletePersonPage(Model model) {

        DeletePersonForm deletePerson = new DeletePersonForm();
        model.addAttribute("deletePersonForm", deletePerson);

        return "deletePerson";
    }

    @RequestMapping(value = { "/deletePerson" }, method = RequestMethod.POST)
    public String deletePerson (Model model, @ModelAttribute("deletePersonForm") DeletePersonForm deletePersonForm) throws URISyntaxException {

        int id = deletePersonForm.getId();

        URI url = new URI("http://localhost:8081/Personnages/" + id );

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(String.valueOf(url), id);

        if (id != 0) {
            return "redirect:/personList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "deletePerson";
    }

    @RequestMapping(value = { "/updatePerson/{id}" }, method = RequestMethod.GET)
    public String updatePersonPage(Model model, @PathVariable int id) {

        RestTemplate restTemplate = new RestTemplate();
        Personnage[] personnages = restTemplate.getForObject("http://localhost:8081/Personnages", Personnage[].class);

        for (Personnage personnage : personnages) {
            if(personnage.getId() == id){
                UpdatePersonForm updatePerson = new UpdatePersonForm(personnage.getId(),personnage.getName(),personnage.getType());
                model.addAttribute("updatePersonForm", updatePerson);
            }
        }

        return "updatePerson";
    }

    @RequestMapping(value = { "/updatePerson/{id}" }, method = RequestMethod.POST)
    public String updatePerson (Model model, @ModelAttribute("updatePersonForm") UpdatePersonForm updatePersonForm) {

        int id = updatePersonForm.getId();

        Personnage personnage = new Personnage(updatePersonForm.getId(),updatePersonForm.getName(),updatePersonForm.getType());

        String url = "http://localhost:8081/Personnages/" + id ;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(url, personnage);

        if (id != 0) {
            return "redirect:/personList";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "updatePerson";
    }

}