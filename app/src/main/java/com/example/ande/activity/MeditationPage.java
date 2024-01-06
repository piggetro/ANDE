package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ande.R;

public class MeditationPage extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private TextView progressText;
    private static final long TOTAL_TIME = 10 * 60 * 1000; // 10 minutes in milliseconds
    private static final long INTERVAL = 1000; // Update interval in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_page);

        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);

        CountDownTimer countDownTimer = new CountDownTimer(TOTAL_TIME, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                long remainingTime = millisUntilFinished / 1000; // Convert to seconds
                long minutes = remainingTime / 60;
                long seconds = remainingTime % 60;
                String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
                int progress = (int) ((TOTAL_TIME - millisUntilFinished) * 100 / TOTAL_TIME);
//                int progress = (int) (millisUntilFinished * 100 / TOTAL_TIME);
                progressBar.setProgress(progress);
                progressText.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                progressBar.setProgress(100);
                progressText.setText("00:00");
            }
        };

        countDownTimer.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.meditationBackButton) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}

