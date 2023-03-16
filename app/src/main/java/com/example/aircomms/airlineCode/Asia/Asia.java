package com.example.aircomms.airlineCode.Asia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircomms.MainActivity;
import com.example.aircomms.R;
import com.example.aircomms.airlineCode.AirlineCode;
import com.example.aircomms.phonetics.Items;
import com.example.aircomms.phonetics.PhoneticsAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class Asia extends AppCompatActivity {


    private ArrayList<AsiaItem> itemsAsia;
    private RecyclerView recyclerView;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asia);


        recyclerView = findViewById(R.id.recyclerview_asia);
        itemsAsia = new ArrayList<>();

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
                    AsiaAdapter adapter = new AsiaAdapter(itemsAsia);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });




    }

    private void fileList(String text) {
        ArrayList<AsiaItem> filteredList = new ArrayList<>();
        for (AsiaItem item : itemsAsia){
            if (item.getAsia_callsign().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            AsiaAdapter adapter = new AsiaAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }


    private void setCharInfo() {
        itemsAsia.add(new AsiaItem("Air Asia",  "AKM", R.drawable.air_asia ));
        itemsAsia.add(new AsiaItem("Air China",  "CCA", R.drawable.air_china ));
        itemsAsia.add(new AsiaItem("Air India",  "AIC", R.drawable.air_india ));
        itemsAsia.add(new AsiaItem("Aira Macau",  "AMU", R.drawable.air_macau ));
        itemsAsia.add(new AsiaItem("Air New Zealand",  "ANZ", R.drawable.air_new_zealand ));
        itemsAsia.add(new AsiaItem("All Nippon Airways",  "ANA", R.drawable.air_nippon_airways ));
        itemsAsia.add(new AsiaItem("Asiana Airlines",  "AAR", R.drawable.air_asiana_airlines ));
        itemsAsia.add(new AsiaItem("Bangkok Airways",  "BKP", R.drawable.air_bangkok_airways ));
        itemsAsia.add(new AsiaItem("Cathay Pacific",  "CPA", R.drawable.air_cathay_pacific ));
        itemsAsia.add(new AsiaItem("China Airlines",  "CAL", R.drawable.air_china_airlines ));
        itemsAsia.add(new AsiaItem("China Eastern Airlines",  "CES", R.drawable.air_china_eastern_airlines ));
        itemsAsia.add(new AsiaItem("China Southern Airlines",  "CSN", R.drawable.air_china_southern_airlines ));
        itemsAsia.add(new AsiaItem("Dragonair",  "HDA", R.drawable.air_dragonair ));
        itemsAsia.add(new AsiaItem("Emirates ",  "UAE", R.drawable.air_emirates ));
        itemsAsia.add(new AsiaItem("Etihad Airways",  "ETD", R.drawable.air_etihad ));
        itemsAsia.add(new AsiaItem("EVA Air",  "EVA", R.drawable.air_eva ));
        itemsAsia.add(new AsiaItem("Garuda Indonesia",  "GIA", R.drawable.air_garuda_indonesia ));
        itemsAsia.add(new AsiaItem("Gulf Air",  "GFA", R.drawable.air_gulf_air ));
        itemsAsia.add(new AsiaItem("Hainan Airlines",  "CHH", R.drawable.air_hainan_airlines ));
        itemsAsia.add(new AsiaItem("Hong Kong Airlines",  "CRK", R.drawable.air_hongkong ));
        itemsAsia.add(new AsiaItem("IndiGo",  "IGO", R.drawable.air_indigo ));
        itemsAsia.add(new AsiaItem("Japan Airlines",  "JAL", R.drawable.air_japan ));
        itemsAsia.add(new AsiaItem("Jet Airways",  "JAI", R.drawable.air_jet_airways ));
        itemsAsia.add(new AsiaItem("Juneyao Airlines",  "DKH", R.drawable.air_juneyao_airlines ));
        itemsAsia.add(new AsiaItem("Korean Air (",  " KAL", R.drawable.air_korean_air ));
        itemsAsia.add(new AsiaItem("Kuwait Airways",  "KAC", R.drawable.air_kuwait ));
        itemsAsia.add(new AsiaItem("Malaysia Airlines",  "MAS", R.drawable.air_malaysia ));
        itemsAsia.add(new AsiaItem("Oman Air (WY)",  "OMA", R.drawable.air_oman ));
        itemsAsia.add(new AsiaItem("Pakistan International Airlines",  "PIA", R.drawable.air_pakistan ));
        itemsAsia.add(new AsiaItem("Philippine Airlines",  "PAL", R.drawable.air_philippine));
        itemsAsia.add(new AsiaItem("Qantas",  "QFA", R.drawable.air_quantas ));
        itemsAsia.add(new AsiaItem("Royal Brunei Airlines",  "RBA", R.drawable.air_royal_brunei ));
        itemsAsia.add(new AsiaItem("Scoot",  "SCO", R.drawable.air_scoot ));
        itemsAsia.add(new AsiaItem("Singapore Airlines",  "SIA", R.drawable.air_singapore ));
        itemsAsia.add(new AsiaItem("SriLankan Airlines",  "ALK", R.drawable.air_sirilanka));
        itemsAsia.add(new AsiaItem("Thai Airways",  "THA", R.drawable.air_taiwan ));
        itemsAsia.add(new AsiaItem("Turkish Airlines",  "THY", R.drawable.air_turkish));
        itemsAsia.add(new AsiaItem("Vietnam Airlines",  "HVN", R.drawable.air_vietnam ));
    }

    private void setAdapter() {

        AsiaAdapter adapter = new AsiaAdapter(itemsAsia);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    public void asia_back(View view) {
        startActivity(new Intent(Asia.this, AirlineCode.class));
    }
}