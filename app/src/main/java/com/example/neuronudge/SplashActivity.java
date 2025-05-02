package com.example.neuronudge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private VideoView splashVideoView;
    private boolean isVideoCompleted = false;
    private final Handler handler = new Handler();
    private final Runnable checkAuthRunnable = this::checkLoginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Handle the splash screen transition (Android 12+)
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashVideoView = findViewById(R.id.splashVideoView);

        // Set video path from raw resources
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.omp;
        splashVideoView.setVideoURI(Uri.parse(videoPath));

        splashVideoView.setOnPreparedListener(mp -> {
            mp.setLooping(false);
            mp.start();

            // Set fallback timeout (video duration + buffer)
            handler.postDelayed(checkAuthRunnable, mp.getDuration() + 500);
        });

        splashVideoView.setOnCompletionListener(mp -> {
            isVideoCompleted = true;
            checkLoginStatus();
        });

        // Fallback timer in case video fails to load
        handler.postDelayed(checkAuthRunnable, 3000);
    }

    private void checkLoginStatus() {
        // Only proceed once (in case both completion listener and timeout trigger)
        if (!isVideoCompleted) {
            isVideoCompleted = true;
            handler.removeCallbacks(checkAuthRunnable);

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Class<?> destination = currentUser != null ? HomeActivity.class : LoginActivity.class;
            startActivity(new Intent(SplashActivity.this, destination));
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (splashVideoView != null && splashVideoView.isPlaying()) {
            splashVideoView.pause();
        }
        handler.removeCallbacks(checkAuthRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (splashVideoView != null && !splashVideoView.isPlaying() && !isVideoCompleted) {
            splashVideoView.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (splashVideoView != null) {
            splashVideoView.stopPlayback();
        }
        handler.removeCallbacksAndMessages(null);
    }
}