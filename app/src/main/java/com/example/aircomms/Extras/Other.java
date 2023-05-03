package com.example.aircomms.Extras;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.aircomms.History.History;
import com.example.aircomms.MainActivity;
import com.example.aircomms.R;
import com.example.aircomms.ShareApp;
import com.example.aircomms.SharedPref;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class Other extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    LinearLayout layout, list, layout1, list1;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Other.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Other.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        initExtras();
        navigationOpen();
        historyExtras();
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

    }

    private void historyExtras() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Other", true);
        editor.putLong("Other", System.currentTimeMillis());
        editor.apply();
    }



    private void initExtras() {


        toolbar = findViewById(R.id.toolbarExtras);
        drawerLayout = findViewById(R.id.drawerExtras);
        navigationView = findViewById(R.id.navExtras);


        //standard phars
        layout = findViewById(R.id.layout);
        list = findViewById(R.id.list);

        //aircraft
        list1 = findViewById(R.id.list1);
        layout1 = findViewById(R.id.layout2);

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
        MenuItem menuItem = menu.findItem(R.id.drawerExtras);
        menuItem.setChecked(true);




    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawerMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.drawerHistory:
                Intent intent1 = new Intent(this, History.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.drawerShare:
                Intent intent2 = new Intent(this, ShareApp.class);
                startActivity(intent2);
                finish();
                break;

        }
        return true;
    }


    public void expand (View view){
        int v = (list.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        list.setVisibility(v);

    }

    public void expand1(View view) {

        int v = (list1.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(layout1, new AutoTransition());
        list1.setVisibility(v);
    }


}