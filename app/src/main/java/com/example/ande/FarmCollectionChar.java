package com.example.ande;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private RecyclerView recyclerView;
    private CollectionCharAdapter adapter;
    private List<CollectionChar> characterList = new ArrayList<>();

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


        // Initialize your characters list here or in a separate method
        characterList = getCharacterList();
        adapter = new CollectionCharAdapter(view.getContext(), characterList);
        recyclerView.setAdapter(adapter);
    }

    private List<CollectionChar> getCharacterList() {
        // Create or fetch your characters here
        // For example:
        characterList.add(new CollectionChar("Oink", R.drawable.emotioncows));
        characterList.add(new CollectionChar("What", R.drawable.happycow));
        characterList.add(new CollectionChar("Meow", R.drawable.neutral_pig));

        return characterList;
    }
}