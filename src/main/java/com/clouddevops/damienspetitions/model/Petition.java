package com.clouddevops.damienspetitions.model;

public class Petition {
    private String title;
    private String description;
    private int signatures;

    public Petition(String title, String description) {
        this.title = title;
        this.description = description;
        this.signatures = 0; // Initialize with zero signatures
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getSignatures() { return signatures; }

    public void signPetition() { this.signatures++; }
}