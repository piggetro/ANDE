package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.ande.R;
import com.example.ande.helpers.DBHandler;
import com.example.ande.helpers.SessionManagement;

public class AddThoughtPage extends AppCompatActivity implements View.OnClickListener {

    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thought_page);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addThoughtPageBackButton) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.addThoughtSubmitButton) {
            EditText thoughtEditText = findViewById(R.id.addThoughtTextArea);
            String thoughtText = thoughtEditText.getText().toString();

            SessionManagement sessionManagement = new SessionManagement(AddThoughtPage.this);
            int userId = sessionManagement.getSession();

            db.addThought(userId, thoughtText);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}