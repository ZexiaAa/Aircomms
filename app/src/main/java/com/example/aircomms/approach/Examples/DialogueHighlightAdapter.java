package com.example.aircomms.approach.Examples;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;

import java.util.ArrayList;

public class DialogueHighlightAdapter extends RecyclerView.Adapter<DialogueHighlightAdapter.ViewHolder> {
    private ArrayList<DialogueHighlight> dialogueHighlights;

    public DialogueHighlightAdapter(ArrayList<DialogueHighlight> dialogueHighlights) {
        this.dialogueHighlights = dialogueHighlights;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_ac_example_highlight, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DialogueHighlight dialogueHighlight = getItem(position);
        if (dialogueHighlight != null) {
            holder.highlight.setText(dialogueHighlight.getHighlightText());
        }
    }

    @Override
    public int getItemCount() {
        return dialogueHighlights.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView highlight;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            highlight = itemView.findViewById(R.id.highlightText);
        }
    }

    private DialogueHighlight getItem(int position) {
        return dialogueHighlights.get(position);
    }
}
