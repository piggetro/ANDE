package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ande.R;
import com.example.ande.helpers.DBHandler;

public class EditThoughtPage extends AppCompatActivity implements View.OnClickListener {

    DBHandler db = new DBHandler(this);
    String selectedAbbreviatedMonthDate;
    String selectedDate;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_thought_page);

        Intent intent = getIntent();
        if (intent != null) {
            String thoughtId = intent.getStringExtra("thoughtId");
            String thoughtText = intent.getStringExtra("thoughtText");
            selectedAbbreviatedMonthDate = intent.getStringExtra("abbreviatedDate");
            selectedDate = intent.getStringExtra("date");
            int thoughtPosition = intent.getIntExtra("thoughtPosition", 0);


            TextView editThoughtHeader = findViewById(R.id.editThoughtHeader);
            if (thoughtPosition == 0) {
                editThoughtHeader.setText("Edit Thought");
            } else {
                editThoughtHeader.setText("Edit Thought #" + thoughtPosition);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.editThoughtPageBackButton) {
            Intent intent = new Intent(this, ThoughtsExpansionPage.class);
            intent.putExtra("abbreviatedDate", selectedAbbreviatedMonthDate);
            intent.putExtra("date", selectedDate);
            startActivity(intent);
        } else if (v.getId() == R.id.editThoughtSubmitButton) {
            Intent intent = new Intent(this, ThoughtsExpansionPage.class);
            intent.putExtra("abbreviatedDate", selectedAbbreviatedMonthDate);
            intent.putExtra("date", selectedDate);
            startActivity(intent);
        }
    }
}