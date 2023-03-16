package com.example.aircomms.airlineCode.Australia;

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

public class AustraliaAdapter extends RecyclerView.Adapter<AustraliaAdapter.MyViewHolder>{

    Context context;
    private ArrayList<AustraliaItem> itemsArrayList;

    public void setFilteredList (ArrayList<AustraliaItem> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();

    }

    public AustraliaAdapter (ArrayList<AustraliaItem> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AustraliaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.australia_code_style, parent, false);
        return new AustraliaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AustraliaAdapter.MyViewHolder holder, int position) {
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

            _callsign = itemView.findViewById(R.id.aus_callsign);
            _icao = itemView.findViewById(R.id.aus_icao_code);
            _logo = itemView.findViewById(R.id.aus_logo);
        }
    }
}
