package com.example.aircomms.Bookmark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aircomms.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BookmarkActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BookmarkAdapter adapter;
    private Set<BookmarkItem> itemSet;

    private static final String PREFS_NAME = "BookmarkPrefs";
    private static final String ITEM_SET_KEY = "itemSet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemSet = loadBookmarkItems();
        adapter = new BookmarkAdapter(new ArrayList<>(itemSet));
        recyclerView.setAdapter(adapter);
    }

    public void addToBookmark(String title, String description) {
        // Check if the item already exists in the bookmark set
        BookmarkItem newItem = new BookmarkItem(title, description);
        if (itemSet.contains(newItem)) {
            // If the item already exists, remove it
            itemSet.remove(newItem);
        }

        // Add the new item to the beginning of the bookmark set
        itemSet.add(newItem);
        adapter.notifyDataSetChanged();

        // Save the updated bookmark set to Shared Preferences
        saveBookmarkItems(itemSet);
    }

    private Set<BookmarkItem> loadBookmarkItems() {
        // Load the bookmark items from Shared Preferences
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString(ITEM_SET_KEY, "");
        Set<BookmarkItem> bookmarkItems = new HashSet<>();
        if (!json.isEmpty()) {
            try {
                Type type = new TypeToken<Set<BookmarkItem>>() {}.getType();
                bookmarkItems = gson.fromJson(json, type);
            } catch (Exception e) {
                // Handle exception
            }
        }
        return bookmarkItems;
    }

    private void saveBookmarkItems(Set<BookmarkItem> bookmarkItems) {
        // Save the bookmark items to Shared Preferences
        SharedPreferences sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookmarkItems);
        editor.putString(ITEM_SET_KEY, json);
        editor.apply();
    }
}
