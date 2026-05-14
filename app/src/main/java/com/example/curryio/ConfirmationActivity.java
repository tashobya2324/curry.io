package com.example.curryio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Random;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_confirmation);

        // ── Read data passed from previous screen ──
        String arrivalTime    = getIntent().getStringExtra("arrival_time");
        int    total          = getIntent().getIntExtra("total", 0);
        boolean isReservation = getIntent().getBooleanExtra("is_reservation", false);
        int    partySize      = getIntent().getIntExtra("party_size", 2);
        String date           = getIntent().getStringExtra("date");

        if (arrivalTime == null) arrivalTime = "1:00 PM";

        // ── Generate unique order number ──
        String orderNumber = "CR-" + (1000 + new Random().nextInt(9000));

        // ── Wire up views ──
        TextView tvTime    = findViewById(R.id.tvArrivalTime);
        TextView tvOrderNo = findViewById(R.id.tvOrderNumber);
        TextView tvTotal   = findViewById(R.id.tvTotal);
        ImageView ivQr     = findViewById(R.id.ivQrCode);

        // ── Set text depending on type (order vs reservation) ──
        if (isReservation) {
            tvTime.setText("Table for " + partySize
                    + " on " + (date != null ? date : "Today")
                    + " at " + arrivalTime);
            tvTotal.setText("UGX " + String.format("%,d", total)
                    + "  (Reservation Fee)");
        } else {
            tvTime.setText("See you at " + arrivalTime);
            tvTotal.setText("UGX " + String.format("%,d", total));
        }

        tvOrderNo.setText("Order #" + orderNumber);

        // ── Generate QR Code ──
        String qrContent = "ORDER:" + orderNumber
                + " | Restaurant: Urban Masala"
                + " | Time: " + arrivalTime
                + " | Total: UGX " + String.format("%,d", total);

        Bitmap qrBitmap = generateQRCode(qrContent, 500);
        if (qrBitmap != null) {
            ivQr.setImageBitmap(qrBitmap);
        }

        // ── Close button ──
        ImageButton btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmationActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // ── View Order History ──
        findViewById(R.id.btnViewHistory).setOnClickListener(v -> {
            Intent intent = new Intent(ConfirmationActivity.this, OrdersActivity.class);
            startActivity(intent);
        });
    }

    // This method does ONE thing — generate a QR bitmap and return it
    // Nothing else belongs inside here
    private Bitmap generateQRCode(String content, int sizePx) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, sizePx, sizePx);
            Bitmap bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.RGB_565);
            for (int x = 0; x < sizePx; x++) {
                for (int y = 0; y < sizePx; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}