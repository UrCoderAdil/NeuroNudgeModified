package com.example.neuronudge.Fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.neuronudge.R;

public class HomeFragment extends Fragment {

    private TextView gptResponseBox;
    private EditText gptPromptInput;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        gptResponseBox = view.findViewById(R.id.gptResponseBox);
        gptPromptInput = view.findViewById(R.id.gptPromptInput);

        // No functionality for now

        return view;
    }
}
