package com.example.aircomms.approach.Arrival;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aircomms.R;
import com.example.aircomms.SharedPref;
import com.example.aircomms.approach.ApproachControl;
import com.example.aircomms.approach.PseudoTMA;

import java.util.Timer;
import java.util.TimerTask;

public class Arrival extends AppCompatActivity {

    SharedPref sharedPref;

    MediaPlayer audioPlayer;
    Timer audioTimer;
    ProgressBar audioProgressBar;
    View previousClickedView, clickedView;
    LinearLayout previousClickedLayout, clickedLayout;
    TextView flightStripContent_1, flightStripContent_2, flightStripContent_3, flightStripContent_4,
            flightStripContent_5, flightStripContent_6, flightStripContent_7, flightStripContent_8,
            flightStripContent_9, flightStripContent_10, flightStripContent_11, flightStripContent_12,
            flightStripContent_13, flightStripContent_14, flightStripContent_15, flightStripContent_16,
            flightStripContent_17;
    int clickedItem = 0;
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

        //Setup Views for Flight Strip
        flightStripContent_1 = findViewById(R.id.flightStripContentAR_1);
        flightStripContent_2 = findViewById(R.id.flightStripContentAR_2);
        flightStripContent_3 = findViewById(R.id.flightStripContentAR_3);
        flightStripContent_4 = findViewById(R.id.flightStripContentAR_4);
        flightStripContent_5 = findViewById(R.id.flightStripContentAR_5);
        flightStripContent_6 = findViewById(R.id.flightStripContentAR_6);
        flightStripContent_7 = findViewById(R.id.flightStripContentAR_7);
        flightStripContent_8 = findViewById(R.id.flightStripContentAR_8);
        flightStripContent_9 = findViewById(R.id.flightStripContentAR_9);
        flightStripContent_10 = findViewById(R.id.flightStripContentAR_10);
        flightStripContent_11 = findViewById(R.id.flightStripContentAR_11);
        flightStripContent_12 = findViewById(R.id.flightStripContentAR_12);
        flightStripContent_13 = findViewById(R.id.flightStripContentAR_13);
        flightStripContent_14 = findViewById(R.id.flightStripContentAR_14);
        flightStripContent_15 = findViewById(R.id.flightStripContentAR_15);
        flightStripContent_16 = findViewById(R.id.flightStripContentAR_16);
        flightStripContent_17 = findViewById(R.id.flightStripContentAR_17);

        //Manage Flight Strip upon onCreate
        manageFlightStrip(clickedItem);



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

    protected void onStop() {
        super.onStop();
        stopAudio();
        if (audioTimer != null) {
            audioTimer.cancel();
            audioTimer = null;
        }
    }

    private void manageFlightStrip(int clickedItem) {
        switch (clickedItem) {
            case 0:
            default:
                flightStripContent_1.setVisibility(View.INVISIBLE);
                flightStripContent_2.setVisibility(View.INVISIBLE);
                flightStripContent_3.setVisibility(View.INVISIBLE);
                flightStripContent_4.setVisibility(View.INVISIBLE);
                flightStripContent_5.setVisibility(View.INVISIBLE);
                flightStripContent_6.setVisibility(View.INVISIBLE);
                flightStripContent_7.setVisibility(View.INVISIBLE);
                flightStripContent_8.setVisibility(View.INVISIBLE);
                flightStripContent_9.setVisibility(View.INVISIBLE);
                flightStripContent_10.setVisibility(View.INVISIBLE);
                flightStripContent_11.setVisibility(View.INVISIBLE);
                flightStripContent_12.setVisibility(View.INVISIBLE);
                flightStripContent_13.setVisibility(View.INVISIBLE);
                flightStripContent_14.setVisibility(View.INVISIBLE);
                flightStripContent_15.setVisibility(View.INVISIBLE);
                flightStripContent_16.setVisibility(View.INVISIBLE);
                flightStripContent_17.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                flightStripContent_3.setPaintFlags(flightStripContent_3.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                flightStripContent_4.setPaintFlags(flightStripContent_4.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                //Color Text
                flightStripContent_1.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_2.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_4.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_6.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_7.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_8.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_9.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_11.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_12.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_13.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_14.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_15.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_17.setTextColor(getResources().getColor(R.color.black));
                break;
            case 1:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.INVISIBLE);
                flightStripContent_5.setVisibility(View.INVISIBLE);
                flightStripContent_6.setVisibility(View.INVISIBLE);
                flightStripContent_7.setVisibility(View.INVISIBLE);
                flightStripContent_8.setVisibility(View.INVISIBLE);
                flightStripContent_9.setVisibility(View.INVISIBLE);
                flightStripContent_10.setVisibility(View.INVISIBLE);
                flightStripContent_11.setVisibility(View.INVISIBLE);
                flightStripContent_12.setVisibility(View.INVISIBLE);
                flightStripContent_13.setVisibility(View.INVISIBLE);
                flightStripContent_14.setVisibility(View.INVISIBLE);
                flightStripContent_15.setVisibility(View.INVISIBLE);
                flightStripContent_16.setVisibility(View.INVISIBLE);
                flightStripContent_17.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                flightStripContent_3.setPaintFlags(flightStripContent_3.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                flightStripContent_4.setPaintFlags(flightStripContent_4.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                //Color Text
                flightStripContent_1.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_2.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_4.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_6.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_7.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_8.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_9.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_11.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_12.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_13.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_14.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_15.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_17.setTextColor(getResources().getColor(R.color.black));
                break;
            case 2:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.INVISIBLE);
                flightStripContent_5.setVisibility(View.INVISIBLE);
                flightStripContent_6.setVisibility(View.INVISIBLE);
                flightStripContent_7.setVisibility(View.INVISIBLE);
                flightStripContent_8.setVisibility(View.INVISIBLE);
                flightStripContent_9.setVisibility(View.INVISIBLE);
                flightStripContent_10.setVisibility(View.INVISIBLE);
                flightStripContent_11.setVisibility(View.INVISIBLE);
                flightStripContent_12.setVisibility(View.INVISIBLE);
                flightStripContent_13.setVisibility(View.INVISIBLE);
                flightStripContent_14.setVisibility(View.INVISIBLE);
                flightStripContent_15.setVisibility(View.INVISIBLE);
                flightStripContent_16.setVisibility(View.INVISIBLE);
                flightStripContent_17.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                flightStripContent_3.setPaintFlags(flightStripContent_3.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                flightStripContent_4.setPaintFlags(flightStripContent_4.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                //Color Text
                flightStripContent_1.setTextColor(getResources().getColor(R.color.purple_highlight));
                flightStripContent_2.setTextColor(getResources().getColor(R.color.purple_highlight));
                flightStripContent_4.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_6.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_7.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_8.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_9.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_11.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_12.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_13.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_14.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_15.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_17.setTextColor(getResources().getColor(R.color.black));
                break;
            case 3:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.INVISIBLE);
                flightStripContent_5.setVisibility(View.INVISIBLE);
                flightStripContent_6.setVisibility(View.VISIBLE);
                flightStripContent_7.setVisibility(View.INVISIBLE);
                flightStripContent_8.setVisibility(View.INVISIBLE);
                flightStripContent_9.setVisibility(View.INVISIBLE);
                flightStripContent_10.setVisibility(View.INVISIBLE);
                flightStripContent_11.setVisibility(View.INVISIBLE);
                flightStripContent_12.setVisibility(View.INVISIBLE);
                flightStripContent_13.setVisibility(View.INVISIBLE);
                flightStripContent_14.setVisibility(View.INVISIBLE);
                flightStripContent_15.setVisibility(View.INVISIBLE);
                flightStripContent_16.setVisibility(View.INVISIBLE);
                flightStripContent_17.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                flightStripContent_3.setPaintFlags(flightStripContent_3.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                flightStripContent_4.setPaintFlags(flightStripContent_4.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                //Color Text
                flightStripContent_1.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_2.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_4.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_6.setTextColor(getResources().getColor(R.color.purple_highlight));
                flightStripContent_7.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_8.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_9.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_11.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_12.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_13.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_14.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_15.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_17.setTextColor(getResources().getColor(R.color.black));
                break;
            case 4:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.VISIBLE);
                flightStripContent_5.setVisibility(View.INVISIBLE);
                flightStripContent_6.setVisibility(View.VISIBLE);
                flightStripContent_7.setVisibility(View.VISIBLE);
                flightStripContent_8.setVisibility(View.INVISIBLE);
                flightStripContent_9.setVisibility(View.INVISIBLE);
                flightStripContent_10.setVisibility(View.INVISIBLE);
                flightStripContent_11.setVisibility(View.INVISIBLE);
                flightStripContent_12.setVisibility(View.INVISIBLE);
                flightStripContent_13.setVisibility(View.INVISIBLE);
                flightStripContent_14.setVisibility(View.INVISIBLE);
                flightStripContent_15.setVisibility(View.INVISIBLE);
                flightStripContent_16.setVisibility(View.INVISIBLE);
                flightStripContent_17.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                flightStripContent_3.setPaintFlags(flightStripContent_3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                flightStripContent_4.setPaintFlags(flightStripContent_4.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                //Color Text
                flightStripContent_1.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_2.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_4.setTextColor(getResources().getColor(R.color.purple_highlight));
                flightStripContent_6.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_7.setTextColor(getResources().getColor(R.color.purple_highlight));
                flightStripContent_8.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_9.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_11.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_12.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_13.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_14.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_15.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_17.setTextColor(getResources().getColor(R.color.black));
                break;
            case 5:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.VISIBLE);
                flightStripContent_5.setVisibility(View.INVISIBLE);
                flightStripContent_6.setVisibility(View.VISIBLE);
                flightStripContent_7.setVisibility(View.VISIBLE);
                flightStripContent_8.setVisibility(View.VISIBLE);
                flightStripContent_9.setVisibility(View.VISIBLE);
                flightStripContent_10.setVisibility(View.INVISIBLE);
                flightStripContent_11.setVisibility(View.INVISIBLE);
                flightStripContent_12.setVisibility(View.INVISIBLE);
                flightStripContent_13.setVisibility(View.INVISIBLE);
                flightStripContent_14.setVisibility(View.INVISIBLE);
                flightStripContent_15.setVisibility(View.INVISIBLE);
                flightStripContent_16.setVisibility(View.INVISIBLE);
                flightStripContent_17.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                flightStripContent_3.setPaintFlags(flightStripContent_3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                flightStripContent_4.setPaintFlags(flightStripContent_4.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                //Color Text
                flightStripContent_1.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_2.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_4.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_6.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_7.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_8.setTextColor(getResources().getColor(R.color.purple_highlight));
                flightStripContent_9.setTextColor(getResources().getColor(R.color.purple_highlight));
                flightStripContent_11.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_12.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_13.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_14.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_15.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_17.setTextColor(getResources().getColor(R.color.black));
                break;
            case 6:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.VISIBLE);
                flightStripContent_5.setVisibility(View.VISIBLE);
                flightStripContent_6.setVisibility(View.VISIBLE);
                flightStripContent_7.setVisibility(View.VISIBLE);
                flightStripContent_8.setVisibility(View.VISIBLE);
                flightStripContent_9.setVisibility(View.VISIBLE);
                flightStripContent_10.setVisibility(View.VISIBLE);
                flightStripContent_11.setVisibility(View.VISIBLE);
                flightStripContent_12.setVisibility(View.VISIBLE);
                flightStripContent_13.setVisibility(View.VISIBLE);
                flightStripContent_14.setVisibility(View.INVISIBLE);
                flightStripContent_15.setVisibility(View.INVISIBLE);
                flightStripContent_16.setVisibility(View.VISIBLE);
                flightStripContent_17.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                flightStripContent_3.setPaintFlags(flightStripContent_3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                flightStripContent_4.setPaintFlags(flightStripContent_4.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                //Color Text
                flightStripContent_1.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_2.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_4.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_6.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_7.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_8.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_9.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_11.setTextColor(getResources().getColor(R.color.purple_highlight));
                flightStripContent_12.setTextColor(getResources().getColor(R.color.purple_highlight));
                flightStripContent_13.setTextColor(getResources().getColor(R.color.purple_highlight));
                flightStripContent_14.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_15.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_17.setTextColor(getResources().getColor(R.color.black));
                break;
            case 7:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.VISIBLE);
                flightStripContent_5.setVisibility(View.VISIBLE);
                flightStripContent_6.setVisibility(View.VISIBLE);
                flightStripContent_7.setVisibility(View.VISIBLE);
                flightStripContent_8.setVisibility(View.VISIBLE);
                flightStripContent_9.setVisibility(View.VISIBLE);
                flightStripContent_10.setVisibility(View.VISIBLE);
                flightStripContent_11.setVisibility(View.VISIBLE);
                flightStripContent_12.setVisibility(View.VISIBLE);
                flightStripContent_13.setVisibility(View.VISIBLE);
                flightStripContent_14.setVisibility(View.VISIBLE);
                flightStripContent_15.setVisibility(View.VISIBLE);
                flightStripContent_16.setVisibility(View.VISIBLE);
                flightStripContent_17.setVisibility(View.VISIBLE);

                //Strikethrough Text
                flightStripContent_3.setPaintFlags(flightStripContent_3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                flightStripContent_4.setPaintFlags(flightStripContent_4.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                //Color Text
                flightStripContent_1.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_2.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_4.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_6.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_7.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_8.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_9.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_11.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_12.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_13.setTextColor(getResources().getColor(R.color.black));
                flightStripContent_14.setTextColor(getResources().getColor(R.color.purple_highlight));
                flightStripContent_15.setTextColor(getResources().getColor(R.color.purple_highlight));
                flightStripContent_17.setTextColor(getResources().getColor(R.color.purple_highlight));
                break;
        }
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

    private void handlePreviousClickedItems() {
        //Checks for Previously Clicked View and Layout
        if (previousClickedView == null && previousClickedLayout == null) {
            previousClickedView = clickedView;
            previousClickedLayout = clickedLayout;
        } else if (previousClickedView != null && previousClickedLayout != null) {
            if (previousClickedView != clickedView && previousClickedLayout != clickedLayout) {
                //Hides Clicked View and Layout
                hideVisibility(previousClickedView,previousClickedLayout);
                previousClickedView = clickedView;
                previousClickedLayout = clickedLayout;
            }
        }
    }

    private void audioController(View view, int audioFileID) {
        int visibility = view.getVisibility();
        if (visibility == View.GONE) {
            if (audioPlayer == null) {
                playAudio(audioFileID);
            } else {
                stopAudio();
                playAudio(audioFileID);
            }
        } else if (visibility == View.VISIBLE) {
            stopAudio();
        }
    }

    private void playAudio(int audioFileID) {
        //Creates MediaPlayer instance
        audioPlayer = MediaPlayer.create(this,audioFileID);

        //Releases MediaPlayer instance once Audio File has ended
        audioPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (audioProgressBar != null) {
                    audioProgressBar.setProgress(audioPlayer.getCurrentPosition());
                }
                stopAudio();
            }
        });

        //Plays the Audio File via MediaPlayer instance
        audioPlayer.start();

        //Audio Progress Bar
        audioProgressBar.setMax(audioPlayer.getDuration());

        //Audio Playback Timer Schedule
        audioTimer = new Timer();
        audioTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (audioProgressBar != null && audioPlayer != null) {
                    audioProgressBar.setProgress(audioPlayer.getCurrentPosition());
                }
            }
        },0,50);
    }

    private void stopAudio() {
        if (audioPlayer != null) {
            if (audioTimer != null) {
                audioTimer.cancel();
                audioTimer = null;
            }

            audioPlayer.release();
            audioPlayer = null;
        }
    }

    public void approachControl_arrival_expand1(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_1);
        audioController(view.findViewById(R.id.list_1),R.raw.arrival1);

        //Shows or Hides Clicked View and Layout
        clickedView = view.findViewById(R.id.list_1);
        clickedLayout = view.findViewById(R.id.departureLayout_1);
        setVisibility(clickedView,clickedLayout);

        //Handle Previously Clicked View and Layout
        handlePreviousClickedItems();

        //Adjust Flight Strip
        clickedItem = 1;
        manageFlightStrip(clickedItem);
    }

    public void approachControl_arrival_expand2(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_2);
        audioController(view.findViewById(R.id.list_2),R.raw.arrival2);

        //Shows or Hides Clicked View and Layout
        clickedView = view.findViewById(R.id.list_2);
        clickedLayout = view.findViewById(R.id.departureLayout_2);
        setVisibility(clickedView,clickedLayout);

        //Handle Previously Clicked View and Layout
        handlePreviousClickedItems();

        //Adjust Flight Strip
        clickedItem = 2;
        manageFlightStrip(clickedItem);
    }

    public void approachControl_arrival_expand3(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_3);
        audioController(view.findViewById(R.id.list_3),R.raw.arrival3);

        //Shows or Hides Clicked View and Layout
        clickedView = view.findViewById(R.id.list_3);
        clickedLayout = view.findViewById(R.id.departureLayout_3);
        setVisibility(clickedView,clickedLayout);

        //Handle Previously Clicked View and Layout
        handlePreviousClickedItems();

        //Adjust Flight Strip
        clickedItem = 3;
        manageFlightStrip(clickedItem);
    }

    public void approachControl_arrival_expand4(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_4);
        audioController(view.findViewById(R.id.list_4),R.raw.arrival4);

        //Shows or Hides Clicked View and Layout
        clickedView = view.findViewById(R.id.list_4);
        clickedLayout = view.findViewById(R.id.departureLayout_4);
        setVisibility(clickedView,clickedLayout);

        //Handle Previously Clicked View and Layout
        handlePreviousClickedItems();

        //Adjust Flight Strip
        clickedItem = 4;
        manageFlightStrip(clickedItem);
    }

    public void approachControl_arrival_expand5(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_5);
        audioController(view.findViewById(R.id.list_5),R.raw.arrival5);

        //Shows or Hides Clicked View and Layout
        clickedView = view.findViewById(R.id.list_5);
        clickedLayout = view.findViewById(R.id.departureLayout_5);
        setVisibility(clickedView,clickedLayout);

        //Handle Previously Clicked View and Layout
        handlePreviousClickedItems();

        //Adjust Flight Strip
        clickedItem = 5;
        manageFlightStrip(clickedItem);
    }

    public void approachControl_arrival_expand6(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_6);
        audioController(view.findViewById(R.id.list_6),R.raw.arrival6);

        //Shows or Hides Clicked View and Layout
        clickedView = view.findViewById(R.id.list_6);
        clickedLayout = view.findViewById(R.id.departureLayout_6);
        setVisibility(clickedView,clickedLayout);

        //Handle Previously Clicked View and Layout
        handlePreviousClickedItems();

        //Adjust Flight Strip
        clickedItem = 6;
        manageFlightStrip(clickedItem);
    }

    public void approachControl_arrival_expand7(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_7);
        audioController(view.findViewById(R.id.list_7),R.raw.arrival7);

        //Shows or Hides Clicked View and Layout
        clickedView = view.findViewById(R.id.list_7);
        clickedLayout = view.findViewById(R.id.departureLayout_7);
        setVisibility(clickedView,clickedLayout);

        //Handle Previously Clicked View and Layout
        handlePreviousClickedItems();

        //Adjust Flight Strip
        clickedItem = 7;
        manageFlightStrip(clickedItem);
    }

    public void approachControl_arrival_back(View view) {
        onBackPressed();
    }

    public void approachControl_arrival_flightStrip(View view) {
        //Shows or Hides Flight Strip
        setVisibility(findViewById(R.id.departureFSContent),findViewById(R.id.departureFSLayout));
    }

    public void approachControl_arrival_pseudoTMA(View view) {
        startActivity(new Intent(this, PseudoTMA.class));
    }



}