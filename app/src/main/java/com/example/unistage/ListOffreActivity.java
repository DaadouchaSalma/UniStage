package com.example.unistage;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class ListOffreActivity extends AppCompatActivity {
    FirebaseFirestore db;

    private void deleteOffre(String documentId) {
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
    }

    /* Appelle cette fonction depuis un bouton ou un menu
    deleteButton.setOnClickListener(v -> deleteOffre(documentId));
    */


}
