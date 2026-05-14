package com.example.curryio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrdersActivity extends AppCompatActivity {

    // Track which tab is active
    private boolean isActiveTab = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_orders);

        // ── Wire up views ──
        LinearLayout tabActive     = findViewById(R.id.tabActive);
        LinearLayout tabPast       = findViewById(R.id.tabPast);
        View indicatorActive       = findViewById(R.id.indicatorActive);
        View indicatorPast         = findViewById(R.id.indicatorPast);
        TextView tvTabActive       = findViewById(R.id.tvTabActive);
        TextView tvTabPast         = findViewById(R.id.tvTabPast);
        LinearLayout contentActive = findViewById(R.id.contentActive);
        LinearLayout contentPast   = findViewById(R.id.contentPast);

        // ── Tab switching logic ──
        tabActive.setOnClickListener(v -> {
            if (!isActiveTab) {
                isActiveTab = true;
                // Show active content, hide past
                contentActive.setVisibility(View.VISIBLE);
                contentPast.setVisibility(View.GONE);
                // Update indicators
                indicatorActive.setVisibility(View.VISIBLE);
                indicatorPast.setVisibility(View.INVISIBLE);
                // Update text colours
                tvTabActive.setTextColor(getResources().getColor(android.R.color.black));
                tvTabPast.setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
        });

        tabPast.setOnClickListener(v -> {
            if (isActiveTab) {
                isActiveTab = false;
                // Show past content, hide active
                contentActive.setVisibility(View.GONE);
                contentPast.setVisibility(View.VISIBLE);
                // Update indicators
                indicatorActive.setVisibility(View.INVISIBLE);
                indicatorPast.setVisibility(View.VISIBLE);
                // Update text colours
                tvTabActive.setTextColor(getResources().getColor(android.R.color.darker_gray));
                tvTabPast.setTextColor(getResources().getColor(android.R.color.black));
            }
        });

        // ── Back button ──
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // ── View Pickup QR Code ──
        findViewById(R.id.btnViewQR).setOnClickListener(v ->
                        Toast.makeText(this, "Showing QR for active order", Toast.LENGTH_SHORT).show()
                // TODO: open ConfirmationActivity with this order's data
        );

        // ── Bottom Nav — Orders is the active tab ──
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_orders);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Intent intent = new Intent(OrdersActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_orders) {
                return true; // already here
            } else if (id == R.id.nav_profile) {
                Toast.makeText(this, "Profile coming soon", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }
}