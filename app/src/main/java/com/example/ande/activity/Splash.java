package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ande.R;
import com.example.ande.helpers.SessionManagement;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionManagement sessionManagement = new SessionManagement(Splash.this);
        int userId = sessionManagement.getSession();
        // Launch the layout -> splash.xml
        setContentView(R.layout.activity_splash_screen);
        Thread splashThread = new Thread() {

            public void run() {
                try {
                    // sleep time in milliseconds (3000 = 3sec)
                    sleep(3000);
                }  catch(InterruptedException e) {
                    // Trace the error
                    e.printStackTrace();
                } finally
                {
                    // Launch the MainActivity class
                    Intent intent;
                    if (userId == -1) {
                        intent = new Intent(Splash.this, LoginSignupPage.class);
                    } else {
                        intent = new Intent(Splash.this, MainActivity.class);
                    }
                    startActivity(intent);
                }

            }
        };
        // To Start the thread
        splashThread.start();
    }
}
