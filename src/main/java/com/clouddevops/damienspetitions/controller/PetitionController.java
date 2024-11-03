package com.clouddevops.damienspetitions.controller;

import com.clouddevops.damienspetitions.model.Petition;
import com.clouddevops.damienspetitions.service.PetitionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PetitionController {
    private final PetitionService petitionService;

    public PetitionController(PetitionService petitionService) {
        this.petitionService = petitionService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/create-petition")
    public String showCreatePetitionPage() {
        return "createPetition";
    }

    @PostMapping("/create-petition")
    public String createPetition(@RequestParam String title, @RequestParam String description) {
        petitionService.addPetition(new Petition(title, description));
        return "redirect:/view-petitions";
    }

    @GetMapping("/view-petitions")
    public String viewPetitions(Model model) {
        List<Petition> petitions = petitionService.getAllPetitions();
        model.addAttribute("petitions", petitions);
        return "viewPetitions";
    }

    @GetMapping("/view-petition/{petitionName}")
    public String viewPetition(@PathVariable String petitionName, Model model) {
        Petition petition = petitionService.getPetitionByTitle(petitionName);
        model.addAttribute("petition", petition);
        return "viewPetition";  // Assumes viewPetition.html exists and expects a "petition" model attribute
    }


    @GetMapping("/search-petition")
    public String showSearchPetitionPage() {
        return "searchPetition";
    }

    @PostMapping("/search-petition")
    public String searchPetition(@RequestParam String title, Model model) {
        Petition petition = petitionService.getPetitionByTitle(title);
        model.addAttribute("petition", petition);
        return "petitionResult";
    }

    @PostMapping("/sign-petition")
    public String signPetition(@RequestParam String title) {
        petitionService.signPetition(title);
        return "redirect:/view-petitions";
    }
}