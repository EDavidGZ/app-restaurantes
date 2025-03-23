package com.example.restaurantes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantes.R;
import com.example.restaurantes.db.DatabaseHelper;
import com.example.restaurantes.model.Restaurant;
import com.example.restaurantes.model.Food;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurants;
    private List<Restaurant> filteredRestaurants;
    private OnRestaurantClickListener listener;
    private DatabaseHelper dbHelper;

    public interface OnRestaurantClickListener {

        void onRestaurantClick(Restaurant restaurant);

        void onRestaurantLongClick(Restaurant restaurant, View view);
    }

    public RestaurantAdapter(List<Restaurant> restaurants, OnRestaurantClickListener listener) {
        this.restaurants = restaurants;
        this.filteredRestaurants = new ArrayList<>(restaurants);
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        dbHelper = new DatabaseHelper(parent.getContext());
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = filteredRestaurants.get(position);
        holder.bind(restaurant);
    }

    @Override
    public int getItemCount() {
        return filteredRestaurants.size();
    }

    public void filterRestaurants(String query) {
        filteredRestaurants.clear();
        if (query.isEmpty()) {
            filteredRestaurants.addAll(restaurants);
        } else {
            query = query.toLowerCase();
            for (Restaurant restaurant : restaurants) {
                if (restaurant.getName().toLowerCase().contains(query)
                        || restaurant.getDescription().toLowerCase().contains(query)
                        || restaurant.getType().toLowerCase().contains(query)) {
                    filteredRestaurants.add(restaurant);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateRestaurants(List<Restaurant> newRestaurants) {
        this.restaurants = new ArrayList<>(newRestaurants);
        this.filteredRestaurants = new ArrayList<>(newRestaurants);
        notifyDataSetChanged();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView descriptionTextView;
        private TextView categoryTextView;
        private TextView menuItemsCountTextView;
        private ImageView restaurantImageView;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            menuItemsCountTextView = itemView.findViewById(R.id.menuItemsCountTextView);
            restaurantImageView = itemView.findViewById(R.id.restaurantImageView);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onRestaurantClick(filteredRestaurants.get(position));
                }
            });

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onRestaurantLongClick(filteredRestaurants.get(position), v);
                    return true;
                }
                return false;
            });
        }

        public void bind(Restaurant restaurant) {
            nameTextView.setText(restaurant.getName());
            descriptionTextView.setText(restaurant.getDescription());
            categoryTextView.setText(restaurant.getType());

            // Obtener el total de items del menú desde la base de datos
            List<Food> foodItems = dbHelper.getFoodsByRestaurantAndType(restaurant.getId(), "food");
            List<Food> drinkItems = dbHelper.getFoodsByRestaurantAndType(restaurant.getId(), "drink");
            List<Food> complementItems = dbHelper.getFoodsByRestaurantAndType(restaurant.getId(), "complement");

            int totalItems = foodItems.size() + drinkItems.size() + complementItems.size();
            menuItemsCountTextView.setText(String.format("%d items en el menú", totalItems));

            // Aquí puedes agregar la lógica para cargar la imagen del restaurante
            // Por ahora usaremos una imagen por defecto
            restaurantImageView.setImageResource(R.drawable.restaurant_placeholder);
        }
    }
}
