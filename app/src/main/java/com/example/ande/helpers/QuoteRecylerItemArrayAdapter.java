package com.example.ande.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande.R;
import com.example.ande.model.Quote;

import java.util.ArrayList;

public class QuoteRecylerItemArrayAdapter extends RecyclerView.Adapter<QuoteRecylerItemArrayAdapter.MyViewHolder> {

    private ArrayList<Quote> mQuotes;
    private MyRecyclerViewItemClickListener mItemClickListener;

    public QuoteRecylerItemArrayAdapter(ArrayList<Quote> quotes, MyRecyclerViewItemClickListener itemClickListener) {
        this.mQuotes = quotes;
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate RecyclerView row
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_thought_recycler_item, parent, false);

        // Create View Holder
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        // Item Clicks
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClicked(mQuotes.get(myViewHolder.getLayoutPosition()));
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String quote = mQuotes.get(position).getQuote();
        holder.quoteText.setText("\"" + quote + "\"");

        String author = mQuotes.get(position).getAuthor();
        holder.quoteAuthor.setText("- " + author);
    }

    @Override
    public int getItemCount() {
        return mQuotes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // RecyclerView View Holder
    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView quoteText;
        private TextView quoteAuthor;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quoteText = itemView.findViewById(R.id.quoteText);
            quoteAuthor = itemView.findViewById(R.id.quoteAuthor);

        }
    }


    // RecyclerView Click Listener
    public interface MyRecyclerViewItemClickListener {
        void onItemClicked(Quote quote);
    }
}
