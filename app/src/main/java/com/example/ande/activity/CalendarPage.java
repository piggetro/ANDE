package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ande.R;

import java.util.Calendar;

public class CalendarPage extends AppCompatActivity implements  View.OnClickListener {
    String selectedAbbreviatedMonthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);

        selectedAbbreviatedMonthDate = getTodayDate();

        TextView dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText(getTodayDateWithoutYear());

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + " " + getMonthName(month) + " " + year;
                selectedAbbreviatedMonthDate = dayOfMonth + " " + getAbbreviatedMonthName(month) + " " + year;
                String selectedDateWithouthYear = dayOfMonth + " " + getMonthName(month);
                dateTextView.setText(selectedDateWithouthYear);
                Toast.makeText(CalendarPage.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
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
            intent.putExtra("date", selectedAbbreviatedMonthDate);
            startActivity(intent);
        }
    }


}