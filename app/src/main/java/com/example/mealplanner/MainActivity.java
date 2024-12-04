package com.example.mealplanner;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import android.widget.CalendarView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    CalendarView calendar;
    TextView date_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Reference to the TextView
        TextView tvDate = findViewById(R.id.tvDate);

        // Get today's date using LocalDate
        LocalDate today = LocalDate.now();

        // Format the date (e.g., "December 2, 2024")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd");
        String formattedDate = today.format(formatter);

        // Set the formatted date to the TextView
        tvDate.setText(formattedDate);
    }
}