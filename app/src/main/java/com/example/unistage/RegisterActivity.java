package com.example.unistage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

     FirebaseAuth mAuth;
     FirebaseFirestore db;

     TextInputEditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
     RadioGroup roleRadioGroup;
     MaterialButton registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_activity);

        //Initialiser FirebaseAuth et Firebase firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //Récupérer les views
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        roleRadioGroup = findViewById(R.id.roleRadioGroup);
        registerButton = findViewById(R.id.registerButton);

        // register bouton
        registerButton.setOnClickListener(v -> registerUser());

        // Redirection vers le login
        findViewById(R.id.loginLink).setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish(); // finir l'activité register
        });

    }


    private void registerUser() {

        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Récupérer le role selectionné du radiobutton
        int selectedId = roleRadioGroup.getCheckedRadioButtonId();
        MaterialRadioButton selectedRadioButton = findViewById(selectedId);
        String role = selectedRadioButton.getText().toString().trim();

        // champs obligatoire
        if (firstName.isEmpty()) {
            firstNameEditText.setError("Le prénom est obligatoire");
            firstNameEditText.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            lastNameEditText.setError("Le nom est obligatoire");
            lastNameEditText.requestFocus();
            return;
        }

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

        if (password.length() < 6) {
            passwordEditText.setError("Le mot de passe doit contenir au moins 6 caractères");
            passwordEditText.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Les mots de passe ne correspondent pas");
            confirmPasswordEditText.requestFocus();
            return;
        }

        // désactiver le bouton
        registerButton.setEnabled(false);
        registerButton.setText("Inscription en cours...");

        // Create user with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Récupere le user crée et connecté
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            //
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("firstName", firstName);
                            userData.put("lastName", lastName);
                            userData.put("email", email);

                         if(role.equals("Encadrant")) {
                             db.collection("encadrant").document(user.getUid())
                                     .set(userData)
                                     .addOnSuccessListener(aVoid -> {
                                         // Redirection
                                         Toast.makeText(RegisterActivity.this, "Inscription réussie!", Toast.LENGTH_SHORT).show();
                                         startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                         finish();
                                     })
                                     .addOnFailureListener(e -> {
                                         registerButton.setEnabled(true);
                                         registerButton.setText("S'inscrire");
                                         Toast.makeText(RegisterActivity.this, "L'enregistrement a échoué" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                     });
                         }else {
                             db.collection("etudiant").document(user.getUid())
                                     .set(userData)
                                     .addOnSuccessListener(aVoid -> {
                                         // Redirection
                                         Toast.makeText(RegisterActivity.this, "Inscription réussie!", Toast.LENGTH_SHORT).show();
                                         startActivity(new Intent(RegisterActivity.this, UpdateOffreActivity.class));
                                         finish();
                                     })
                                     .addOnFailureListener(e -> {
                                         registerButton.setEnabled(true);
                                         registerButton.setText("S'inscrire");
                                         Toast.makeText(RegisterActivity.this, "L'enregistrement a échoué" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                     });
                         }
                        }
                    } else {

                        registerButton.setEnabled(true);
                        registerButton.setText("S'inscrire");
                        Toast.makeText(RegisterActivity.this, "Echec d'inscription" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
