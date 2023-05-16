package com.example.notes_last_version;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class chatGPTAdapter extends RecyclerView.Adapter<chatGPTAdapter.chatGPTViewHolder> {
    public static class chatGPTViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView text;

        SharedPreferences noteNow;

        chatGPTViewHolder(View noteView) {
            super(noteView);
            title = (TextView) noteView.findViewById(R.id.title);
            text = (TextView) noteView.findViewById(R.id.text);
            noteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("", text.getText().toString());
                    clipboard.setPrimaryClip(clip);
                }
            });
        }
    }
    List<Note> notes;

    chatGPTAdapter(List<Note> notes) {
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

    public chatGPTViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_note, viewGroup, false);
        chatGPTViewHolder nvh = new chatGPTViewHolder(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(chatGPTViewHolder holder, int position) {
        holder.title.setText(notes.get(position).title);
        holder.text.setText(notes.get(position).text);
    }

}
