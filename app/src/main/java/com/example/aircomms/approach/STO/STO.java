package com.example.aircomms.approach.STO;

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
import com.example.aircomms.approach.Arrival.Arrival;
import com.example.aircomms.approach.SampleSTO;

public class STO extends AppCompatActivity {

    SharedPref sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(STO.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(STO.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sto);
        history();
    }

    private void history() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Approach Control \nStraight-Out Departure", true);
        editor.putLong("Approach Control \nStraight-Out Departure", System.currentTimeMillis());
        editor.apply();
    }

    public void sto_back(View view) {
        startActivity(new Intent(STO.this, ApproachControl.class));
    }

    public void estimateFrom(View view) {
        startActivity(new Intent(STO.this, SampleSTO.class));
    }

    public void additional(View view) {
        startActivity(new Intent(STO.this, SampleSTO.class));
    }

    public void actualTime(View view) {
        startActivity(new Intent(STO.this, SampleSTO.class));
    }

    public void pilotInitialInstructions(View view) {
        startActivity(new Intent(STO.this, SampleSTO.class));
    }

    public void monitoringOfAircraft(View view) {
        startActivity(new Intent(STO.this, SampleSTO.class));
    }

    public void releaseMsg(View view) {
        startActivity(new Intent(STO.this, SampleSTO.class));
    }
}