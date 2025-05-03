package com.example.unistage.candidature;

import java.time.LocalDate;
import java.util.Date;

public class Candidature {

        private String idEtudiant;
        private String idOffre;
        private LocalDate dateCandidature;

        private String profilLinkedin;
        private String lienGithub;

        private String competences;
        private String experiences;
        private String formations;
        private String langues;
        private String certifications;
        private String projets;

        private String statut;

    public Candidature( String profilLinkedin, String lienGithub, String competences, String experiences, String formations, String langues, String certifications, String projets) {
        this.idEtudiant = "1SIyQDvUyrUBtNwXuRxj";
        this.idOffre = "1SIyQDvUyrUBtNwXuRxU";
        this.dateCandidature = LocalDate.now();
        this.profilLinkedin = profilLinkedin;
        this.lienGithub = lienGithub;
        this.competences = competences;
        this.experiences = experiences;
        this.formations = formations;
        this.langues = langues;
        this.certifications = certifications;
        this.projets = projets;
        this.statut = "en attente";
    }


    public void setIdEtudiant(String idEtudiant) {
        this.idEtudiant = idEtudiant;
    }

    public void setIdOffre(String idOffre) {
        this.idOffre = idOffre;
    }

    public void setDateCandidature(LocalDate Candidature) {
        this.dateCandidature = dateCandidature;
    }

    public void setProfilLinkedin(String profilLinkedin) {
        this.profilLinkedin = profilLinkedin;
    }

    public void setLienGithub(String lienGithub) {
        this.lienGithub = lienGithub;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }

    public void setExperiences(String experiences) {
        this.experiences = experiences;
    }

    public void setFormations(String formations) {
        this.formations = formations;
    }

    public void setLangues(String langues) {
        this.langues = langues;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public void setProjets(String projets) {
        this.projets = projets;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }



    public String getIdEtudiant() {
        return idEtudiant;
    }

    public String getIdOffre() {
        return idOffre;
    }

    public LocalDate getDateCandidature() {
        return dateCandidature;
    }

    public String getProfilLinkedin() {
        return profilLinkedin;
    }

    public String getLienGithub() {
        return lienGithub;
    }

    public String getCompetences() {
        return competences;
    }

    public String getExperiences() {
        return experiences;
    }

    public String getFormations() {
        return formations;
    }

    public String getLangues() {
        return langues;
    }

    public String getCertifications() {
        return certifications;
    }

    public String getProjets() {
        return projets;
    }

    public String getStatut() {
        return statut;
    }
}
