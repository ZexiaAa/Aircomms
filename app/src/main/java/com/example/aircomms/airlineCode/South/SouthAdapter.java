package com.example.aircomms.airlineCode.South;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airlineCode.Europe.EuropeAdapter;
import com.example.aircomms.airlineCode.Europe.EuropeItem;

import java.util.ArrayList;

public class SouthAdapter extends RecyclerView.Adapter<SouthAdapter.MyViewHolder>{

    Context context;
    private ArrayList<SouthItem> itemsArrayList;

    public void setFilteredList (ArrayList<SouthItem> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();

    }

    public SouthAdapter (ArrayList<SouthItem> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public SouthAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.south_code_style, parent, false);
        return new SouthAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SouthAdapter.MyViewHolder holder, int position) {
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


            _callsign = itemView.findViewById(R.id.south_callsign);
            _icao = itemView.findViewById(R.id.south_icao_code);
            _logo = itemView.findViewById(R.id.south_logo);
        }
    }
}
