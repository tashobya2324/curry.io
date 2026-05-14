package com.example.curryio;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantProfileActivity extends AppCompatActivity {

    private int itemCount = 0;
    private int totalPrice = 0;

    // Prices per item
    private final int[] prices = {25000, 32000, 22000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_restaurant_profile);

        TextView tvItemCount = findViewById(R.id.tvItemCount);
        TextView tvTotal     = findViewById(R.id.tvTotal);

        // ── Back button ──
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish()); // goes back to previous screen

        // ── Favourite ──
        ImageButton btnFav = findViewById(R.id.btnFavourite);
        btnFav.setOnClickListener(v ->
                Toast.makeText(this, "Added to favourites", Toast.LENGTH_SHORT).show()
        );

        // ── Add item buttons ──
        int[] addBtnIds = {R.id.btnAddItem1, R.id.btnAddItem2, R.id.btnAddItem3};

        for (int i = 0; i < addBtnIds.length; i++) {
            final int index = i;
            findViewById(addBtnIds[i]).setOnClickListener(v -> {
                itemCount++;
                totalPrice += prices[index];
                tvItemCount.setText(itemCount + " item" + (itemCount > 1 ? "s" : ""));
                tvTotal.setText("UGX " + String.format("%,d", totalPrice));
            });
        }
// Reserve a Table
        findViewById(R.id.btnReserveTable).setOnClickListener(v -> {
            Intent intent = new Intent(
                    RestaurantProfileActivity.this, TableReservationActivity.class
            );
            startActivity(intent);
        });
        // ── Order Now ──
        findViewById(R.id.btnOrderNow).setOnClickListener(v -> {
            if (itemCount == 0) {
                Toast.makeText(this, "Add items first", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(RestaurantProfileActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }
}