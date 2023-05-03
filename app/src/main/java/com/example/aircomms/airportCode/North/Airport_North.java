package com.example.aircomms.airportCode.North;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircomms.R;
import com.example.aircomms.SharedPref;
import com.example.aircomms.airlineCode.Noth.North;
import com.example.aircomms.airlineCode.Noth.NorthAdapter;
import com.example.aircomms.airlineCode.Noth.NorthItem;
import com.example.aircomms.airportCode.Africa.Airport_Africa;
import com.example.aircomms.airportCode.AirportCode;

import java.util.ArrayList;

public class Airport_North extends AppCompatActivity {

    private ArrayList<NorthItemm> items;
    private RecyclerView recyclerView;
    private SearchView searchView;
    SharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Airport_North.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Airport_North.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_north);

        recyclerView = findViewById(R.id.rvNorth);
        items = new ArrayList<>();

        searchView = findViewById(R.id.searchView);

        TextView textView = null;
        int id = getResources().getIdentifier("android:id/search_src_text", null, null);
        if (searchView != null) {
            textView = searchView.findViewById(id);
        }
        if (textView != null) {
            Typeface typeface = ResourcesCompat.getFont(this, R.font.poppins_medium);
            textView.setTypeface(typeface);
        }
        setAdapter();
        setCharInfo();
        setCustomSearchView();
        historyNorth();

    }
    private void historyNorth() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Airport Code \n(North America)", true);
        editor.putLong("Airport Code \n(North America)", System.currentTimeMillis());
        editor.apply();
    }

    private void setCustomSearchView() {
        searchView.clearFocus();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fileList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    // show the original data if the search text is empty
                    NorthAdapterr adapter = new NorthAdapterr(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });
    }
    private void fileList(String text) {
        ArrayList<NorthItemm> filteredList = new ArrayList<>();
        for (NorthItemm item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            NorthAdapterr adapter = new  NorthAdapterr(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }
    private void setCharInfo() {
        items.add(new NorthItemm("Hartsfield-Jackson Atlanta International Airport ",  "KATL","Georgia, USA"));
        items.add(new NorthItemm("Los Angeles International Airport ",  "KLAX","California, USA"));
        items.add(new NorthItemm("O'Hare International Airport ",  "KORD","Illinois, USA"));
        items.add(new NorthItemm("Dallas/Fort Worth International Airport ",  "KDFW","Texas, USA"));
        items.add(new NorthItemm("Denver International Airport",  "KDEN","Colorado, USA"));
        items.add(new NorthItemm("Vancouver International Airport",  "CYYZ","Ontario, Canada"));
        items.add(new NorthItemm("Cancún International Airpor",  "CYYR","Vancouver BC, Canada"));
        items.add(new NorthItemm("San Francisco International Airport",  "MMUN","Quintana Roo, Mexico"));
        items.add(new NorthItemm("McCarran International Airport ",  "KSFO","California, USA"));
        items.add(new NorthItemm("Seattle-Tacoma International Airport",  "KSEA","Nevada, USA"));
        items.add(new NorthItemm("John F. Kennedy International Airport",  "KJFK","Washington, USA"));
        items.add(new NorthItemm("Mexico City International Airport",  "MMMX","New York, US"));
        items.add(new NorthItemm("Charlotte Douglas International Airport ",  "KCLT","Mexico City, Mexico"));
        items.add(new NorthItemm("LaGuardia Airport ",  "KLGA","North Carolina, USA"));


    }

    private void setAdapter() {
        NorthAdapterr adapter = new  NorthAdapterr(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void north_backk(View view) {
        startActivity(new Intent(Airport_North.this, AirportCode.class));
    }
}