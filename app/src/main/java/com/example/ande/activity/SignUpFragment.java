package com.example.ande.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ande.R;
import com.example.ande.helpers.DBHandler;
import com.example.ande.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DBHandler dbHandler;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        Button signUpButton = view.findViewById(R.id.signUp);
        signUpButton.setOnClickListener(this);

        dbHandler = new DBHandler(getContext());

        return view;
    }

    @Override
    public void onClick(View view) {
        EditText emailEditText = getView().findViewById(R.id.email);
        EditText usernameEditText = getView().findViewById(R.id.username);
        EditText passwordEditText = getView().findViewById(R.id.password);

        String email = emailEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        try {
            if (view.getId() == R.id.signUp && !email.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
                if (isValidEmail(email)) {
                    if (dbHandler.checkIfUserExists(email)) {
                        Toast.makeText(getContext(), "Email Already Exists", Toast.LENGTH_SHORT).show();
                    } else {
                        User user = new User(email, username, password);
                        dbHandler.addUser(user);
                        Toast.makeText(getContext(), "Sign Up Success", Toast.LENGTH_SHORT).show();

                        emailEditText.getText().clear();
                        usernameEditText.getText().clear();
                        passwordEditText.getText().clear();
                    }
                } else {
                    Toast.makeText(getContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
