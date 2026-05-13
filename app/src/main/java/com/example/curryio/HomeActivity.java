package com.example.curryio;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_home);

        // ── Bottom Nav ──
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_home); // Home is active by default

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_orders) {
                Toast.makeText(this, "Orders coming soon", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_profile) {
                Toast.makeText(this, "Profile coming soon", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // ── Restaurant card clicks ──
        CardView cardKatiKati = findViewById(R.id.cardKatiKati);
        cardKatiKati.setOnClickListener(v ->
                        Toast.makeText(this, "Kati Kati tapped", Toast.LENGTH_SHORT).show()
                // TODO: open RestaurantProfileActivity
        );

        // ── Category clicks ──
        CardView catHealthy = findViewById(R.id.catHealthy);
        catHealthy.setOnClickListener(v ->
                Toast.makeText(this, "Healthy Choices tapped", Toast.LENGTH_SHORT).show()
        );
    }
}