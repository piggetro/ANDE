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
import com.example.ande.helpers.DateConverter;
import com.example.ande.helpers.SessionManagement;
import com.example.ande.helpers.ThoughtRecylerItemArrayAdapter;
import com.example.ande.model.Thought;

import java.util.ArrayList;

public class ThoughtsExpansionPage extends AppCompatActivity implements View.OnClickListener {

    DBHandler db = new DBHandler(this);
    String selectedAbbreviatedMonthDate;
    String convertedDate;
    String[] sortByDropDownItems = {"Earliest", "Latest"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> sortByDropDownAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Thought> mThoughts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoughts_expansion_page);

        Intent intent = getIntent();
        if (intent != null) {
            selectedAbbreviatedMonthDate = intent.getStringExtra("abbreviatedDate");
            String selectedDate = intent.getStringExtra("date");
            convertedDate = DateConverter.convertToMMddyyyyFormat(selectedDate);

            TextView abbreviatedDateTextView = findViewById(R.id.thoughtsExpansionAbbreviatedDateDateText);
            abbreviatedDateTextView.setText(selectedAbbreviatedMonthDate);

            SessionManagement sessionManagement = new SessionManagement(ThoughtsExpansionPage.this);
            int userId = sessionManagement.getSession();

            ArrayList<Thought> thoughtsFromDb = db.getThoughtsByUserIdAndDate(userId, convertedDate);

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
                sortThoughts(item);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.thoughtsExpansionPageBackButton) {
            Intent intent = new Intent(this, CalendarPage.class);
            startActivity(intent);
        } else if (v.getId() == R.id.addThoughtFloatingActionButton) {
            Intent intent = new Intent(this, AddThoughtPage.class);
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
            public void onItemClicked(Thought thought) {
                Toast.makeText(ThoughtsExpansionPage.this, thought.getThoughtText(), Toast.LENGTH_SHORT).show();
            }

        });

        //Set adapter to RecyclerView
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
    }


    private void sortThoughts(String sortOrder) {
        SessionManagement sessionManagement = new SessionManagement(ThoughtsExpansionPage.this);
        int userId = sessionManagement.getSession();

        mThoughts.clear();

        if (sortOrder.equals("Latest")) {
            mThoughts.addAll(db.getThoughtsByUserIdAndDateOrderByLatest(userId, convertedDate));
        } else {
            mThoughts.addAll(db.getThoughtsByUserIdAndDateOrderByEarliest(userId, convertedDate));
        }

        mRecyclerView.getAdapter().notifyDataSetChanged();
    }



}