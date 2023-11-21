package com.example.ande;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ThoughtsExpansionPage extends AppCompatActivity implements View.OnClickListener {

    String[] sortByDropDownItems = {"Earliest", "Latest"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> sortByDropDownAdapter;

    FloatingActionButton addThoughtButton;
    Boolean isAllFabsVisible;

    private RecyclerView mRecyclerView;
    private ArrayList<ThoughtRecyclerItem> mThoughts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoughts_expansion_page);
        bindThoughtsData();
        setUIRef();

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

    private void setUIRef()
    {
        //Reference of RecyclerView
        mRecyclerView = findViewById(R.id.thoughtsRecyclerView);
        //Linear Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThoughtsExpansionPage.this, RecyclerView.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ThoughtsExpansionPage.this, 2);

        //Set Layout Manager to RecyclerView
        mRecyclerView.setLayoutManager(gridLayoutManager);

        //Create adapter
        ThoughtRecylerItemArrayAdapter myRecyclerViewAdapter = new ThoughtRecylerItemArrayAdapter(mThoughts, new ThoughtRecylerItemArrayAdapter.MyRecyclerViewItemClickListener()
        {
            @Override
            public void onItemClicked(ThoughtRecyclerItem thought) {
                Toast.makeText(ThoughtsExpansionPage.this, thought.getThoughtText(), Toast.LENGTH_SHORT).show();
            }

        });

        //Set adapter to RecyclerView
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
    }

    private void bindThoughtsData() {
        mThoughts.add(new ThoughtRecyclerItem("1", "i love life"));
        mThoughts.add(new ThoughtRecyclerItem("2", "i like ice cream"));
        mThoughts.add(new ThoughtRecyclerItem("3", "i love INC"));
        mThoughts.add(new ThoughtRecyclerItem("4", "Today is a beautiful day"));
        mThoughts.add(new ThoughtRecyclerItem("5", "Coding is fun"));
        mThoughts.add(new ThoughtRecyclerItem("6", "Nature is amazing"));
        mThoughts.add(new ThoughtRecyclerItem("7", "Music brings joy"));
        mThoughts.add(new ThoughtRecyclerItem("8", "Learning new things is exciting"));
        mThoughts.add(new ThoughtRecyclerItem("9", "Coffee helps me focus"));
        mThoughts.add(new ThoughtRecyclerItem("10", "Kindness matters"));
        mThoughts.add(new ThoughtRecyclerItem("11", "Books open new worlds"));
        mThoughts.add(new ThoughtRecyclerItem("12", "Exercise is good for the body"));
        mThoughts.add(new ThoughtRecyclerItem("13", "Family time is precious"));
        mThoughts.add(new ThoughtRecyclerItem("14", "Creativity knows no bounds"));
        mThoughts.add(new ThoughtRecyclerItem("15", "The sun always shines after the rain"));
        mThoughts.add(new ThoughtRecyclerItem("16", "Challenges make us stronger"));
    }


}