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