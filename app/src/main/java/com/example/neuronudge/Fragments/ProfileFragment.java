package com.example.neuronudge.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.neuronudge.ChangePasswordActivity;
import com.example.neuronudge.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private TextView nameTextView, emailTextView, activitiesTextView, medicationsTextView;
    private EditText activitiesInput, medicationsInput;
    private Button saveButton, changePasswordButton;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // TextViews
        nameTextView = view.findViewById(R.id.name);
        emailTextView = view.findViewById(R.id.email);
        activitiesTextView = view.findViewById(R.id.activitiesLabel);
        medicationsTextView = view.findViewById(R.id.medicationsLabel);  // Adjust this if needed

        // Input fields (for editing)
        activitiesInput = view.findViewById(R.id.activitiesInput);
        medicationsInput = view.findViewById(R.id.medicationsInput);

        // Buttons
        saveButton = view.findViewById(R.id.saveProfileBtn);
        changePasswordButton = view.findViewById(R.id.editProfileBtn);
        changePasswordButton.setText("Change Password");

        // Firebase
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        currentUser = auth.getCurrentUser();

        loadUserProfile();

        saveButton.setOnClickListener(v -> saveUserProfile());
        changePasswordButton.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class))
        );

        return view;
    }

    private void loadUserProfile() {
        if (currentUser == null) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();
        DocumentReference docRef = firestore.collection("users")
                .document(userId)
                .collection("personal_details")
                .document("details");

        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String name = documentSnapshot.getString("fullName");
                String email = documentSnapshot.getString("email");
                String activities = documentSnapshot.getString("activities");
                String medications = documentSnapshot.getString("medications");

                nameTextView.setText(name != null ? name : "No Name");
                emailTextView.setText(email != null ? email : "No Email");
                activitiesInput.setText(activities != null ? activities : "");
                medicationsInput.setText(medications != null ? medications : "");

            } else {
                Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(getContext(), "Failed to load profile: " + e.getMessage(), Toast.LENGTH_SHORT).show()
        );
    }

    private void saveUserProfile() {
        if (currentUser == null) return;

        String userId = currentUser.getUid();
        String activities = activitiesInput.getText().toString().trim();
        String medications = medicationsInput.getText().toString().trim();

        Map<String, Object> updates = new HashMap<>();
        updates.put("activities", activities);
        updates.put("medications", medications);

        firestore.collection("users")
                .document(userId)
                .collection("personal_details")
                .document("details")
                .update(updates)
                .addOnSuccessListener(unused ->
                        Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Failed to update: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
