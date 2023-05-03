package com.example.aircomms.airportCode.Asia.Phil.Domestic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airportCode.Asia.Phil.International.InterAdapter;
import com.example.aircomms.airportCode.Asia.Phil.International.InterItem;

import java.util.ArrayList;

public class DomAdapter extends RecyclerView.Adapter<DomAdapter.MyViewHolder>{

    Context context;
    private ArrayList<DomItem> itemsArrayList;

    public void setFilteredList (ArrayList<DomItem> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();}


    public DomAdapter(ArrayList<DomItem> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }



    @NonNull
    @Override
    public DomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ph_domestic_style, parent, false);
        return new DomAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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

            _airport = itemView.findViewById(R.id.dom_airport);
            _icao = itemView.findViewById(R.id.dom_icao);
            _location = itemView.findViewById(R.id.dom_location);
        }
    }
}
