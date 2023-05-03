package com.example.aircomms.airportCode.Australia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airlineCode.Australia.AustraliaAdapter;
import com.example.aircomms.airlineCode.Australia.AustraliaItem;

import java.util.ArrayList;

public class AustraliaAdapterr extends RecyclerView.Adapter<AustraliaAdapterr.MyViewHolder>{

    Context context;
    private ArrayList<AustraliaItemm> itemsArrayList;

    public void setFilteredList (ArrayList<AustraliaItemm> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();

    }

    public AustraliaAdapterr (ArrayList<AustraliaItemm> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AustraliaAdapterr.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.airport_australia, parent, false);
        return new AustraliaAdapterr.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AustraliaAdapterr.MyViewHolder holder, int position) {
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

            _airport = itemView.findViewById(R.id.australia_airport);
            _icao = itemView.findViewById(R.id.australia_icao);
            _location = itemView.findViewById(R.id.australia_location);
        }
    }
}
