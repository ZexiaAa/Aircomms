package com.example.aircomms.airportCode.Europe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airportCode.Australia.AustraliaAdapterr;
import com.example.aircomms.airportCode.Australia.AustraliaItemm;

import java.util.ArrayList;

public class EuropeAdapterr extends RecyclerView.Adapter<EuropeAdapterr.MyViewHolder>{


    Context context;
    private ArrayList<EuropeItemm> itemsArrayList;

    public void setFilteredList (ArrayList<EuropeItemm> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();

    }

    public EuropeAdapterr (ArrayList<EuropeItemm> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public EuropeAdapterr.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.airport_europe, parent, false);
        return new EuropeAdapterr.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EuropeAdapterr.MyViewHolder holder, int position) {
        String airport = itemsArrayList.get(position).get_airport();
        String icao = itemsArrayList.get(position).get_icao();
        String location = itemsArrayList.get(position).get_location();

        holder._icao.setText(icao);
        holder._airport.setText(airport);
        holder._location.setText(location);
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView _airport,  _icao, _location;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            _airport = itemView.findViewById(R.id.europe_airport);
            _icao = itemView.findViewById(R.id.europe_icao);
            _location = itemView.findViewById(R.id.europe_location);
        }
    }
}
