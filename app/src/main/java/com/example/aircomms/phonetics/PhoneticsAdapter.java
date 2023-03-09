package com.example.aircomms.phonetics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;

import java.util.ArrayList;
import java.util.List;

public class PhoneticsAdapter extends RecyclerView.Adapter<PhoneticsAdapter.MyViewHolder> {
    private List<String> mData;
    private LayoutInflater mInflater;
    private ArrayList <Items> itemsArrayList;

    public PhoneticsAdapter(ArrayList<Items> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView charTxt, teleTxt, phonicTxt;

        public MyViewHolder(final View view){
            super(view);
            charTxt = view.findViewById(R.id.phone_char);
            teleTxt = view.findViewById(R.id.phone_telephony);
            phonicTxt= view.findViewById(R.id.phone_phonic);
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
        String pchar = itemsArrayList.get(position).getCharacter();
        String ptele = itemsArrayList.get(position).getTelephony();
        String pphonic = itemsArrayList.get(position).getPhonic();

        holder.teleTxt.setText(ptele);
        holder.charTxt.setText(pchar);
        holder.phonicTxt.setText(pphonic);
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }


}
