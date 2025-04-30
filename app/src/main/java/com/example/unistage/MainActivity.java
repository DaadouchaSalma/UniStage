package com.example.unistage;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);
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
            Offre offre = new Offre(titre, nb_place,duree,localisation,competance,description);

            db.collection("offres")
                    .add(offre)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Offre ajoutée !", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Erreur : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }


}