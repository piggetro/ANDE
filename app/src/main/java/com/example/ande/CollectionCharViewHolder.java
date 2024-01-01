package com.example.ande;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CollectionCharViewHolder extends RecyclerView.ViewHolder {
    public ImageView characterImage;
    public TextView characterName;

    public CollectionCharViewHolder(View itemView) {
        super(itemView);
        characterImage = itemView.findViewById(R.id.character_image);
        characterName = itemView.findViewById(R.id.character_name);
    }
}