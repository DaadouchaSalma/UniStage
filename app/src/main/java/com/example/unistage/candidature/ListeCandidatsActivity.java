package com.example.unistage.candidature;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unistage.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListeCandidatsActivity extends AppCompatActivity {

    private ListView listView;
    private FirebaseFirestore db;

    private CandidatAdapter adapter ;
    private List<String> listeAffichage = new ArrayList<>();
    private List<String> listeIdEtudiants = new ArrayList<>();
    List<Map<String, String>> listeCandidats = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_candidats);


        ListView listView = findViewById(R.id.listViewCandidats);
        adapter = new CandidatAdapter(this, listeCandidats);
        listView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        String idOffre = getIntent().getStringExtra("idOffre");
        chargerCandidats(idOffre);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String idEtudiant = listeIdEtudiants.get(position);

            Intent intent = new Intent(ListeCandidatsActivity.this, DetailsCandidatureActivity.class);
            intent.putExtra("idEtudiant", idEtudiant);
            intent.putExtra("idOffre", idOffre); // on transmet aussi l'offre
            startActivity(intent);
        });

    }

    private void chargerCandidats(String idOffre) {
        db.collection("candidatures")
                .whereEqualTo("idOffre", idOffre)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String idEtudiant = doc.getString("idEtudiant");

                        db.collection("etudiant")
                                .document(idEtudiant)
                                .get()
                                .addOnSuccessListener(etudoc -> {
                                    String nom = etudoc.getString("lastName");
                                    String prenom = etudoc.getString("firstName");
                                    String email = etudoc.getString("email");

                                    // Créer une map contenant les données du candidat
                                    Map<String, String> candidat = new HashMap<>();
                                    candidat.put("nom", nom);
                                    candidat.put("prenom", prenom);
                                    candidat.put("email", email);

                                    // Ajouter à la liste et mettre à jour l'adapter
                                    listeCandidats.add(candidat);
                                    listeIdEtudiants.add(idEtudiant);
                                    adapter.notifyDataSetChanged();
                                });
                    }
                });
    }
}
