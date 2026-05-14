package com.example.curryio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TableReservationActivity extends AppCompatActivity {

    private static final int RESERVATION_FEE = 10000;

    // Track selections
    private int selectedParty    = 2;
    private int selectedDateChip = R.id.dateChip2;
    private int selectedTimeChip = R.id.time700;
    private String selectedTime  = "7:00 PM";
    private String selectedDate  = "Tue, 13";

    // All party size chip IDs
    private final int[] partyIds = {
            R.id.party1, R.id.party2, R.id.party3,
            R.id.party4, R.id.party5, R.id.party6,
            R.id.party7, R.id.party8
    };

    // All date chip IDs + labels
    private final int[] dateIds = {
            R.id.dateChip1, R.id.dateChip2, R.id.dateChip3,
            R.id.dateChip4, R.id.dateChip5
    };
    private final String[] dateLabels = {
            "Mon, 12", "Tue, 13", "Wed, 14", "Thu, 15", "Fri, 16"
    };

    // All time chip IDs + labels
    private final int[] timeIds = {
            R.id.time600, R.id.time630, R.id.time700,
            R.id.time730, R.id.time800, R.id.time830
    };
    private final String[] timeLabels = {
            "6:00 PM", "6:30 PM", "7:00 PM",
            "7:30 PM", "8:00 PM", "8:30 PM"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_table_reservation);

        // ── Back ──
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // ── Party size chips ──
        for (int i = 0; i < partyIds.length; i++) {
            final int size  = i + 1;
            final int chipId = partyIds[i];
            findViewById(chipId).setOnClickListener(v -> {
                // Reset all
                for (int id : partyIds) {
                    findViewById(id).setBackgroundResource(R.drawable.bg_square_chip_inactive);
                    ((TextView) findViewById(id)).setTextColor(
                            getResources().getColor(android.R.color.black));
                }
                // Select tapped
                v.setBackgroundResource(R.drawable.bg_square_chip_active);
                ((TextView) v).setTextColor(
                        getResources().getColor(android.R.color.white));
                selectedParty = size;
            });
        }

        // ── Date chips ──
        for (int i = 0; i < dateIds.length; i++) {
            final int dateChipId = dateIds[i];
            final String label   = dateLabels[i];
            View chip = findViewById(dateChipId);
            chip.setOnClickListener(v -> {
                // Reset all — date chips are LinearLayouts containing two TextViews
                resetDateChips();
                // Select tapped
                v.setBackgroundResource(R.drawable.bg_square_chip_active);
                setDateChipTextColor(v, "#FFFFFF", "#CCCCCC");
                selectedDateChip = dateChipId;
                selectedDate     = label;
            });
        }

        // ── Time chips ──
        for (int i = 0; i < timeIds.length; i++) {
            final int timeChipId = timeIds[i];
            final String label   = timeLabels[i];
            findViewById(timeChipId).setOnClickListener(v -> {
                // Reset all
                for (int id : timeIds) {
                    findViewById(id).setBackgroundResource(R.drawable.bg_square_chip_inactive);
                    ((TextView) findViewById(id)).setTextColor(
                            getResources().getColor(android.R.color.black));
                }
                // Select tapped
                v.setBackgroundResource(R.drawable.bg_button_amber);
                selectedTimeChip = timeChipId;
                selectedTime     = label;
            });
        }

        // ── Confirm Reservation ──
        EditText etRequests = findViewById(R.id.etSpecialRequests);

        findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            String requests = etRequests.getText().toString().trim();

            // Navigate to ConfirmationActivity with reservation details
            Intent intent = new Intent(TableReservationActivity.this, ConfirmationActivity.class);
            intent.putExtra("arrival_time", selectedTime);
            intent.putExtra("total", RESERVATION_FEE);
            intent.putExtra("is_reservation", true);
            intent.putExtra("party_size", selectedParty);
            intent.putExtra("date", selectedDate);
            intent.putExtra("special_requests", requests);
            startActivity(intent);
        });
    }

    // Resets all date chip backgrounds and text colors
    private void resetDateChips() {
        for (int id : dateIds) {
            View chip = findViewById(id);
            chip.setBackgroundResource(R.drawable.bg_square_chip_inactive);
            setDateChipTextColor(chip, "#000000", "#888888");
        }
    }

    // Sets color for both TextViews inside a date chip LinearLayout
    private void setDateChipTextColor(View chip, String numberColor, String labelColor) {
        if (chip instanceof android.widget.LinearLayout) {
            android.widget.LinearLayout layout = (android.widget.LinearLayout) chip;
            if (layout.getChildCount() >= 2) {
                ((TextView) layout.getChildAt(0))
                        .setTextColor(android.graphics.Color.parseColor(labelColor));
                ((TextView) layout.getChildAt(1))
                        .setTextColor(android.graphics.Color.parseColor(numberColor));
            }
        }
    }
}