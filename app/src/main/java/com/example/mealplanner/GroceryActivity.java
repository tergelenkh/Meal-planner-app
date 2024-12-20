package com.example.mealplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GroceryActivity extends AppCompatActivity {

    private RecyclerView rvGroceryList;
    private GroceryAdapter groceryAdapter;
    private List<String> groceryItems;
    private Button btnClearList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        // Initialize views
        rvGroceryList = findViewById(R.id.rvGroceryList);
        btnClearList = findViewById(R.id.btnClearList);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("GroceryList", MODE_PRIVATE);

        // Load grocery list from SharedPreferences
        groceryItems = loadGroceryList();

        // Set up RecyclerView
        groceryAdapter = new GroceryAdapter(groceryItems);
        rvGroceryList.setLayoutManager(new LinearLayoutManager(this));
        rvGroceryList.setAdapter(groceryAdapter);

        // Clear grocery list
        btnClearList.setOnClickListener(v -> clearGroceryList());
    }

    /**
     * Load the grocery list from SharedPreferences.
     */
    private List<String> loadGroceryList() {
        List<String> items = new ArrayList<>();
        int size = sharedPreferences.getInt("size", 0);
        for (int i = 0; i < size; i++) {
            String item = sharedPreferences.getString("item_" + i, null);
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

    /**
     * Clear the grocery list and update the RecyclerView.
     */
    private void clearGroceryList() {
        // Clear SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Clear the local list
        groceryItems.clear();
        groceryAdapter.notifyDataSetChanged();

        // Show a confirmation message
        Toast.makeText(this, "Grocery list cleared!", Toast.LENGTH_SHORT).show();
    }
}
