package com.example.ande.activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    String selectedDate;
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
            selectedDate = intent.getStringExtra("date");
            convertedDate = DateConverter.convertToMMddyyyyFormat(selectedDate);

            TextView abbreviatedDateTextView = findViewById(R.id.thoughtsExpansionAbbreviatedDateText);
            abbreviatedDateTextView.setText(selectedAbbreviatedMonthDate);

            ImageView moodView = (ImageView) findViewById(R.id.thoughtsExpansionPageMoodImage);
            SessionManagement sessionManagement = new SessionManagement(ThoughtsExpansionPage.this);
            int userId = sessionManagement.getSession();

            String mood = db.getMood(userId, convertedDate);

            if (mood.equals("happy")) {
                moodView.setImageResource(R.drawable.happy);
            } else if (mood.equals("sad")) {
                moodView.setImageResource(R.drawable.sad);
            } else if (mood.equals("angry")) {
                moodView.setImageResource(R.drawable.angry);
            } else if (mood.equals("neutral")) {
                moodView.setImageResource(R.drawable.neutral);
            } else {
                moodView.setImageDrawable(null);
            }

            try {
                ArrayList<Thought> thoughtsFromDb = db.getThoughtsByUserIdAndDate(userId, convertedDate);
                mThoughts.addAll(thoughtsFromDb);
                if (mThoughts.size() == 0) {
                    Toast.makeText(this, "No thoughts for this date. Let's add some!", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLiteException e) {
                Toast.makeText(this, "Error retrieving thoughts", Toast.LENGTH_SHORT).show();
            }

            setThoughtPosition("Earliest");
        }

        setUIRef();

        autoCompleteTextView = findViewById(R.id.auto_complete_meditation_minutes_text_view);
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
            intent.putExtra("abbreviatedDate", selectedAbbreviatedMonthDate);
            intent.putExtra("date", selectedDate);
            startActivity(intent);
        }
    }

    private void setUIRef()
    {
        //Reference of RecyclerView
        mRecyclerView = findViewById(R.id.thoughtsRecyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ThoughtsExpansionPage.this, 2);

        //Set Layout Manager to RecyclerView
        mRecyclerView.setLayoutManager(gridLayoutManager);

        //Create adapter
        ThoughtRecylerItemArrayAdapter myRecyclerViewAdapter = new ThoughtRecylerItemArrayAdapter(mThoughts, new ThoughtRecylerItemArrayAdapter.MyRecyclerViewItemClickListener()
        {
            @Override
            public void onItemClicked(Thought thought) {
                showDialog(thought.getPosition(), thought.getThoughtText());
            }

            @Override
            public void onEditIconClicked(Thought thought) {
                Intent intent = new Intent(ThoughtsExpansionPage.this, EditThoughtPage.class);
                intent.putExtra("thoughtId", thought.getThoughtId());
                intent.putExtra("thoughtText", thought.getThoughtText());
                intent.putExtra("thoughtPosition", thought.getPosition());
                intent.putExtra("abbreviatedDate", selectedAbbreviatedMonthDate);
                intent.putExtra("date", selectedDate);
                startActivity(intent);
            }

        });

        //Set adapter to RecyclerView
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
    }


    private void sortThoughts(String sortOrder) {
        SessionManagement sessionManagement = new SessionManagement(ThoughtsExpansionPage.this);
        int userId = sessionManagement.getSession();

        mThoughts.clear();

        try {
            if (sortOrder.equals("Latest")) {
                mThoughts.addAll(db.getThoughtsByUserIdAndDateOrderByLatest(userId, convertedDate));
            } else {
                mThoughts.addAll(db.getThoughtsByUserIdAndDateOrderByEarliest(userId, convertedDate));
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving thoughts", Toast.LENGTH_SHORT).show();
        }
        setThoughtPosition(sortOrder);

        if (mRecyclerView.getAdapter() == null) {
            setUIRef();
        }

        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private void setThoughtPosition(String orderBy) {
        if (orderBy.equals("Latest")) {
            for (int i = mThoughts.size() - 1; i >= 0; i--) {
                mThoughts.get(i).setPosition(mThoughts.size() - i);
            }
        } else {
            for (int i = 0; i < mThoughts.size(); i++) {
                mThoughts.get(i).setPosition(i + 1);
            }
        }
    }

    public void showDialog(int position, String thoughtText) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout_thoughtsexpansionpage_thought);

        TextView thoughtsExpansionBottomSheetTitle = dialog.findViewById(R.id.thoughtsExpansionBottomSheetTitle);
        thoughtsExpansionBottomSheetTitle.setText("Thought #" + position);

        TextView thoughtsExpansionBottomSheetThoughtText = dialog.findViewById(R.id.thoughtsExpansionBottomSheetThoughtText);
        thoughtsExpansionBottomSheetThoughtText.setText(thoughtText);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


}