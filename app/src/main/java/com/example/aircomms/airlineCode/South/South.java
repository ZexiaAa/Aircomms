package com.example.aircomms.airlineCode.South;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircomms.R;
import com.example.aircomms.airlineCode.Europe.EuropeAdapter;
import com.example.aircomms.airlineCode.Europe.EuropeItem;
import com.example.aircomms.airlineCode.Noth.NorthAdapter;
import com.example.aircomms.airlineCode.Noth.NorthItem;

import java.util.ArrayList;

public class South extends AppCompatActivity {
    private ArrayList<SouthItem> items;
    private RecyclerView recyclerView;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_south);

        recyclerView = findViewById(R.id.recyclerview_south);
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
                    SouthAdapter adapter = new SouthAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });
    }

    private void fileList(String text) {
        ArrayList<SouthItem> filteredList = new ArrayList<>();
        for (SouthItem item : items){
            if (item.get_callsign().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            SouthAdapter adapter = new  SouthAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void setCharInfo() {
        items.add(new SouthItem("Aerolíneas Argentinas",  "ARG", R.drawable.air_aerolineas_argentinas));
        items.add(new SouthItem("Avianca",  "AVA", R.drawable.air_avianca));
        items.add(new SouthItem("Azul Brazilian Airlines",  "GLO", R.drawable.air_azul));
        items.add(new SouthItem("GOL Airlines",  "LAN", R.drawable.air_gol));
        items.add(new SouthItem("LATAM Airlines",  "SKU", R.drawable.air_latam));
        items.add(new SouthItem("Sky Airline",  "", R.drawable.air_sky));
        items.add(new SouthItem("Surinam Airways",  "SLM", R.drawable.air_surinam));
        items.add(new SouthItem("8. TAP Air Portugal ",  "TAP", R.drawable.air_tap_portugal));
        items.add(new SouthItem("Viva Air",  "VVC", R.drawable.air_viva));
    }

    private void setAdapter() {
        SouthAdapter adapter = new  SouthAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}