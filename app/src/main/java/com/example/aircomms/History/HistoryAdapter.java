package com.example.aircomms.History;

import static com.example.aircomms.History.HistoryItem.formatDate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aircomms.Extras.Other;
import com.example.aircomms.MainActivity;
import com.example.aircomms.R;
import com.example.aircomms.airlineCode.Africa.Africa;
import com.example.aircomms.airlineCode.AirlineCode;
import com.example.aircomms.airlineCode.Asia.Asia;
import com.example.aircomms.airlineCode.Australia.Australia;
import com.example.aircomms.airlineCode.Europe.Europe;
import com.example.aircomms.airlineCode.Noth.North;
import com.example.aircomms.airlineCode.South.South;
import com.example.aircomms.airportCode.Africa.Airport_Africa;
import com.example.aircomms.airportCode.AirportCode;
import com.example.aircomms.airportCode.Asia.AirportAsia;
import com.example.aircomms.airportCode.Asia.China.AsiaChina;
import com.example.aircomms.airportCode.Asia.India.AsiaIndia;
import com.example.aircomms.airportCode.Asia.Indonesia.AsiaIndonesia;
import com.example.aircomms.airportCode.Asia.Japan.AsiaJapan;
import com.example.aircomms.airportCode.Asia.SouthKorea.AsiaSouthKorea;
import com.example.aircomms.airportCode.Asia.Thailand.AsiaThailand;
import com.example.aircomms.airportCode.Australia.Airport_Australia;
import com.example.aircomms.airportCode.Europe.Airport_Europee;
import com.example.aircomms.airportCode.North.Airport_North;
import com.example.aircomms.airportCode.South.Airport_South;
import com.example.aircomms.approach.ApproachControl;
import com.example.aircomms.approach.Arrival.Arrival;
import com.example.aircomms.approach.Arrival.ArrivalClearance;
import com.example.aircomms.approach.Arrival.ArrivalCoordination;
import com.example.aircomms.approach.Arrival.ArrivalEstimate;
import com.example.aircomms.approach.Arrival.ArrivalInitial;
import com.example.aircomms.approach.Arrival.ArrivalMonitoring;
import com.example.aircomms.approach.Arrival.ArrivalRelay;
import com.example.aircomms.approach.Arrival.ArrivalRelease;
import com.example.aircomms.approach.Departure.Departure;
import com.example.aircomms.phonetics.Phonetics;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<HistoryItem> historyItems;
    private Context context;
    private SharedPreferences sharedPreferences;

    public HistoryAdapter(Context context, List<HistoryItem> historyItems) {
        this.context = context;
        this.historyItems = historyItems;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);


        // Retrieve the last viewed time for each history item from SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("HistoryPreferences", Context.MODE_PRIVATE);
        for (HistoryItem item : historyItems) {
            long lastViewedTime = sharedPreferences.getLong(item.getTitle(), 0);
            item.setLastViewedTime(lastViewedTime);
        }

        // Sort the history items by last viewed time in descending order
        Collections.sort(historyItems, new Comparator<HistoryItem>() {
            @Override
            public int compare(HistoryItem item1, HistoryItem item2) {
                return Long.compare(item2.getLastViewedTime(), item1.getLastViewedTime());
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryItem historyItem = historyItems.get(position);
        holder.textView.setText(historyItem.getTitle());

        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Object lastViewedTimeObj = sharedPreferences.getAll().get(historyItem.getTitle());
        if (lastViewedTimeObj instanceof Long) {
            long lastViewedTime = (long) lastViewedTimeObj;
            if (lastViewedTime != 0) {
                String formattedDate = formatDate(lastViewedTime);
                holder.lastViewedTimeTextView.setText("Last viewed on: \n" + formattedDate);
            } else {
                holder.lastViewedTimeTextView.setText("");
            }
        } else {
            // handle the case where the value is not a Long
        }

        // Update last viewed time for the history item
        historyItem.setLastViewedTime(System.currentTimeMillis());


        // Set click listener for the item view
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove "Home" from historyItems
                if (historyItem.getTitle().equals("Home")) {
                    historyItems.remove(historyItem);
                }

                // Save the last viewed time in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong(historyItem.getTitle(), System.currentTimeMillis());
                editor.apply();

                // Launch the corresponding activity
                Class<?> activityClass = MainActivity.class;
                switch (historyItem.getTitle()) {
                    case "Home":
                        activityClass = MainActivity.class;
                        break;
                    case "Phonetics":
                        activityClass = Phonetics.class;
                        break;
                    case "Others":
                        activityClass = Other.class;
                        break;
                    case "Arline Code \n(Africa)":
                        activityClass = Africa.class;
                        break;
                    case "Arline Code \n(Asia)":
                        activityClass = Asia.class;
                        break;
                    case "Arline Code \n(Australia)":
                        activityClass = Australia.class;
                        break;
                    case "Arline Code \n(Europe)":
                        activityClass = Europe.class;
                        break;
                    case "Arline Code \n(North America)":
                        activityClass = North.class;
                        break;
                    case "Arline Code \n(South America)":
                        activityClass = South.class;
                        break;
                    case "Airport Code \n(Africa)":
                        activityClass = Airport_Africa.class;
                        break;
                    case "Airport Code \n(Asia)":
                        activityClass = AirportAsia.class;
                        break;
                    case "Airport Code \n(Australia)":
                        activityClass = Airport_Australia.class;
                        break;
                    case "Airport Code \n(Europe)":
                        activityClass = Airport_Europee.class;
                        break;
                    case "Airport Code \n(North America)":
                        activityClass = Airport_North.class;
                        break;
                    case "Airport Code \n(South America)":
                        activityClass = Airport_South.class;
                        break;
                    case "Airport Code \n(Asia - China)":
                        activityClass = AsiaChina.class;
                        break;
                    case "Airport Code \n(Asia - Thailand)":
                        activityClass = AsiaThailand.class;
                        break;
                    case "Airport Code \n(Asia - India)":
                        activityClass = AsiaIndia.class;
                        break;
                    case "Airport Code \n(Asia - Japan)":
                        activityClass = AsiaJapan.class;
                        break;
                    case "Airport Code \n(Asia - South Korea)":
                        activityClass = AsiaSouthKorea.class;
                        break;
                    case "Airport Code \n(Asia - Indonesia)":
                        activityClass = AsiaIndonesia.class;
                        break;
                    case "Approach Control \nStraight-Out Departure":
                        activityClass = ApproachControl.class;
                        break;
                    case "Approach Control \nArrival":
                        activityClass = Arrival.class;
                        break;
                    case "Approach Control \nDeparture":
                        activityClass = Departure.class;
                        break;
                    case "Approach Control":
                        activityClass = ApproachControl.class;
                        break;
                    case "Airline Code":
                        activityClass = AirlineCode.class;
                        break;
                    case "Airport Code":
                        activityClass = AirportCode.class;
                        break;
                    case "Approach Control \nArrival (Relay Estimates to Tower)":
                        activityClass = ArrivalRelay.class;
                        break;
                    case "Approach Control \nArrival (Estimate From ACC)":
                        activityClass = ArrivalEstimate.class;
                        break;
                    case "Approach Control \nArrival (Release message from ACC)":
                        activityClass = ArrivalRelease.class;
                        break;
                    case "Approach Control \nArrival (Initial Contact from Arriving Aircraft)":
                        activityClass = ArrivalInitial.class;
                        break;
                    case "Approach Control \nArrival (Monitoring of Aircraft)":
                        activityClass = ArrivalMonitoring.class;
                        break;
                    case "Approach Control \nArrival (Clearance for Commencement (ILS))":
                        activityClass = ArrivalClearance.class;
                        break;
                    case "Approach Control \nArrival (Coordination of Tower)":
                        activityClass = ArrivalCoordination.class;
                        break;
                }
                Intent intent = new Intent(context, activityClass);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public void setHistoryItems(List<HistoryItem> historyItems) {
        // Sort the historyItems list by lastViewedTime in descending order
        Collections.sort(historyItems, (item1, item2) -> Long.compare(item2.getLastViewedTime(), item1.getLastViewedTime()));
        this.historyItems = historyItems;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView lastViewedTimeTextView;

        public ViewHolder(View view) {
            super(view);
            textView = itemView.findViewById(R.id.title);
            lastViewedTimeTextView = itemView.findViewById(R.id.timestamp);

        }
    }
}
