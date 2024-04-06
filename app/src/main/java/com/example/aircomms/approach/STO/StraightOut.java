package com.example.aircomms.approach.STO;

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
import com.example.aircomms.approach.PseudoTMA;

import java.util.Timer;
import java.util.TimerTask;

public class StraightOut extends AppCompatActivity {

    SharedPref sharedPref;
    MediaPlayer audioPlayer;
    Timer audioTimer;
    ProgressBar audioProgressBar;
    View previousClickedView, clickedView;
    LinearLayout previousClickedLayout, clickedLayout;
    View flightStripContent_1, flightStripContent_2, flightStripContent_3, flightStripContent_4,
            flightStripContent_5, flightStripContent_6, flightStripContent_7, flightStripContent_8,
            flightStripContent_9, flightStripContent_10, flightStripContent_11, flightStripContent_12,
            flightStripContent_13, flightStripContent_14, flightStripContent_15, flightStripContent_16,
            flightStripContent_17, flightStripContent_18, flightStripContent_19, flightStripContent_20,
            flightStripContent_21, flightStripContent_22;
    int clickedItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(StraightOut.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(StraightOut.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sto);


        //Setup Views for Flight Strip
        flightStripContent_1 = findViewById(R.id.flightStripContent_1);
        flightStripContent_2 = findViewById(R.id.flightStripContent_2);
        flightStripContent_3 = findViewById(R.id.flightStripContent_3);
        flightStripContent_4 = findViewById(R.id.flightStripContent_4);
        flightStripContent_5 = findViewById(R.id.flightStripContent_5);
        flightStripContent_6 = findViewById(R.id.flightStripContent_6);
        flightStripContent_7 = findViewById(R.id.flightStripContent_7);
        flightStripContent_8 = findViewById(R.id.flightStripContent_8);
        flightStripContent_9 = findViewById(R.id.flightStripContent_9);
        flightStripContent_10 = findViewById(R.id.flightStripContent_10);
        flightStripContent_11 = findViewById(R.id.flightStripContent_11);
        flightStripContent_12 = findViewById(R.id.flightStripContent_12);
        flightStripContent_13 = findViewById(R.id.flightStripContent_13);
        flightStripContent_14 = findViewById(R.id.flightStripContent_14);
        flightStripContent_15 = findViewById(R.id.flightStripContent_15);
        flightStripContent_16 = findViewById(R.id.flightStripContent_16);
        flightStripContent_17 = findViewById(R.id.flightStripContent_17);
        flightStripContent_18 = findViewById(R.id.flightStripContent_18);
        flightStripContent_19 = findViewById(R.id.flightStripContent_19);
        flightStripContent_20 = findViewById(R.id.flightStripContent_20);
        flightStripContent_21 = findViewById(R.id.flightStripContent_21);
        flightStripContent_22 = findViewById(R.id.flightStripContent_22);

        //Manage Flight Strip upon onCreate
        manageFlightStrip(clickedItem);


        history();
    }

    private void history() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Approach Control \nStraight-Out Departure", true);
        editor.putLong("Approach Control \nStraight-Out Departure", System.currentTimeMillis());
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAudio();
        if (audioTimer != null) {
            audioTimer.cancel();
            audioTimer = null;
        }
    }

    private void manageFlightStrip(int clickedItem) {
        //TextViews to Strikethrough
        TextView text_8 = (TextView) flightStripContent_8;
        TextView text_9 = (TextView) flightStripContent_9;
        TextView text_11 = (TextView) flightStripContent_11;

        switch (clickedItem) {
            case 0:
            case 1:
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
                flightStripContent_18.setVisibility(View.INVISIBLE);
                flightStripContent_19.setVisibility(View.INVISIBLE);
                flightStripContent_20.setVisibility(View.INVISIBLE);
                flightStripContent_21.setVisibility(View.INVISIBLE);
                flightStripContent_22.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                text_8.setPaintFlags(text_8.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                text_9.setPaintFlags(text_9.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                text_11.setPaintFlags(text_11.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
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
                flightStripContent_13.setVisibility(View.VISIBLE);
                flightStripContent_14.setVisibility(View.VISIBLE);
                flightStripContent_15.setVisibility(View.INVISIBLE);
                flightStripContent_16.setVisibility(View.INVISIBLE);
                flightStripContent_17.setVisibility(View.INVISIBLE);
                flightStripContent_18.setVisibility(View.INVISIBLE);
                flightStripContent_19.setVisibility(View.INVISIBLE);
                flightStripContent_20.setVisibility(View.INVISIBLE);
                flightStripContent_21.setVisibility(View.INVISIBLE);
                flightStripContent_22.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                text_8.setPaintFlags(text_8.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                text_9.setPaintFlags(text_9.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                text_11.setPaintFlags(text_11.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                break;
            case 3:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.VISIBLE);
                flightStripContent_5.setVisibility(View.INVISIBLE);
                flightStripContent_6.setVisibility(View.INVISIBLE);
                flightStripContent_7.setVisibility(View.VISIBLE);
                flightStripContent_8.setVisibility(View.VISIBLE);
                flightStripContent_9.setVisibility(View.INVISIBLE);
                flightStripContent_10.setVisibility(View.INVISIBLE);
                flightStripContent_11.setVisibility(View.INVISIBLE);
                flightStripContent_12.setVisibility(View.INVISIBLE);
                flightStripContent_13.setVisibility(View.VISIBLE);
                flightStripContent_14.setVisibility(View.VISIBLE);
                flightStripContent_15.setVisibility(View.VISIBLE);
                flightStripContent_16.setVisibility(View.VISIBLE);
                flightStripContent_17.setVisibility(View.VISIBLE);
                flightStripContent_18.setVisibility(View.INVISIBLE);
                flightStripContent_19.setVisibility(View.INVISIBLE);
                flightStripContent_20.setVisibility(View.INVISIBLE);
                flightStripContent_21.setVisibility(View.INVISIBLE);
                flightStripContent_22.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                text_8.setPaintFlags(text_8.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                text_9.setPaintFlags(text_9.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                text_11.setPaintFlags(text_11.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                break;
            case 4:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.VISIBLE);
                flightStripContent_5.setVisibility(View.INVISIBLE);
                flightStripContent_6.setVisibility(View.INVISIBLE);
                flightStripContent_7.setVisibility(View.VISIBLE);
                flightStripContent_8.setVisibility(View.VISIBLE);
                flightStripContent_9.setVisibility(View.INVISIBLE);
                flightStripContent_10.setVisibility(View.INVISIBLE);
                flightStripContent_11.setVisibility(View.VISIBLE);
                flightStripContent_12.setVisibility(View.INVISIBLE);
                flightStripContent_13.setVisibility(View.VISIBLE);
                flightStripContent_14.setVisibility(View.VISIBLE);
                flightStripContent_15.setVisibility(View.VISIBLE);
                flightStripContent_16.setVisibility(View.VISIBLE);
                flightStripContent_17.setVisibility(View.VISIBLE);
                flightStripContent_18.setVisibility(View.VISIBLE);
                flightStripContent_19.setVisibility(View.VISIBLE);
                flightStripContent_20.setVisibility(View.INVISIBLE);
                flightStripContent_21.setVisibility(View.INVISIBLE);
                flightStripContent_22.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                text_8.setPaintFlags(text_8.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                text_9.setPaintFlags(text_9.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                text_11.setPaintFlags(text_11.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                break;
            case 5:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.VISIBLE);
                flightStripContent_5.setVisibility(View.INVISIBLE);
                flightStripContent_6.setVisibility(View.INVISIBLE);
                flightStripContent_7.setVisibility(View.VISIBLE);
                flightStripContent_8.setVisibility(View.VISIBLE);
                flightStripContent_9.setVisibility(View.VISIBLE);
                flightStripContent_10.setVisibility(View.INVISIBLE);
                flightStripContent_11.setVisibility(View.VISIBLE);
                flightStripContent_12.setVisibility(View.INVISIBLE);
                flightStripContent_13.setVisibility(View.VISIBLE);
                flightStripContent_14.setVisibility(View.VISIBLE);
                flightStripContent_15.setVisibility(View.VISIBLE);
                flightStripContent_16.setVisibility(View.VISIBLE);
                flightStripContent_17.setVisibility(View.VISIBLE);
                flightStripContent_18.setVisibility(View.VISIBLE);
                flightStripContent_19.setVisibility(View.VISIBLE);
                flightStripContent_20.setVisibility(View.VISIBLE);
                flightStripContent_21.setVisibility(View.INVISIBLE);
                flightStripContent_22.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                text_8.setPaintFlags(text_8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text_9.setPaintFlags(text_9.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                text_11.setPaintFlags(text_11.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                break;
            case 6:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.VISIBLE);
                flightStripContent_5.setVisibility(View.INVISIBLE);
                flightStripContent_6.setVisibility(View.INVISIBLE);
                flightStripContent_7.setVisibility(View.VISIBLE);
                flightStripContent_8.setVisibility(View.VISIBLE);
                flightStripContent_9.setVisibility(View.VISIBLE);
                flightStripContent_10.setVisibility(View.VISIBLE);
                flightStripContent_11.setVisibility(View.VISIBLE);
                flightStripContent_12.setVisibility(View.INVISIBLE);
                flightStripContent_13.setVisibility(View.VISIBLE);
                flightStripContent_14.setVisibility(View.VISIBLE);
                flightStripContent_15.setVisibility(View.VISIBLE);
                flightStripContent_16.setVisibility(View.VISIBLE);
                flightStripContent_17.setVisibility(View.VISIBLE);
                flightStripContent_18.setVisibility(View.VISIBLE);
                flightStripContent_19.setVisibility(View.VISIBLE);
                flightStripContent_20.setVisibility(View.VISIBLE);
                flightStripContent_21.setVisibility(View.INVISIBLE);
                flightStripContent_22.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                text_8.setPaintFlags(text_8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text_9.setPaintFlags(text_9.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text_11.setPaintFlags(text_11.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                break;
            case 7:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.VISIBLE);
                flightStripContent_5.setVisibility(View.INVISIBLE);
                flightStripContent_6.setVisibility(View.INVISIBLE);
                flightStripContent_7.setVisibility(View.VISIBLE);
                flightStripContent_8.setVisibility(View.VISIBLE);
                flightStripContent_9.setVisibility(View.VISIBLE);
                flightStripContent_10.setVisibility(View.VISIBLE);
                flightStripContent_11.setVisibility(View.VISIBLE);
                flightStripContent_12.setVisibility(View.INVISIBLE);
                flightStripContent_13.setVisibility(View.VISIBLE);
                flightStripContent_14.setVisibility(View.VISIBLE);
                flightStripContent_15.setVisibility(View.VISIBLE);
                flightStripContent_16.setVisibility(View.VISIBLE);
                flightStripContent_17.setVisibility(View.VISIBLE);
                flightStripContent_18.setVisibility(View.VISIBLE);
                flightStripContent_19.setVisibility(View.VISIBLE);
                flightStripContent_20.setVisibility(View.VISIBLE);
                flightStripContent_21.setVisibility(View.VISIBLE);
                flightStripContent_22.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                text_8.setPaintFlags(text_8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text_9.setPaintFlags(text_9.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text_11.setPaintFlags(text_11.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                break;
            case 8:
                flightStripContent_1.setVisibility(View.VISIBLE);
                flightStripContent_2.setVisibility(View.VISIBLE);
                flightStripContent_3.setVisibility(View.VISIBLE);
                flightStripContent_4.setVisibility(View.VISIBLE);
                flightStripContent_5.setVisibility(View.VISIBLE);
                flightStripContent_6.setVisibility(View.INVISIBLE);
                flightStripContent_7.setVisibility(View.VISIBLE);
                flightStripContent_8.setVisibility(View.VISIBLE);
                flightStripContent_9.setVisibility(View.VISIBLE);
                flightStripContent_10.setVisibility(View.VISIBLE);
                flightStripContent_11.setVisibility(View.VISIBLE);
                flightStripContent_12.setVisibility(View.INVISIBLE);
                flightStripContent_13.setVisibility(View.VISIBLE);
                flightStripContent_14.setVisibility(View.VISIBLE);
                flightStripContent_15.setVisibility(View.VISIBLE);
                flightStripContent_16.setVisibility(View.VISIBLE);
                flightStripContent_17.setVisibility(View.VISIBLE);
                flightStripContent_18.setVisibility(View.VISIBLE);
                flightStripContent_19.setVisibility(View.VISIBLE);
                flightStripContent_20.setVisibility(View.VISIBLE);
                flightStripContent_21.setVisibility(View.VISIBLE);
                flightStripContent_22.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                text_8.setPaintFlags(text_8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text_9.setPaintFlags(text_9.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text_11.setPaintFlags(text_11.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                break;
            case 9:
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
                flightStripContent_18.setVisibility(View.VISIBLE);
                flightStripContent_19.setVisibility(View.VISIBLE);
                flightStripContent_20.setVisibility(View.VISIBLE);
                flightStripContent_21.setVisibility(View.VISIBLE);
                flightStripContent_22.setVisibility(View.INVISIBLE);

                //Strikethrough Text
                text_8.setPaintFlags(text_8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text_9.setPaintFlags(text_9.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text_11.setPaintFlags(text_11.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                break;
            case 10:
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
                flightStripContent_18.setVisibility(View.VISIBLE);
                flightStripContent_19.setVisibility(View.VISIBLE);
                flightStripContent_20.setVisibility(View.VISIBLE);
                flightStripContent_21.setVisibility(View.VISIBLE);
                flightStripContent_22.setVisibility(View.VISIBLE);

                //Strikethrough Text
                text_8.setPaintFlags(text_8.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text_9.setPaintFlags(text_9.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                text_11.setPaintFlags(text_11.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
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

    public void approachControl_sto_expand1(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_1);
        audioController(view.findViewById(R.id.list_1),R.raw.sto1);

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

    public void approachControl_sto_expand2(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_2);
        audioController(view.findViewById(R.id.list_2),R.raw.sto2);

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

    public void approachControl_sto_expand3(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_3);
        audioController(view.findViewById(R.id.list_3),R.raw.sto3);

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

    public void approachControl_sto_expand4(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_4);
        audioController(view.findViewById(R.id.list_4),R.raw.sto4);

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

    public void approachControl_sto_expand5(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_5);
        audioController(view.findViewById(R.id.list_5),R.raw.sto5);

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

    public void approachControl_sto_expand6(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_6);
        audioController(view.findViewById(R.id.list_6),R.raw.sto6);

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

    public void approachControl_sto_expand7(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_7);
        audioController(view.findViewById(R.id.list_7),R.raw.sto7);

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

    public void approachControl_sto_expand8(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_8);
        audioController(view.findViewById(R.id.list_8),R.raw.sto8);

        //Shows or Hides Clicked View and Layout
        clickedView = view.findViewById(R.id.list_8);
        clickedLayout = view.findViewById(R.id.departureLayout_8);
        setVisibility(clickedView,clickedLayout);

        //Handle Previously Clicked View and Layout
        handlePreviousClickedItems();

        //Adjust Flight Strip
        clickedItem = 8;
        manageFlightStrip(clickedItem);
    }

    public void approachControl_sto_expand9(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_9);
        audioController(view.findViewById(R.id.list_9),R.raw.sto9);

        //Shows or Hides Clicked View and Layout
        clickedView = view.findViewById(R.id.list_9);
        clickedLayout = view.findViewById(R.id.departureLayout_9);
        setVisibility(clickedView,clickedLayout);

        //Handle Previously Clicked View and Layout
        handlePreviousClickedItems();

        //Adjust Flight Strip
        clickedItem = 9;
        manageFlightStrip(clickedItem);
    }

    public void approachControl_sto_expand10(View view) {
        //Controls Audio
        audioProgressBar = view.findViewById(R.id.progressBar_10);
        audioController(view.findViewById(R.id.list_10),R.raw.sto10);

        //Shows or Hides Clicked View and Layout
        clickedView = view.findViewById(R.id.list_10);
        clickedLayout = view.findViewById(R.id.departureLayout_10);
        setVisibility(clickedView,clickedLayout);

        //Handle Previously Clicked View and Layout
        handlePreviousClickedItems();

        //Adjust Flight Strip
        clickedItem = 10;
        manageFlightStrip(clickedItem);
    }

    public void approachControl_sto_back(View view) {
        onBackPressed();
    }

    public void approachControl_sto_flightStrip(View view) {
        //Shows or Hides Flight Strip
        setVisibility(findViewById(R.id.departureFSContent),findViewById(R.id.departureFSLayout));
    }

    public void approachControl_sto_pseudoTMA(View view) {
        startActivity(new Intent(this, PseudoTMA.class));
    }
}