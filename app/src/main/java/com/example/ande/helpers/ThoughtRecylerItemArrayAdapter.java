package com.example.ande.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande.R;
import com.example.ande.model.Thought;

import java.util.ArrayList;

public class ThoughtRecylerItemArrayAdapter extends RecyclerView.Adapter<ThoughtRecylerItemArrayAdapter.MyViewHolder> {

    private ArrayList<Thought> mThoughts;
    private MyRecyclerViewItemClickListener mItemClickListener;

    public ThoughtRecylerItemArrayAdapter(ArrayList<Thought> thoughts, MyRecyclerViewItemClickListener itemClickListener) {
        this.mThoughts = thoughts;
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
                mItemClickListener.onItemClicked(mThoughts.get(myViewHolder.getLayoutPosition()));
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.thoughtId.setText("#" + mThoughts.get(position).getPosition());

        String thought = mThoughts.get(position).getThoughtText();
        holder.thoughtText.setText(thought);
    }

    @Override
    public int getItemCount() {
        return mThoughts.size();
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
        private TextView thoughtId;
        private TextView thoughtText;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thoughtId = itemView.findViewById(R.id.thoughtId);
            thoughtText = itemView.findViewById(R.id.thoughtText);
        }
    }

    // RecyclerView Click Listener
    public interface MyRecyclerViewItemClickListener {
        void onItemClicked(Thought thought);
    }
}
