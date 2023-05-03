package com.example.aircomms.airlineCode.South;

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

import com.example.aircomms.MainActivity;
import com.example.aircomms.R;
import com.example.aircomms.SharedPref;
import com.example.aircomms.airlineCode.Africa.Africa;
import com.example.aircomms.airlineCode.AirlineCode;
import com.example.aircomms.airlineCode.Europe.EuropeAdapter;
import com.example.aircomms.airlineCode.Europe.EuropeItem;
import com.example.aircomms.airlineCode.Noth.NorthAdapter;
import com.example.aircomms.airlineCode.Noth.NorthItem;

import java.util.ArrayList;

public class South extends AppCompatActivity {
    private ArrayList<SouthItem> items;
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
            window.setStatusBarColor(ContextCompat.getColor(South.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(South.this, R.color.bg));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_south);

        recyclerView = findViewById(R.id.recyclerview_south);
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
        historySouth();
    }
    private void historySouth() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Arline Code \n(South America)", true);
        editor.putLong("Arline Code \n(South America)", System.currentTimeMillis());
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
                    SouthAdapter adapter = new SouthAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });
    }

    private void fileList(String text) {
        ArrayList<SouthItem> filteredList = new ArrayList<>();
        for (SouthItem item : items){
            if (item.get_callsign().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            SouthAdapter adapter = new  SouthAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void setCharInfo() {
        items.add(new SouthItem("Aerolíneas Argentinas",  "ARG", R.drawable.air_aerolineas_argentinas));
        items.add(new SouthItem("Avianca",  "AVA", R.drawable.air_avianca));
        items.add(new SouthItem("Azul Brazilian Airlines",  "GLO", R.drawable.air_azul));
        items.add(new SouthItem("GOL Airlines",  "LAN", R.drawable.air_gol));
        items.add(new SouthItem("LATAM Airlines",  "SKU", R.drawable.air_latam));
        items.add(new SouthItem("Sky Airline",  "", R.drawable.air_sky));
        items.add(new SouthItem("Surinam Airways",  "SLM", R.drawable.air_surinam));
        items.add(new SouthItem("8. TAP Air Portugal ",  "TAP", R.drawable.air_tap_portugal));
        items.add(new SouthItem("Viva Air",  "VVC", R.drawable.air_viva));
    }

    private void setAdapter() {
        SouthAdapter adapter = new  SouthAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    public void south_back(View view) {
        startActivity(new Intent(South.this, AirlineCode.class));
    }
}