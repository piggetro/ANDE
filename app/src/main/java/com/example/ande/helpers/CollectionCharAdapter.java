package com.example.ande.helpers;



import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande.R;
import com.example.ande.activity.AnimalViewerActivity;
import com.example.ande.model.CollectionChar;
import com.example.ande.activity.CollectionCharViewHolder;

import java.util.List;

public class CollectionCharAdapter extends RecyclerView.Adapter<CollectionCharViewHolder> {
    Context context;
    List<CollectionChar> collectionCharList;

    private OnItemClickListener listener;

    public CollectionCharAdapter(Context context, List<CollectionChar> collectionCharList, OnItemClickListener listener) {
        this.context = context;
        this.collectionCharList = collectionCharList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CollectionCharViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CollectionCharViewHolder(LayoutInflater.from(context).inflate(R.layout.item_collection_char, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionCharViewHolder holder, int position) {
        holder.characterName.setText(collectionCharList.get(position).getName());
        holder.characterImage.setImageResource(collectionCharList.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(adapterPosition);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return collectionCharList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
