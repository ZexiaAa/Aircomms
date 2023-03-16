package com.example.aircomms.airlineCode.Australia;

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
import com.example.aircomms.airlineCode.Africa.AfricaAdapter;
import com.example.aircomms.airlineCode.Africa.AfricaItem;

import java.util.ArrayList;

public class Australia extends AppCompatActivity {
    private ArrayList<AustraliaItem> items;
    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_australia);

        recyclerView = findViewById(R.id.recyclerview_aus);
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
                    AustraliaAdapter adapter = new AustraliaAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });
    }

    private void fileList(String text) {
        ArrayList<AustraliaItem> filteredList = new ArrayList<>();
        for (AustraliaItem item : items){
            if (item.get_callsign().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            AustraliaAdapter adapter = new AustraliaAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void setCharInfo() {
        items.add(new AustraliaItem("Qantas ",  "QFA", R.drawable.air_qantas));
        items.add(new AustraliaItem("Virgin Australia",  "VOZ", R.drawable.air_virgin));
        items.add(new AustraliaItem("Jetstar Airways",  "JST", R.drawable.air_jetstar));
        items.add(new AustraliaItem("Regional Express Airlines",  "ZL", R.drawable.air_regional));
        items.add(new AustraliaItem("Tigerair Australia",  "TGW", R.drawable.air_tigerair));
    }


    private void setAdapter() {
        AustraliaAdapter adapter = new AustraliaAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

}