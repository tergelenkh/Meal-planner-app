package com.example.mealplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnPlanner, btnGrocery, btnSettings;
    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        tvDate = findViewById(R.id.tvDate);
        btnPlanner = findViewById(R.id.btnPlanner);
        btnGrocery = findViewById(R.id.btnGrocery);
        btnSettings = findViewById(R.id.btnSettings);

        // Display today's date
        displayTodaysDate();

        // Navigate to Planner Screen
        btnPlanner.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(MainActivity.this, PlannerActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        // Navigate to Grocery Screen
        btnGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GroceryActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to Profile/Settings Screen
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Display today's date in the TextView
     */
    private void displayTodaysDate() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("MMMM dd");
        String formattedDate = formatter.format(calendar.getTime());
        tvDate.setText(formattedDate);
    }

}
