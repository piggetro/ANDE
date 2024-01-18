package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.ande.R;
import com.example.ande.helpers.DBHandler;
import com.example.ande.helpers.SessionManagement;
import com.example.ande.model.CollectionChar;

public class FarmCollection extends AppCompatActivity {

    DBHandler dbHandler = new DBHandler(this);
    private int userId;


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


        SessionManagement sessionManagement = new SessionManagement(FarmCollection.this);
        userId = sessionManagement.getSession();
        System.out.print(userId);
        CollectionChar currentCharacter = dbHandler.getActiveUserAnimal(userId, FarmCollection.this);

        ImageView imageView = findViewById(R.id.imageChar);

     //   int imageResId = getResources().getIdentifier(currentCharacter.getImage(), "drawable", getPackageName());
        imageView.setImageResource(currentCharacter.getImage());

    }



}