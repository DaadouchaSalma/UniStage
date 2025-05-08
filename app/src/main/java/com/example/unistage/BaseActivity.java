package com.example.unistage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.unistage.candidature.ajouterCandActivity;
import com.example.unistage.offre.AddOffreActivity;
import com.example.unistage.offre.ListeOffresEncadrantActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    protected FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_drawer);
        mAuth = FirebaseAuth.getInstance();

        //Récuperer les views
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ImageButton hamburgerButton = findViewById(R.id.hamburger_button);
        hamburgerButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));


        // Gère le layout spécifique à chaque activité
        int layoutId = getLayoutResourceId();
        if (layoutId != 0) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View contentView = inflater.inflate(layoutId, null);
            FrameLayout contentFrame = findViewById(R.id.content_frame);
            contentFrame.addView(contentView);
        }


        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_offres) {
                Intent intent = new Intent(this, AddOffreActivity.class); // à adapter à ton activité
                startActivity(intent);
            } else if (id == R.id.nav_candidatures) {
                Intent intent = new Intent(this, ListeOffresEncadrantActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_logout) {
                mAuth.signOut();
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
        

        // Récupère nav header
        View headerView = navigationView.getHeaderView(0);
        TextView nameTextView = headerView.findViewById(R.id.textView_name);
        TextView typeTextView = headerView.findViewById(R.id.textView_type);

       // Récupérer le user connecté
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (user != null) {
            db.collection("encadrant").document(user.getUid()).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String nom = documentSnapshot.getString("lastName");
                            String prenom = documentSnapshot.getString("firstName");

                            nameTextView.setText(nom + " " + prenom);
                            typeTextView.setText("Encadrant");
                        }
                    })
                    .addOnFailureListener(e -> {
                        nameTextView.setText("Erreur de chargement");
                        typeTextView.setText("");
                    });
        }


    }

    protected abstract int getLayoutResourceId();
}