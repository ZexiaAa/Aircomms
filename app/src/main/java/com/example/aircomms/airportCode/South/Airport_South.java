package com.example.aircomms.airportCode.South;

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
import com.example.aircomms.airlineCode.South.South;
import com.example.aircomms.airlineCode.South.SouthAdapter;
import com.example.aircomms.airlineCode.South.SouthItem;
import com.example.aircomms.airportCode.Africa.Airport_Africa;
import com.example.aircomms.airportCode.AirportCode;

import java.util.ArrayList;

public class Airport_South extends AppCompatActivity {
    private ArrayList<SouthItemm> items;
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
            window.setStatusBarColor(ContextCompat.getColor(Airport_South.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Airport_South.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_south);

        recyclerView = findViewById(R.id.rvSouth);
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
        historySouthh();

    }
    private void historySouthh() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Airport Code \n(South America)", true);
        editor.putLong("Airport Code \n(South America)", System.currentTimeMillis());
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
                    SouthAdapterr adapter = new SouthAdapterr(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });
    }
    private void fileList(String text) {
        ArrayList<SouthItemm> filteredList = new ArrayList<>();
        for (SouthItemm item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            SouthAdapterr adapter = new  SouthAdapterr(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }
    private void setCharInfo() {
        items.add(new SouthItemm("São Paulo–Guarulhos International Airport",  "SBGR", "São Paulo, Brazil"));
        items.add(new SouthItemm("El Dorado International Airport ",  "SKBO", "Bogotá, Colombia"));
        items.add(new SouthItemm("Comodoro Arturo Merino Benítez International Airport",  "SCELL", "Santiago, Chile"));
        items.add(new SouthItemm("Jorge Chávez International Airport",  "SPIM", "Lima, Peru"));
        items.add(new SouthItemm("Ministro Pistarini International Airport",  "SAEZ", "Argentina"));
        items.add(new SouthItemm("Galeão International Airport",  "SBGL", "Rio de Janeiro, Brazil"));
        items.add(new SouthItemm("Viracopos International Airpor",  "SBKP", "Campinas, Brazil"));
        items.add(new SouthItemm("Tancredo Neves International Airport",  "SBCF", "Belo Horizonte, Brazil"));
        items.add(new SouthItemm("Guararapes–Gilberto Freyre International Airport ",  "SBRF", "Recife, Brazil"));
        items.add(new SouthItemm("Carrasco International Airport",  "SUMU", "Montevideo, Uruguay"));
        items.add(new SouthItemm("Simón Bolívar International Airport",  "SVMI", "Caracas, Venezuela"));
        items.add(new SouthItemm("José Joaquín de Olmedo International Airport",  "SEGU", "Guayaquil, Ecuador"));
        items.add(new SouthItemm("Alfredo Vásquez Cobo International Airport",  "SKFL", "Leticia, Colombia"));
        items.add(new SouthItemm("Mariscal Sucre International Airport ",  "SEQM", "Quito, Ecuador"));
        items.add(new SouthItemm("Alejandro Velasco Astete International Airport ",  "SPZO", "Cusco, Peru"));

    }

    private void setAdapter() {
        SouthAdapterr adapter = new  SouthAdapterr(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void south_backk(View view) {
        startActivity(new Intent(Airport_South.this, AirportCode.class));
    }
}