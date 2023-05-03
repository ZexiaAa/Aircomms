package com.example.aircomms.airportCode.Asia.Japan;

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
import com.example.aircomms.airportCode.Asia.Indonesia.AsiaIndonesia;
import com.example.aircomms.airportCode.Asia.Indonesia.IndonesiaAdapter;
import com.example.aircomms.airportCode.Asia.Indonesia.IndonesiaItem;

import java.util.ArrayList;

public class AsiaJapan extends AppCompatActivity {

    private ArrayList<JapanItem> items;
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
            window.setStatusBarColor(ContextCompat.getColor(AsiaJapan.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(AsiaJapan.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asia_japan);

        recyclerView = findViewById(R.id.recyclerview_japan);
        items = new ArrayList<>();

        searchView = findViewById(R.id.searchViewJapan);

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
        history();
    }


    private void history() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Airport Code \n(Asia - Japan)", true);
        editor.putLong("Airport Code \n(Asia - Japan)", System.currentTimeMillis());
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
                    JapanAdapter adapter = new JapanAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }

    private void fileList(String text) {
        ArrayList<JapanItem> filteredList = new ArrayList<>();
        for (JapanItem item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            JapanAdapter adapter = new JapanAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    private void setCharInfo() {
        items.add(new JapanItem("Tokyo Haneda International Airport",  "RJTT", "Tokyo"));
        items.add(new JapanItem("Tokyo Narita International Airport",  "RJAA", "Narita"));
        items.add(new JapanItem("Kansai International Airport",  "RJBB", "Osaka"));
        items.add(new JapanItem("Chubu Centrair International Airport",  "RJGG", "Nagoya"));
        items.add(new JapanItem("Fukuoka Airport",  "RJFF", "Fukuoka"));
        items.add(new JapanItem("New Chitose Airport",  "RJCC", "Sapporo"));
        items.add(new JapanItem("Naha Airport",  "ROAH", "Okinawa"));
        items.add(new JapanItem("Sendai Airport ",  "RJSS", "Sendai"));
        items.add(new JapanItem("Hiroshima Airport",  "RJGG", "Hiroshima"));
        items.add(new JapanItem("Osaka International Airport",  "RJOO", "Osaka"));

    }

    private void setAdapter() {
        JapanAdapter adapter = new JapanAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void japan_back(View view) {
        startActivity(new Intent(AsiaJapan.this, AirportAsia.class));
    }
}