package com.example.aircomms.airportCode.Africa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airlineCode.Africa.AfricaAdapter;
import com.example.aircomms.airlineCode.Africa.AfricaItem;

import java.util.ArrayList;

public class AfricaAdapterr extends RecyclerView.Adapter<AfricaAdapterr.MyViewHolder>{
    Context context;
    private ArrayList<AfricaItemm> itemsArrayList;

    public void setFilteredList (ArrayList<AfricaItemm> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();}


    public AfricaAdapterr(ArrayList<AfricaItemm> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public AfricaAdapterr.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.airport_africa, parent, false);
        return new AfricaAdapterr.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AfricaAdapterr.MyViewHolder holder, int position) {
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

            _airport = itemView.findViewById(R.id.africa_airport);
            _icao = itemView.findViewById(R.id.africa_icao);
            _location = itemView.findViewById(R.id.africa_location);
        }
    }
}
