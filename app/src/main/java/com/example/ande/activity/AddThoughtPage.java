package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ande.R;

public class AddThoughtPage extends AppCompatActivity implements View.OnClickListener {

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
        }
    }
}