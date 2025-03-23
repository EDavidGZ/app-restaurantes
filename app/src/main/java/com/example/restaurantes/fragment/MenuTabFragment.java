package com.example.restaurantes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantes.R;
import com.example.restaurantes.adapter.MenuItemAdapter;
import com.example.restaurantes.db.DatabaseHelper;
import com.example.restaurantes.model.Food;
import com.example.restaurantes.model.MenuItemModel;

import java.util.ArrayList;
import java.util.List;

public class MenuTabFragment extends Fragment {

    private RecyclerView recyclerView;
    private MenuItemAdapter adapter;
    private String type;
    private long restaurantId;

    public static MenuTabFragment newInstance(String type, long restaurantId) {
        MenuTabFragment fragment = new MenuTabFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putLong("restaurantId", restaurantId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString("type");
            restaurantId = getArguments().getLong("restaurantId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_tab, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        loadMenuItems();

        return view;
    }

    public void filterMenuItems(String query) {
        if (adapter != null) {
            adapter.filterMenuItems(query);
        }
    }

    private void loadMenuItems() {
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        List<Food> foodItems = dbHelper.getFoodsByRestaurantAndType(restaurantId, type);

        // Convert Food objects to MenuItemModel objects
        List<MenuItemModel> menuItems = new ArrayList<>();
        for (Food food : foodItems) {
            MenuItemModel menuItem = new MenuItemModel(
                    food.getName(),
                    food.getDescription(),
                    food.getPrice(),
                    R.drawable.food_placeholder // Using placeholder image
            );
            menuItems.add(menuItem);
        }

        adapter = new MenuItemAdapter(menuItems, getContext());
        recyclerView.setAdapter(adapter);
    }
}
