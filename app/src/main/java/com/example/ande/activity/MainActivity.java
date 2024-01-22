package com.example.ande.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ande.R;
import com.example.ande.helpers.DBHandler;
import com.example.ande.helpers.SessionManagement;
import com.example.ande.model.CollectionChar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;

    ImageView homeAnimal;
    private DBHandler dbHandler;
    private String mood;
    private int userId;

    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        long lastActiveMillis = prefs.getLong("lastActiveDate", 0);
        long currentMillis = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lastActiveMillis);
        int lastActiveDay = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTimeInMillis(currentMillis);
        int currentDay = calendar.get(Calendar.DAY_OF_YEAR);

        if (lastActiveDay != currentDay) {
            checkLoginStreak(lastActiveDay, currentDay);
            prefs.edit().putLong("lastActiveDate", currentMillis).apply();
        }
    }

    private void checkLoginStreak(int lastActiveDay, int currentDay) {
        CollectionChar currentChar = dbHandler.getActiveUserAnimal(userId, MainActivity.this);
        if (currentChar != null) {
            if (lastActiveDay == currentDay - 1) {
                // User was active yesterday
                dbHandler.addPointsToUserAnimal(userId, 5);
                showCheckInModal(0);
            } else if (lastActiveDay < currentDay - 1) {
                // User missed one or more days
                int daysMissed = currentDay - lastActiveDay - 1;
                dbHandler.deductPointsFromUserAnimal(userId, 10 * daysMissed);
                showCheckInModal(daysMissed);
            }
        }
        // If lastActiveDay == currentDay, do nothing (user already opened app today)
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView2);
        homeAnimal = findViewById(R.id.homePageAnimalImg);
        dbHandler = new DBHandler(MainActivity.this);

        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        userId = sessionManagement.getSession();

        Toast.makeText(MainActivity.this, "User ID" + userId, Toast.LENGTH_SHORT).show();

        mood = dbHandler.getMood(userId, getTodayDate());
        CollectionChar currentCharacter = dbHandler.getActiveUserAnimal(userId, MainActivity.this);
        homeAnimal.setImageResource(currentCharacter.getImage());

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
            showMeditationOptionsDialog();
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
            showMoodDialog();
        }
    }

    public void showMoodDialog() {

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

    public void showMeditationOptionsDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout_meditationpage_options);

        AutoCompleteTextView autoCompleteTypeTextView = dialog.findViewById(R.id.auto_complete_meditation_type_text_view);
        autoCompleteTypeTextView.setFocusable(false);
        autoCompleteTypeTextView.setFocusableInTouchMode(false);
        autoCompleteTypeTextView.setInputType(InputType.TYPE_NULL);

        String[] meditationTypeOptions = {"Guided Meditation", "Meditation Music"};

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, R.layout.dropdown_list_meditation_options, meditationTypeOptions);
        autoCompleteTypeTextView.setAdapter(typeAdapter);

        AutoCompleteTextView autoCompleteMinutesTextView = dialog.findViewById(R.id.auto_complete_meditation_minutes_text_view);
        autoCompleteMinutesTextView.setFocusable(false);
        autoCompleteMinutesTextView.setFocusableInTouchMode(false);
        autoCompleteMinutesTextView.setInputType(InputType.TYPE_NULL);

        String[] meditationMinutesOptions = {"5", "10", "15"};
        ArrayAdapter<String> minutesAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_list_meditation_options, meditationMinutesOptions) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(R.id.meditation_option_minutes_text_view);
                textView.setText(meditationMinutesOptions[position] + " Mins");
                return view;
            }
        };

        autoCompleteMinutesTextView.setAdapter(minutesAdapter);

        Button startMeditationButton = dialog.findViewById(R.id.startMeditationButton);

        startMeditationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String meditationType = autoCompleteTypeTextView.getText().toString();
                String meditationDuration = autoCompleteMinutesTextView.getText().toString();

                if (meditationType.isEmpty() || meditationType.equals("Type")) {
                    Toast.makeText(MainActivity.this, "Please select a meditation type", Toast.LENGTH_SHORT).show();
                } else if (meditationDuration.isEmpty() || meditationDuration.equals("Duration")) {
                    Toast.makeText(MainActivity.this, "Please select a meditation duration", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, MeditationPage.class);
                    intent.putExtra("meditationType", meditationType);
                    intent.putExtra("meditationDuration", meditationDuration);
                    startActivity(intent);
                    dialog.dismiss();
                }
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



    private void showCheckInModal(int daysMissed) {
        // Create a Dialog instance
        Dialog dialog = new Dialog(this);

        // Set the custom layout
        dialog.setContentView(R.layout.check_in_modal);

        // Find views and set values or listeners
        TextView missedDaysTextView = dialog.findViewById(R.id.missedDaysTextView);
        TextView conditionPetTextView = dialog.findViewById(R.id.conditionPetTextView);
       ImageView petConditionImage = dialog.findViewById(R.id.petConditionImage);
        CollectionChar currentCharacter = dbHandler.getActiveUserAnimal(userId, MainActivity.this);
        missedDaysTextView.setText("");

        petConditionImage.setImageResource(currentCharacter.getImage());
        Button button = dialog.findViewById(R.id.customModalButton);


        String message;
        conditionPetTextView.setText("You've made your pet happier by checking in today!");
        if (daysMissed >= 1) {
            message = "You've missed " + daysMissed + " days. ";
            missedDaysTextView.setVisibility(View.VISIBLE);
            missedDaysTextView.setText(message);
            conditionPetTextView.setText("Your pet missed you dearly.");
        } else {
            missedDaysTextView.setText("");
        }
        button.setOnClickListener(v -> dialog.dismiss());


        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(layoutParams);
        }
        // Show the dialog
        dialog.show();
    }

}