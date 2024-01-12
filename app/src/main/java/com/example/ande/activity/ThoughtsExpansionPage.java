package com.example.ande.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ande.R;
import com.example.ande.helpers.DBHandler;
import com.example.ande.helpers.SessionManagement;
import com.example.ande.helpers.ThoughtRecylerItemArrayAdapter;
import com.example.ande.model.ThoughtRecyclerItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ThoughtsExpansionPage extends AppCompatActivity implements View.OnClickListener {

    DBHandler db = new DBHandler(this);
    String selectedAbbreviatedMonthDate;
    String selectedDate;
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

        Intent intent = getIntent();
        if (intent != null) {
            selectedAbbreviatedMonthDate = intent.getStringExtra("abbreviatedDate");
            selectedDate = intent.getStringExtra("date");

            TextView abbreviatedDateTextView = findViewById(R.id.thoughtsExpansionAbbreviatedDateDateText);
            abbreviatedDateTextView.setText(selectedAbbreviatedMonthDate);

            SessionManagement sessionManagement = new SessionManagement(ThoughtsExpansionPage.this);
            int userId = sessionManagement.getSession();

            ArrayList<ThoughtRecyclerItem> thoughtsFromDb = db.getThoughtsByUserIdAndDate(userId, selectedDate);

            mThoughts.addAll(thoughtsFromDb);
        }

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


}