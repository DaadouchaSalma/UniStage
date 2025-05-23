package com.example.unistage.offre;

import java.time.LocalDate;

public class Offre {
    private String id;
    private String titre ;
    private String description ;
    private String dateCreation;
    private String competencesRequises;
    private String idEncadrant;
    private String localisation;
    private String duree;
    private int nombreCandidatures;



    private int nombrePlaces;

    public Offre( String user,String titre, int nombrePlaces, String duree, String localisation, String competencesRequises, String description) {
        this.titre = titre;
        this.nombrePlaces = nombrePlaces;
        this.duree = duree;
        this.localisation = localisation;
        this.competencesRequises = competencesRequises;
        this.dateCreation = LocalDate.now().toString();
        this.description = description;
        this.idEncadrant=user;
    }

    public Offre (){

    }

    public int getNombreCandidatures() {
        return nombreCandidatures;
    }
    public void setNombreCandidatures(int nombreCandidatures) {
        this.nombreCandidatures = nombreCandidatures;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
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
