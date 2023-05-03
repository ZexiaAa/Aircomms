package com.example.aircomms.airportCode.Asia.Phil.International;

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
import com.example.aircomms.airportCode.Asia.Japan.AsiaJapan;
import com.example.aircomms.airportCode.Asia.Japan.JapanItem;
import com.example.aircomms.airportCode.Asia.Phil.AsiaPH;
import com.example.aircomms.airportCode.Asia.Phil.Domestic.DomAdapter;
import com.example.aircomms.airportCode.Asia.Phil.Domestic.DomItem;
import com.example.aircomms.airportCode.Asia.Phil.Domestic.Domestic;

import java.util.ArrayList;

public class International extends AppCompatActivity {

    private ArrayList<InterItem> items;
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
            window.setStatusBarColor(ContextCompat.getColor(International.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(International.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_international);

        recyclerView = findViewById(R.id.recyclerview_int);
        items = new ArrayList<>();

        searchView = findViewById(R.id.searchViewInt);

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
        editor.putBoolean("Airport Code \nAsia - Philippines\nInternational Airport", true);
        editor.putLong("Airport Code \nAsia - Philippines\nInternational Airport", System.currentTimeMillis());
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
                    InterAdapter adapter = new InterAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }

    private void fileList(String text) {
        ArrayList<InterItem> filteredList = new ArrayList<>();
        for (InterItem item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            InterAdapter adapter = new InterAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    private void setCharInfo() {
        items.add(new InterItem("Clark International Airport",  "RPLC", "Mabalacat, Pampanga"));
        items.add(new InterItem("Mactan-Cebu International Airport ",  "RPVM", "Lapu-Lapu City"));
        items.add(new InterItem("Francisco Bangoy International Airport ",  "RPMD", "Davao City"));
        items.add(new InterItem("General Santos International Airport",  "RPMR", "General Santos"));
        items.add(new InterItem("Iloilo International Airpor",  "RPVI", "Cabatuan, Iloilo"));
        items.add(new InterItem("Kalibo International Airport",  "RPVK", "Kalibo, Aklan"));
        items.add(new InterItem("Laoag International Airport ",  "RPLI", "Laoag, Ilocos Norte"));
        items.add(new InterItem("Ninoy Aquino International Airport",  "RPLL", "Pasay, Metro Manila"));
        items.add(new InterItem("Bohol-Panglao International Airport ",  "RPSP", "Panglao, Boho"));
        items.add(new InterItem("Puerto Princesa International Airport",  "RPVP", "Puerto Princesa"));
        items.add(new InterItem("Subic Bay International Airport",  "RPLB", "Morong, Bataan"));
        items.add(new InterItem("Zamboanga International Airport ",  "RPMZ", "Zamboanga City"));

    }

    private void setAdapter() {
        InterAdapter adapter = new InterAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void int_back(View view) {
        startActivity(new Intent(International.this, AsiaPH.class));
    }
}