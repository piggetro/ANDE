package com.example.ande.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
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

import android.widget.Toast;

import com.example.ande.R;
import com.example.ande.helpers.DBHandler;
import com.example.ande.helpers.DateConverter;
import com.example.ande.helpers.QuoteRecylerItemArrayAdapter;
import com.example.ande.helpers.SessionManagement;
import com.example.ande.helpers.SpacingItemDecorator;
import com.example.ande.model.Quote;

import java.util.ArrayList;

public class QuotesPage extends AppCompatActivity implements View.OnClickListener {

    DBHandler db = new DBHandler(this);
    String selectedDate;
    String convertedDate;
    String[] sortByDropDownItems = {"All", "Happy", "Sad", "Angry", "Neutral"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> sortByDropDownAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Quote> mQuotes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_page);

        autoCompleteTextView = findViewById(R.id.auto_complete_quotes_text_view);

        Intent intent = getIntent();
        if (intent != null) {
            selectedDate = intent.getStringExtra("date");
            convertedDate = DateConverter.convertToMMddyyyyFormat(selectedDate);

            SessionManagement sessionManagement = new SessionManagement(QuotesPage.this);
            int userId = sessionManagement.getSession();

            String mood = db.getMood(userId, convertedDate);

            if (mood.equals("")) {
                mood = "All";
            }
            autoCompleteTextView.setText(mood);

            mQuotes.addAll(db.getQuotesByCategory(mood));
        }

        setUIRef();

        sortByDropDownAdapter = new ArrayAdapter<>(this, R.layout.list_item, sortByDropDownItems);
        autoCompleteTextView.setAdapter(sortByDropDownAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                sortQuotes(item);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.quotesPageBackButton) {
            Intent intent = new Intent(this, CalendarPage.class);
            startActivity(intent);
        }
    }

    private void setUIRef()
    {
        //Reference of RecyclerView
        mRecyclerView = findViewById(R.id.quotesRecyclerView);
        //Linear Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuotesPage.this, RecyclerView.VERTICAL, false);
        SpacingItemDecorator spacingItemDecorator = new SpacingItemDecorator(-100);
        mRecyclerView.addItemDecoration(spacingItemDecorator);
        //Set Layout Manager to RecyclerView
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //Create adapter
        QuoteRecylerItemArrayAdapter myRecyclerViewAdapter = new QuoteRecylerItemArrayAdapter(mQuotes, new QuoteRecylerItemArrayAdapter.MyRecyclerViewItemClickListener()
        {
            @Override
            public void onItemClicked(Quote quote) {
                Toast.makeText(QuotesPage.this, quote.getQuote(), Toast.LENGTH_SHORT).show();
            }



        });

        //Set adapter to RecyclerView
        mRecyclerView.setAdapter(myRecyclerViewAdapter);
    }


    private void sortQuotes(String mood) {
        mQuotes.clear();

        ArrayList<Quote> quotesFromDb = db.getQuotesByCategory(mood);
        mQuotes.addAll(quotesFromDb);

        if (mRecyclerView.getAdapter() == null) {
            setUIRef();
        }

        mRecyclerView.getAdapter().notifyDataSetChanged();
    }


    public void showDialog(String quoteText) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout_thoughtsexpansionpage_thought);



        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}