package com.example.aircomms.airportCode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.aircomms.MainActivity;
import com.example.aircomms.R;
import com.example.aircomms.SharedPref;
import com.example.aircomms.airportCode.Africa.Airport_Africa;
import com.example.aircomms.airportCode.Asia.AirportAsia;
import com.example.aircomms.airportCode.Australia.Airport_Australia;
import com.example.aircomms.airportCode.Europe.Airport_Europee;
import com.example.aircomms.airportCode.North.Airport_North;
import com.example.aircomms.airportCode.South.Airport_South;

public class AirportCode extends AppCompatActivity {

    SharedPref sharedPref;
    LinearLayout layout, list;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(AirportCode.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(AirportCode.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_code);

        history();
    }

    private void history() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Airport Code", true);
        editor.putLong("Airport Code", System.currentTimeMillis());
        editor.apply();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    public void airport_africa(View view) {
        startActivity(new Intent(AirportCode.this, Airport_Africa.class));
    }

    public void airport_australia(View view) {
        startActivity(new Intent(AirportCode.this, Airport_Australia.class));
    }

    public void airport_europe(View view) {
        startActivity(new Intent(AirportCode.this, Airport_Europee.class));
    }

    public void airport_north(View view) {
        startActivity(new Intent(AirportCode.this, Airport_North.class));
    }

    public void airport_south(View view) {
        startActivity(new Intent(AirportCode.this, Airport_South.class));
    }

    public void airport_asia(View view) {
        startActivity(new Intent(AirportCode.this, AirportAsia.class));
    }

    public void airport_back(View view) {
        startActivity(new Intent(AirportCode.this, MainActivity.class));
    }
}