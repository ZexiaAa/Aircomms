package com.example.aircomms.airportCode.Asia.Thailand;

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
import com.example.aircomms.airportCode.Asia.Japan.AsiaJapan;
import com.example.aircomms.airportCode.Asia.Japan.JapanAdapter;
import com.example.aircomms.airportCode.Asia.Japan.JapanItem;
import com.example.aircomms.airportCode.Asia.SouthKorea.SKItem;

import java.util.ArrayList;

public class AsiaThailand extends AppCompatActivity {

    private ArrayList<ThaiItem> items;
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
            window.setStatusBarColor(ContextCompat.getColor(AsiaThailand.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(AsiaThailand.this, R.color.bg));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asia_thailand);
        recyclerView = findViewById(R.id.recyclerview_thai);
        items = new ArrayList<>();

        searchView = findViewById(R.id.searchViewThai);

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
                    ThailandAdapter adapter = new ThailandAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }

    private void fileList(String text) {
        ArrayList<ThaiItem> filteredList = new ArrayList<>();
        for (ThaiItem item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            ThailandAdapter adapter = new ThailandAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    private void setCharInfo() {
        items.add(new ThaiItem("Suvarnabhumi Airport",  "VTBS", "Bangkok"));
        items.add(new ThaiItem("Don Mueang International Airport ",  "VTBD", "Bangkok"));
        items.add(new ThaiItem("Don Mueang International Airport ",  "VTCC)", "Chiang Mai"));
        items.add(new ThaiItem("Phuket International Airport",  "VTSP", "Phuket"));
        items.add(new ThaiItem("Hat Yai International Airport",  "VTSS", "Hat Ya"));
        items.add(new ThaiItem("U-Tapao International Airport",  "VTBU", "Rayong/Pattaya"));
        items.add(new ThaiItem("Krabi International Airport",  "VTSG", "Krabi"));
        items.add(new ThaiItem("Samui International Airport ",  "VTSM", "Koh Samui"));
        items.add(new ThaiItem("Mae Fah Luang - Chiang Rai International Airport",  "VTCT", "Chiang Rai"));
        items.add(new ThaiItem("Surat Thani Airport",  "VTSB", "Surat Thani"));



    }

    private void setAdapter() {
        ThailandAdapter adapter = new ThailandAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void thai_back(View view) {
        startActivity(new Intent(AsiaThailand.this, AirportAsia.class));
    }
}