package com.example.curryio;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_profile);

        // ── Load the ProfileFragment into the container ──
        // This is the key Fragment concept:
        // We get the FragmentManager, start a transaction,
        // and place our Fragment into the FrameLayout container
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new ProfileFragment());
        ft.commit(); // execute the transaction

        // ── Bottom nav — Profile is active ──
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_profile);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_orders) {
                Intent intent = new Intent(ProfileActivity.this, OrdersActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_profile) {
                return true; // already here
            }
            return false;
        });
    }
}