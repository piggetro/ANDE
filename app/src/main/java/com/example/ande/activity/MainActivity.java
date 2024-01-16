package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ande.R;
import com.example.ande.helpers.DBHandler;
import com.example.ande.helpers.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    private DBHandler dbHandler;
    private String mood;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView2);
        dbHandler = new DBHandler(MainActivity.this);

        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        userId = sessionManagement.getSession();

        Toast.makeText(MainActivity.this, "User ID" + userId, Toast.LENGTH_SHORT).show();

        mood = dbHandler.getMood(userId, getTodayDate());

        if (mood.equals("happy")) {
            imageView.setEnabled(false);
            imageView.setImageResource(R.drawable.happy);
        } else if (mood.equals("sad")) {
            imageView.setEnabled(false);
            imageView.setImageResource(R.drawable.sad);
        } else if (mood.equals("angry")) {
            imageView.setEnabled(false);
            imageView.setImageResource(R.drawable.angry);
        } else if (mood.equals("neutral")) {
            imageView.setEnabled(false);
            imageView.setImageResource(R.drawable.neutral);
        } else {
            imageView.setImageResource(R.drawable.homepage_emoji);
        }

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
        } else if (v.getId() == R.id.imageView2) {
            showDialog();
        }
    }

    public void showDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout_homepage_emoji);

        LinearLayout happyLayout = dialog.findViewById(R.id.happyOption);
        LinearLayout sadLayout = dialog.findViewById(R.id.sadOption);
        LinearLayout angryLayout = dialog.findViewById(R.id.angryOption);
        LinearLayout neutralLayout = dialog.findViewById(R.id.neutralOption);

        happyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setEnabled(false);
                imageView.setImageResource(R.drawable.happy);
                dbHandler.addMood(userId, "happy");
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Happy", Toast.LENGTH_SHORT).show();
            }
        });

        sadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setEnabled(false);
                imageView.setImageResource(R.drawable.sad);
                dbHandler.addMood(userId, "sad");
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Sad", Toast.LENGTH_SHORT).show();
            }
        });

        angryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setEnabled(false);
                imageView.setImageResource(R.drawable.angry);
                dbHandler.addMood(userId, "angry");
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Angry", Toast.LENGTH_SHORT).show();
            }
        });

        neutralLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setEnabled(false);
                imageView.setImageResource(R.drawable.neutral);
                dbHandler.addMood(userId, "neutral");
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Neutral", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    private String getTodayDate() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Define the desired date format (MM/dd/yyyy)
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        // Format the current date
        String formattedDate = dateFormat.format(currentDate);

        return formattedDate;
    }

}