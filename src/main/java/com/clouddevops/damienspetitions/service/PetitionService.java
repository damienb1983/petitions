package com.clouddevops.damienspetitions.service;

import com.clouddevops.damienspetitions.model.Petition;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PetitionService {

    //Initialize some random data in a service class.
    private final List<Petition> petitions = new ArrayList<>();

    public PetitionService() {
        // Add some random petitions
        petitions.add(new Petition("Save the Rainforests", "We need to save our planet's lungs."));
        petitions.add(new Petition("Clean Oceans", "Help us keep our oceans clean."));
        petitions.add(new Petition("Protect Endangered Species", "Join us in protecting endangered animals from extinction."));
        petitions.add(new Petition("Reduce Plastic Waste", "Support policies to reduce plastic usage and promote recycling."));
        petitions.add(new Petition("Renewable Energy for All", "Advocate for increased access to renewable energy sources."));
        petitions.add(new Petition("Fight Climate Change", "Demand action from our leaders to combat climate change."));
        petitions.add(new Petition("Improve Mental Health Services", "Help increase funding and accessibility for mental health support."));
        petitions.add(new Petition("Increase Funding for Education", "Support better resources and funding for our schools."));
        petitions.add(new Petition("Promote Animal Welfare", "Encourage better treatment and protection for animals in shelters."));
    }

    public void addPetition(Petition petition) {
        petitions.add(petition);
    }

    public List<Petition> getAllPetitions() {
        return petitions;
    }

    public Petition getPetitionByTitle(String title) {
        return petitions.stream()
                .filter(petition -> petition.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public void signPetition(String title) {
        Petition petition = getPetitionByTitle(title);
        if (petition != null) {
            petition.signPetition();
        }
    }
}