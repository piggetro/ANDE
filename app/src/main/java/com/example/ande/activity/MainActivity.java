package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ande.R;
import com.example.ande.helpers.SessionManagement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        int userId = sessionManagement.getSession();

        Toast.makeText(MainActivity.this, "User ID" + userId, Toast.LENGTH_SHORT).show();

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