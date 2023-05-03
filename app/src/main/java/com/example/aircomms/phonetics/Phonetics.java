package com.example.aircomms.phonetics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aircomms.Bookmark.BookmarkItem;
import com.example.aircomms.MainActivity;
import com.example.aircomms.R;
import com.example.aircomms.SharedPref;
import com.example.aircomms.airlineCode.Africa.Africa;
import com.example.aircomms.airlineCode.AirlineCode;
import com.example.aircomms.airlineCode.Asia.Asia;
import com.example.aircomms.phonetics.Items;
import com.example.aircomms.phonetics.PhoneticsAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Phonetics extends AppCompatActivity {

    private ArrayList<Items> itemsArrayList;
    private RecyclerView recyclerView;
    private PhoneticsAdapter adapter;
    private EditText searchEditText;


    private ImageButton bookmarkButton;
    private String airportTitle = "Phonetics";
    private String airportDescription = "This is the Phonetics";
    private boolean isBookmarked = false;


    SharedPref sharedPref;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);

        if (sharedPref.loadNightModeState()){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Phonetics.this, R.color.bg));
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Phonetics.this, R.color.bg));
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonetics);

        recyclerView = findViewById(R.id.rvPhonetics);
        itemsArrayList = new ArrayList<>();

        setAdapter();
        setCharInfo();
        history();
        search();



    }

    private void saveBookmarkItems(List<BookmarkItem> bookmarkItems) {
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookmarkItems);
        editor.putString("bookmarkItems", json);
        editor.apply();
    }

    private List<BookmarkItem> loadBookmarkItems() {
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("bookmarkItems", "");
        Type type = new TypeToken<List<BookmarkItem>>() {}.getType();
        List<BookmarkItem> bookmarkItems = gson.fromJson(json, type);
        if (bookmarkItems == null) {
            bookmarkItems = new ArrayList<>();
        }
        return bookmarkItems;
    
    }

    public void bookmarkButtonClicked(View view) {
        // Toggle bookmark state
        isBookmarked = !isBookmarked;

        // Set bookmark button drawable
        ImageView bookmarkButton = (ImageView) view;
        bookmarkButton.setImageResource(isBookmarked ? R.drawable.bookmark_outline : R.drawable.bookmark_filled);

        // Update bookmark state in SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("BookmarkState", Context.MODE_PRIVATE);
        if (isBookmarked) {
            // Add airport to bookmark list
            sharedPref.edit().putBoolean("phonetics", true).apply();
        } else {
            // Remove airport from bookmark list
            sharedPref.edit().remove("phonetics").apply();
        }

        // Show a Toast message to inform the user of the bookmark state
        String message = isBookmarked ? "Bookmarked!" : "Unbookmarked";
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


        // Save bookmark items
        List<BookmarkItem> bookmarkItems = loadBookmarkItems();
        saveBookmarkItems(bookmarkItems);
    }


    private void search() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchText = s.toString().trim().toLowerCase();
                ArrayList<Items> filteredList = new ArrayList<>();
                for (Items item : itemsArrayList) {
                    if (item.getCharacter().toLowerCase().contains(searchText) ||
                            item.getTelephony().toLowerCase().contains(searchText) ||
                            item.getPhonic().toLowerCase().contains(searchText)) {
                        filteredList.add(item);
                    }
                }
                adapter.filterList(filteredList);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setAdapter (){
        searchEditText = findViewById(R.id.search);
        adapter = new PhoneticsAdapter(itemsArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void history() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Phonetics", true);
        editor.putLong("Phonetics", System.currentTimeMillis());
        editor.apply();

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }



    private void  setCharInfo (){
        itemsArrayList.add(new Items("A", "Alpha", "Al-fha", R.raw.alpha1));
        itemsArrayList.add(new Items("B", "Bravo", "Brah-voh", R.raw.bravo));
        itemsArrayList.add(new Items("C", "Charlie", "Char-lee", R.raw.charlie));
        itemsArrayList.add(new Items("D", "Delta", "Dell-tah", R.raw.delta));
        itemsArrayList.add(new Items("E", "Echo", "Eck-oh", R.raw.echo));
        itemsArrayList.add(new Items("F", "Foxtrot", "Foks-trot", R.raw.foxtrot));
        itemsArrayList.add(new Items("G", "Golf", "Golf", R.raw.golf));
        itemsArrayList.add(new Items("H", "Hotel", "Hoh-tell", R.raw.hotel));
        itemsArrayList.add(new Items("I", "India", "In-dee-ah", R.raw.india));
        itemsArrayList.add(new Items("J", "Juliett", "Jew-lee-ett", R.raw.juliett));
        itemsArrayList.add(new Items("K", "Kilo", "Key-loh", R.raw.kilo));
        itemsArrayList.add(new Items("L", "Lima", "Lee-mah", R.raw.lima));
        itemsArrayList.add(new Items("M", "Mike", "Mike", R.raw.mike));
        itemsArrayList.add(new Items("N", "November", "No-vem-ber", R.raw.november));
        itemsArrayList.add(new Items("O", "Oscar", "Oss-cah", R.raw.oscar));
        itemsArrayList.add(new Items("P", "Papa", "Pah-pah", R.raw.papa));
        itemsArrayList.add(new Items("Q", "Quebec", "Key-beck", R.raw.quebec));
        itemsArrayList.add(new Items("R", "Romeo", "Row-me-oh", R.raw.romeo));
        itemsArrayList.add(new Items("S", "Sierra", "See-air-rah", R.raw.sierra));
        itemsArrayList.add(new Items("T", "Tango", "Tang-go", R.raw.tango));
        itemsArrayList.add(new Items("U", "Uniform", "You-nee-form", R.raw.uniform));
        itemsArrayList.add(new Items("V", "Victor", "Vik-tah", R.raw.victor));
        itemsArrayList.add(new Items("W", "Whiskey", "Wiss-key", R.raw.whiskey));
        itemsArrayList.add(new Items("X", "X-ray", "Ecks-ray", R.raw.xray));
        itemsArrayList.add(new Items("Y", "Yankee", "Yang-key", R.raw.yankee));
        itemsArrayList.add(new Items("Z", "Zulu", "Zoo-loo", R.raw.zulu));
        itemsArrayList.add(new Items("0", "Zero", "Ze-ro", R.raw.zero));
        itemsArrayList.add(new Items("1", "One", "Wun", R.raw.one));
        itemsArrayList.add(new Items("2", "Two", "Too", R.raw.two));
        itemsArrayList.add(new Items("3", "Three", "Tree", R.raw.three));
        itemsArrayList.add(new Items("4", "Four", "Fow-er", R.raw.four));
        itemsArrayList.add(new Items("5", "Five", "Fife", R.raw.five));
        itemsArrayList.add(new Items("6", "Six", "Six", R.raw.six));
        itemsArrayList.add(new Items("7", "Seven", "Sev-en", R.raw.seven));
        itemsArrayList.add(new Items("8", "Eight", "Ait", R.raw.eight));
        itemsArrayList.add(new Items("9", "Nine", "Nin-er", R.raw.nine));

    }

    public void phonetics_back(View view) {
        startActivity(new Intent(Phonetics.this, MainActivity.class));
    }


}