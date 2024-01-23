package com.example.ande.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ande.R;
import com.example.ande.helpers.SessionManagement;

public class SettingsPage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.settingsPageBackButton) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.logout || v.getId() == R.id.logoutIcon) {
            SessionManagement sessionManagement = new SessionManagement(SettingsPage.this);
            sessionManagement.removeSession();
            Intent intent = new Intent(this, LoginSignupPage.class);
            startActivity(intent);
        }
    }
}