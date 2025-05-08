package com.example.unistage.offre;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unistage.BaseActivity;
import com.example.unistage.LogoutActivity;
import com.example.unistage.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ListOffreActivity extends LogoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.list_offre_activity);
        setupLogoutButton();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Offre> offres = new ArrayList<>();
        OffreAdapter adapter = new OffreAdapter(offres, this);
        recyclerView.setAdapter(adapter);

        // Récupération des données Firestore
        db.collection("offres").get().addOnSuccessListener(queryDocumentSnapshots -> {
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

                // Si tu veux affecter la date manuellement (car elle n'est pas dans le constructeur)
                //offre.setDateCreation(dateStr);  // Nécessite d'ajouter un setter dans ta classe Offre
                offre.setId(doc.getId());
                Log.d("FIRESTORE_ID", "Offre ID liste: " + doc.getId()+"offre"+offre.getId());
                offres.add(offre);
            }

            adapter.notifyDataSetChanged();
        });

    /*private void deleteOffre(String documentId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("offres")
                .document(documentId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Offre supprimée avec succès", Toast.LENGTH_SHORT).show();
                    // Optionnel : revenir en arrière ou rafraîchir la liste
                    finish(); // ferme l'activité courante
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erreur lors de la suppression : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }*/

    /* Appelle cette fonction depuis un bouton ou un menu
    deleteButton.setOnClickListener(v -> deleteOffre(documentId));
    */
    }
    /*@Override
    protected int getLayoutResourceId() {
        return R.layout.list_offre_activity; // le layout spécifique à cette activité
    }*/
}
