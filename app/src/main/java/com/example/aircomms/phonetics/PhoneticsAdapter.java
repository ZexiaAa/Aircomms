package com.example.aircomms.phonetics;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;

import java.util.ArrayList;
import java.util.List;

public class PhoneticsAdapter extends RecyclerView.Adapter<PhoneticsAdapter.MyViewHolder> {
    private List<Items> itemsArrayList;


    public PhoneticsAdapter(List<Items> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }

    public void filterList(ArrayList<Items> filteredList) {
        itemsArrayList = filteredList;
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView charTxt, teleTxt, phonicTxt;
        private ImageButton playBtn;

        public MyViewHolder(final View view){
            super(view);
            charTxt = view.findViewById(R.id.phone_char);
            teleTxt = view.findViewById(R.id.phone_telephony);
            phonicTxt= view.findViewById(R.id.phone_phonic);
            playBtn = view.findViewById(R.id.play_button);
            playBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Items item = itemsArrayList.get(position);

            MediaPlayer mediaPlayer = MediaPlayer.create(v.getContext(), item.getAudioResource());
            mediaPlayer.start();
        }

    }

    @NonNull
    @Override
    public PhoneticsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.phonetics_style, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneticsAdapter.MyViewHolder holder, int position) {
        Items item = itemsArrayList.get(position);

        holder.teleTxt.setText(item.getTelephony());
        holder.charTxt.setText(item.getCharacter());
        holder.phonicTxt.setText(item.getPhonic());
        holder.playBtn.setTag(position);

    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }



}


