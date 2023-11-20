package com.example.ande;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ThoughtsExpansionPage extends AppCompatActivity implements View.OnClickListener {

    String[] sortByDropDownItems = {"Earliest", "Latest"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> sortByDropDownAdapter;

    FloatingActionButton addThoughtButton;
    Boolean isAllFabsVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoughts_expansion_page);

        autoCompleteTextView = findViewById(R.id.auto_complete_text_view);
        sortByDropDownAdapter = new ArrayAdapter<>(this, R.layout.list_item, sortByDropDownItems);
        autoCompleteTextView.setAdapter(sortByDropDownAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(ThoughtsExpansionPage.this, "Selected: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        addThoughtButton = findViewById(R.id.addThoughtButton);
        isAllFabsVisible = false;

        addThoughtButton.setOnClickListener(view -> {
            Toast.makeText(ThoughtsExpansionPage.this, "Add Thought Button Click", Toast.LENGTH_SHORT
            ).show();
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.thoughtsExpansionPageBackButton) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}