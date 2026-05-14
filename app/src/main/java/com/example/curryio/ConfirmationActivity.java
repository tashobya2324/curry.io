package com.example.curryio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        // ── Read data passed from OrderActivity ──
        String arrivalTime = getIntent().getStringExtra("arrival_time");
        int    total       = getIntent().getIntExtra("total", 0);

        if (arrivalTime == null) arrivalTime = "1:00 PM";

        // ── Generate unique order number ──
        String orderNumber = "CR-" + (1000 + new Random().nextInt(9000));

        // ── Wire up views ──
        TextView tvTime    = findViewById(R.id.tvArrivalTime);
        TextView tvOrderNo = findViewById(R.id.tvOrderNumber);
        TextView tvTotal   = findViewById(R.id.tvTotal);
        ImageView ivQr     = findViewById(R.id.ivQrCode);

        // Set text
        tvTime.setText("See you at " + arrivalTime);
        tvOrderNo.setText("Order #" + orderNumber);
        tvTotal.setText("UGX " + String.format("%,d", total));

        // ── Generate QR Code ──
        // We encode the full order details so any scanner can read them
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
            // Go all the way back to Home, clear the stack
            Intent intent = new Intent(ConfirmationActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // ── View Order History ──
        findViewById(R.id.btnViewHistory).setOnClickListener(v ->
                Toast.makeText(this, "Order History coming soon", Toast.LENGTH_SHORT).show()
        );
    }

    /**
     * Converts a string into a QR code Bitmap using ZXing.
     * @param content The text to encode inside the QR code
     * @param sizePx  Width and height of the output image in pixels
     * @return        A black-and-white Bitmap ready to display in an ImageView
     */
    private Bitmap generateQRCode(String content, int sizePx) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            // Ask ZXing to encode the content into a grid of black/white squares
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, sizePx, sizePx);

            // Convert the BitMatrix into an actual Bitmap pixel by pixel
            Bitmap bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.RGB_565);
            for (int x = 0; x < sizePx; x++) {
                for (int y = 0; y < sizePx; y++) {
                    // true = dark square, false = light square
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