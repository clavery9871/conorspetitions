package com.assignment.conorspetitions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PetitionHandler {

    private List<Petition> petitions = new ArrayList<>();

    // Initialize with some sample data
    public PetitionHandler(){
        petitions.add(new Petition(1, "Reform football", "Detailing ways to help reform football", "Entertainment"));
        petitions.add(new Petition(2, "Save our Games", "Ensures companies have to provide a way for online games to continue to be available even if they don't want to support their own servers anymore.", "Gaming Technology"));
    }

    // Handles GET requests sent to the /petitions endpoint
    @GetMapping("/petitions")
    public String viewPetitions(Model model){
        // Model object used to pass data from the controller to the view (webpage)
        // Adds an attribute with the key "petitions" and the value petitions (a list of petition objects)
        model.addAttribute("petitions", petitions);
        // Name of the thymeleaf template (i.e. partitions.html)
        return "petitions";
    }

    // Handles GET requests sent to /petition/{id} endpoint
    @GetMapping("/petition/{id}")
    //Use @PathVariable to use this variable in the url endpoint
    public String viewPetition(@PathVariable int id, Model model){
        // Filter petition list and find one with the matching id
        Petition petition = petitions.stream()
                .filter(x -> x.getId() == id)
                // Take the first petition with this id
                .findFirst()
                // If no petition with this id exists, return null
                .orElse(null);
        model.addAttribute("petition", petition);
        //view.html
        return "view";
    }

    // Handles POST requests sent to /petition/{id}/sign endpoint
    @PostMapping("/petition/{id}/sign")
    //@RequestParam takes the value from the POST request corresponding to the name field and stores it
    public String signPetition(@PathVariable int id, @RequestParam String name){
        Petition petition = petitions.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElse(null);

        // If we have a petition for the given id - proceed to sign it
        if (petition != null){
            petition.sign(name);
        }

        // After signing - reload and return to the petition/{id} page
        return "redirect:/petition/" + id;
    }

    // Execute for GET requests sent to the /query endpoint
    @GetMapping("/query")
    public String queryPetitions(){
        // Load query.html
        return "query";
    }

    // Execute for GET requests sent to the /result endpoint
    @GetMapping("/result")
    public String queryResults(@RequestParam String query, Model model){
        List<Petition> results = petitions.stream()
                // Cast everything to lowercase to make querying less error-prone
                // Return a list of the results
                .filter(x -> x.getTitle().toLowerCase().contains(query.toLowerCase()))
                .toList();
        model.addAttribute("results", results);
        // Load result.html
        return "result";
    }

    // Execute for GET requests sent to the /create endpoint
    @GetMapping("/create")
    public String create(){
        // Load create.html
        return "create";
    }

    // Execute for POST requests sent to the /create endpoint
    @PostMapping("create")
    public String create(@RequestParam String title, @RequestParam String description, @RequestParam String sector){
        // Ensure the id for the new petition is one greater than the previous one
        int newId = petitions.size() + 1;
        // Add the new petition to the petitions list
        petitions.add(new Petition(newId, title, description, sector));
        // After creating - reload and return to the petitions page
        return "redirect:/petitions";
    }

}
