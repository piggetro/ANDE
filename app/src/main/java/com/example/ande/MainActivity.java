package com.example.ande;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.meditationButton) {
            Intent intent = new Intent(this, MeditationPage.class);
            startActivity(intent);
        } else if (v.getId() == R.id.settingsButton) {
            Intent intent = new Intent(this, SettingsPage.class);
            startActivity(intent);
        } else if (v.getId() == R.id.calendarButton) {
            Intent intent = new Intent(this, CalendarPage.class);
            startActivity(intent);
        } else if (v.getId() == R.id.farmButton) {
            Intent intent = new Intent(this, FarmCollection.class);
            startActivity(intent);
        }
    }
}