package com.example.unistage.offre;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unistage.R;
import com.example.unistage.candidature.ListeCandidatsActivity;

import java.util.List;

public class OffreEncadrantAdapter extends RecyclerView.Adapter<OffreEncadrantAdapter.ViewHolder> {
    private final List<Offre> offreList;
    private final Context context;

    public OffreEncadrantAdapter(List<Offre> offreList, Context context) {
        this.offreList = offreList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_offre_encadrant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offre offre = offreList.get(position);
        holder.titre.setText(offre.getTitre());
        holder.nbCandidatures.setText("Nombre de candidatures : " + offre.getNombreCandidatures());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ListeCandidatsActivity.class);
            intent.putExtra("idOffre", offre.getId());
            context.startActivity(intent);
        });
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateOffreActivity.class);
            intent.putExtra("idOffre", offre.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return offreList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titre;
        TextView nbCandidatures;
        ImageButton btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.titreOffreEncadrant);
            nbCandidatures = itemView.findViewById(R.id.nbCandidatures);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}

