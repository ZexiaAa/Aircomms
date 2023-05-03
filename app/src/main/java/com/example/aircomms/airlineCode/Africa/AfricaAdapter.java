package com.example.aircomms.airlineCode.Africa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airlineCode.Asia.AsiaAdapter;
import com.example.aircomms.airlineCode.Asia.AsiaItem;
import com.example.aircomms.airlineCode.Australia.AustraliaItem;
import com.example.aircomms.phonetics.Items;

import java.util.ArrayList;

public class AfricaAdapter extends RecyclerView.Adapter<AfricaAdapter.MyViewHolder> {

    Context context;
    private ArrayList<AfricaItem> itemsArrayList;


    public AfricaAdapter(ArrayList<AfricaItem> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }


    public void setFilteredList (ArrayList<AfricaItem> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public AfricaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.africa_code_style, parent, false);
        return new AfricaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AfricaAdapter.MyViewHolder holder, int position) {
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

            _callsign = itemView.findViewById(R.id.africa_callsign);
            _icao = itemView.findViewById(R.id.africa_icao_code);
            _logo = itemView.findViewById(R.id.africa_logo);

        }
    }
}
