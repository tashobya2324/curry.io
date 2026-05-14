package com.example.curryio;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    // Fragments inflate their own layout in onCreateView
    // instead of using setContentView like Activities do
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate turns the XML file into an actual View object
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // ── Wire up menu rows ──
        view.findViewById(R.id.rowPersonalInfo).setOnClickListener(v ->
                Toast.makeText(getContext(), "Personal Information", Toast.LENGTH_SHORT).show()
        );

        view.findViewById(R.id.rowPayment).setOnClickListener(v ->
                Toast.makeText(getContext(), "Payment Methods", Toast.LENGTH_SHORT).show()
        );

        view.findViewById(R.id.rowAddresses).setOnClickListener(v ->
                Toast.makeText(getContext(), "Saved Addresses", Toast.LENGTH_SHORT).show()
        );

        view.findViewById(R.id.rowHelp).setOnClickListener(v ->
                Toast.makeText(getContext(), "Help & Support", Toast.LENGTH_SHORT).show()
        );

        view.findViewById(R.id.rowSettings).setOnClickListener(v ->
                Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show()
        );

        view.findViewById(R.id.btnEditPhoto).setOnClickListener(v ->
                Toast.makeText(getContext(), "Edit profile photo", Toast.LENGTH_SHORT).show()
        );

        // ── Log Out ──
        TextView tvLogOut = view.findViewById(R.id.tvLogOut);
        tvLogOut.setOnClickListener(v -> {
            // Navigate to Login and clear everything off the back stack
            // so the user can't press Back to return to the app
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view; // return the inflated view — this is what gets displayed
    }
}