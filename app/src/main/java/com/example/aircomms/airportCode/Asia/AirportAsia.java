package com.example.aircomms.airportCode.Asia;

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
import com.example.aircomms.airportCode.Africa.Airport_Africa;
import com.example.aircomms.airportCode.AirportCode;
import com.example.aircomms.airportCode.Asia.China.AsiaChina;
import com.example.aircomms.airportCode.Asia.India.AsiaIndia;
import com.example.aircomms.airportCode.Asia.Indonesia.AsiaIndonesia;
import com.example.aircomms.airportCode.Asia.Japan.AsiaJapan;
import com.example.aircomms.airportCode.Asia.Phil.AsiaPH;
import com.example.aircomms.airportCode.Asia.SouthKorea.AsiaSouthKorea;
import com.example.aircomms.airportCode.Asia.Thailand.AsiaThailand;
import com.example.aircomms.phonetics.Phonetics;

public class AirportAsia extends AppCompatActivity {
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(AirportAsia.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(AirportAsia.this, R.color.bg));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_asia);
        historyAsiaa();

    }
    private void historyAsiaa() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Airport Code \n(Asia)", true);
        editor.putLong("Airport Code \n(Asia)", System.currentTimeMillis());
        editor.apply();
    }

    public void airport_back(View view) {
        startActivity(new Intent(AirportAsia.this, AirportCode.class));
    }

    public void china(View view) {
        startActivity(new Intent(AirportAsia.this, AsiaChina.class));
    }

    public void india(View view) {
        startActivity(new Intent(AirportAsia.this, AsiaIndia.class));
    }

    public void indo(View view) {
        startActivity(new Intent(AirportAsia.this, AsiaIndonesia.class));
    }

    public void japan(View view) {
        startActivity(new Intent(AirportAsia.this, AsiaJapan.class));
    }

    public void thailand(View view) {
        startActivity(new Intent(AirportAsia.this, AsiaThailand.class));
    }

    public void southkorea(View view) {
        startActivity(new Intent(AirportAsia.this, AsiaSouthKorea.class));
    }

    public void ph(View view) {
        startActivity(new Intent(AirportAsia.this, AsiaPH.class));
    }
}