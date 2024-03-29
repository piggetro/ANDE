package com.example.ande.activity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ande.R;
import com.example.ande.helpers.DBHandler;
import com.example.ande.helpers.DateConverter;
import com.example.ande.helpers.SessionManagement;

public class AddThoughtPage extends AppCompatActivity implements View.OnClickListener {

    private final DBHandler db = new DBHandler(this);
    private String selectedAbbreviatedMonthDate;
    private String selectedDate;
    private String convertedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thought_page);

        Intent intent = getIntent();
        if (intent != null) {
            selectedAbbreviatedMonthDate = intent.getStringExtra("abbreviatedDate");
            selectedDate = intent.getStringExtra("date");
            convertedDate = DateConverter.convertToMMddyyyyFormat(selectedDate);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addThoughtPageBackButton) {
            Intent intent = new Intent(this, ThoughtsExpansionPage.class);
            intent.putExtra("abbreviatedDate", selectedAbbreviatedMonthDate);
            intent.putExtra("date", selectedDate);
            startActivity(intent);
        } else if (v.getId() == R.id.addThoughtSubmitButton) {
            EditText thoughtEditText = findViewById(R.id.addThoughtTextArea);
            String thoughtText = thoughtEditText.getText().toString().trim();

            if (thoughtText.equals("")) {
                Toast.makeText(this, "Thought cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            SessionManagement sessionManagement = new SessionManagement(AddThoughtPage.this);
            int userId = sessionManagement.getSession();

            try {
                db.addThought(userId, thoughtText, convertedDate);
            } catch (SQLException e) {
                Toast.makeText(AddThoughtPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(this, ThoughtsExpansionPage.class);
            intent.putExtra("abbreviatedDate", selectedAbbreviatedMonthDate);
            intent.putExtra("date", selectedDate);
            startActivity(intent);
        }
    }
}