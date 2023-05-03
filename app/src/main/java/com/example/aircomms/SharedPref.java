package com.example.aircomms;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    public final SharedPreferences sharedPreferences;

    public SharedPref(Context context){
        sharedPreferences = context.getSharedPreferences("night", Context.MODE_PRIVATE);
    }

    public void setNightModeState(Boolean state){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NightMode", state);
        editor.apply();
    }

    public Boolean loadNightModeState(){
        return sharedPreferences.getBoolean("NightMode", false);
    }
}
