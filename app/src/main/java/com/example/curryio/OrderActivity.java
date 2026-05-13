package com.example.curryio;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    // Unit prices
    private final int PRICE_1 = 25000;
    private final int PRICE_2 = 4500;
    private final int TAX     = 5000;

    private int qty1 = 1;
    private int qty2 = 2;

    // Currently selected time chip id
    private int selectedChipId = R.id.chip100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_order);

        // ── Views ──
        TextView tvQty1    = findViewById(R.id.tvQty1);
        TextView tvQty2    = findViewById(R.id.tvQty2);
        TextView tvPrice1  = findViewById(R.id.tvPrice1);
        TextView tvPrice2  = findViewById(R.id.tvPrice2);
        TextView tvSub     = findViewById(R.id.tvSubtotal);
        TextView btnPayNow = findViewById(R.id.btnPayNow);

        // ── Back ──
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // ── Qty controls item 1 ──
        findViewById(R.id.btnMinus1).setOnClickListener(v -> {
            if (qty1 > 1) {
                qty1--;
                tvQty1.setText(String.valueOf(qty1));
                refreshTotals(tvPrice1, tvPrice2, tvSub, btnPayNow);
            }
        });

        findViewById(R.id.btnPlus1).setOnClickListener(v -> {
            qty1++;
            tvQty1.setText(String.valueOf(qty1));
            refreshTotals(tvPrice1, tvPrice2, tvSub, btnPayNow);
        });

        // ── Qty controls item 2 ──
        findViewById(R.id.btnMinus2).setOnClickListener(v -> {
            if (qty2 > 1) {
                qty2--;
                tvQty2.setText(String.valueOf(qty2));
                refreshTotals(tvPrice1, tvPrice2, tvSub, btnPayNow);
            }
        });

        findViewById(R.id.btnPlus2).setOnClickListener(v -> {
            qty2++;
            tvQty2.setText(String.valueOf(qty2));
            refreshTotals(tvPrice1, tvPrice2, tvSub, btnPayNow);
        });

        // ── Time chip selection ──
        int[] chipIds = {
                R.id.chip1230, R.id.chip100,
                R.id.chip130,  R.id.chip200, R.id.chip230
        };

        for (int chipId : chipIds) {
            findViewById(chipId).setOnClickListener(v -> {
                // Reset previous chip
                findViewById(selectedChipId)
                        .setBackgroundResource(R.drawable.bg_chip_inactive);
                // Highlight new chip
                selectedChipId = v.getId();
                v.setBackgroundResource(R.drawable.bg_button_amber);
            });
        }

        // ── Add more items ──
        findViewById(R.id.btnAddMore).setOnClickListener(v -> {
            finish(); // goes back to restaurant profile to add more
        });

        // ── Pay Now ──
        btnPayNow.setOnClickListener(v -> {
            String time = ((TextView) findViewById(selectedChipId)).getText().toString();
            Toast.makeText(this,
                    "Order placed for " + time + "!", Toast.LENGTH_LONG).show();
            // TODO: navigate to Confirmation screen
        });

        // Set initial state
        refreshTotals(tvPrice1, tvPrice2, tvSub, btnPayNow);
    }

    private void refreshTotals(TextView tvP1, TextView tvP2,
                               TextView tvSub, TextView btnPay) {
        int subtotal = (PRICE_1 * qty1) + (PRICE_2 * qty2);
        int total    = subtotal + TAX;

        tvP1.setText("UGX " + String.format("%,d", PRICE_1 * qty1));
        tvP2.setText("UGX " + String.format("%,d", PRICE_2 * qty2));
        tvSub.setText("UGX " + String.format("%,d", subtotal));
        btnPay.setText("Pay Now  —  UGX " + String.format("%,d", total));
    }
}