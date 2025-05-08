package com.example.unistage.offre;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unistage.BaseActivity;
import com.example.unistage.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListeOffresEncadrantActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private OffreEncadrantAdapter adapter;
    private List<Offre> offreList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_liste_offres_encadrant);

        recyclerView = findViewById(R.id.recyclerViewOffresEncadrant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new OffreEncadrantAdapter(offreList, this);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        chargerOffres();
    }

    private void chargerOffres() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String encadrantId = user.getUid();
        db.collection("offres")
                .whereEqualTo("idEncadrant", encadrantId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String titre = doc.getString("titre");
                        String idOffre = doc.getId();
                        String description = doc.getString("description");
                        String competencesRequises = doc.getString("competencesRequises");
                        String localisation = doc.getString("localisation");
                        String duree = doc.getString("duree");
                        Long nombrePlacesLong = doc.getLong("nombrePlaces");

                        int nombrePlaces = (nombrePlacesLong != null) ? nombrePlacesLong.intValue() : 0;
                        //String dateStr = doc.getString("dateCreation");

                        Offre offre = new Offre("",titre, nombrePlaces, duree, localisation, competencesRequises, description);
                        offre.setId(doc.getId());

                        db.collection("candidatures")
                                .whereEqualTo("idOffre", offre.getId())
                                .get()
                                .addOnSuccessListener(candidatures -> {
                                    int count = candidatures.size();
                                    offre.setNombreCandidatures(count);
                                    offreList.add(offre);
                                    adapter.notifyDataSetChanged();
                                });
                    }
                });
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_liste_offres_encadrant; // le layout spécifique à cette activité
    }
}
