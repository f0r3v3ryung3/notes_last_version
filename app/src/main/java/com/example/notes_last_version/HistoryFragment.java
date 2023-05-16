package com.example.notes_last_version;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HistoryFragment extends Fragment {
    MaterialToolbar toolbar;
    RecyclerView rv;
    SharedPreferences history;
    List<Note> historyChatGPT = new ArrayList<Note>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuVisibility(true);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);


        toolbar = view.findViewById(R.id.top_app_bar_history);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment newFragment = new ChatFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.flFragment, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();

            }
        });

        history = view.getContext().getSharedPreferences("ChatGPT", MODE_PRIVATE);
        Map<String, ?> keeps = history.getAll();
        for (String i: keeps.keySet()) {
            if (!history.getString(i, "").equals("")) {
                Note note = new Note(i, history.getString(i, ""));
                historyChatGPT.add(note);
            }
        }
        System.out.println(historyChatGPT.size());

        rv = view.findViewById(R.id.recycler_view_history);

        rv.setLayoutManager(new LinearLayoutManager(view.getContext()));

        rv.setHasFixedSize(true);

        chatGPTAdapter adapter = new chatGPTAdapter(historyChatGPT);
        rv.setAdapter(adapter);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_note_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_note_spacing_small);
        rv.addItemDecoration(new NoteItemDecoration(largePadding, smallPadding));

        return view;
    }
}