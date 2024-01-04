package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.example.ande.R;

public class FarmCollection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_collection);

      ImageButton backButton = findViewById(R.id.imageButton);
        backButton.setOnClickListener(v -> finish());

        // Fragment Transaction to add FarmCollectionCharFragment to its container
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_CharacterDetailCollection, new FarmCollectionChar())
                    .commit();
        }

    }


}