package com.example.aircomms.airlineCode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.aircomms.MainActivity;
import com.example.aircomms.R;
import com.example.aircomms.airlineCode.Africa.Africa;
import com.example.aircomms.airlineCode.Asia.Asia;
import com.example.aircomms.airlineCode.Australia.Australia;
import com.example.aircomms.airlineCode.Europe.Europe;
import com.example.aircomms.airlineCode.Noth.North;
import com.example.aircomms.airlineCode.South.South;

public class AirlineCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airline_code);

    }

    public void asia(View view) {
        startActivity(new Intent(AirlineCode.this, Asia.class));
    }

    public void asia_back(View view) {
        startActivity(new Intent(AirlineCode.this,MainActivity.class));
    }

    public void africa(View view) {

        Intent intent = new Intent(this, Africa.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    public void australia(View view) {
        startActivity(new Intent(AirlineCode.this, Australia.class));
    }

    public void europe(View view) {
        startActivity(new Intent(AirlineCode.this, Europe.class));
    }

    public void north(View view) {
        startActivity(new Intent(AirlineCode.this, North.class));
    }

    public void south(View view) {
        startActivity(new Intent(AirlineCode.this, South.class));
    }
}