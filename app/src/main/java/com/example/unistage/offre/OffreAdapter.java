package com.example.unistage.offre;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unistage.R;

import java.util.List;
import java.util.Random;

public class OffreAdapter extends RecyclerView.Adapter<OffreAdapter.ViewHolder> {
    private final List<Offre> offreList;
    private final Context context;
    private final int[] imageIds = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8, R.drawable.img9, R.drawable.img10};

    public OffreAdapter(List<Offre> offreList, Context context) {
        this.offreList = offreList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_offre, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offre offre = offreList.get(position);
        holder.titre.setText(offre.getTitre());
        holder.localisation.setText(offre.getLocalisation());

        // Sélection aléatoire d'une image
        int randomImage = imageIds[new Random().nextInt(imageIds.length)];
        holder.image.setImageResource(randomImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailOffreActivity.class);
            intent.putExtra("offreTitre", offre.getTitre());
            intent.putExtra("offreDescription", offre.getDescription());
            intent.putExtra("offreCompetence", offre.getCompetencesRequises());
            intent.putExtra("offreDuree", offre.getDuree());
            intent.putExtra("offreLocalisation", offre.getLocalisation());
            intent.putExtra("offrePlaces", offre.getNombrePlaces());
            intent.putExtra("imageResId", randomImage); // image drawable resId
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return offreList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titre;
        TextView localisation;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.titreOffre);
            localisation = itemView.findViewById(R.id.localisationOffre);
            image = itemView.findViewById(R.id.imageOffre);
        }
    }
}
