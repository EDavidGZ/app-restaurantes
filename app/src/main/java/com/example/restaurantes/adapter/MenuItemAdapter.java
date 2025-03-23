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
import com.example.restaurantes.model.MenuItemModel;

import java.util.ArrayList;
import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder> {

    private List<MenuItemModel> menuItems;
    private Context context;

    public MenuItemAdapter(List<MenuItemModel> menuItems, Context context) {
        this.menuItems = menuItems;
        this.context = context;
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
        MenuItemModel menuItem = menuItems.get(position);
        holder.bind(menuItem);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public void updateMenuItems(List<MenuItemModel> newMenuItems) {
        this.menuItems = new ArrayList<>(newMenuItems);
        notifyDataSetChanged();
    }

    public void filterMenuItems(String query) {
        List<MenuItemModel> filteredList = new ArrayList<>();
        for (MenuItemModel menuItem : menuItems) {
            if (menuItem.getName().toLowerCase().contains(query.toLowerCase())
                    || menuItem.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(menuItem);
            }
        }
        this.menuItems = filteredList;
        notifyDataSetChanged();
    }

    class MenuItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textName;
        private TextView textDescription;
        private TextView textPrice;

        public MenuItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textDescription = itemView.findViewById(R.id.textDescription);
            textPrice = itemView.findViewById(R.id.textPrice);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    MenuItemModel menuItem = menuItems.get(position);
                    Intent intent = new Intent(context, ItemDetailsActivity.class);
                    intent.putExtra("item_name", menuItem.getName());
                    intent.putExtra("item_description", menuItem.getDescription());
                    intent.putExtra("item_price", menuItem.getPrice());
                    intent.putExtra("item_image", menuItem.getImageResourceId());
                    context.startActivity(intent);
                }
            });
        }

        public void bind(MenuItemModel menuItem) {
            textName.setText(menuItem.getName());
            textDescription.setText(menuItem.getDescription());
            textPrice.setText(String.format("$%.2f", menuItem.getPrice()));
        }
    }
}
