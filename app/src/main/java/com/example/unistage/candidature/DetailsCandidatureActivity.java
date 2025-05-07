package com.example.unistage.candidature;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unistage.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailsCandidatureActivity extends AppCompatActivity {

    private TextView textViewDetails;
    private FirebaseFirestore db;

    private String idEtudiant;
    private String idOffre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_candidature);


        db = FirebaseFirestore.getInstance();

        idEtudiant = getIntent().getStringExtra("idEtudiant");
        idOffre = getIntent().getStringExtra("idOffre");

        if (idEtudiant != null && idOffre != null) {
            chargerCandidature(idEtudiant, idOffre);
        } else {
            Toast.makeText(this, "Paramètres manquants", Toast.LENGTH_SHORT).show();
        }
    }

    private void chargerCandidature(String idEtudiant, String idOffre) {
        db.collection("candidatures")
                .whereEqualTo("idEtudiant", idEtudiant)
                .whereEqualTo("idOffre", idOffre)
                .get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        DocumentSnapshot doc = query.getDocuments().get(0);

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

                    } else {
                        Toast.makeText(this, "Candidature non trouvée.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
