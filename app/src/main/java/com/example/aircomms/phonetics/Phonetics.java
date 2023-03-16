package com.example.aircomms.phonetics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircomms.R;
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
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
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
        itemsArrayList.add(new Items("B", "Bravo", "Brah-voh"));
        itemsArrayList.add(new Items("C", "Charlie", "Char-lee"));
        itemsArrayList.add(new Items("D", "Delta", "Dell-tah"));
        itemsArrayList.add(new Items("E", "Echo", "Eck-oh"));
        itemsArrayList.add(new Items("F", "Foxtrot", "Foks-trot"));
        itemsArrayList.add(new Items("G", "Golf", "Golf"));
        itemsArrayList.add(new Items("H", "Hotel", "Hoh-tell"));
        itemsArrayList.add(new Items("I", "India", "In-dee-ah"));
        itemsArrayList.add(new Items("J", "Juliett", "Jew-lee-ett"));
        itemsArrayList.add(new Items("K", "Kilo", "Key-loh"));
        itemsArrayList.add(new Items("L", "Lima", "Lee-mah"));
        itemsArrayList.add(new Items("M", "Mike", "Mike"));
        itemsArrayList.add(new Items("N", "November", "No-vem-ber"));
        itemsArrayList.add(new Items("O", "Oscar", "Oss-cah"));
        itemsArrayList.add(new Items("P", "Papa", "Pah-pah"));
        itemsArrayList.add(new Items("Q", "Quebec", "Key-beck"));
        itemsArrayList.add(new Items("R", "Romeo", "Row-me-oh"));
        itemsArrayList.add(new Items("S", "Sierra", "See-air-rah"));
        itemsArrayList.add(new Items("T", "Tango", "Tang-go"));
        itemsArrayList.add(new Items("U", "Uniform", "You-nee-form"));
        itemsArrayList.add(new Items("V", "Victor", "Vik-tah"));
        itemsArrayList.add(new Items("W", "Whiskey", "Wiss-key"));
        itemsArrayList.add(new Items("X", "X-ray", "Ecks-ray"));
        itemsArrayList.add(new Items("Y", "Yankee", "Yang-key"));
        itemsArrayList.add(new Items("Z", "Zulu", "Zoo-loo"));
        itemsArrayList.add(new Items("0", "Zero", "Ze-ro"));
        itemsArrayList.add(new Items("1", "One", "Wun"));
        itemsArrayList.add(new Items("2", "Two", "Too"));
        itemsArrayList.add(new Items("3", "Three", "Tree"));
        itemsArrayList.add(new Items("4", "Four", "Fow-er"));
        itemsArrayList.add(new Items("5", "Five", "Fife"));
        itemsArrayList.add(new Items("6", "Six", "Six"));
        itemsArrayList.add(new Items("7", "Seven", "Sev-en"));
        itemsArrayList.add(new Items("8", "Eight", "Ait"));
        itemsArrayList.add(new Items("9", "Nine", "Nin-er"));





    }

}