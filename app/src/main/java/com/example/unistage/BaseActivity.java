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
import com.google.android.material.navigation.NavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_drawer);

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

        // Tu peux ici ajouter un listener pour gérer les clics du menu :
        navigationView.setNavigationItemSelectedListener(item -> {
            // Gère les actions ici (ex: startActivity)
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    protected abstract int getLayoutResourceId();
}