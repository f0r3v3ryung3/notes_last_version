package com.example.notes_last_version;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.notes_last_version.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class MakeNoteFragment extends Fragment {
    SharedPreferences memory;
    EditText name;
    EditText textKeep;
    MaterialToolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransitionInflater inflater_exit = TransitionInflater.from(requireContext());
        setExitTransition(inflater_exit.inflateTransition(R.transition.slide_right));

        TransitionInflater inflater_enter = TransitionInflater.from(requireContext());
        setEnterTransition(inflater_enter.inflateTransition(R.transition.slide_right));
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_make_note, container, false);

        name = view.findViewById(R.id.nameEdit);
        textKeep = view.findViewById(R.id.textKeep);
        memory = view.getContext().getSharedPreferences("Notes", MODE_PRIVATE);



        toolbar = view.findViewById(R.id.topAppBar);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.delete: {
                    Fragment newFragment = new NotesFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    transaction.replace(R.id.flFragment, newFragment);
                    transaction.addToBackStack(null);

                    transaction.commit();
                    return true;
                }
                default:
                    return super.onOptionsItemSelected(item);

            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor ed = memory.edit();
                ed.putString(name.getText().toString(), textKeep.getText().toString());
                ed.commit();

                Fragment newFragment = new NotesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.flFragment, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();

            }
        });


        return view;
    }

    @Override
    public void onDestroy() {

        SharedPreferences.Editor ed = memory.edit();
        ed.putString(name.getText().toString(), textKeep.getText().toString());
        ed.commit();

        Fragment newFragment = new NotesFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.flFragment, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
        super.onDestroy();
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:  {
                System.out.println("k");

                Fragment newFragment = new NotesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.flFragment, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}