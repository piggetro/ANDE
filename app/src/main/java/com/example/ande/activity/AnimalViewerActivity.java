package com.example.ande.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ande.R;
import com.example.ande.helpers.AnimalPagerAdapter;
import com.example.ande.helpers.DBHandler;
import com.example.ande.helpers.SessionManagement;
import com.example.ande.model.CollectionChar;

import java.util.List;

public class AnimalViewerActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager2 viewPager;
    private AnimalPagerAdapter adapter;
    private DBHandler dbHandler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_collection_view);
        dbHandler = new DBHandler(this);
        SessionManagement sessionManagement = new SessionManagement(this);
        int userId = sessionManagement.getSession();



        List<CollectionChar> animals;

        animals = dbHandler.getAllUserAnimals(userId,this);
        viewPager = findViewById(R.id.viewPager);
        adapter = new AnimalPagerAdapter(animals);
        viewPager.setAdapter(adapter);
//
//        ImageButton btnPrevious = findViewById(R.id.btnPrevious);
//        ImageButton btnNext = findViewById(R.id.btnNext);
//
//        btnPrevious.setOnClickListener(v -> {
//            int currentItem = viewPager.getCurrentItem();
//            if (currentItem > 0) {
//                viewPager.setCurrentItem(currentItem - 1);
//            } else {
//                viewPager.setCurrentItem(adapter.getItemCount() - 1); // Loop to the end
//            }
//        });
//
//        btnNext.setOnClickListener(v -> {
//            int currentItem = viewPager.getCurrentItem();
//            if (currentItem < adapter.getItemCount() - 1) {
//                viewPager.setCurrentItem(currentItem + 1);
//            } else {
//                viewPager.setCurrentItem(0); // Loop back to the start
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnViewAllAnimalsBack){

            Intent intent = new Intent(this, FarmCollection.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnPrevious){

            int currentItem = viewPager.getCurrentItem();
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1);
            } else {
                viewPager.setCurrentItem(adapter.getItemCount() - 1); // Loop to the end
            }
        } else if (view.getId() == R.id.btnNext){
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < adapter.getItemCount() - 1) {
                viewPager.setCurrentItem(currentItem + 1);
            } else {
                viewPager.setCurrentItem(0); // Loop back to the start
            }
        }
    }
}