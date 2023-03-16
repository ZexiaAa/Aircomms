package com.example.aircomms.airlineCode.Africa;

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
import com.example.aircomms.airlineCode.Asia.AsiaAdapter;
import com.example.aircomms.airlineCode.Asia.AsiaItem;

import java.util.ArrayList;

public class Africa extends AppCompatActivity {



    private ArrayList<AfricaItem> items;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_africa);


        recyclerView = findViewById(R.id.recyclerview_africa);
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

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void setAdapter() {
        AfricaAdapter adapter = new AfricaAdapter(items);
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

}