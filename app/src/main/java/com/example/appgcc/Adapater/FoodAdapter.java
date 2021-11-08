package com.example.appgcc.Adapater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgcc.Entities.Food;
import com.example.appgcc.R;

import java.util.ArrayList;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {


    ArrayList<Food> foods;
    public FoodAdapter(ArrayList<Food> foods) {
        this.foods = foods;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        holder.name.setText(foods.get(position).getName());
        holder.category.setText(foods.get(position).getCategory());
        holder.price.setText(foods.get(position).getPrice());
        holder.description.setText(foods.get(position).getDescription());
        holder.creatorID.setText(foods.get(position).getCreatorID());
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView category;
        public TextView price;
        public TextView description;
        public TextView creatorID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            category = itemView.findViewById(R.id.tvCategory);
            price = itemView.findViewById(R.id.tvPrice);
            description = itemView.findViewById(R.id.tvDescription);
            creatorID = itemView.findViewById(R.id.tvCreatorID);
        }
    }
}