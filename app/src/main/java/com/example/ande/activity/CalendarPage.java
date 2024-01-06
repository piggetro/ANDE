package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ande.R;

public class CalendarPage extends AppCompatActivity implements  View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_page);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.calendarBackButton) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.calendarThoughts || v.getId() == R.id.viewMoreCalendar) {
            Intent intent = new Intent(this, ThoughtsExpansionPage.class);
            startActivity(intent);
        }
    }


}