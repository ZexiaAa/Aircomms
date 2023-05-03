package com.example.aircomms.airportCode.Asia.China;

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
import com.example.aircomms.airportCode.Africa.AfricaAdapterr;
import com.example.aircomms.airportCode.Africa.AfricaItemm;
import com.example.aircomms.airportCode.Africa.Airport_Africa;
import com.example.aircomms.airportCode.Asia.AirportAsia;
import com.example.aircomms.airportCode.Asia.SouthKorea.AsiaSouthKorea;

import java.util.ArrayList;

public class AsiaChina extends AppCompatActivity {
    
    private ArrayList<ChinaItem> items;
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
            window.setStatusBarColor(ContextCompat.getColor(AsiaChina.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(AsiaChina.this, R.color.bg));
        }
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asia_china);

        recyclerView = findViewById(R.id.recyclerview_china);
        items = new ArrayList<>();

        searchView = findViewById(R.id.searchViewChina);

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
        historyChina();
    }

    private void historyChina() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Airport Code \n(Asia - China)", true);
        editor.putLong("Airport Code \n(Asia - China)", System.currentTimeMillis());
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
                    ChinaAdapter adapter = new ChinaAdapter(items);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }
        });

    }

    private void fileList(String text) {
        ArrayList<ChinaItem> filteredList = new ArrayList<>();
        for (ChinaItem item : items){
            if (item.get_airport().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            recyclerView.setAdapter(null); // clear the RecyclerView if no data found
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else{
            ChinaAdapter adapter = new ChinaAdapter(filteredList);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void setCharInfo() {
        items.add(new ChinaItem("Beijing Capital International Airport",  "ZBAA", "Beijing"));
        items.add(new ChinaItem("Shanghai Pudong International Airport",  "ZSPD", "Shanghai"));
        items.add(new ChinaItem("Guangzhou Baiyun International Airport",  "ZGGG", "Guangzhou"));
        items.add(new ChinaItem("Chengdu Shuangliu International Airport",  "ZUUU", "Chengdu"));
        items.add(new ChinaItem("Shenzhen Bao'an International Airport",  "ZGSZ", "Shenzhen"));
        items.add(new ChinaItem("Kunming Changshui International Airport",  "ZPP", "Kunming"));
        items.add(new ChinaItem("Xi'an Xianyang International Airport ",  "ZLXY", "Xi'an"));
        items.add(new ChinaItem("Chongqing Jiangbei International Airport (",  "ZUCK", "Chongqing"));
        items.add(new ChinaItem("Hangzhou Xiaoshan International Airport ",  "ZSHC", "Hangzhou"));
        items.add(new ChinaItem("Qingdao Liuting International Airport",  "ZSQD", "Qingdao"));
    }

    private void setAdapter() {
        ChinaAdapter adapter = new ChinaAdapter(items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void china_back(View view) {
        startActivity(new Intent(AsiaChina.this, AirportAsia.class));
    }
}