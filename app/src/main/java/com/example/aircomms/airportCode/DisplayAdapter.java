package com.example.aircomms.airportCode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;

import java.util.ArrayList;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.ViewHolder> {
    private final ArrayList<DisplayItem> displayItemArrayList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.airport_code_style, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Fetches Data
        String airportName, airportICAO, airportLocation;
        airportName = displayItemArrayList.get(position).getAirportName();
        airportICAO = displayItemArrayList.get(position).getAirportICAO();
        airportLocation = displayItemArrayList.get(position).getAirportLocation();

        //Sets into Holder, and handles Null Items
        if (airportName != null) {
            holder.airportName.setText(airportName);
        } else {
            holder.airportName.setText("-");
        }
        if (airportICAO != null) {
            holder.airportICAO.setText(airportICAO);
        } else {
            holder.airportICAO.setText("-");
        }
        if (airportLocation != null) {
            holder.airportLocation.setText(airportLocation);
        } else {
            holder.airportLocation.setText("-");
        }
    }

    @Override
    public int getItemCount() {
        return displayItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView airportName, airportICAO, airportLocation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            airportName = itemView.findViewById(R.id.displayItem_Name);
            airportICAO = itemView.findViewById(R.id.displayItem_ICAO);
            airportLocation = itemView.findViewById(R.id.displayItem_Location);
        }
    }

    public DisplayAdapter(ArrayList<DisplayItem> items) {
        displayItemArrayList = items;
    }

}
