package com.example.aircomms.approach.Examples;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;

import java.util.ArrayList;

public class DialogueItemAdapter extends RecyclerView.Adapter<DialogueItemAdapter.ViewHolder> {
    private ArrayList<DialogueItem> dialogueItems;

    private final Context context;

    public DialogueItemAdapter(ArrayList<DialogueItem> dialogueItems, Context context) {
        this.dialogueItems = dialogueItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_ac_example_dialogue, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Gets Data
        DialogueItem dialogueItem = getItem(position);

        //Sets Data
        if (dialogueItem.getDialogueSpeaker() != null) {
            holder.speaker.setVisibility(View.VISIBLE);
            holder.speaker.setText(dialogueItem.getDialogueSpeaker());
        } else {
            holder.speaker.setVisibility(View.GONE);
        }
        if (dialogueItem.getDialogueContent() != null) {
            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText(dialogueItem.getDialogueContent());
        } else {
            holder.content.setVisibility(View.GONE);
        }

        //Setup Dialogue Highlight RecyclerView
        if (dialogueItem.getDialogueHighlights() != null) {
            holder.highlightHolder.setLayoutManager(new LinearLayoutManager(context));
            holder.highlightHolder.setItemAnimator(new DefaultItemAnimator());
            holder.highlightHolder.setAdapter(new DialogueHighlightAdapter(dialogueItem.getDialogueHighlights()));
        }
    }

    @Override
    public int getItemCount() {
        return dialogueItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView speaker, content;
        RecyclerView highlightHolder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            speaker = itemView.findViewById(R.id.dialogueItemSpeaker);
            content = itemView.findViewById(R.id.dialogueItemContent);

            highlightHolder = itemView.findViewById(R.id.dialogueItemHighlight);
        }
    }

    private DialogueItem getItem(int position) {
        return dialogueItems.get(position);
    }
}
