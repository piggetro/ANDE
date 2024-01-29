package com.example.ande.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.ande.R;
import com.example.ande.helpers.SessionManagement;

public class SettingsPage extends AppCompatActivity implements View.OnClickListener {

    private static final String PREFS_NAME = "UserPrefs";
    private static final String KEY_NOTIFICATIONS = "notifications";
    private static final String KEY_SOUND = "sound";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        SwitchCompat switchNotifications = findViewById(R.id.switchNotifications);
        SwitchCompat switchSound = findViewById(R.id.switchSound);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        switchNotifications.setChecked(settings.getBoolean(KEY_NOTIFICATIONS, true));
        switchSound.setChecked(settings.getBoolean(KEY_SOUND, true));

        switchNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
                editor.putBoolean(KEY_NOTIFICATIONS, isChecked);
                editor.apply();

                Toast.makeText(SettingsPage.this, "Notifications " + (isChecked ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
            }
        });

        switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
                editor.putBoolean(KEY_SOUND, isChecked);
                editor.apply();

                Toast.makeText(SettingsPage.this, "Sound " + (isChecked ? "enabled" : "disabled"), Toast.LENGTH_SHORT).show();
            }
        });
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
