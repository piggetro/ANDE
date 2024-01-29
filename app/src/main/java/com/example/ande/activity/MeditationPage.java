package com.example.ande.activity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.ande.R;
import com.example.ande.helpers.DBHandler;
import com.example.ande.helpers.PauseableCountDownTimer;
import com.example.ande.helpers.SessionManagement;

public class MeditationPage extends AppCompatActivity implements View.OnClickListener {

    DBHandler dbHandler = new DBHandler(this);
    private ProgressBar progressBar;
    private TextView progressText;
    private long TOTAL_TIME;
    private static final long INTERVAL = 1; // Update interval in milliseconds
    private static final int REQUEST_NOTIFICATION_PERMISSION = 1;

    private MediaPlayer mediaPlayer;
    private Button pauseButton;
    private boolean isPaused = false;
    private PauseableCountDownTimer countDownTimer;
    private ImageView muteAudioButton;
    private boolean isAudioMuted = false;
    private boolean isMeditationEnded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditation_page);

        Intent intent = getIntent();
        String meditationType = intent.getStringExtra("meditationType");
        String meditationDuration = intent.getStringExtra("meditationDuration");

        if (meditationType == null || meditationDuration == null || meditationType.equals("Type") || meditationDuration.equals("Duration")) {
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
            return;
        }

        SharedPreferences settings = getSharedPreferences("UserPrefs", 0);
        boolean enableNotifications = settings.getBoolean("notifications", true);
        boolean enableSound = settings.getBoolean("sound", true);
        isAudioMuted = !enableSound;

        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);

        pauseButton = findViewById(R.id.meditationPauseButton);
        pauseButton.setOnClickListener(this);

        muteAudioButton = findViewById(R.id.meditationMuteAudioButton);
        muteAudioButton.setOnClickListener(this);

        int audioResource = chooseAudioResource(meditationType, meditationDuration);
        mediaPlayer = MediaPlayer.create(this, audioResource);
        mediaPlayer.setWakeMode(getApplicationContext(), 1);
        TOTAL_TIME = mediaPlayer.getDuration();

        mediaPlayer.start();

        if (isAudioMuted) {
            mediaPlayer.setVolume(0, 0);
            muteAudioButton.setImageResource(R.drawable.volume_muted_icon);
        }

        if (enableNotifications) {
            showNotification();
        }

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

                cancelNotification();

                SessionManagement sessionManagement = new SessionManagement(MeditationPage.this);
                int userId = sessionManagement.getSession();
                try {
                    dbHandler.addMeditation(userId, Integer.parseInt(meditationDuration));
                } catch (SQLException e) {
                    Toast.makeText(MeditationPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(MeditationPage.this, "Meditation Session Ended. Enjoy the rest of your day!", Toast.LENGTH_SHORT).show();

                isMeditationEnded = true;
            }

        };

        countDownTimer.start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.meditationBackButton) {
            if (!isMeditationEnded) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            countDownTimer.cancel();
            cancelNotification();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.meditationPauseButton) {
            handlePauseButtonClick();
        } else if (v.getId() == R.id.meditationMuteAudioButton) {
            handleMuteAudioButtonClick();
        }
    }

    private void handlePauseButtonClick() {
        if (!isMeditationEnded) {
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

    private void showNotification() {
        // Create an explicit intent for the notification
        Intent notificationIntent = new Intent(this, MeditationPage.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        // Create a notification channel (for Android 8.0 and above)
        createNotificationChannel();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MeditationChannel")
                .setSmallIcon(R.drawable.moodease_noline)
                .setContentTitle("Meditation in Progress")
                .setContentText("Your meditation session is running")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setAutoCancel(false)
                .setSilent(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    private void cancelNotification() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(1);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MeditationChannel";
            String description = "Channel for meditation notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MeditationChannel", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showNotification();
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }



}


