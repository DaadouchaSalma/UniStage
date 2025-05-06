package com.example.unistage.candidature;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unistage.LoginActivity;
import com.example.unistage.R;
import com.example.unistage.offre.ListOffreActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ajouterCandActivity extends AppCompatActivity {
    FirebaseFirestore db;
    EditText profilLinkedinInput, lienGithubInput, competencesInput, experiencesInput, formationsInput, languesInput, certificationsInput, projetsInput;
    Button btnCandidater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ajout_candidature);
        db = FirebaseFirestore.getInstance();
        profilLinkedinInput = findViewById(R.id.profil_linkedin);
        lienGithubInput = findViewById(R.id.lien_github);
        competencesInput = findViewById(R.id.competences);
        experiencesInput = findViewById(R.id.experiences);
        formationsInput = findViewById(R.id.formations);
        languesInput = findViewById(R.id.langues);
        certificationsInput = findViewById(R.id.certifications);
        projetsInput = findViewById(R.id.projets);
        btnCandidater = findViewById(R.id.btnCandidater);
        btnCandidater.setOnClickListener(view->{

            String profilLinkedin = profilLinkedinInput.getText().toString().trim();
            String lienGithub = lienGithubInput.getText().toString().trim();
            String competences = competencesInput.getText().toString().trim();
            String experiences = experiencesInput.getText().toString().trim();
            String formations = formationsInput.getText().toString().trim();
            String langues = languesInput.getText().toString().trim();
            String certifications = certificationsInput.getText().toString().trim();
            String projets = projetsInput.getText().toString().trim();
            Intent intent = getIntent();
           String id=intent.getStringExtra("offre_id");
            Log.d("FIRESTORE_ID", "Offre ID: " + id);



            if (profilLinkedin.isEmpty() || lienGithub.isEmpty() || competences.isEmpty() ||
                    experiences.isEmpty() || formations.isEmpty() || langues.isEmpty() ||
                    certifications.isEmpty() || projets.isEmpty()) {
                Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            Candidature candidature=new Candidature(user.getUid(),id,profilLinkedin, lienGithub,  competences,  experiences, formations, langues, certifications, projets);
            db.collection("candidatures")
                    .add(candidature)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Candidature envoyée !", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ajouterCandActivity.this, ListOffreActivity.class));
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
