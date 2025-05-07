package com.example.unistage.candidature;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.unistage.R;

import java.util.List;
import java.util.Map;

public class CandidatAdapter extends BaseAdapter {
    private final Context context;
    private final List<Map<String, String>> data;

    public CandidatAdapter(Context context, List<Map<String, String>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Map<String, String> getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_candidat, parent, false);
        }

        TextView nomPrenomView = convertView.findViewById(R.id.textNomPrenom);
        TextView emailView = convertView.findViewById(R.id.textEmail);

        Map<String, String> candidat = getItem(position);
        String nom = candidat.get("nom");
        String prenom = candidat.get("prenom");
        String email = candidat.get("email");

        nomPrenomView.setText(prenom + " " + nom);
        emailView.setText(email);

        return convertView;
    }
}
