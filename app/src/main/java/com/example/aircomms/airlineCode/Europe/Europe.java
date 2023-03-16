package com.example.aircomms.airlineCode.Europe;

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
import com.example.aircomms.airlineCode.Australia.AustraliaAdapter;
import com.example.aircomms.airlineCode.Australia.AustraliaItem;

import java.util.ArrayList;

public class Europe extends AppCompatActivity {
    private ArrayList<EuropeItem> items;
    private RecyclerView recyclerView;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_europe);

        recyclerView = findViewById(R.id.recyclerview_eu);
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
                    EuropeAdapter adapter = new EuropeAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }
    private void fileList(String text) {
        ArrayList<EuropeItem> filteredList = new ArrayList<>();
        for (EuropeItem item : items){
            if (item.get_callsign().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            EuropeAdapter adapter = new  EuropeAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void setCharInfo() {
        items.add(new EuropeItem("Aegean Airlines",  "AEE", R.drawable.air_aegean));
        items.add(new EuropeItem("Aer Lingus",  "EIN", R.drawable.air_aer_lingus));
        items.add(new EuropeItem("Air France",  "AFR", R.drawable.air_france));
        items.add(new EuropeItem("Alitalia",  "AZA", R.drawable.air_alitalia));
        items.add(new EuropeItem("Austrian Airlines",  "AUA", R.drawable.air_austrian));
        items.add(new EuropeItem("British Airways",  "BAW", R.drawable.air_british));
        items.add(new EuropeItem("Brussels Airlines",  "BEL", R.drawable.air_brussel));
        items.add(new EuropeItem("Crotia Airlines",  "CTN", R.drawable.air_crotia));
        items.add(new EuropeItem("EasyJet",  "EZY", R.drawable.air_easylet));
        items.add(new EuropeItem("Finnair",  "FUB", R.drawable.air_finnair));
        items.add(new EuropeItem("Iberia",  "IBE", R.drawable.iberia));
        items.add(new EuropeItem("KLM Royal Dutch Airlines",  "KLM", R.drawable.air_klm_royal));
        items.add(new EuropeItem("Lufthansa",  "DLH", R.drawable.air_lufthansa));
        items.add(new EuropeItem("Norwegian Air Shuttle",  "NAX", R.drawable.air_norwegian));
        items.add(new EuropeItem("Ryanair",  "RYR", R.drawable.air_ryan));
        items.add(new EuropeItem("Scandinavian Airlines",  "SAS", R.drawable.air_sacndinavian));
        items.add(new EuropeItem("Swiss International Air Lines",  "SWR", R.drawable.air_swiss));
        items.add(new EuropeItem("Turkish Airlines",  "THY", R.drawable.air_turkish));
        items.add(new EuropeItem("Wizz Air (W6)",  "WZZ", R.drawable.air_wizz));



    }


    private void setAdapter() {
        EuropeAdapter adapter = new EuropeAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}