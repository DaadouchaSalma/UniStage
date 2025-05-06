package com.example.unistage.offre;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unistage.R;

public class DetailOffreActivity extends AppCompatActivity {
    TextView titre, description, competence, duree, localisation, places;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_offre);

        titre = findViewById(R.id.detailTitre);
        description = findViewById(R.id.detailDescription);
        competence = findViewById(R.id.detailCompetence);
        duree = findViewById(R.id.detailDuree);
        localisation = findViewById(R.id.detailLocalisation);
        places = findViewById(R.id.detailPlaces);
        image = findViewById(R.id.detailImage);

        Intent intent = getIntent();
        titre.setText(intent.getStringExtra("offreTitre"));
        description.setText(intent.getStringExtra("offreDescription"));
        competence.setText(intent.getStringExtra("offreCompetence"));
        duree.setText("" + intent.getStringExtra("offreDuree"));
        localisation.setText("" + intent.getStringExtra("offreLocalisation"));
        places.setText("" + intent.getIntExtra("offrePlaces", 0));
        image.setImageResource(intent.getIntExtra("imageResId", R.drawable.img1)); // Fallback image
    }
}
