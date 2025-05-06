package com.example.unistage.offre;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.example.unistage.BaseActivity;
import com.example.unistage.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddOffreActivity extends BaseActivity {
    FirebaseFirestore db;
    EditText titreInput ;
    EditText descriptionInput ;
    EditText competanceInput;
    EditText localisationInput;
    EditText dureeInput;
    EditText nb_placeInput;
    Button btnAjouter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();
        titreInput = findViewById(R.id.titre);
        descriptionInput = findViewById(R.id.description);
        competanceInput=findViewById(R.id.competences_requises);
        localisationInput=findViewById(R.id.localisation);
        dureeInput=findViewById(R.id.duree);
        nb_placeInput=findViewById(R.id.nombre_places);
        btnAjouter = findViewById(R.id.btnAjouter);

        btnAjouter.setOnClickListener(view -> {
            String titre = titreInput.getText().toString().trim();
            String description = descriptionInput.getText().toString().trim();
            String competance = competanceInput.getText().toString().trim();
            String localisation = localisationInput.getText().toString().trim();
            String duree = dureeInput.getText().toString().trim();
            String nb_places = nb_placeInput.getText().toString().trim();


            if (titre.isEmpty() || description.isEmpty() || competance.isEmpty() || localisation.isEmpty() || duree.isEmpty() || nb_places.isEmpty()) {
                Toast.makeText(this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }
            int nb_place=Integer.parseInt(nb_places);
            if (nb_place <= 0) {
                Toast.makeText(this, "Le nombre de places doit être supérieur à zéro", Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            Offre offre = new Offre(user.getUid(),titre, nb_place,duree,localisation,competance,description);

            db.collection("offres")
                    .add(offre)
                    .addOnSuccessListener(documentReference -> {

                        Toast.makeText(this, "Offre ajoutée !", Toast.LENGTH_SHORT).show();
                        titreInput.setText("");
                        descriptionInput.setText("");
                        competanceInput.setText("");
                        localisationInput.setText("");
                        dureeInput.setText("");
                        nb_placeInput.setText("");

                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_offre; // le layout spécifique à cette activité
    }
}