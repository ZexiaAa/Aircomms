package com.example.aircomms.approach.Examples;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;

import java.util.ArrayList;

public class NoteItemAdapter extends RecyclerView.Adapter<NoteItemAdapter.ViewHolder> {
    private ArrayList<NoteItem> noteItems;

    public NoteItemAdapter(ArrayList<NoteItem> noteItems) {
        this.noteItems = noteItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_ac_example_note, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteItem noteItem = getItem(position);
        if (noteItem != null) {
            holder.note.setText(noteItem.getNoteContent());
        }
    }

    @Override
    public int getItemCount() {
        return noteItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView note;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            note = itemView.findViewById(R.id.noteText);
        }
    }

    private NoteItem getItem(int position) {
        return noteItems.get(position);
    }
}
