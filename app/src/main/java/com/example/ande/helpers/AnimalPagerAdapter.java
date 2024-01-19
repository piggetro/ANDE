package com.example.ande.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ande.R;
import com.example.ande.model.Animal;
import com.example.ande.model.CollectionChar;

import java.util.List;

public class AnimalPagerAdapter extends RecyclerView.Adapter<AnimalPagerAdapter.AnimalViewHolder> {
    private List<CollectionChar> animals;

    public AnimalPagerAdapter(List<CollectionChar> animals) {
        this.animals = animals;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        CollectionChar animal = animals.get(position);
        holder.animalName.setText(animal.getName());
        holder.animalImage.setImageResource(animal.getImage());
        // Set animal image using holder.animalImage
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        TextView animalName;
        ImageView animalImage;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            animalName = itemView.findViewById(R.id.animalName);
            animalImage = itemView.findViewById(R.id.animalImage);
        }
    }
}
