package com.example.aircomms.airportCode.Asia.Japan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airportCode.Asia.India.IndiaAdapter;
import com.example.aircomms.airportCode.Asia.India.IndiaItem;

import java.util.ArrayList;

public class JapanAdapter extends RecyclerView.Adapter<JapanAdapter.MyViewHolder>  {

    Context context;
    private ArrayList<JapanItem> itemsArrayList;

    public void setFilteredList (ArrayList<JapanItem> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();}


    public JapanAdapter(ArrayList<JapanItem> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }



    @NonNull
    @Override
    public JapanAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.asia_japan_style, parent, false);
        return new JapanAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JapanAdapter.MyViewHolder holder, int position) {
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

            _airport = itemView.findViewById(R.id.japan_airport);
            _icao = itemView.findViewById(R.id.japan_icao);
            _location = itemView.findViewById(R.id.japan_location);
        }
    }
}
