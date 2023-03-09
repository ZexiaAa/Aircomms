package com.example.aircomms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircomms.phonetics.Items;
import com.example.aircomms.phonetics.PhoneticsAdapter;

import java.util.ArrayList;

public class Phonetics extends AppCompatActivity {

    private ArrayList<Items> itemsArrayList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonetics);

        recyclerView = findViewById(R.id.rvPhonetics);
        itemsArrayList = new ArrayList<>();

        setAdapter();
        setCharInfo();

    }

    private void setAdapter (){
        PhoneticsAdapter adapter = new PhoneticsAdapter(itemsArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void  setCharInfo (){
        itemsArrayList.add(new Items("A", "Alpha", "Al-fha"));
        itemsArrayList.add(new Items("B", "Bravo", "Bra-vo"));
        itemsArrayList.add(new Items("A", "Alpha", "Al-fha"));
        itemsArrayList.add(new Items("B", "Bravo", "Bra-vo"));
        itemsArrayList.add(new Items("A", "Alpha", "Al-fha"));
        itemsArrayList.add(new Items("B", "Bravo", "Bra-vo"));
        itemsArrayList.add(new Items("A", "Alpha", "Al-fha"));
        itemsArrayList.add(new Items("B", "Bravo", "Bra-vo"));
        itemsArrayList.add(new Items("A", "Alpha", "Al-fha"));
        itemsArrayList.add(new Items("B", "Bravo", "Bra-vo"));
        itemsArrayList.add(new Items("B", "Bravo", "Bra-vo"));
        itemsArrayList.add(new Items("A", "Alpha", "Al-fha"));
        itemsArrayList.add(new Items("B", "Bravo", "Bra-vo"));

    }

}