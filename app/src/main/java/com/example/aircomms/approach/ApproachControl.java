package com.example.aircomms.approach;

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
import com.example.aircomms.airlineCode.AirlineCode;
import com.example.aircomms.airlineCode.Europe.Europe;
import com.example.aircomms.airportCode.AirportCode;
import com.example.aircomms.approach.Arrival.Arrival;
import com.example.aircomms.approach.Departure.Departure;
import com.example.aircomms.approach.Examples.DisplayExample;
import com.example.aircomms.approach.STO.StraightOut;

public class ApproachControl extends AppCompatActivity {
    public static final String EXAMPLE_DEPARTURE = "approachControl_examples_open_departure";
    public static final String EXAMPLE_ARRIVAL = "approachControl_examples_open_arrival";
    public static final String EXAMPLE_STO = "approachControl_examples_open_sto";

    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(ApproachControl.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(ApproachControl.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approach_control);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
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

    public void approachControl_back(View view) {
        onBackPressed();
    }

    public void approachControl_departure(View view) {
        hideVisibility(findViewById(R.id.others_departure),findViewById(R.id.linearLayout_others));
        hideVisibility(findViewById(R.id.others_arrival),findViewById(R.id.linearLayout_others));
        hideVisibility(findViewById(R.id.others_sto),findViewById(R.id.linearLayout_others));

        startActivity(new Intent(ApproachControl.this, Departure.class));
    }

    public void approachControl_arrival(View view) {
        hideVisibility(findViewById(R.id.others_departure),findViewById(R.id.linearLayout_others));
        hideVisibility(findViewById(R.id.others_arrival),findViewById(R.id.linearLayout_others));
        hideVisibility(findViewById(R.id.others_sto),findViewById(R.id.linearLayout_others));

        startActivity(new Intent(ApproachControl.this, Arrival.class));
    }

    public void approachControl_sto(View view) {
        hideVisibility(findViewById(R.id.others_departure),findViewById(R.id.linearLayout_others));
        hideVisibility(findViewById(R.id.others_arrival),findViewById(R.id.linearLayout_others));
        hideVisibility(findViewById(R.id.others_sto),findViewById(R.id.linearLayout_others));

        startActivity(new Intent(ApproachControl.this, StraightOut.class));
    }

    public void approachControl_examples(View view) {
        setVisibility(findViewById(R.id.others_departure),findViewById(R.id.linearLayout_others));
        setVisibility(findViewById(R.id.others_arrival),findViewById(R.id.linearLayout_others));
        setVisibility(findViewById(R.id.others_sto),findViewById(R.id.linearLayout_others));
    }

    public void approachControl_examples_open_departure(View view) {
        Intent intent = new Intent(this, DisplayExample.class);
        intent.putExtra("startupCode",EXAMPLE_DEPARTURE);

        startActivity(intent);
    }

    public void approachControl_examples_open_arrival(View view) {
        Intent intent = new Intent(this, DisplayExample.class);
        intent.putExtra("startupCode",EXAMPLE_ARRIVAL);

        startActivity(intent);
    }

    public void approachControl_examples_open_sto(View view) {
        Intent intent = new Intent(this, DisplayExample.class);
        intent.putExtra("startupCode",EXAMPLE_STO);

        startActivity(intent);
    }
}