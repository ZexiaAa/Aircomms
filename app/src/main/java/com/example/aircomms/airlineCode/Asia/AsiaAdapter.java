package com.example.aircomms.airlineCode.Asia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;

import java.util.ArrayList;

public class AsiaAdapter extends RecyclerView.Adapter<AsiaAdapter.MyViewHolder> {

    Context context;
    private ArrayList<AsiaItem> itemsArrayList;


    public void setFilteredList (ArrayList<AsiaItem> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();

    }

    public AsiaAdapter(ArrayList<AsiaItem> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView a_callsign,  a_icao;
        private ImageView a_logo;
        public MyViewHolder(@NonNull View view) {
            super(view);

            a_callsign = view.findViewById(R.id.asia_callsign);
            a_icao = view.findViewById(R.id.asia_icao_code);
            a_logo = view.findViewById(R.id.asia_logo);

        }
    }


    @NonNull
    @Override
    public AsiaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.asia_code_style, parent, false);
        return new AsiaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AsiaAdapter.MyViewHolder holder, int position) {
        String callsign = itemsArrayList.get(position).getAsia_callsign();
        String icao = itemsArrayList.get(position).getAsia_icao();
        int logo = itemsArrayList.get(position).getAsia_logo();

        holder.a_logo.setImageResource(itemsArrayList.get(position).getAsia_logo());
        holder.a_callsign.setText(callsign);
        holder.a_icao.setText(icao);

    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }


}
