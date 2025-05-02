package com.example.neuronudge;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_home);

        // Ensure we have a clean state
        if (savedInstanceState == null) {
            setupNavigation();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
        setupNavigation();
    }

    private void setupNavigation() {
        try {
            // Get the NavHostFragment
            navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);

            // Setup BottomNavigationView
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_menu);
            NavigationUI.setupWithNavController(bottomNav, navController);

            // Optional: Add navigation listener
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                Log.d(TAG, "Navigated to: " + destination.getLabel());
            });

        } catch (Exception e) {
            Log.e(TAG, "Navigation setup failed", e);
            // Handle error or restart activity
            recreate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        // Clean up any listeners if needed
    }
}