package com.example.unistage.offre;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unistage.R;
import com.example.unistage.candidature.ajouterCandActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateOffreActivity extends AppCompatActivity {
    EditText titreEditText, descriptionEditText,
            competencesEditText, localisationEditText, dureeEditText, nombrePlacesEditText;
    Button btnUpdate;
    FirebaseFirestore db;
    String offreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.update_offre_activity);
        db = FirebaseFirestore.getInstance();
        offreId = getIntent().getStringExtra("idOffre");


        if (offreId != null) {
            db.collection("offres").document(offreId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String titre = documentSnapshot.getString("titre");
                            String description = documentSnapshot.getString("description");
                            String competences = documentSnapshot.getString("competencesRequises");
                            String localisation = documentSnapshot.getString("localisation");
                            String duree = documentSnapshot.getString("duree");
                            Long nombrePlaces = documentSnapshot.getLong("nombrePlaces");

                            // Affichage dans les EditText
                            titreEditText.setText(titre);
                            descriptionEditText.setText(description);
                            competencesEditText.setText(competences);
                            localisationEditText.setText(localisation);
                            dureeEditText.setText(duree);
                            nombrePlacesEditText.setText(String.valueOf(nombrePlaces));
                        } else {
                            Toast.makeText(this, "Offre introuvable", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Erreur de chargement : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "ID de l'offre manquant", Toast.LENGTH_SHORT).show();
        }
        // Récupération des vues
        titreEditText = findViewById(R.id.titre_2);
        descriptionEditText = findViewById(R.id.description_2);
        competencesEditText = findViewById(R.id.competences_requises_2);
        localisationEditText = findViewById(R.id.localisation_2);
        dureeEditText = findViewById(R.id.duree_2);
        nombrePlacesEditText = findViewById(R.id.nombre_places_2);
        btnUpdate = findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(view -> {
            String titre = titreEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            String competences = competencesEditText.getText().toString().trim();
            String localisation = localisationEditText.getText().toString().trim();
            String duree = dureeEditText.getText().toString().trim();
            String nbPlacesStr = nombrePlacesEditText.getText().toString().trim();

            if (titre.isEmpty() || description.isEmpty()|| competences.isEmpty() || localisation.isEmpty() || duree.isEmpty() || nbPlacesStr.isEmpty()) {
                Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            int nbPlaces = Integer.parseInt(nbPlacesStr);
            if (nbPlaces <= 0) {
                    Toast.makeText(this, "Le nombre de places doit être supérieur à zéro", Toast.LENGTH_SHORT).show();
                    return;
            }


            // Création d'une map avec les nouvelles valeurs
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("titre", titre);
            updatedData.put("description", description);
            updatedData.put("competencesRequises", competences);
            updatedData.put("localisation", localisation);
            updatedData.put("duree", duree);
            updatedData.put("nombrePlaces", nbPlaces);


            DocumentReference docRef = db.collection("offres").document(offreId);
            docRef.update(updatedData)
                    .addOnSuccessListener(aVoid ->
                            { Toast.makeText(this, "Offre mise à jour", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateOffreActivity.this, ListeOffresEncadrantActivity.class)); }
)
                    .addOnFailureListener(e -> Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show());
        });


    }
}
