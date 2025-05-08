package com.example.unistage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unistage.offre.AddOffreActivity;
import com.example.unistage.offre.ListOffreActivity;
import com.example.unistage.offre.ListeOffresEncadrantActivity;
import com.example.unistage.offre.UpdateOffreActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

     FirebaseAuth mAuth;
     FirebaseFirestore db ;
     EditText emailEditText, passwordEditText;
     Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);
        // instancier firebaseauth
        mAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        // récuperer les views
        emailEditText = findViewById(R.id.emailLogin);
        passwordEditText = findViewById(R.id.passwordlogin);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> loginUser());
        // redirection vers le register
        findViewById(R.id.registerLink).setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }
    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("L'adresse e-mail est obligatoire");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Veuillez saisir une adresse e-mail valide");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Le mot de passe est obligatoire");
            passwordEditText.requestFocus();
            return;
        }



        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Récupère l'utilisateur connecté
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Récupère le rôle de user
                            db.collection("etudiant").document(user.getUid()).get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            // Si etudiant
                                            Toast.makeText(LoginActivity.this, "Connexion réussie!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, ListOffreActivity.class));
                                            finish();
                                        } else {

                                            db.collection("encadrant").document(user.getUid()).get()
                                                    .addOnSuccessListener(documentSnapshotEncadrant -> {
                                                        if (documentSnapshotEncadrant.exists()) {
                                                            // Si encadrant
                                                            Toast.makeText(LoginActivity.this, "Connexion réussie!", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(LoginActivity.this, ListeOffresEncadrantActivity.class));
                                                            finish();
                                                        } else {
                                                            // ni etudiant ni encadrant
                                                            Toast.makeText(LoginActivity.this, "Utilisateur inconnu!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(LoginActivity.this, "Erreur lors de la récupération des données!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(e -> {

                                        Toast.makeText(LoginActivity.this, "Erreur lors de la récupération des données!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    } else {
                        // Si la connexion échoue
                        Toast.makeText(LoginActivity.this, "La connexion a échoué: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
