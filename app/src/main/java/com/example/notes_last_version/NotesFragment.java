package com.example.notes_last_version;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NotesFragment extends Fragment {
    com.google.android.material.floatingactionbutton.FloatingActionButton fab;
    RecyclerView rv;
    SharedPreferences memory;
    List<Note> notes = new ArrayList<Note>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater_exit = TransitionInflater.from(requireContext());
        setExitTransition(inflater_exit.inflateTransition(R.transition.fade));

        TransitionInflater inflater_enter = TransitionInflater.from(requireContext());
        setEnterTransition(inflater_enter.inflateTransition(R.transition.fade));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);


        memory = view.getContext().getSharedPreferences("Notes", MODE_PRIVATE);
        Map<String, ?> keeps = memory.getAll();
        for (String i: keeps.keySet()) {
            if (!memory.getString(i, "").equals("")) {
                Note note = new Note(i, memory.getString(i, ""));
                notes.add(note);
            }
        }

        rv = view.findViewById(R.id.recycler_view);

        rv.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));

        rv.setHasFixedSize(true);

        fab = view.findViewById(R.id.floating_action_button);

        fab.setOnClickListener((v)->{
            Fragment newFragment = new MakeNoteFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.flFragment, newFragment);
            transaction.addToBackStack(null);

            transaction.commit();
        });

        RVAdapter adapter = new RVAdapter(notes);
        rv.setAdapter(adapter);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_note_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_note_spacing_small);
        rv.addItemDecoration(new NoteItemDecoration(largePadding, smallPadding));

        return view;
    }

}