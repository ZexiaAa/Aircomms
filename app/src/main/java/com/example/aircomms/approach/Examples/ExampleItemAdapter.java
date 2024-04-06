package com.example.aircomms.approach.Examples;

import android.content.Context;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.approach.ApproachControl;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ExampleItemAdapter extends RecyclerView.Adapter<ExampleItemAdapter.ViewHolder> {
    private ArrayList<ExampleItem> exampleItemArrayList;
    private final Context context;
    private final ArrayList<ViewHolder> adapterViewHolders;
    private ArrayList<TextView> flightStripTextViews;
    private ArrayList<View> flightStripOtherViews;
    private String startupCode;
    private MediaPlayer audioPlayer;
    private Timer audioTimer;
    private ProgressBar audioProgressBar;

    public ExampleItemAdapter(ArrayList<ExampleItem> exampleItemArrayList, Context context) {
        this.exampleItemArrayList = exampleItemArrayList;
        this.context = context;
        adapterViewHolders = new ArrayList<>();
    }

    public void setFlightStripTextViews(ArrayList<TextView> flightStripTextViews) {
        this.flightStripTextViews = flightStripTextViews;
    }

    public void setFlightStripOtherViews(ArrayList<View> flightStripOtherViews) {
        this.flightStripOtherViews = flightStripOtherViews;
    }

    public void setStartupCode(String startupCode) {
        this.startupCode = startupCode;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.style_ac_example_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Gets Data
        ExampleItem exampleItem = getItem(position);

        //Sets Title
        holder.title.setText(exampleItem.getExampleTitle());

        //Setup Audio Icon
        if (exampleItem.getAudioFileID() == 0) {
            holder.audioIcon.setVisibility(View.GONE);
        } else {
            holder.audioIcon.setVisibility(View.VISIBLE);
        }

        //Setup Item On-Click
        if (holder.contentLayout.getVisibility() == View.VISIBLE) {
            holder.contentLayout.setVisibility(View.GONE);
        }
        adapterViewHolders.add(position,holder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gets Current Data
                int clickedItemPosition = holder.getAdapterPosition();
                ViewHolder clickedItemHolder = adapterViewHolders.get(clickedItemPosition);
                ExampleItem currentExampleItem = getItem(clickedItemPosition);

                //Controls Audio
                audioProgressBar = clickedItemHolder.audioProgressBar;
                audioController(clickedItemHolder.contentLayout,currentExampleItem.getAudioFileID());

                //Toggles for Clicked Item
                if (clickedItemHolder.contentLayout.getVisibility() == View.GONE) {
                    clickedItemHolder.contentLayout.setVisibility(View.VISIBLE);
                } else if (clickedItemHolder.contentLayout.getVisibility() == View.VISIBLE) {
                    clickedItemHolder.contentLayout.setVisibility(View.GONE);
                }

                //Hide Other Items
                for (int i = 0; i < adapterViewHolders.size(); i++) {
                    if (i != clickedItemPosition) {
                        adapterViewHolders.get(i).contentLayout.setVisibility(View.GONE);
                    }
                }

                //Manage Flight Strip
                manageFlightStrip(clickedItemPosition);
            }
        });

        //Setup Dialogue RecyclerView
        holder.dialogueHolder.setLayoutManager(new LinearLayoutManager(context));
        holder.dialogueHolder.setItemAnimator(new DefaultItemAnimator());
        holder.dialogueHolder.setAdapter(new DialogueItemAdapter(exampleItem.getExampleDialogue(),context));

        //Setup Notes RecyclerView
        if (exampleItem.getExampleNotes() != null) {
            holder.notesLayout.setVisibility(View.VISIBLE);

            holder.notesHolder.setLayoutManager(new LinearLayoutManager(context));
            holder.notesHolder.setItemAnimator(new DefaultItemAnimator());
            holder.notesHolder.setAdapter(new NoteItemAdapter(exampleItem.getExampleNotes()));
        } else {
            holder.notesLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return exampleItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView audioIcon;
        RecyclerView dialogueHolder, notesHolder;
        ProgressBar audioProgressBar;
        LinearLayout contentLayout, notesLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.exampleItemTitle);

            audioIcon = itemView.findViewById(R.id.exampleItemPlayButton);

            dialogueHolder = itemView.findViewById(R.id.exampleItemDialogueRecyclerView);
            notesHolder = itemView.findViewById(R.id.exampleItemNotesRecyclerView);

            audioProgressBar = itemView.findViewById(R.id.exampleItemProgressBar);

            contentLayout = itemView.findViewById(R.id.exampleItemList);
            notesLayout = itemView.findViewById(R.id.exampleItemNotesHolder);
        }
    }

    private ExampleItem getItem(int position) {
        return exampleItemArrayList.get(position);
    }

    public void updateData(ArrayList<ExampleItem> exampleItems) {
        exampleItemArrayList = exampleItems;
        adapterViewHolders.clear();
        notifyDataSetChanged();
    }

    public void manageFlightStrip(int clickedItemPosition) {
        switch (startupCode) {
            case ApproachControl.EXAMPLE_DEPARTURE:
                switch (clickedItemPosition) {
                    default:
                        flightStripTextViews.get(0).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(8).setPaintFlags(flightStripTextViews.get(8).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(9).setPaintFlags(flightStripTextViews.get(9).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 1:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(8).setPaintFlags(flightStripTextViews.get(8).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(9).setPaintFlags(flightStripTextViews.get(9).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 2:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(8).setPaintFlags(flightStripTextViews.get(8).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(9).setPaintFlags(flightStripTextViews.get(9).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 3:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(8).setPaintFlags(flightStripTextViews.get(8).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(9).setPaintFlags(flightStripTextViews.get(9).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 4:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(8).setPaintFlags(flightStripTextViews.get(8).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(9).setPaintFlags(flightStripTextViews.get(9).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 5:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(8).setPaintFlags(flightStripTextViews.get(8).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(9).setPaintFlags(flightStripTextViews.get(9).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 6:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.VISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(8).setPaintFlags(flightStripTextViews.get(8).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(9).setPaintFlags(flightStripTextViews.get(9).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 7:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.VISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(8).setPaintFlags(flightStripTextViews.get(8).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(9).setPaintFlags(flightStripTextViews.get(9).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;
                    case 8:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.VISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.VISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(8).setPaintFlags(flightStripTextViews.get(8).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(9).setPaintFlags(flightStripTextViews.get(9).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;
                }
                break;
            case ApproachControl.EXAMPLE_ARRIVAL:
                switch (clickedItemPosition) {
                    default:
                        flightStripTextViews.get(0).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(24).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(25).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(26).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(7).setPaintFlags(flightStripTextViews.get(7).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 0:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(24).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(25).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(26).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(7).setPaintFlags(flightStripTextViews.get(7).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 1:
                    case 2:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(24).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(25).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(26).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(7).setPaintFlags(flightStripTextViews.get(7).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 3:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(24).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(25).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(26).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(7).setPaintFlags(flightStripTextViews.get(7).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 4:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(24).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(25).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(26).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(7).setPaintFlags(flightStripTextViews.get(7).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 5:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(24).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(25).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(26).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(7).setPaintFlags(flightStripTextViews.get(7).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;
                    case 6:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(24).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(25).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(26).setVisibility(View.VISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(7).setPaintFlags(flightStripTextViews.get(7).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;
                    case 7:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(24).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(25).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(26).setVisibility(View.VISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(7).setPaintFlags(flightStripTextViews.get(7).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;
                }
                break;
            case ApproachControl.EXAMPLE_STO:
                switch (clickedItemPosition) {
                    default:
                        flightStripTextViews.get(0).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(10).setPaintFlags(flightStripTextViews.get(10).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(12).setPaintFlags(flightStripTextViews.get(12).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 1:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(10).setPaintFlags(flightStripTextViews.get(10).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(12).setPaintFlags(flightStripTextViews.get(12).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 2:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(10).setPaintFlags(flightStripTextViews.get(10).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(12).setPaintFlags(flightStripTextViews.get(12).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 3:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.INVISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(10).setPaintFlags(flightStripTextViews.get(10).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(12).setPaintFlags(flightStripTextViews.get(12).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        break;
                    case 4:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.VISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(10).setPaintFlags(flightStripTextViews.get(10).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(12).setPaintFlags(flightStripTextViews.get(12).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;
                    case 5:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.VISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(10).setPaintFlags(flightStripTextViews.get(10).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(12).setPaintFlags(flightStripTextViews.get(12).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;
                    case 6:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.VISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(10).setPaintFlags(flightStripTextViews.get(10).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(12).setPaintFlags(flightStripTextViews.get(12).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;
                    case 7:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.INVISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.VISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() & ~(Paint.STRIKE_THRU_TEXT_FLAG));
                        flightStripTextViews.get(10).setPaintFlags(flightStripTextViews.get(10).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(12).setPaintFlags(flightStripTextViews.get(12).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;
                    case 8:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.INVISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.VISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(10).setPaintFlags(flightStripTextViews.get(10).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(12).setPaintFlags(flightStripTextViews.get(12).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;
                    case 9:
                        flightStripTextViews.get(0).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(1).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(2).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(3).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(4).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(5).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(6).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(7).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(8).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(9).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(10).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(11).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(12).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(13).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(14).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(15).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(16).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(17).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(18).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(19).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(20).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(21).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(22).setVisibility(View.VISIBLE);
                        flightStripTextViews.get(23).setVisibility(View.VISIBLE);

                        flightStripOtherViews.get(0).setVisibility(View.VISIBLE);

                        //Strike Through Text
                        flightStripTextViews.get(6).setPaintFlags(flightStripTextViews.get(6).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(10).setPaintFlags(flightStripTextViews.get(10).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        flightStripTextViews.get(12).setPaintFlags(flightStripTextViews.get(12).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        break;
                }
                break;
        }
    }

    private void audioController(View view, int audioFileID) {
        int visibility = view.getVisibility();
        if (visibility == View.GONE) {
            if (audioPlayer != null) {
                stopAudio();
            }
            if (audioFileID != 0) {
                playAudio(audioFileID);
            }
        } else if (visibility == View.VISIBLE) {
            stopAudio();
        }
    }

    private void playAudio(int audioFileID) {
        //Creates MediaPlayer instance
        audioPlayer = MediaPlayer.create(context,audioFileID);

        //Releases MediaPlayer instance once Audio File has ended
        audioPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (audioProgressBar != null) {
                    audioProgressBar.setProgress(audioPlayer.getCurrentPosition());
                }
                stopAudio();
            }
        });

        //Plays the Audio File via MediaPlayer instance
        audioPlayer.start();

        //Audio Progress Bar
        audioProgressBar.setMax(audioPlayer.getDuration());

        //Audio Playback Timer Schedule
        audioTimer = new Timer();
        audioTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (audioProgressBar != null && audioPlayer != null) {
                    audioProgressBar.setProgress(audioPlayer.getCurrentPosition());
                }
            }
        },0,50);
    }

    private void stopAudio() {
        if (audioPlayer != null) {
            if (audioTimer != null) {
                audioTimer.cancel();
                audioTimer = null;
            }

            audioPlayer.release();
            audioPlayer = null;
        }
    }

    public void onStop() {
        stopAudio();
        if (audioTimer != null) {
            audioTimer.cancel();
            audioTimer = null;
        }
    }
}
