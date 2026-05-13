package com.example.curryio;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_login);

        EditText etPassword  = findViewById(R.id.etPassword);
        ImageView ivToggle   = findViewById(R.id.ivTogglePassword);
        Button btnLogin      = findViewById(R.id.btnLogin);
        Button btnGoogle     = findViewById(R.id.btnGoogle);
        Button btnApple      = findViewById(R.id.btnApple);
        TextView tvForgot    = findViewById(R.id.tvForgotPassword);
        TextView tvCreate    = findViewById(R.id.tvCreateAccount);

        // ── Password toggle ──
        ivToggle.setOnClickListener(v -> {
            if (isPasswordVisible) {
                etPassword.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                );
                ivToggle.setImageResource(R.drawable.ic_eye);
            } else {
                etPassword.setInputType(
                        InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                );
                ivToggle.setImageResource(R.drawable.ic_eye);
            }
            isPasswordVisible = !isPasswordVisible;
            etPassword.setSelection(etPassword.getText().length());
        });

        // ── Log In ──
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish(); // so user can't press Back to return to login
        });

        // ── Forgot Password ──
        tvForgot.setOnClickListener(v ->
                Toast.makeText(this, "Forgot password tapped", Toast.LENGTH_SHORT).show()
        );

        // ── Google ──
        btnGoogle.setOnClickListener(v ->
                Toast.makeText(this, "Google sign-in coming soon", Toast.LENGTH_SHORT).show()
        );

        // ── Apple ──
        btnApple.setOnClickListener(v ->
                Toast.makeText(this, "Apple sign-in coming soon", Toast.LENGTH_SHORT).show()
        );

        // ── Go to Sign Up ──
        tvCreate.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}