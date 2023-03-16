package com.example.aircomms.airlineCode.Europe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.R;
import com.example.aircomms.airlineCode.Australia.AustraliaAdapter;
import com.example.aircomms.airlineCode.Australia.AustraliaItem;

import java.util.ArrayList;

public class EuropeAdapter  extends RecyclerView.Adapter<EuropeAdapter.MyViewHolder>{

    Context context;
    private ArrayList<EuropeItem> itemsArrayList;

    public void setFilteredList (ArrayList<EuropeItem> filteredList){

        this.itemsArrayList = filteredList;
        notifyDataSetChanged();

    }

    public EuropeAdapter (ArrayList<EuropeItem> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
        this.context = context;
    }
    @NonNull
    @Override
    public EuropeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.europe_code_style, parent, false);
        return new EuropeAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EuropeAdapter.MyViewHolder holder, int position) {
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
            _callsign = itemView.findViewById(R.id.eu_callsign);
            _icao = itemView.findViewById(R.id.eu_icao_code);
            _logo = itemView.findViewById(R.id.eu_logo);
        }
    }
}
