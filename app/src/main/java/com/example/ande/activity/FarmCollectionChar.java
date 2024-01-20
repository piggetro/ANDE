package com.example.ande.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ande.helpers.AnimalTypeListAdapter;
import com.example.ande.helpers.CollectionCharAdapter;
import com.example.ande.R;
import com.example.ande.helpers.DBHandler;
import com.example.ande.helpers.SessionManagement;
import com.example.ande.model.Animal;
import com.example.ande.model.CollectionChar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FarmCollectionChar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FarmCollectionChar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int userId;
    private RecyclerView recyclerView;
    private CollectionCharAdapter adapter;
    private List<CollectionChar> characterList = new ArrayList<>();
    private DBHandler dbHandler;
    private ProgressBar happinessMeter;

    private String currentPointsText;
    public FarmCollectionChar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FarmCollectionChar.
     */
    // TODO: Rename and change types and number of parameters
    public static FarmCollectionChar newInstance(String param1, String param2) {
        FarmCollectionChar fragment = new FarmCollectionChar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
        dbHandler = new DBHandler(getContext());
        SessionManagement sessionManagement = new SessionManagement(getContext());
        userId = sessionManagement.getSession();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_farm_collection_char, container, false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.collectionCharRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);
        TextView noAnimalsTextView = view.findViewById(R.id.noAnimalsTextView);
        Button newAnimalButton = view.findViewById(R.id.newAnimalBtn);
        ImageButton editNameBtn = view.findViewById(R.id.editNameBtn);

        newAnimalButton.setVisibility(View.GONE);
        noAnimalsTextView.setVisibility(View.GONE);

        int currentProgress = 0;
        // Initialize your characters list here or in a separate method
        characterList = getCharacterList();
        if (characterList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            noAnimalsTextView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setHasFixedSize(true);

            adapter = new CollectionCharAdapter(view.getContext(), characterList, new CollectionCharAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    navigateToAnimalViewerActivity(position);
                }
            });
            recyclerView.setAdapter(adapter);
        }


        TextView charNameTextView = view.findViewById(R.id.currentCharName);
        CollectionChar currentCharacter = dbHandler.getActiveUserAnimal(userId, getContext());
        happinessMeter = view.findViewById(R.id.happinessMeterBar);
        TextView currentPointsTextView = view.findViewById(R.id.progressText);

        int currentPoints = 0;
        int maxPoints = 100;
        if(currentCharacter.getAnimalId() == -1) {
//            charNameTextView.setText("");
            editNameBtn.setVisibility(View.GONE);
            charNameTextView.setVisibility(View.GONE);
            newAnimalButton.setVisibility(View.VISIBLE);
            newAnimalButton.setOnClickListener(v -> {
                showAnimalListDialog();
            });
        } else {
            editNameBtn.setOnClickListener(v -> {
                showEditPetNameDialog(currentCharacter);
            });
            charNameTextView.setText(currentCharacter.getName());
            currentPoints = currentCharacter.getCurrentPoints();
            maxPoints = dbHandler.getAnimalMaxPoints(currentCharacter.getAnimalId());
        }


        happinessMeter.setProgress(currentPoints);
        happinessMeter.setMax(maxPoints);

        currentPointsText = currentPoints + "/" + maxPoints;
        currentPointsTextView.setText(currentPointsText);


    }
    private void navigateToAnimalViewerActivity(int position) {
        Intent intent = new Intent(getActivity(), AnimalViewerActivity.class);
        intent.putExtra("position", position); // Pass the clicked position
        startActivity(intent);
    }
    private List<CollectionChar> getCharacterList() {
      characterList = dbHandler.getAllUserAnimals(userId, getContext());

        return characterList;
    }

    private void showAnimalListDialog() {
        List<Animal> animals = dbHandler.getAllAnimalTypes(this.getContext());

        AnimalTypeListAdapter adapter = new AnimalTypeListAdapter(this.getContext(), R.layout.item_animal_type, animals);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Choose an animal to raise");

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // 'which' is the index of the selected item
                Animal selectedAnimal = animals.get(which);
                // Handle the selection, e.g., show a toast
                dbHandler.addUserAnimal(userId, selectedAnimal.getAnimalId(), selectedAnimal.getType());
                Toast.makeText(getContext(), "Set " + selectedAnimal.getType() +" , happy raising!", Toast.LENGTH_LONG).show();
                if (getActivity() != null) {
                    getActivity().recreate();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showEditPetNameDialog(CollectionChar pet) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Pet Name");

        // Set up the input
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(pet.getName()); // Set current name
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = input.getText().toString();
                if (validatePetName(newName)) {
                    // Update name in the database
                    dbHandler.updateAnimalName(userId, newName);
                    // Refresh data
                    if (getActivity() != null) {
                        getActivity().recreate();
                    }

                } else {
                    Toast.makeText(getContext(), "Invalid name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private boolean validatePetName(String name) {
        // Implement your validation logic here
        return name != null && !name.trim().isEmpty();
    }


}