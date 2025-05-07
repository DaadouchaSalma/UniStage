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
import java.util.List;

public class ListeCandidatsActivity extends AppCompatActivity {

    private ListView listView;
    private FirebaseFirestore db;
    private ArrayAdapter<String> adapter;
    private List<String> listeAffichage = new ArrayList<>();
    private List<String> listeIdEtudiants = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_candidats);

        listView = findViewById(R.id.listViewCandidats);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listeAffichage);
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

                                    String texte = prenom + " " + nom + "\n" + email;
                                    listeAffichage.add(texte);
                                    listeIdEtudiants.add(idEtudiant);
                                    adapter.notifyDataSetChanged();
                                });
                    }
                });
    }
    }
