package com.example.mealplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroceryAdapter extends RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder> {

    private List<String> groceryItems;

    public GroceryAdapter(List<String> groceryItems) {
        this.groceryItems = groceryItems;
    }

    @NonNull
    @Override
    public GroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grocery, parent, false);
        return new GroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryViewHolder holder, int position) {
        String item = groceryItems.get(position);
        holder.tvItemName.setText(item);
    }

    @Override
    public int getItemCount() {
        return groceryItems.size();
    }

    public static class GroceryViewHolder extends RecyclerView.ViewHolder {

        TextView tvItemName;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
        }
    }
}
