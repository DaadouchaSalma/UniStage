package com.example.unistage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.unistage.candidature.ajouterCandActivity;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_base_drawer);
    }

    // Appel dans onCreate() de chaque sous-activité
    protected void setupDrawer(int layoutResID) {
        // On insère le contenu spécifique à l'activité dans le drawer
        DrawerLayout fullLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base_drawer, null);
        FrameLayout contentFrame = fullLayout.findViewById(R.id.content_frame);
        getLayoutInflater().inflate(layoutResID, contentFrame, true);
        super.setContentView(fullLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = fullLayout.findViewById(R.id.drawer_layout);
        navigationView = fullLayout.findViewById(R.id.navigation_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState(); // ← active le bouton hamburger

        View headerView = navigationView.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.userName);
        userName.setText("Ali Ben Salah");

        // Navigation menu item click listener
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
            } else if (id == R.id.nav_offres) {
                startActivity(new Intent(this, ListOffreActivity.class));
            } else if (id == R.id.nav_candidatures) {
                startActivity(new Intent(this, ajouterCandActivity.class));
            } else if (id == R.id.nav_logout) {
                // Handle logout
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}