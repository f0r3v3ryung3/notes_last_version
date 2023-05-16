package com.example.notes_last_version;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.NoteViewHolder> {
    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView text;

        SharedPreferences noteNow;

        NoteViewHolder(View noteView) {
            super(noteView);
            cv = (CardView) noteView.findViewById(R.id.card);
            title = (TextView) noteView.findViewById(R.id.title);
            text = (TextView) noteView.findViewById(R.id.text);
            noteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteNow = noteView.getContext().getSharedPreferences("noteNow", Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = noteNow.edit();
                    ed.putString("noteNow", title.getText().toString());
                    ed.commit();

                    Fragment newFragment = new ChangeNoteFragment();
                    FragmentTransaction transaction = ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.flFragment, newFragment);
                    transaction.addToBackStack(null);

                    transaction.commit();
                }
            });
        }
    }
    List<Note> notes;

    RVAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        if (notes == null) return 0;
        return notes.size();
    }

    public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_note, viewGroup, false);
        NoteViewHolder nvh = new NoteViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.title.setText(notes.get(position).title);
        holder.text.setText(notes.get(position).text);
    }

}
