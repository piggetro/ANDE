package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ande.R;
import com.example.ande.helpers.PauseableCountDownTimer;

public class MeditationPage extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private TextView progressText;
    private long TOTAL_TIME;
    private static final long INTERVAL = 1000; // Update interval in milliseconds
    private MediaPlayer mediaPlayer;
    private Button pauseButton;
    private boolean isPaused = false;
    private PauseableCountDownTimer countDownTimer;
    private ImageView muteAudioButton;
    private boolean isAudioMuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_page);

        Intent intent = getIntent();
        String meditationType = intent.getStringExtra("meditationType");
        String meditationMinutes = intent.getStringExtra("meditationMinutes");

        if (meditationType == null || meditationMinutes == null || meditationType.equals("Type") || meditationMinutes.equals("Duration")) {
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
            return;
        }

        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);

        pauseButton = findViewById(R.id.meditationPauseButton);
        pauseButton.setOnClickListener(this);

        muteAudioButton = findViewById(R.id.meditationMuteAudioButton);
        muteAudioButton.setOnClickListener(this);

        int audioResource = chooseAudioResource(meditationType, meditationMinutes);
        mediaPlayer = MediaPlayer.create(this, audioResource);
        mediaPlayer.setWakeMode(getApplicationContext(), 1);
        TOTAL_TIME = mediaPlayer.getDuration();

        mediaPlayer.start();

        countDownTimer = new PauseableCountDownTimer(TOTAL_TIME, INTERVAL) {
            @Override
            public void onTickCustom(long millisUntilFinished) {
                long remainingTime = millisUntilFinished / 1000; // Convert to seconds
                long minutes = remainingTime / 60;
                long seconds = remainingTime % 60;
                String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
                int progress = (int) ((TOTAL_TIME - millisUntilFinished) * 100 / TOTAL_TIME);
                progressBar.setProgress(progress);
                progressText.setText(timeLeftFormatted);
            }

            @Override
            public void onFinishCustom() {
                mediaPlayer.stop();
                mediaPlayer.release();

                progressBar.setProgress(100);
                progressText.setText("00:00");
            }
        };

        countDownTimer.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.meditationBackButton) {
            mediaPlayer.stop();
            mediaPlayer.release();
            countDownTimer.cancel();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.meditationPauseButton) {
            handlePauseButtonClick();
        } else if (v.getId() == R.id.meditationMuteAudioButton) {
            handleMuteAudioButtonClick();
        }
    }

    private void handlePauseButtonClick() {
        if (!isPaused) {
            mediaPlayer.pause();
            pauseButton.setText("RESUME");
            countDownTimer.pause();
        } else {
            mediaPlayer.start();
            pauseButton.setText("PAUSE");
            countDownTimer.resume();
        }
        isPaused = !isPaused;
    }

    private void handleMuteAudioButtonClick() {
        if (!isAudioMuted) {
            mediaPlayer.setVolume(0, 0);
            muteAudioButton.setImageResource(R.drawable.volume_muted_icon);
        } else {
            mediaPlayer.setVolume(1, 1);
            muteAudioButton.setImageResource(R.drawable.volume_icon);
        }
        isAudioMuted = !isAudioMuted;
    }

    private int chooseAudioResource(String meditationType, String meditationMinutes) {
        // Default audio resource
        int audioResource = R.raw.guided_meditation_10_minutes;

        // Choose audio resource based on meditation type and minutes
        if (meditationType.equals("Guided Meditation")) {
            switch (meditationMinutes) {
                case "5":
                    audioResource = R.raw.guided_meditation_5_minutes;
                    break;
                case "10":
                    audioResource = R.raw.guided_meditation_10_minutes;
                    break;
                case "15":
                    audioResource = R.raw.guided_meditation_15_minutes;
                    break;
            }
        } else if (meditationType.equals("Meditation Music")) {
            switch (meditationMinutes) {
                case "5":
                    audioResource = R.raw.meditation_music_5_minutes;
                    break;
                case "10":
                    audioResource = R.raw.meditation_music_10_minutes;
                    break;
                case "15":
                    audioResource = R.raw.meditation_music_15_minutes;
                    break;
            }
        }

        return audioResource;
    }

}


