package com.example.mealplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import api.ChatGptApi;
import api.ChatGptRequest;
import api.ChatGptResponse;
import api.Message;
import api.RetrofitClient;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlannerActivity extends AppCompatActivity {

    private EditText etBreakfast, etLunch, etDinner, etSnack;
    private Button btnSaveMealPlan, btnGenerateGroceryList, btnViewGroceryList;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

        // Initialize views
        etBreakfast = findViewById(R.id.etBreakfast);
        etLunch = findViewById(R.id.etLunch);
        etDinner = findViewById(R.id.etDinner);
        etSnack = findViewById(R.id.etSnack);
        btnSaveMealPlan = findViewById(R.id.btnSaveMealPlan);
        btnGenerateGroceryList = findViewById(R.id.btnGenerateGroceryList);
        btnViewGroceryList = findViewById(R.id.btnViewGroceryList);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MealPlans", MODE_PRIVATE);

        // Save Meal Plan
        btnSaveMealPlan.setOnClickListener(v -> saveMealPlan());

        // Generate Grocery List
        btnGenerateGroceryList.setOnClickListener(v -> fetchIngredientsFromMealPlan());

        // View Grocery List
        btnViewGroceryList.setOnClickListener(v -> {
            Intent intent = new Intent(PlannerActivity.this, GroceryActivity.class);
            startActivity(intent);
        });

        // Load saved meal plans (optional)
        loadMealPlan();
    }

    /**
     * Save the meal plan to SharedPreferences.
     */
    private void saveMealPlan() {
        String breakfast = etBreakfast.getText().toString();
        String lunch = etLunch.getText().toString();
        String dinner = etDinner.getText().toString();
        String snack = etSnack.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("breakfast", breakfast);
        editor.putString("lunch", lunch);
        editor.putString("dinner", dinner);
        editor.putString("snack", snack);
        editor.apply();

        Toast.makeText(this, "Meal plan saved!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Load the meal plan from SharedPreferences.
     */
    private void loadMealPlan() {
        String breakfast = sharedPreferences.getString("breakfast", "");
        String lunch = sharedPreferences.getString("lunch", "");
        String dinner = sharedPreferences.getString("dinner", "");
        String snack = sharedPreferences.getString("snack", "");

        etBreakfast.setText(breakfast);
        etLunch.setText(lunch);
        etDinner.setText(dinner);
        etSnack.setText(snack);
    }

    /**
     * Fetch the meal plan and send it to ChatGPT to generate a grocery list.
     */
    private void fetchIngredientsFromMealPlan() {
        String breakfast = etBreakfast.getText().toString();
        String lunch = etLunch.getText().toString();
        String dinner = etDinner.getText().toString();
        String snack = etSnack.getText().toString();

        if (breakfast.isEmpty() && lunch.isEmpty() && dinner.isEmpty() && snack.isEmpty()) {
            Toast.makeText(this, "Please enter your meal plan!", Toast.LENGTH_SHORT).show();
            return;
        }

        String mealPlan = "Breakfast: " + breakfast + "\n" +
                "Lunch: " + lunch + "\n" +
                "Dinner: " + dinner + "\n" +
                "Snack: " + snack;

        generateGroceryList(mealPlan);
    }

    /**
     * Use ChatGPT API to generate a grocery list from the meal plan.
     */
    private void generateGroceryList(String mealPlan) {
        ChatGptApi api = RetrofitClient.getApi();

        // Create the request
        Message systemMessage = new Message("system", "You are a helpful assistant.");
        Message userMessage = new Message("user", "Extract a grocery list from this meal plan:\n" + mealPlan);
        ChatGptRequest request = new ChatGptRequest("gpt-3.5-turbo", Arrays.asList(systemMessage, userMessage));

        // Make the API call
        api.getChatCompletion(request).enqueue(new Callback<ChatGptResponse>() {
            @Override
            public void onResponse(Call<ChatGptResponse> call, Response<ChatGptResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String reply = response.body().getChoices().get(0).getMessage().getContent();
                    List<String> ingredients = Arrays.asList(reply.split("\n"));
                    updateGroceryList(ingredients);
                } else {
                    Toast.makeText(PlannerActivity.this, "Failed to generate grocery list!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChatGptResponse> call, Throwable t) {
                Toast.makeText(PlannerActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Update the grocery list and save it to SharedPreferences.
     */
    private void updateGroceryList(List<String> ingredients) {
        SharedPreferences groceryPreferences = getSharedPreferences("GroceryList", MODE_PRIVATE);
        SharedPreferences.Editor editor = groceryPreferences.edit();

        editor.putInt("size", ingredients.size());
        for (int i = 0; i < ingredients.size(); i++) {
            editor.putString("item_" + i, ingredients.get(i));
        }
        editor.apply();

        Toast.makeText(this, "Grocery list generated!", Toast.LENGTH_SHORT).show();
    }
}
