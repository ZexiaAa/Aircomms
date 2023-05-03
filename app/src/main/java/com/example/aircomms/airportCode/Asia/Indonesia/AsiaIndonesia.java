package com.example.aircomms.airportCode.Asia.Indonesia;

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
import com.example.aircomms.airportCode.Asia.India.AsiaIndia;
import com.example.aircomms.airportCode.Asia.India.IndiaAdapter;
import com.example.aircomms.airportCode.Asia.India.IndiaItem;
import com.example.aircomms.airportCode.Asia.SouthKorea.AsiaSouthKorea;

import java.util.ArrayList;

public class AsiaIndonesia extends AppCompatActivity {

    private ArrayList<IndonesiaItem> items;
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
            window.setStatusBarColor(ContextCompat.getColor(AsiaIndonesia.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(AsiaIndonesia.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asia_indonesia);

        recyclerView = findViewById(R.id.recyclerview_indo);
        items = new ArrayList<>();

        searchView = findViewById(R.id.searchViewIndo);

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
        editor.putBoolean("Airport Code \n(Asia - Indonesia)", true);
        editor.putLong("Airport Code \n(Asia - Indonesia)", System.currentTimeMillis());
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
                    IndonesiaAdapter adapter = new IndonesiaAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }

    private void fileList(String text) {
        ArrayList<IndonesiaItem> filteredList = new ArrayList<>();
        for (IndonesiaItem item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            IndonesiaAdapter adapter = new IndonesiaAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    private void setCharInfo() {
        items.add(new IndonesiaItem("Soekarno-Hatta International Airport",  "WIII", "Jakarta"));
        items.add(new IndonesiaItem("Ngurah Rai International Airport ",  "WADD", "Bali"));
        items.add(new IndonesiaItem("Juanda International Airport",  "WARR", "Surabaya"));
        items.add(new IndonesiaItem("Juanda International Airport",  "WIMM", "Medan"));
        items.add(new IndonesiaItem("Sultan Hasanuddin International Airport",  "WAAA", "Makassar"));
        items.add(new IndonesiaItem("Sepinggan International Airport ",  "WALL", "Balikpapan"));
        items.add(new IndonesiaItem("Adisutjipto International Airport",  "WAHH", "Yogyakarta"));
        items.add(new IndonesiaItem("Sam Ratulangi International Airport ",  "WAMM", "Manado"));
        items.add(new IndonesiaItem("Minangkabau International Airport",  "WIPT", "Padang"));
        items.add(new IndonesiaItem("Sultan Aji Muhammad Sulaiman Airport ",  "WIOO", "Balikpapan"));
    }

    private void setAdapter() {
        IndonesiaAdapter adapter = new IndonesiaAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void indo_back(View view) {
        startActivity(new Intent(AsiaIndonesia.this, AirportAsia.class));
    }
}