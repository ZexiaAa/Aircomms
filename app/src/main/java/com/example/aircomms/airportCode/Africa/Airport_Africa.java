package com.example.aircomms.airportCode.Africa;

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
import com.example.aircomms.airportCode.AirportCode;

import java.util.ArrayList;

public class Airport_Africa extends AppCompatActivity {

    private ArrayList<AfricaItemm> items;
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
            window.setStatusBarColor(ContextCompat.getColor(Airport_Africa.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Airport_Africa.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_africa);

        recyclerView = findViewById(R.id.rvAfrica);
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
        historyAfricaa();

    }
    private void historyAfricaa() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Airport Code \n(Africa)", true);
        editor.putLong("Airport Code \n(Africa)", System.currentTimeMillis());
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
                    AfricaAdapterr adapter = new AfricaAdapterr(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }

    private void fileList(String text) {
        ArrayList<AfricaItemm> filteredList = new ArrayList<>();
        for (AfricaItemm item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            AfricaAdapterr adapter = new AfricaAdapterr(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void setCharInfo() {

        items.add(new AfricaItemm("O.R. Tambo International Airport",  "FAOR", "Johannesburg, South Africa"));
        items.add(new AfricaItemm("Cairo International Airport",  "HECA", "Cairo, Egypt"));
        items.add(new AfricaItemm("Cape Town International Airport",  "FACR", "Cape Town, South Africa"));
        items.add(new AfricaItemm("Mohammed V International Airport ",  "GMMN", "Casablanca, Morocco"));
        items.add(new AfricaItemm("Addis Ababa Bole International Airport",  "HAAB", "Addis Ababa, Ethiopia"));
        items.add(new AfricaItemm("Hurghada International Airport ",  "(HEGN", "Hurghada, Egypt"));
        items.add(new AfricaItemm("Sharm el-Sheikh International Airport",  "HESH", "Sharm el-Sheikh, Egypt"));
        items.add(new AfricaItemm("Houari Boumediene Airport ",  "DAAG", "Algiers, Algeria"));
        items.add(new AfricaItemm("King Shaka International Airport",  "FALE", "Durban, South Africa"));
        items.add(new AfricaItemm("Lagos Murtala Muhammed International Airport",  "DNMM", "Lagos, Nigeria"));
        items.add(new AfricaItemm("Julius Nyerere International Airport",  "HTDA", "Dar es Salaam, Tanzania"));
        items.add(new AfricaItemm("Enfidha-Hammamet International Airport ",  "DTNH", "Enfidha, Tunisia"));
        items.add(new AfricaItemm("Jomo Kenyatta International Airport ",  "HKJK", "Nairobi, Kenya"));
        items.add(new AfricaItemm("Blaise Diagne International Airport",  "GOBD", "Dakar, Senegal"));
        items.add(new AfricaItemm("Kenneth Kaunda International Airport ",  "FLKK", "Lusaka, Zambia"));

    }

    private void setAdapter() {
        AfricaAdapterr adapter = new AfricaAdapterr(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void africa_back(View view) {
        startActivity(new Intent(Airport_Africa.this, AirportCode.class));
    }
}