package com.example.aircomms.approach.Departure;

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
import com.example.aircomms.approach.SampleFile;

public class Departure extends AppCompatActivity {

    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Departure.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Departure.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departure);
        history();
    }

    private void history() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Approach Control \nDeparture", true);
        editor.putLong("Approach Control \nDeparture", System.currentTimeMillis());
        editor.apply();
    }

    public void departure_back(View view) {
        startActivity(new Intent(Departure.this, ApproachControl.class));
    }

    public void request(View view) {
        startActivity(new Intent(Departure.this, SampleFile.class));
    }

    public void pilot(View view) {
        startActivity(new Intent(Departure.this, SampleFile.class));
    }

    public void clearance(View view) {
        startActivity(new Intent(Departure.this, SampleFile.class));
    }

    public void atc(View view) {
        startActivity(new Intent(Departure.this, SampleFile.class));
    }

    public void requesttt(View view) {
        startActivity(new Intent(Departure.this, SampleFile.class));
    }

    public void actual(View view) {
        startActivity(new Intent(Departure.this, SampleFile.class));
    }

    public void pilotsInitial(View view) {
        startActivity(new Intent(Departure.this, SampleFile.class));
    }

    public void releaseMessage(View view) {
        startActivity(new Intent(Departure.this, SampleFile.class));
    }
}