package com.example.aircomms.airportCode.Asia.India;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airportCode.Asia.China.ChinaAdapter;
import com.example.aircomms.airportCode.Asia.China.ChinaItem;

import java.util.ArrayList;

public class IndiaAdapter extends RecyclerView.Adapter<IndiaAdapter.MyViewHolder> {

    Context context;
    private ArrayList<IndiaItem> itemsArrayList;

    public void setFilteredList (ArrayList<IndiaItem> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();}


    public IndiaAdapter(ArrayList<IndiaItem> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }



    @NonNull
    @Override
    public IndiaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.asia_india_style, parent, false);
        return new IndiaAdapter.MyViewHolder(itemView);

        }

    @Override
    public void onBindViewHolder(@NonNull IndiaAdapter.MyViewHolder holder, int position) {
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

            _airport = itemView.findViewById(R.id.india_airport);
            _icao = itemView.findViewById(R.id.india_icao);
            _location = itemView.findViewById(R.id.india_location);
        }
    }
}
