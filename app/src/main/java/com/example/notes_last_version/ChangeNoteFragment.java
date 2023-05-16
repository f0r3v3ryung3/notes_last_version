package com.example.notes_last_version;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.notes_last_version.databinding.FragmentChangeNoteBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class ChangeNoteFragment extends Fragment {
    SharedPreferences memory;
    SharedPreferences noteNow;
    String title;
    String text;
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

        noteNow = view.getContext().getSharedPreferences("noteNow", MODE_PRIVATE);
        memory = view.getContext().getSharedPreferences("Notes", MODE_PRIVATE);

        title = noteNow.getString("noteNow", "");
        text = memory.getString(title, "");

        name = view.findViewById(R.id.nameEdit);
        textKeep = view.findViewById(R.id.textKeep);
        memory = view.getContext().getSharedPreferences("Notes", MODE_PRIVATE);

        name.setText(title);
        textKeep.setText(text);


        toolbar = view.findViewById(R.id.topAppBar);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.delete: {

                    SharedPreferences.Editor ed = memory.edit();
                    ed.remove(title);
                    ed.commit();

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
                ed.remove(title);
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
        ed.remove(title);
        ed.putString(name.getText().toString(), textKeep.getText().toString());
        ed.commit();

        Fragment newFragment = new NotesFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.flFragment, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
        super.onDestroy();
    }
}