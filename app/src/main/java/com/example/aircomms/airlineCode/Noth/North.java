package com.example.aircomms.airlineCode.Noth;

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

import java.util.ArrayList;

public class North extends AppCompatActivity {
    private ArrayList<NorthItem> items;
    private RecyclerView recyclerView;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_north);

        recyclerView = findViewById(R.id.recyclerview_north);
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
                    NorthAdapter adapter = new NorthAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });
    }
    private void fileList(String text) {
        ArrayList<NorthItem> filteredList = new ArrayList<>();
        for (NorthItem item : items){
            if (item.get_callsign().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            NorthAdapter adapter = new  NorthAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }
    private void setCharInfo() {
        items.add(new NorthItem("Aeromexico",  "AMX", R.drawable.air_aeromexico));
        items.add(new NorthItem("Air Canada",  "ACA", R.drawable.air_canada));
        items.add(new NorthItem("Air Transat",  "TSC", R.drawable.air_transat));
        items.add(new NorthItem("Alaska Airlines",  "ASA", R.drawable.air_alaska));
        items.add(new NorthItem("Allegiant Air",  "AAY", R.drawable.air_allegant));
        items.add(new NorthItem("American Airlines (",  "AAL", R.drawable.air_american));
        items.add(new NorthItem("Delta Air Lines",  "DAL", R.drawable.air_delta));
        items.add(new NorthItem("Frontier Airlines",  "FFT", R.drawable.air_frontier));
        items.add(new NorthItem("Hawaiian Airlines",  "HAL", R.drawable.air_hawaiian));
        items.add(new NorthItem("JetBlue Airways",  "JBU", R.drawable.air_jetblue));
        items.add(new NorthItem("Southwest Airlines",  "SWA", R.drawable.air_southwest));
        items.add(new NorthItem("Spirit Airlines",  "NKS", R.drawable.air_spirit));
        items.add(new NorthItem("Sun Country Airlines ",  "SCX", R.drawable.air_sun_country));
        items.add(new NorthItem("United Airlines",  "UAL", R.drawable.air_united_airline));
        items.add(new NorthItem("WestJet",  "WJA", R.drawable.air_westjet));

    }

    private void setAdapter() {
        NorthAdapter adapter = new  NorthAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}