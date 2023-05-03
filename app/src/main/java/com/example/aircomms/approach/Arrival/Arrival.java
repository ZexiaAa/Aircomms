package com.example.aircomms.approach.Arrival;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.aircomms.R;
import com.example.aircomms.SharedPref;
import com.example.aircomms.approach.ApproachControl;
import com.example.aircomms.approach.STO.STO;

public class Arrival extends AppCompatActivity {

    SharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Arrival.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Arrival.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);
        history();
    }

    private void history() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Approach Control \nArrival", true);
        editor.putLong("Approach Control \nArrival", System.currentTimeMillis());
        editor.apply();
    }

    public void arrival_back(View view) {
        startActivity(new Intent(Arrival.this, ApproachControl.class));
    }

    public void estimates(View view) {
        startActivity(new Intent(Arrival.this, ArrivalEstimate.class));
    }

    public void relay(View view) {
        startActivity(new Intent(Arrival.this, ArrivalRelay.class));
    }

    public void release(View view) {
        startActivity(new Intent(Arrival.this, ArrivalRelease.class));
    }

    public void initial(View view) {
        startActivity(new Intent(Arrival.this, ArrivalInitial.class));
    }

    public void monitoring(View view) {
        startActivity(new Intent(Arrival.this, ArrivalMonitoring.class));
    }

    public void clearanceh(View view) {
        startActivity(new Intent(Arrival.this, ArrivalClearance.class));
    }

    public void coordination(View view) {
        startActivity(new Intent(Arrival.this, ArrivalCoordination.class));
    }

    public void estimate(View view) {
        startActivity(new Intent(Arrival.this, ArrivalEstimate.class));
    }
}