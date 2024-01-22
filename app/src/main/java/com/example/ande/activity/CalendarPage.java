package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ande.R;
import com.example.ande.helpers.DBHandler;
import com.example.ande.helpers.SessionManagement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarPage extends AppCompatActivity implements  View.OnClickListener {
    private String selectedAbbreviatedMonthDate;
    private String selectedDate;
    private ImageView moodView;
    private TextView thoughtView;
    private TextView mediateView;
    private DBHandler dbHandler;
    private String mood;
    private String thought;
    private String meditate;
    private int userId;
    private LinearLayout calendarMoodView;
    private LinearLayout calendarThoughtsView;
    private LinearLayout calendarMeditationView;
    private ImageView viewMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);
        moodView = findViewById(R.id.calendarMood);
        thoughtView = findViewById(R.id.calendarThoughtsText);
        mediateView = findViewById(R.id.calendarMeditationText);
        calendarMoodView = findViewById(R.id.calendarMoodView);
        calendarThoughtsView = findViewById(R.id.calendarThoughts);
        calendarMeditationView = findViewById(R.id.calendarMeditationView);
        viewMore = findViewById(R.id.viewMoreCalendar);
        dbHandler = new DBHandler(CalendarPage.this);
        SessionManagement sessionManagement = new SessionManagement(CalendarPage.this);
        userId = sessionManagement.getSession();

        mood = dbHandler.getMood(userId, getTodayDateMMDDYYYY());
        thought = dbHandler.getLatestThought(userId, getTodayDateMMDDYYYY());
        meditate = dbHandler.getMeditationTime(userId, getTodayDateMMDDYYYY());

        if (mood.equals("happy")) {
            moodView.setImageResource(R.drawable.happy);
        } else if (mood.equals("sad")) {
            moodView.setImageResource(R.drawable.sad);
        } else if (mood.equals("angry")) {
            moodView.setImageResource(R.drawable.angry);
        } else if (mood.equals("neutral")) {
            moodView.setImageResource(R.drawable.neutral);
        } else {
            moodView.setImageResource(R.drawable.homepage_emoji);
        }

        if (thought.equals("")) {
            thoughtView.setText("No thoughts recorded");
        } else {
            thoughtView.setText(thought);
        }

        mediateView.setText(meditate + " Minutes");

        selectedAbbreviatedMonthDate = getTodayDate();

        TextView dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText(getTodayDateWithoutYear());

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", (month + 1), dayOfMonth, year);
                selectedDate = dayOfMonth + " " + getMonthName(month) + " " + year;
                selectedAbbreviatedMonthDate = dayOfMonth + " " + getAbbreviatedMonthName(month) + " " + year;
                String selectedDateWithouthYear = dayOfMonth + " " + getMonthName(month);
                dateTextView.setText(selectedDateWithouthYear);
                Toast.makeText(CalendarPage.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
                mood = dbHandler.getMood(userId, formattedDate);
                thought = dbHandler.getLatestThought(userId, formattedDate);
                meditate = dbHandler.getMeditationTime(userId, formattedDate);

                if (!getTodayDateMMDDYYYY().equals(formattedDate)){
                    moodView.setImageAlpha(0x3F);
                    viewMore.setImageAlpha(0x3F);
                    viewMore.setEnabled(false);
                    calendarThoughtsView.setEnabled(false);
                    calendarMoodView.setBackgroundResource(R.drawable.grey_mood);
                    calendarThoughtsView.setBackgroundResource(R.drawable.grey_thoughts);
                    calendarMeditationView.setBackgroundResource(R.drawable.grey_meditation);
                    mediateView.setTextColor(Color.parseColor("#808080"));
                    thoughtView.setTextColor(Color.parseColor("#808080"));
                    dateTextView.setTextColor(Color.parseColor("#808080"));
                } else {
                    calendarThoughtsView.setEnabled(true);
                    viewMore.setEnabled(true);
                    moodView.setImageAlpha(0xFF);
                    viewMore.setImageAlpha(0xFF);
                    calendarMoodView.setBackgroundResource(R.drawable.mood);
                    calendarThoughtsView.setBackgroundResource(R.drawable.thoughts);
                    calendarMeditationView.setBackgroundResource(R.drawable.meditation);
                    mediateView.setTextColor(Color.parseColor("#7DA9AC"));
                    thoughtView.setTextColor(Color.parseColor("#7DA9AC"));
                    dateTextView.setTextColor(Color.parseColor("#7DA9AC"));
                }

                mediateView.setText(meditate + " Minutes");

                if (thought.equals("")) {
                    thoughtView.setText("No thoughts recorded");
                } else {
                    thoughtView.setText(thought);
                }

                if (mood.equals("happy")) {
                    moodView.setImageResource(R.drawable.happy);
                } else if (mood.equals("sad")) {
                    moodView.setImageResource(R.drawable.sad);
                } else if (mood.equals("angry")) {
                    moodView.setImageResource(R.drawable.angry);
                } else if (mood.equals("neutral")) {
                    moodView.setImageResource(R.drawable.neutral);
                } else {
                    moodView.setImageResource(R.drawable.homepage_emoji);
                }

            }
        });
    }

    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return dayOfMonth + " " + getAbbreviatedMonthName(month) + " " + year;
    }

    private String getTodayDateMMDDYYYY() {
        // Get current date and time
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Define the desired date format (MM/dd/yyyy)
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        // Format the current date
        String formattedDate = dateFormat.format(currentDate);

        return formattedDate;
    }

    private String getTodayDateWithoutYear() {
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        return dayOfMonth + " " + getMonthName(month);
    }

    private String getMonthName(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[month];
    }

    private String getAbbreviatedMonthName(int month) {
        String[] abbreviatedMonths = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return abbreviatedMonths[month];
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.calendarBackButton) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.calendarThoughts || v.getId() == R.id.viewMoreCalendar) {
            Intent intent = new Intent(this, ThoughtsExpansionPage.class);
            intent.putExtra("abbreviatedDate", selectedAbbreviatedMonthDate);
            intent.putExtra("date", selectedAbbreviatedMonthDate);
            startActivity(intent);
        }
    }

}