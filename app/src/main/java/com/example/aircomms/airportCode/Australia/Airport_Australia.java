package com.example.aircomms.airportCode.Australia;

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
import com.example.aircomms.airlineCode.Australia.Australia;
import com.example.aircomms.airlineCode.Australia.AustraliaAdapter;
import com.example.aircomms.airlineCode.Australia.AustraliaItem;
import com.example.aircomms.airportCode.Africa.Airport_Africa;
import com.example.aircomms.airportCode.AirportCode;

import java.util.ArrayList;

public class Airport_Australia extends AppCompatActivity {

    private ArrayList<AustraliaItemm> items;
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
            window.setStatusBarColor(ContextCompat.getColor(Airport_Australia.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Airport_Australia.this, R.color.bg));
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_australia);

        recyclerView = findViewById(R.id.recyclerview_australia);
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
        setcharInfo();
        setCustomSearchView();
        historyAustraliaa();

    }
    private void historyAustraliaa() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Airport Code \n(Australia)", true);
        editor.putLong("Airport Code \n(Australia)", System.currentTimeMillis());
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
                    AustraliaAdapterr adapter = new AustraliaAdapterr(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }

    private void fileList(String text) {
        ArrayList<AustraliaItemm> filteredList = new ArrayList<>();
        for (AustraliaItemm item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            AustraliaAdapterr adapter = new AustraliaAdapterr(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    private void setcharInfo() {
        items.add(new AustraliaItemm("Sydney Kingsford Smith International Airport",  "YSSY", "Sydney, New South Wales"));
        items.add(new AustraliaItemm("Melbourne Airport ",  "YMML", "Melbourne, Victoria"));
        items.add(new AustraliaItemm("Brisbane Airport",  "YBBN", "Melbourne, Victoria"));
        items.add(new AustraliaItemm("Perth Airpor",  "YPPH", "Perth, Western Australia"));
        items.add(new AustraliaItemm("Adelaide Airport",  "YPAD", "Adelaide, South Australia"));
        items.add(new AustraliaItemm("Gold Coast Airport ",  "YBCG", "Gold Coast, Queensland"));
        items.add(new AustraliaItemm("Cairns Airport",  "YBCS", "Cairns, Queensland"));
        items.add(new AustraliaItemm("Darwin International Airport",  "YPDN", "Cairns, Queensland"));
        items.add(new AustraliaItemm("Hobart International Airport ",  "YMHB", "Hobart, Tasmania"));
        items.add(new AustraliaItemm("Canberra International Airport",  "YSCB", "Canberra, Australian Capital Territory"));
        items.add(new AustraliaItemm("Newcastle Airport ",  "YWLM", "Newcastle, New South Wales"));
        items.add(new AustraliaItemm("Townsville Airport",  "YBTL", "Townsville, Queensland"));
        items.add(new AustraliaItemm("Avalon Airport",  "YMAV", "Melbourne, Victoria"));
        items.add(new AustraliaItemm("Launceston Airport",  "YMLT", "Melbourne, Victoria"));
        items.add(new AustraliaItemm("Mackay Airport",  "YBMK", "Mackay, Queensland"));
    }

    private void setAdapter() {
        AustraliaAdapterr adapter = new AustraliaAdapterr(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void aus_back(View view) {
        startActivity(new Intent(Airport_Australia.this, AirportCode.class));
    }
}