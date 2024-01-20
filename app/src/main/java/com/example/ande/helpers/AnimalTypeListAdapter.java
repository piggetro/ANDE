package com.example.ande.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ande.R;
import com.example.ande.model.Animal;

import java.util.List;

public class AnimalTypeListAdapter extends ArrayAdapter<Animal> {
    private Context mContext;
    private List<Animal> animalList;
    public AnimalTypeListAdapter(@NonNull Context context, int resource, @NonNull List<Animal> objects) {
        super(context, resource, objects);
        mContext = context;
        animalList = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_animal_type, parent, false);
        }

        ImageView imageViewAnimal = convertView.findViewById(R.id.animalTypeImg);
        TextView textViewAnimalType = convertView.findViewById(R.id.animalTypeText);
        TextView textViewMaxPoints = convertView.findViewById(R.id.animalTypeMax);

        Animal animal = animalList.get(position);

        // Set image, type, and max points
        // You may need to load the image differently depending on your setup
        imageViewAnimal.setImageResource(animal.getImageResourceId());
        textViewAnimalType.setText(animal.getType());
        textViewMaxPoints.setText(String.valueOf(animal.getMaxPoints()));

        return convertView;
    }

}
