package com.example.restaurantes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantes.ItemDetailsActivity;
import com.example.restaurantes.R;
import com.example.restaurantes.model.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {

    private List<Food> items;
    private List<Food> filteredItems;
    private Context context;
    private OnMenuItemClickListener listener;

    public interface OnMenuItemClickListener {

        void onMenuItemClick(Food food);

        void onMenuItemLongClick(Food food, View view);
    }

    public MenuItemAdapter(List<Food> items, Context context, OnMenuItemClickListener listener) {
        this.items = new ArrayList<>(items);
        this.filteredItems = new ArrayList<>(items);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);
        return new MenuItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {
        Food item = filteredItems.get(position);
        holder.bind(item);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMenuItemClick(item);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onMenuItemLongClick(item, v);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    public void updateItems(List<Food> newItems) {
        this.items = new ArrayList<>(newItems);
        this.filteredItems = new ArrayList<>(newItems);
        notifyDataSetChanged();
    }

    public void filterItems(String query) {
        filteredItems.clear();
        if (query.isEmpty()) {
            filteredItems.addAll(items);
        } else {
            query = query.toLowerCase();
            for (Food item : items) {
                if (item.getName().toLowerCase().contains(query)
                        || item.getDescription().toLowerCase().contains(query)) {
                    filteredItems.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView;
        private TextView descriptionTextView;
        private TextView priceTextView;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }

        public void bind(Food item) {
            nameTextView.setText(item.getName());
            descriptionTextView.setText(item.getDescription());
            priceTextView.setText(String.format(Locale.getDefault(), "$%.2f", item.getPrice()));
        }
    }
}
