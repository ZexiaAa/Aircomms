package com.example.aircomms.airlineCode.Africa;

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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircomms.MainActivity;
import com.example.aircomms.R;
import com.example.aircomms.SharedPref;
import com.example.aircomms.airlineCode.AirlineCode;
import com.example.aircomms.airlineCode.Asia.AsiaAdapter;
import com.example.aircomms.airlineCode.Asia.AsiaItem;
import com.example.aircomms.airlineCode.Noth.NorthAdapter;
import com.example.aircomms.airlineCode.Noth.NorthItem;
import com.example.aircomms.phonetics.Items;
import com.example.aircomms.phonetics.PhoneticsAdapter;

import java.util.ArrayList;

public class Africa extends AppCompatActivity {

    SharedPref sharedPref;
    private ArrayList<AfricaItem> items;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private AfricaAdapter adapter;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Africa.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Africa.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_africa);


        recyclerView = findViewById(R.id.recyclerview_africa);
        items = new ArrayList<>();
        searchView = findViewById(R.id.searchViewAfrica);

        setAdapter();
        setCharInfo();
        historyAfrica();
        setCustomSearchView();


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
                    AfricaAdapter adapter = new AfricaAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });
    }
    private void fileList(String text) {
        ArrayList<AfricaItem> filteredList = new ArrayList<>();
        for (AfricaItem item : items){
            if (item.get_callsign().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            AfricaAdapter adapter = new AfricaAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }

    private void historyAfrica() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Arline Code \n(Africa)", true);
        editor.putLong("Arline Code \n(Africa)", System.currentTimeMillis());
        editor.apply();
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void setAdapter() {
        adapter = new AfricaAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setCharInfo() {
        items.add(new AfricaItem("Air Algérie",  "DAH", R.drawable. asia_algerie));
        items.add(new AfricaItem("Air Botswana",  "BOT", R.drawable.air_botswana ));
        items.add(new AfricaItem("Air Burkina",  "VBW", R.drawable. air_burkina));
        items.add(new AfricaItem("Air Côte d'Ivoire",  "VRE", R.drawable.air_cote_divoire ));
        items.add(new AfricaItem("Air Mauritius",  "MAU", R.drawable.air_mauritius ));
        items.add(new AfricaItem("Air Namibia",  " NMB", R.drawable.air_namibia ));
        items.add(new AfricaItem("Air Peace",  "APK", R.drawable. air_peace));
        items.add(new AfricaItem("Air Seychelles",  "SEY", R.drawable.air_seychelles));


    }

    public void asia_africa(View view) {
        startActivity(new Intent(Africa.this,AirlineCode.class));
    }
}