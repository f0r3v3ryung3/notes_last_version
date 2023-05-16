package com.example.notes_last_version;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SettingsFragment extends Fragment {
    SharedPreferences settings;
    Button button;
    EditText edit_api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater_exit = TransitionInflater.from(requireContext());
        setExitTransition(inflater_exit.inflateTransition(R.transition.fade));

        TransitionInflater inflater_enter = TransitionInflater.from(requireContext());
        setEnterTransition(inflater_enter.inflateTransition(R.transition.fade));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        settings = view.getContext().getSharedPreferences("API", MODE_PRIVATE);
        button = view.findViewById(R.id.confirm);
        edit_api = view.findViewById(R.id.edit_api);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor ed = settings.edit();
                ed.putString("API", edit_api.getText().toString());
                ed.commit();
                showText("Api is added");
            }
        });

        return view;
    }

    private void showText(String text) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show();
    }
}