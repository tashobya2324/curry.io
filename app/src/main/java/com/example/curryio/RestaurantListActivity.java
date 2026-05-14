package com.example.curryio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RestaurantListActivity extends AppCompatActivity {

    private boolean isBookTable = true; // Book a Table is default active tab

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_restaurant_list);

        TextView tabPreorder  = findViewById(R.id.tabPreorder);
        TextView tabBookTable = findViewById(R.id.tabBookTable);

        // ── Tab switching ──
        tabPreorder.setOnClickListener(v -> {
            if (isBookTable) {
                isBookTable = false;
                tabPreorder.setBackgroundResource(R.drawable.bg_chip_active);
                tabPreorder.setTextColor(getResources().getColor(android.R.color.white));
                tabBookTable.setBackgroundResource(R.drawable.bg_chip_inactive);
                tabBookTable.setTextColor(getResources().getColor(android.R.color.darker_gray));
                Toast.makeText(this, "Pre-order mode", Toast.LENGTH_SHORT).show();
            }
        });

        tabBookTable.setOnClickListener(v -> {
            if (!isBookTable) {
                isBookTable = true;
                tabBookTable.setBackgroundResource(R.drawable.bg_chip_active);
                tabBookTable.setTextColor(getResources().getColor(android.R.color.white));
                tabPreorder.setBackgroundResource(R.drawable.bg_chip_inactive);
                tabPreorder.setTextColor(getResources().getColor(android.R.color.darker_gray));
                Toast.makeText(this, "Book a Table mode", Toast.LENGTH_SHORT).show();
            }
        });

        // ── Book Now buttons — all navigate to Restaurant Profile ──
        int[] bookBtns = {R.id.btnBook1, R.id.btnBook2, R.id.btnBook3, R.id.btnBook4};
        String[] names = {"The Amber Saffron", "Venezia Garden", "Kyoto Zen", "Coastal Bites"};

        for (int i = 0; i < bookBtns.length; i++) {
            final String name = names[i];
            findViewById(bookBtns[i]).setOnClickListener(v -> {
                Intent intent = new Intent(
                        RestaurantListActivity.this, RestaurantProfileActivity.class
                );
                // TODO: pass restaurant ID/name when you wire up a real backend
                startActivity(intent);
            });
        }

        // ── Bottom Nav ──
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                finish(); // go back to home
                return true;
            } else if (id == R.id.nav_orders) {
                startActivity(new Intent(this, OrdersActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }
}