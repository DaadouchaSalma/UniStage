package com.example.unistage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unistage.LoginActivity;
import com.example.unistage.R;
import com.google.firebase.auth.FirebaseAuth;

public abstract class LogoutActivity extends AppCompatActivity {

    protected FirebaseAuth mAuth;
    protected Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    protected void setupLogoutButton() {
        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(view -> {
            mAuth.signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
