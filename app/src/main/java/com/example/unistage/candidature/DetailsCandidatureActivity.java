package com.example.unistage.candidature;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unistage.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailsCandidatureActivity extends AppCompatActivity {

    private TextView textViewDetails;
    private ImageButton buttonAccepter;
    private ImageButton buttonRefuser;
    private FirebaseFirestore db;
    private String email;

    private String idEtudiant;
    private String idOffre;
    private String candidatureDocId ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_candidature);


        db = FirebaseFirestore.getInstance();
        buttonAccepter = findViewById(R.id.buttonAccepter);
        buttonRefuser = findViewById(R.id.buttonRefuser);

        idEtudiant = getIntent().getStringExtra("idEtudiant");
        idOffre = getIntent().getStringExtra("idOffre");

        if (idEtudiant != null && idOffre != null) {
            chargerCandidature(idEtudiant, idOffre);
        } else {
            Toast.makeText(this, "Paramètres manquants", Toast.LENGTH_SHORT).show();
        }
        buttonAccepter.setOnClickListener(v -> {
            changerStatut("acceptée");
            envoyerEmail(email, "Votre candidature a été acceptée pour le stage",
                    "Bonjour,\n\nNous avons le plaisir de vous informer que votre candidature pour le stage proposé par Unisatge a été acceptée.\n\n" +
                            "Nous vous félicitons pour votre sélection et sommes impatients de vous accueillir au sein de notre équipe. Nous vous contacterons bientôt pour les prochaines étapes.\n\n" +
                            "N'hésitez pas à nous contacter si vous avez des questions.\n\n" +
                            "Cordialement,\n\n" +
                            "L’équipe Unisatge\n" +
                            "Encadrant du stage");
        });
        buttonRefuser.setOnClickListener(v -> {
            changerStatut("refusée");
            envoyerEmail(email, "Statut de votre candidature - Unisatge",
                    "Bonjour,\n\nNous vous remercions pour l’intérêt que vous avez porté à notre offre de stage au sein de Unisatge.\n\n" +
                            "Après avoir examiné votre candidature, nous sommes au regret de vous informer que nous n’avons pas retenu votre profil pour cette session de stage.\n\n" +
                            "Nous vous encourageons à postuler à de futures opportunités chez Unisatge. Nous vous souhaitons bonne chance dans vos recherches et votre développement professionnel.\n\n" +
                            "Cordialement,\n\n" +
                            "L’équipe Unisatge\n" +
                            "Encadrant du stage");
        });

    }

    private void chargerCandidature(String idEtudiant, String idOffre) {
        db.collection("candidatures")
                .whereEqualTo("idEtudiant", idEtudiant)
                .whereEqualTo("idOffre", idOffre)
                .get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        DocumentSnapshot doc = query.getDocuments().get(0);
                        candidatureDocId = doc.getId();
                        //((TextView) findViewById(R.id.detailStatut)).setText(doc.getString("statut"));
                        /*((TextView) findViewById(R.id.detailDate)).setText(doc.getString("dateCandidature"));*/
                        ((TextView) findViewById(R.id.detailLinkedin)).setText(doc.getString("profilLinkedin"));
                        ((TextView) findViewById(R.id.detailGithub)).setText(doc.getString("lienGithub"));
                        ((TextView) findViewById(R.id.detailCompetences)).setText(doc.getString("competences"));
                        ((TextView) findViewById(R.id.detailExperiences)).setText(doc.getString("experiences"));
                        ((TextView) findViewById(R.id.detailFormations)).setText(doc.getString("formations"));
                        ((TextView) findViewById(R.id.detailLangues)).setText(doc.getString("langues"));
                        ((TextView) findViewById(R.id.detailCertifications)).setText(doc.getString("certifications"));
                        ((TextView) findViewById(R.id.detailProjets)).setText(doc.getString("projets"));
                        db.collection("etudiant")
                                .document(idEtudiant)
                                .get()
                                .addOnSuccessListener(etudoc -> {
                                    String nom = etudoc.getString("lastName");
                                    String prenom = etudoc.getString("firstName");
                                     email = etudoc.getString("email");

                                    ((TextView) findViewById(R.id.detailNomComplet)).setText(prenom + " " + nom);
                                    ((TextView) findViewById(R.id.detailEmail)).setText(email);
                                });

                    } else {
                        Toast.makeText(this, "Candidature non trouvée.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private void changerStatut(String nouveauStatut) {
        if (candidatureDocId == null) {
            Toast.makeText(this, "Candidature non chargée", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("candidatures").document(candidatureDocId)
                .update("statut", nouveauStatut)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Statut mis à jour : " + nouveauStatut, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erreur de mise à jour : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    private void envoyerEmail(String destinataire, String sujet, String message) {
        String uriText = "mailto:" + Uri.encode(destinataire) +
                "?subject=" + Uri.encode(sujet) +
                "&body=" + Uri.encode(message);
        Uri uri = Uri.parse(uriText);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(uri);

        try {
            startActivity(Intent.createChooser(intent, "Envoyer l'email via..."));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Aucune application de messagerie trouvée.", Toast.LENGTH_SHORT).show();
        }
    }
}
