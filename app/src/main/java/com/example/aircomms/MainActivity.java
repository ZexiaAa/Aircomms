package com.example.aircomms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        navigationOpen();


        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    public void init()
    {
        toolbar = findViewById(R.id.toolbarMain);
        drawerLayout = findViewById(R.id.drawerMain);
        navigationView = findViewById(R.id.navMain);
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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.mm1);


        navigationView.setNavigationItemSelectedListener(this); //navigation drawer item clickable
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawerExtras:
                Intent intent = new Intent(this, Extras.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }


    // BACK BUTTON FUNCTION OF THE PHONE
    @Override
    public void onBackPressed() {

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
}