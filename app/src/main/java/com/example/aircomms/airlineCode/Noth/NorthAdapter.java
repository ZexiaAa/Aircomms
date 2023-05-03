package com.example.aircomms.airlineCode.Noth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airlineCode.Africa.AfricaItem;
import com.example.aircomms.airlineCode.Australia.AustraliaItem;
import com.example.aircomms.airlineCode.Europe.EuropeAdapter;
import com.example.aircomms.airlineCode.Europe.EuropeItem;

import java.util.ArrayList;

public class NorthAdapter extends RecyclerView.Adapter<NorthAdapter.MyViewHolder>{

    Context context;
    private ArrayList<NorthItem> itemsArrayList;

    public NorthAdapter (ArrayList<NorthItem> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }

    public void setFilteredList (ArrayList<NorthItem> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public NorthAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.north_code_style, parent, false);
        return new NorthAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NorthAdapter.MyViewHolder holder, int position) {
        String callsign = itemsArrayList.get(position).get_callsign();
        String icao = itemsArrayList.get(position).get_icao();
        int logo = itemsArrayList.get(position).get_logo();

        holder._logo.setImageResource(itemsArrayList.get(position).get_logo());
        holder._callsign.setText(callsign);
        holder._icao.setText(icao);
    }

    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView _callsign,  _icao;
        private ImageView _logo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            _callsign = itemView.findViewById(R.id.north_callsign);
            _icao = itemView.findViewById(R.id.north_icao_code);
            _logo = itemView.findViewById(R.id.north_logo);
        }
    }
}
