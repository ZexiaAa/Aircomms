package com.example.aircomms.History;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.reflect.Type;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircomms.Extras.Other;
import com.example.aircomms.MainActivity;
import com.example.aircomms.R;
import com.example.aircomms.SharedPref;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class History extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    View noFile;
    TextView noSubject;

    private SharedPref sharedPref;
    private TextView timestamp;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private RecyclerView historyRecyclerView;
    private List<HistoryItem> historyItems = new ArrayList<>();
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(History.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(History.this, R.color.bg));
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Log.d("History", "Number of history items: " + historyItems.size());

        initialized();

        // Load the data from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString("history_items", null);
        Type type = new TypeToken<ArrayList<HistoryItem>>(){}.getType();
        ArrayList<HistoryItem> items = gson.fromJson(json, type);
        if (items != null) {
            historyItems.addAll(items);
        }


        historyRecyclerView = findViewById(R.id.history_recyclerview);
        // Create LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // Set LinearLayoutManager to RecyclerView

        historyRecyclerView.setLayoutManager(layoutManager);
        historyRecyclerView = findViewById(R.id.history_recyclerview);
        historyAdapter = new HistoryAdapter(this, historyItems); // create the adapter and assign it to the variable

        if (historyRecyclerView != null) {
            historyRecyclerView.setAdapter(historyAdapter);
        }

        retrieve();
        navigationOpen();
        sort();

    }

    private void sort() {
        // Sort the list of history items based on last viewed time
        Collections.sort(historyItems, new Comparator<HistoryItem>() {
            @Override
            public int compare(HistoryItem o1, HistoryItem o2) {
                return Long.compare(o2.getLastViewedTime(), o1.getLastViewedTime());
            }
        });

        // Update the adapter with the sorted list and refresh the RecyclerView
        historyAdapter.setHistoryItems(historyItems);
        historyAdapter.notifyDataSetChanged();

    }


    private void initialized() {
        toolbar = findViewById(R.id.toolbarHistory);
        drawerLayout = findViewById(R.id.drawerHistory);
        navigationView = findViewById(R.id.navHistory);
        timestamp = findViewById(R.id.timestamp);
        noFile = findViewById(R.id.empty_history_imageview);
        noSubject = findViewById(R.id.empty_history_textview);
    }

    public void clearlist(View view) {
        // Clear the list of items
        historyItems.clear();

        // Notify the adapter that the data set has changed
        Toast.makeText(getApplicationContext(), "History is cleared", Toast.LENGTH_SHORT).show();


        // Clear the data in SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        prefs.edit().clear().apply();

        // Update the empty view
        updateEmptyView();
        historyAdapter.notifyDataSetChanged();


    }

    private void retrieve() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        Map<String, ?> allPrefs = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allPrefs.entrySet()) {
            String activityName = entry.getKey();
            if (activityName.equals("MainActivity")) {
                activityName = "Home";
            }
            else if (activityName.equals("Phonetics")) {
                activityName = "Phonetics";
            }
            else if (activityName.equals("Other")) {
                activityName = "Other";
            } else if (activityName.equals("Arline Code (Africa)")) {
                activityName = "Arline Code (Africa)";
            }
            else if (activityName.equals("Arline Code (Asia)")) {
                activityName = "Arline Code (Asia)";
            }
            else if (activityName.equals("Arline Code (Australia)")) {
                activityName = "Arline Code (Australia)";
            }
            else if (activityName.equals("Arline Code (Europe)")) {
                activityName = "Arline Code (Europe)";
            }
            else if (activityName.equals("Arline Code (North America)")) {
                activityName = "Arline Code (North America)";
            }
            else if (activityName.equals("Arline Code (South America)")) {
                activityName = "Arline Code (South America)";
            }
            else if (activityName.equals("Airport Code (Asia - China)")) {
                activityName = "Airport Code (Asia - China)";
            }
            else if (activityName.equals("Airport Code (Asia - India)")) {
                activityName = "Airport Code (Asia - India)";
            }
            else if (activityName.equals("Airport Code (Asia - Thailand)")) {
                activityName = "Airport Code (Asia - Thailand)";
            }
            else if (activityName.equals("Airport Code (Asia - South Korea)")) {
                activityName = "Airport Code (Asia - South Korea)";
            }
            else if (activityName.equals("Airport Code (Asia - Japan)")) {
                activityName = "Airport Code (Asia - Japan)";
            }
            else if (activityName.equals("Airport Code (Asia - Indonesia)")) {
                activityName = "Airport Code (Asia - Indonesia)";
            }

            SharedPref sharedPref = new SharedPref(this);
            long lastViewedTime = sharedPref.sharedPreferences.getLong(activityName + "_last_viewed", 0);

            // Create a new HistoryItem object with the activity name and last viewed time
            HistoryItem historyItem = new HistoryItem(activityName, lastViewedTime);

            // Add the new HistoryItem object to the list
            historyItems.add(historyItem);
        }

        // Notify the adapter that the data set has changed
        historyAdapter.notifyDataSetChanged();
    }




    @Override
    public void onResume() {
        super.onResume();

        initialized();
        navigationOpen();
        updateEmptyView();
    }

    private void updateEmptyView() {
        if (historyItems.size() > 0) {
            // Hide the empty view if there are items in the RecyclerView
            noFile.setVisibility(View.GONE);
            noSubject.setVisibility(View.GONE);
            historyRecyclerView.setVisibility(View.VISIBLE);
        } else {
            // Show the empty view if there are no items in the RecyclerView
            noFile.setVisibility(View.VISIBLE);
            noSubject.setVisibility(View.VISIBLE);
            historyRecyclerView.setVisibility(View.GONE);
        }
    }

    private void navigationOpen() {
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();     // Show navigation drawer when clicked
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.mm5);

        navigationView.setNavigationItemSelectedListener(this); //navigation drawer item clickable
        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.drawerHistory);
        menuItem.setChecked(true);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawerExtras:
                Intent intent = new Intent(this, Other.class);
                startActivity(intent);
                finish();
                break;
            case R.id.drawerMain:
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                finish();
                break;


        }
        return true;
    }

    // BACK BUTTON FUNCTION OF THE PHONE
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);  // When back button is pressed while navigation drawer is open, it will close the navigation drawer.
        }
        else {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, id) -> finishAffinity())
                    .setNegativeButton("No", null)
                    .show();                    // Exit pop up when back button is pressed if navigation drawer is not open
        }
    }



}



