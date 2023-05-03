package com.example.aircomms.airportCode.Asia.China;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airportCode.Africa.AfricaAdapterr;
import com.example.aircomms.airportCode.Africa.AfricaItemm;

import java.util.ArrayList;

public class ChinaAdapter extends RecyclerView.Adapter<ChinaAdapter.MyViewHolder> {
    Context context;
    private ArrayList<ChinaItem> itemsArrayList;

    public void setFilteredList (ArrayList<ChinaItem> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();}


    public ChinaAdapter(ArrayList<ChinaItem> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public ChinaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.asia_china_style, parent, false);
        return new ChinaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChinaAdapter.MyViewHolder holder, int position) {
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

            _airport = itemView.findViewById(R.id.china_airport);
            _icao = itemView.findViewById(R.id.china_icao);
            _location = itemView.findViewById(R.id.china_location);
        }
    }
}
