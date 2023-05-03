package com.example.aircomms.airportCode.Europe;

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
import com.example.aircomms.airlineCode.Europe.Europe;
import com.example.aircomms.airportCode.Africa.Airport_Africa;
import com.example.aircomms.airportCode.AirportCode;
import com.example.aircomms.airportCode.Australia.Airport_Australia;
import com.example.aircomms.airportCode.Australia.AustraliaAdapterr;
import com.example.aircomms.airportCode.Australia.AustraliaItemm;

import java.util.ArrayList;

public class Airport_Europee extends AppCompatActivity {

    private ArrayList<EuropeItemm> items;
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
            window.setStatusBarColor(ContextCompat.getColor(Airport_Europee.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Airport_Europee.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_europee);

        recyclerView = findViewById(R.id.rvEurope);
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
        setcharInfo();
        setCustomSearchView();
        historyEuropee();

    }
    private void historyEuropee() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Airport Code \n(Europe)", true);
        editor.putLong("Airport Code \n(Europe)", System.currentTimeMillis());
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
                    EuropeAdapterr adapter = new EuropeAdapterr(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });
    }

    private void fileList(String text) {
        ArrayList<EuropeItemm> filteredList = new ArrayList<>();
        for (EuropeItemm item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            EuropeAdapterr adapter = new EuropeAdapterr(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void setcharInfo() {
        items.add(new EuropeItemm("Hartsfield-Jackson Atlanta International Airport ",  "KATL","Georgia, USA"));
        items.add(new EuropeItemm("Los Angeles International Airport ",  "KLAX","California, USA"));
        items.add(new EuropeItemm("London Heathrow Airport ", "EGLL","United Kingdom"));
        items.add(new EuropeItemm("Paris Charles de Gaulle Airport", "LFPG","France"));
        items.add(new EuropeItemm("Amsterdam Airport Schiphol ", "EHAM","Netherlands"));
        items.add(new EuropeItemm("Frankfurt Airport", "EDDF","Germany"));
        items.add(new EuropeItemm("Madrid Barajas Airport", "LEMD","Spain"));
        items.add(new EuropeItemm("Barcelona El Prat Airport", "LEBL","Spain"));
        items.add(new EuropeItemm("Munich Airport",  "EDDM","Germany"));
        items.add(new EuropeItemm("Istanbul Airport",  "LTFM","Turkey"));
        items.add(new EuropeItemm("Rome Fiumicino Airpor",  "LIRF","Italy"));
        items.add(new EuropeItemm("Zurich Airport",  "LSZH","Switzerland"));
        items.add(new EuropeItemm("Vienna International Airport ",  "LOWW","Austria"));
        items.add(new EuropeItemm("Copenhagen Airport",  "EKCH","Denmark"));
        items.add(new EuropeItemm("Oslo Gardermoen Airport ",  "ENGM","Norway"));
        items.add(new EuropeItemm("Stockholm Arlanda Airport ",  "ESSA","Sweden"));
        items.add(new EuropeItemm("Helsinki-Vantaa Airport",  "EFHK","Finland"));
        items.add(new EuropeItemm("Moscow Sheremetyevo International Airport",  "UUEE ","Russia"));
        items.add(new EuropeItemm("Dublin Airport", "EIDW","Ireland"));
        items.add(new EuropeItemm("Lisbon Portela Airport",  "LPPT","Portugal"));
        items.add(new EuropeItemm("Athens International Airport",  "LGAV","Greece"));
        items.add(new EuropeItemm("Brussels Airport",  "BBR","Belgium"));
    }

    private void setAdapter() {
        EuropeAdapterr adapter = new EuropeAdapterr(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void eu_back(View view) {
        startActivity(new Intent(Airport_Europee.this, AirportCode.class));
    }
}