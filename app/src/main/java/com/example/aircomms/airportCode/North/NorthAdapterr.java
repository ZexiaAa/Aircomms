package com.example.aircomms.airportCode.North;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airlineCode.Noth.NorthAdapter;
import com.example.aircomms.airlineCode.Noth.NorthItem;

import java.util.ArrayList;

public class NorthAdapterr extends RecyclerView.Adapter<NorthAdapterr.MyViewHolder>{

    Context context;
    private ArrayList<NorthItemm> itemsArrayList;

    public void setFilteredList (ArrayList<NorthItemm> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();

    }
    public NorthAdapterr (ArrayList<NorthItemm> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NorthAdapterr.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.airport_north, parent, false);
        return new NorthAdapterr.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NorthAdapterr.MyViewHolder holder, int position) {
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

            _airport = itemView.findViewById(R.id.north_airport);
            _icao = itemView.findViewById(R.id.north_icao_);
            _location = itemView.findViewById(R.id.north_location);
        }
    }
}
