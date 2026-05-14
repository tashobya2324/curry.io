package com.example.curryio;

import android.os.Bundle;
import android.widget.Toast;
import android.content.Intent;
import android.widget.TextView;
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
                Intent intent = new Intent(HomeActivity.this, OrdersActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_profile) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        // ── Restaurant card clicks ──
        CardView cardKatiKati = findViewById(R.id.cardKatiKati);
        cardKatiKati.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RestaurantProfileActivity.class);
            startActivity(intent);
        });

        // ── Category clicks ──
        CardView catHealthy = findViewById(R.id.catHealthy);
        catHealthy.setOnClickListener(v ->
                Toast.makeText(this, "Healthy Choices tapped", Toast.LENGTH_SHORT).show()
        );

        // ── See All → Restaurant Listing ──
        TextView tvSeeAll = findViewById(R.id.tvSeeAll);
        tvSeeAll.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RestaurantListActivity.class);
            startActivity(intent);
        });
    }
}