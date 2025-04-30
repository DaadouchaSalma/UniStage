package com.example.unistage;

import java.time.LocalDate;

public class Offre {
    private String titre ;
    private String description ;
    private LocalDate dateCreation;
    private String competencesRequises;
    private String idEncadrant; // Référence à un encadrant (id)
    private String localisation;
    private String duree; // Exemple : "8 semaines" ou "2 mois"
    private int nombrePlaces;

    public Offre(String titre, int nombrePlaces, String duree, String localisation, String competencesRequises, String description) {
        this.titre = titre;
        this.nombrePlaces = nombrePlaces;
        this.duree = duree;
        this.localisation = localisation;
        this.competencesRequises = competencesRequises;
        this.dateCreation = LocalDate.now();
        this.description = description;
        this.idEncadrant="2ULm0A3dEFC76igR3khp";
    }

    public Offre (){

    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCompetencesRequises() {
        return competencesRequises;
    }

    public void setCompetencesRequises(String competencesRequises) {
        this.competencesRequises = competencesRequises;
    }

    public String getIdEncadrant() {
        return idEncadrant;
    }

    public void setIdEncadrant(String idEncadrant) {
        this.idEncadrant = idEncadrant;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }
}
