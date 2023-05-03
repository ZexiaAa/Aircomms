package com.example.aircomms.airportCode.South;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airlineCode.South.SouthAdapter;
import com.example.aircomms.airlineCode.South.SouthItem;

import java.util.ArrayList;

public class SouthAdapterr extends RecyclerView.Adapter<SouthAdapterr.MyViewHolder>{

    Context context;
    private ArrayList<SouthItemm> itemsArrayList;

    public void setFilteredList (ArrayList<SouthItemm> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();

    }
    public SouthAdapterr (ArrayList<SouthItemm> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SouthAdapterr.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.airport_south, parent, false);
        return new SouthAdapterr.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SouthAdapterr.MyViewHolder holder, int position) {
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

            _airport = itemView.findViewById(R.id.south_airport);
            _icao = itemView.findViewById(R.id.south_icao);
            _location = itemView.findViewById(R.id.south_location);
        }
    }
}
