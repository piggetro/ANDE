package com.example.ande.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ande.R;
import com.example.ande.helpers.DBHandler;

public class EditThoughtPage extends AppCompatActivity implements View.OnClickListener {

    DBHandler db = new DBHandler(this);
    String selectedAbbreviatedMonthDate;
    String selectedDate;
    String thoughtId;
    String thoughtText;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_thought_page);

        Intent intent = getIntent();
        if (intent != null) {
            thoughtId = intent.getStringExtra("thoughtId");
            thoughtText = intent.getStringExtra("thoughtText");
            selectedAbbreviatedMonthDate = intent.getStringExtra("abbreviatedDate");
            selectedDate = intent.getStringExtra("date");
            int thoughtPosition = intent.getIntExtra("thoughtPosition", 0);

            TextView thoughtEditText = findViewById(R.id.editThoughtTextArea);
            thoughtEditText.setText(thoughtText);

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
        if (v.getId() == R.id.viewAllAnimalsBackButton) {
            Intent intent = new Intent(this, ThoughtsExpansionPage.class);
            intent.putExtra("abbreviatedDate", selectedAbbreviatedMonthDate);
            intent.putExtra("date", selectedDate);
            startActivity(intent);
        } else if (v.getId() == R.id.editThoughtSubmitButton) {

            TextView thoughtEditText = findViewById(R.id.editThoughtTextArea);
            String thoughtText = thoughtEditText.getText().toString();

            if (thoughtText.equals("")) {
                Toast.makeText(this, "Thought cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!thoughtText.equals(this.thoughtText)) {
                try {
                    db.updateThought(thoughtId, thoughtText);
                } catch (SQLException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Intent intent = new Intent(this, ThoughtsExpansionPage.class);
            intent.putExtra("abbreviatedDate", selectedAbbreviatedMonthDate);
            intent.putExtra("date", selectedDate);
            startActivity(intent);
        }
    }
}