package com.example.neuronudge.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.neuronudge.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment {

    private EditText editBodyCondition, editBloodPressure, editSugarLevel, editDiseases, editBloodGroup, editMedicines;
    private Button saveButton;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize Firestore and Auth
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;

        if (userId == null) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return view;
        }

        // Initialize UI components
        editBodyCondition = view.findViewById(R.id.editBodyCondition);
        editBloodPressure = view.findViewById(R.id.editBloodPressure);
        editSugarLevel = view.findViewById(R.id.editSugarLevel);
        editDiseases = view.findViewById(R.id.editDiseases);
        editBloodGroup = view.findViewById(R.id.editBloodGroup);
        editMedicines = view.findViewById(R.id.editMedicines);
        saveButton = view.findViewById(R.id.saveHealthInfoButton);

        // Fetch user data on load
        fetchHealthDetails();

        // Save button action
        saveButton.setOnClickListener(v -> saveHealthDetails());

        return view;
    }

    private void saveHealthDetails() {
        Map<String, Object> healthData = new HashMap<>();
        healthData.put("bodyCondition", editBodyCondition.getText().toString().trim());
        healthData.put("bloodPressure", editBloodPressure.getText().toString().trim());
        healthData.put("sugarLevel", editSugarLevel.getText().toString().trim());
        healthData.put("diseases", editDiseases.getText().toString().trim());
        healthData.put("bloodGroup", editBloodGroup.getText().toString().trim());
        healthData.put("medicines", editMedicines.getText().toString().trim());

        db.collection("users").document(userId)
                .collection("health").document("details")
                .set(healthData)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(getContext(), "Health details saved", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Failed to save: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void fetchHealthDetails() {
        db.collection("users").document(userId)
                .collection("health").document("details")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        editBodyCondition.setText(documentSnapshot.getString("bodyCondition"));
                        editBloodPressure.setText(documentSnapshot.getString("bloodPressure"));
                        editSugarLevel.setText(documentSnapshot.getString("sugarLevel"));
                        editDiseases.setText(documentSnapshot.getString("diseases"));
                        editBloodGroup.setText(documentSnapshot.getString("bloodGroup"));
                        editMedicines.setText(documentSnapshot.getString("medicines"));
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Failed to fetch: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
