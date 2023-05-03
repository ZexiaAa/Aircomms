package com.example.aircomms.airportCode.Asia.India;

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
import com.example.aircomms.airportCode.Asia.AirportAsia;
import com.example.aircomms.airportCode.Asia.China.AsiaChina;
import com.example.aircomms.airportCode.Asia.China.ChinaAdapter;
import com.example.aircomms.airportCode.Asia.China.ChinaItem;
import com.example.aircomms.airportCode.Asia.SouthKorea.AsiaSouthKorea;

import java.util.ArrayList;

public class AsiaIndia extends AppCompatActivity {

    private ArrayList<IndiaItem> items;
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
            window.setStatusBarColor(ContextCompat.getColor(AsiaIndia.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(AsiaIndia.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asia_india);


        recyclerView = findViewById(R.id.recyclerview_india);
        items = new ArrayList<>();

        searchView = findViewById(R.id.searchViewIndia);

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
        historyIndia();
    }

    private void historyIndia() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Airport Code \n(Asia - India)", true);
        editor.putLong("Airport Code \n(Asia - India)", System.currentTimeMillis());
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
                    IndiaAdapter adapter = new IndiaAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }

    private void fileList(String text) {
        ArrayList<IndiaItem> filteredList = new ArrayList<>();
        for (IndiaItem item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            IndiaAdapter adapter = new IndiaAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void setCharInfo() {

        items.add(new IndiaItem("Indira Gandhi International Airport ",  "VIDP", "New Delhi"));
        items.add(new IndiaItem("Chhatrapati Shivaji Maharaj International Airport ",  "VABB", "Mumbai"));
        items.add(new IndiaItem("Kempegowda International Airport",  "VOBL", "Bengaluru"));
        items.add(new IndiaItem("Chennai International Airport ",  "VOMM", "Chennai"));
        items.add(new IndiaItem("Netaji Subhas Chandra Bose International Airport",  "VECC", "Kolkata"));
        items.add(new IndiaItem("Rajiv Gandhi International Airport ",  "VOHS", "Hyderabad"));
        items.add(new IndiaItem("Cochin International Airport",  "VOCI", "Kochi"));
        items.add(new IndiaItem("Sardar Vallabhbhai Patel International Airport",  "VAAH", "Ahmedabad"));
        items.add(new IndiaItem("Pune International Airport ",  "VAPO", "Pune"));
        items.add(new IndiaItem("Goa International Airport",  "VOGO", "Dabolim, Goa"));



    }

    private void setAdapter() {
        IndiaAdapter adapter = new IndiaAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void india_back(View view) {
        startActivity(new Intent(AsiaIndia.this, AirportAsia.class));
    }
}