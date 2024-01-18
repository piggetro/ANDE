package com.example.ande.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande.R;
import com.example.ande.model.CollectionChar;
import com.example.ande.activity.CollectionCharViewHolder;

import java.util.List;

public class CollectionCharAdapter extends RecyclerView.Adapter<CollectionCharViewHolder>{
    Context context;
    List<CollectionChar> collectionCharList;

    public CollectionCharAdapter(Context context, List<CollectionChar> collectionCharList) {
        this.context = context;
        this.collectionCharList = collectionCharList;
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
    }

    @Override
    public int getItemCount() {
        return collectionCharList.size();
    }
}
