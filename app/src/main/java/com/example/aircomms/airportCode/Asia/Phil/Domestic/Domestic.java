package com.example.aircomms.airportCode.Asia.Phil.Domestic;

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
import com.example.aircomms.airportCode.Asia.Japan.AsiaJapan;
import com.example.aircomms.airportCode.Asia.Japan.JapanAdapter;
import com.example.aircomms.airportCode.Asia.Japan.JapanItem;
import com.example.aircomms.airportCode.Asia.Phil.AsiaPH;

import java.util.ArrayList;

public class Domestic extends AppCompatActivity {

    private ArrayList<DomItem> items;
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
            window.setStatusBarColor(ContextCompat.getColor(Domestic.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Domestic.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic);


        recyclerView = findViewById(R.id.recyclerview_dom);
        items = new ArrayList<>();

        searchView = findViewById(R.id.searchViewDom);

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
        editor.putBoolean("Airport Code \nAsia - Philippines\nDomestic Airport", true);
        editor.putLong("Airport Code \nAsia - Philippines\nDomestic Airport", System.currentTimeMillis());
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
                    DomAdapter adapter = new DomAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }

    private void fileList(String text) {
        ArrayList<DomItem> filteredList = new ArrayList<>();
        for (DomItem item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            DomAdapter adapter = new DomAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    private void setCharInfo() {
        items.add(new DomItem("Bacolod-Silay International Airport",  "RPVB", "Silay"));
        items.add(new DomItem("Bancasi (Butuan) Airport",  "RPME ", "Butuan"));
        items.add(new DomItem("Cotabato",  "RPMC", "Datu Odin Sinsua"));
        items.add(new DomItem("Dipolog Airport",  "RPMG", "Dipolog"));
        items.add(new DomItem("Sibulan (Dumaguete) Airport",  "RPVD ", "Sibulan"));
        items.add(new DomItem("Laguindingan Airport ",  "RPLP", "Laguindingan"));
        items.add(new DomItem("Legazpi Airport ",  "RPUN ", "Legazpi\n"));
        items.add(new DomItem("Naga (Pili) Airport ",  "RPMP", "Pili"));
        items.add(new DomItem("Pagadian Airport ",  "RPVR", "Pagadian"));
        items.add(new DomItem("Roxas Airport ",  "RPUH ", "Roxas"));
        items.add(new DomItem("San Jose Airport",  "RPVA", "San Jose"));
        items.add(new DomItem("Daniel Z. Romualdez (Tacloban) Airport",  "RPUT", "Tacloban"));
        items.add(new DomItem("Tuguegarao Airport ",  "RPVS", "Taguegarao"));
        items.add(new DomItem("Evelio Javier (Antique) Airport",  "RPUB ", "Antique"));
        items.add(new DomItem("Loakan (Baguio) Airport ",  "RPUO", "Baguio"));
        items.add(new DomItem("Basco Airport ",  "RPPVV", "Batanes"));
        items.add(new DomItem("Francisco B. Reyes (Busuanga) Airport",  "RPVC", "Palawan"));
        items.add(new DomItem("Calbayog Airport",  "RPMH", "Samar"));
        items.add(new DomItem("Camiguin Airport",  "RPVE", "Camiguin"));
        items.add(new DomItem("Catarman National Airport",  "RPVE", ""));
        items.add(new DomItem("Godofredo P. Ramos (Caticlan/Boracay) Airport ",  "RPLO ", "Northern Samar"));
        items.add(new DomItem("Cuyo Airport",  "RPMJ", "Aklan"));
        items.add(new DomItem("Jolo Airport ",  " RPUW", "Palawan"));
        items.add(new DomItem("Marinduque Airport",  "RPVJ ", ""));
        items.add(new DomItem("Moises R. Espinosa (Masbate Airport)",  "RPVO ", "Sulu"));
        items.add(new DomItem("Ormoc Airport ",  "RPNS", "Marinduque"));
        items.add(new DomItem("Sayak (Siargao) Airport",  "RPMS", "Masbate"));
        items.add(new DomItem("Surigao Airport",  "", ""));
        items.add(new DomItem("Tugdan (Tablas/Romblon) Airport",  "RPVU", "Leyte"));
        items.add(new DomItem("Tandag Airport",  "RPUV", "Surigao del Norte"));
        items.add(new DomItem("Sanga-Sanga (Tawi-Tawi) Airport",  "RPUV", "Tawi-Tawi"));
        items.add(new DomItem("Virac Airport",  "RPUV", "Catanduanes"));


    }

    private void setAdapter() {
        DomAdapter adapter = new DomAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void dom_back(View view) {
        startActivity(new Intent(Domestic.this, AsiaPH.class));
    }
}