package com.example.aircomms.airlineCode.Europe;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircomms.MainActivity;
import com.example.aircomms.R;
import com.example.aircomms.SharedPref;
import com.example.aircomms.airlineCode.Africa.Africa;
import com.example.aircomms.airlineCode.AirlineCode;
import com.example.aircomms.airlineCode.Australia.AustraliaAdapter;
import com.example.aircomms.airlineCode.Australia.AustraliaItem;
import com.example.aircomms.airlineCode.Noth.NorthAdapter;
import com.example.aircomms.airlineCode.Noth.NorthItem;
import com.example.aircomms.airportCode.Europe.EuropeItemm;

import java.util.ArrayList;

public class Europe extends AppCompatActivity {
    private ArrayList<EuropeItem> items;
    private RecyclerView recyclerView;
    SharedPref sharedPref;
    private EditText searchEditText;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Europe.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Europe.this, R.color.bg));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_europe);

        recyclerView = findViewById(R.id.recyclerview_eu);
        items = new ArrayList<>();
        searchView = findViewById(R.id.searchViewEu);

        setAdapter();
        setCharInfo();
        historyEurope();
        setCustomSearchView();
    }
    private void historyEurope() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Arline Code \n(Europe)", true);
        editor.putLong("Arline Code \n(Europe)", System.currentTimeMillis());
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
                    EuropeAdapter adapter = new EuropeAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });
    }
    private void fileList(String text) {
        ArrayList<EuropeItem> filteredList = new ArrayList<>();
        for (EuropeItem item : items){
            if (item.get_callsign().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            EuropeAdapter adapter = new  EuropeAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void setCharInfo() {
        items.add(new EuropeItem("Aegean Airlines",  "AEE", R.drawable.air_aegean));
        items.add(new EuropeItem("Aer Lingus",  "EIN", R.drawable.air_aer_lingus));
        items.add(new EuropeItem("Air France",  "AFR", R.drawable.air_france));
        items.add(new EuropeItem("Alitalia",  "AZA", R.drawable.air_alitalia));
        items.add(new EuropeItem("Austrian Airlines",  "AUA", R.drawable.air_austrian));
        items.add(new EuropeItem("British Airways",  "BAW", R.drawable.air_british));
        items.add(new EuropeItem("Brussels Airlines",  "BEL", R.drawable.air_brussel));
        items.add(new EuropeItem("Crotia Airlines",  "CTN", R.drawable.air_crotia));
        items.add(new EuropeItem("EasyJet",  "EZY", R.drawable.air_easylet));
        items.add(new EuropeItem("Finnair",  "FUB", R.drawable.air_finnair));
        items.add(new EuropeItem("Iberia",  "IBE", R.drawable.iberia));
        items.add(new EuropeItem("KLM Royal Dutch Airlines",  "KLM", R.drawable.air_klm_royal));
        items.add(new EuropeItem("Lufthansa",  "DLH", R.drawable.air_lufthansa));
        items.add(new EuropeItem("Norwegian Air Shuttle",  "NAX", R.drawable.air_norwegian));
        items.add(new EuropeItem("Ryanair",  "RYR", R.drawable.air_ryan));
        items.add(new EuropeItem("Scandinavian Airlines",  "SAS", R.drawable.air_sacndinavian));
        items.add(new EuropeItem("Swiss International Air Lines",  "SWR", R.drawable.air_swiss));
        items.add(new EuropeItem("Turkish Airlines",  "THY", R.drawable.air_turkish));
        items.add(new EuropeItem("Wizz Air (W6)",  "WZZ", R.drawable.air_wizz));



    }


    private void setAdapter() {
        EuropeAdapter adapter = new EuropeAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void europe_back(View view) {
        startActivity(new Intent(Europe.this, AirlineCode.class));
    }
}