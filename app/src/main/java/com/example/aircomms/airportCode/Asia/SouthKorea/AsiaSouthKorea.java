package com.example.aircomms.airportCode.Asia.SouthKorea;

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
import com.example.aircomms.airportCode.Asia.Indonesia.IndonesiaItem;
import com.example.aircomms.airportCode.Asia.Japan.AsiaJapan;
import com.example.aircomms.airportCode.Asia.Japan.JapanAdapter;
import com.example.aircomms.airportCode.Asia.Japan.JapanItem;

import java.util.ArrayList;

public class AsiaSouthKorea extends AppCompatActivity {

    private ArrayList<SKItem> items;
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
            window.setStatusBarColor(ContextCompat.getColor(AsiaSouthKorea.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(AsiaSouthKorea.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asia_south_korea);
        recyclerView = findViewById(R.id.recyclerview_korea);
        items = new ArrayList<>();

        searchView = findViewById(R.id.searchViewKorea);

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
        editor.putBoolean("Airport Code \n(Asia - South Korea)", true);
        editor.putLong("Airport Code \n(Asia - South Korea)", System.currentTimeMillis());
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
                    SKAdapter adapter = new SKAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }

    private void fileList(String text) {
        ArrayList<SKItem> filteredList = new ArrayList<>();
        for (SKItem item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            SKAdapter adapter = new SKAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    private void setCharInfo() {
        items.add(new SKItem("Incheon International Airport",  "RKSI", "Incheon"));
        items.add(new SKItem("Gimpo International Airport ",  "RKSS", "Seoul"));
        items.add(new SKItem("Jeju International Airport",  "RKPC", "Jeju City"));
        items.add(new SKItem("Daegu International Airport",  "RKTN", "Daegu"));
        items.add(new SKItem("Busan International Airport",  "RKPK", "Busan"));
        items.add(new SKItem("Cheongju International Airport",  "RKTU", "Cheongju"));
        items.add(new SKItem("Muan International Airport ",  "RKJB", "Muan"));
        items.add(new SKItem("Yangyang International Airport",  "RKNY", "Yangyang"));
        items.add(new SKItem("Gwangju International Airport",  "RKJJ", "Gwangju"));
        items.add(new SKItem("Ulsan Airport",  "RKPU", "Ulsan"));


    }

    private void setAdapter() {
        SKAdapter adapter = new SKAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void korea_back(View view) {
        startActivity(new Intent(AsiaSouthKorea.this, AirportAsia.class));
    }
}