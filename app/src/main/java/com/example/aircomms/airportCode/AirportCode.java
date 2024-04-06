package com.example.aircomms.airportCode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.aircomms.MainActivity;
import com.example.aircomms.R;
import com.example.aircomms.SharedPref;

public class AirportCode extends AppCompatActivity {
    public static final String DOMESTIC_PRINCIPAL = "airportCode_domestic_principal";
    public static final String DOMESTIC_COMMUNITY = "airportCode_domestic_community";
    public static final String INTERNATIONAL_MIDDLE_EAST = "airportCode_international_middleEast";
    public static final String INTERNATIONAL_THAILAND = "airportCode_international_thailand";
    public static final String INTERNATIONAL_AUSTRALIA = "airportCode_international_australia";
    public static final String INTERNATIONAL_TAIWAN = "airportCode_international_taiwan";
    public static final String INTERNATIONAL_JAPAN = "airportCode_international_japan";
    public static final String INTERNATIONAL_SINGAPORE = "airportCode_international_singapore";
    public static final String INTERNATIONAL_MALAYSIA = "airportCode_international_malaysia";
    public static final String INTERNATIONAL_INDONESIA = "airportCode_international_indonesia";
    public static final String INTERNATIONAL_CHINA = "airportCode_international_china";
    public static final String INTERNATIONAL_PHILIPPINES = "airportCode_international_philippines";
    SharedPref sharedPref;

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    public void airportCode_back(View view) {
        onBackPressed();
    }

    private void setVisibility(View view, LinearLayout layout) {
        int visibility = (view.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        view.setVisibility(visibility);
    }

    private void hideVisibility(View view, LinearLayout layout) {
        if (view.getVisibility() == View.VISIBLE) {
            TransitionManager.beginDelayedTransition(layout, new AutoTransition());
            view.setVisibility(View.GONE);
        }
    }

    public void airportCode_domestic(View view) {
        setVisibility(view.findViewById(R.id.domestic_principal),view.findViewById(R.id.domesticLayout));
        setVisibility(view.findViewById(R.id.domestic_community),view.findViewById(R.id.domesticLayout));

        hideVisibility(findViewById(R.id.international_1),findViewById(R.id.internationalLayout));
        hideVisibility(findViewById(R.id.international_2),findViewById(R.id.internationalLayout));
        hideVisibility(findViewById(R.id.international_3),findViewById(R.id.internationalLayout));
        hideVisibility(findViewById(R.id.international_4),findViewById(R.id.internationalLayout));
        hideVisibility(findViewById(R.id.international_5),findViewById(R.id.internationalLayout));
        hideVisibility(findViewById(R.id.international_6),findViewById(R.id.internationalLayout));
        hideVisibility(findViewById(R.id.international_7),findViewById(R.id.internationalLayout));
        hideVisibility(findViewById(R.id.international_8),findViewById(R.id.internationalLayout));
        hideVisibility(findViewById(R.id.international_9),findViewById(R.id.internationalLayout));
        hideVisibility(findViewById(R.id.international_10),findViewById(R.id.internationalLayout));
    }

    public void airportCode_international(View view) {
        hideVisibility(findViewById(R.id.domestic_principal),findViewById(R.id.domesticLayout));
        hideVisibility(findViewById(R.id.domestic_community),findViewById(R.id.domesticLayout));

        setVisibility(view.findViewById(R.id.international_1),view.findViewById(R.id.internationalLayout));
        setVisibility(view.findViewById(R.id.international_2),view.findViewById(R.id.internationalLayout));
        setVisibility(view.findViewById(R.id.international_3),view.findViewById(R.id.internationalLayout));
        setVisibility(view.findViewById(R.id.international_4),view.findViewById(R.id.internationalLayout));
        setVisibility(view.findViewById(R.id.international_5),view.findViewById(R.id.internationalLayout));
        setVisibility(view.findViewById(R.id.international_6),view.findViewById(R.id.internationalLayout));
        setVisibility(view.findViewById(R.id.international_7),view.findViewById(R.id.internationalLayout));
        setVisibility(view.findViewById(R.id.international_8),view.findViewById(R.id.internationalLayout));
        setVisibility(view.findViewById(R.id.international_9),view.findViewById(R.id.internationalLayout));
        setVisibility(view.findViewById(R.id.international_10),view.findViewById(R.id.internationalLayout));
    }

    public void airportCode_domestic_principal(View view) {
        Intent intent = new Intent(AirportCode.this, Display.class);
        intent.putExtra("startupCode",DOMESTIC_PRINCIPAL);

        startActivity(intent);
    }

    public void airportCode_domestic_community(View view) {
        Intent intent = new Intent(AirportCode.this, Display.class);
        intent.putExtra("startupCode",DOMESTIC_COMMUNITY);

        startActivity(intent);
    }

    public void airportCode_international_middleEast(View view) {
        Intent intent = new Intent(AirportCode.this, Display.class);
        intent.putExtra("startupCode",INTERNATIONAL_MIDDLE_EAST);

        startActivity(intent);
    }

    public void airportCode_international_thailand(View view) {
        Intent intent = new Intent(AirportCode.this, Display.class);
        intent.putExtra("startupCode",INTERNATIONAL_THAILAND);

        startActivity(intent);
    }

    public void airportCode_international_australia(View view) {
        Intent intent = new Intent(AirportCode.this, Display.class);
        intent.putExtra("startupCode",INTERNATIONAL_AUSTRALIA);

        startActivity(intent);
    }

    public void airportCode_international_taiwan(View view) {
        Intent intent = new Intent(AirportCode.this, Display.class);
        intent.putExtra("startupCode",INTERNATIONAL_TAIWAN);

        startActivity(intent);
    }

    public void airportCode_international_japan(View view) {
        Intent intent = new Intent(AirportCode.this, Display.class);
        intent.putExtra("startupCode",INTERNATIONAL_JAPAN);

        startActivity(intent);
    }

    public void airportCode_international_singapore(View view) {
        Intent intent = new Intent(AirportCode.this, Display.class);
        intent.putExtra("startupCode",INTERNATIONAL_SINGAPORE);

        startActivity(intent);
    }

    public void airportCode_international_malaysia(View view) {
        Intent intent = new Intent(AirportCode.this, Display.class);
        intent.putExtra("startupCode",INTERNATIONAL_MALAYSIA);

        startActivity(intent);
    }

    public void airportCode_international_indonesia(View view) {
        Intent intent = new Intent(AirportCode.this, Display.class);
        intent.putExtra("startupCode",INTERNATIONAL_INDONESIA);

        startActivity(intent);
    }

    public void airportCode_international_china(View view) {
        Intent intent = new Intent(AirportCode.this, Display.class);
        intent.putExtra("startupCode",INTERNATIONAL_CHINA);

        startActivity(intent);
    }

    public void airportCode_international_philippines(View view) {
        Intent intent = new Intent(AirportCode.this, Display.class);
        intent.putExtra("startupCode",INTERNATIONAL_PHILIPPINES);

        startActivity(intent);
    }
}