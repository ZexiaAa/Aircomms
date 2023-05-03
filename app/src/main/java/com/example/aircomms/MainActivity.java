package com.example.aircomms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;

import com.example.aircomms.Bookmark.BookmarkActivity;
import com.example.aircomms.History.History;
import com.example.aircomms.airlineCode.AirlineCode;
import com.example.aircomms.airportCode.AirportCode;
import com.example.aircomms.approach.ApproachControl;
import com.example.aircomms.Extras.Other;
import com.example.aircomms.phonetics.Phonetics;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    SwitchMaterial toggleButton;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        navigationOpen();
        setNightModeTheme(); // Button toggle for night mode
        history();
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    private void history() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("MainActivity"); // remove the "Home" item
        editor.apply();
    }

    private void setNightModeTheme() {
        if (sharedPref.loadNightModeState()){
            toggleButton.setChecked(true);
        }
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    sharedPref.setNightModeState(true);
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    overridePendingTransition(R.transition.fade_in_out,R.transition.fade_in_out);
                    finish();

                }else {
                    sharedPref.setNightModeState(false);
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.transition.fade_in_out,R.transition.fade_in_out);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    finish();
                }

            }
        });
    }

    public void init()
    {
        toolbar = findViewById(R.id.toolbarMain);
        drawerLayout = findViewById(R.id.drawerMain);
        navigationView = findViewById(R.id.navMain);
        toggleButton = findViewById(R.id.darkModeButton);
    }


    @Override
    public void onResume() {
        super.onResume();

        init();
        navigationOpen();   //NAVIGATION DRAWER

    }


    public void navigationOpen() {
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
        MenuItem menuItem = menu.findItem(R.id.drawerMain);
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


    public void phonetics(View view) {
        startActivity(new Intent(MainActivity.this, Phonetics.class));
    }

    public void airline_code(View view) {
        startActivity(new Intent(MainActivity.this, AirlineCode.class));
    }

    public void approach(View view) {

        startActivity(new Intent(MainActivity.this, ApproachControl.class));
    }

    public void airport_code(View view) {
        startActivity(new Intent(MainActivity.this, AirportCode.class));
    }
}