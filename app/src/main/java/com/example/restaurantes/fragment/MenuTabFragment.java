package com.example.restaurantes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restaurantes.FoodDetailsActivity;
import com.example.restaurantes.FoodFormActivity;
import com.example.restaurantes.R;
import com.example.restaurantes.adapter.MenuItemAdapter;
import com.example.restaurantes.db.DatabaseHelper;
import com.example.restaurantes.model.Food;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MenuTabFragment extends Fragment implements MenuItemAdapter.OnMenuItemClickListener {

    private static final String ARG_RESTAURANT_ID = "restaurant_id";
    private static final String ARG_TYPE = "type";

    private long restaurantId;
    private String type;
    private RecyclerView recyclerView;
    private MenuItemAdapter adapter;
    private DatabaseHelper dbHelper;
    private FloatingActionButton fab;

    private final ActivityResultLauncher<Intent> formLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == android.app.Activity.RESULT_OK) {
                    loadMenuItems();
                }
            }
    );

    public static MenuTabFragment newInstance(long restaurantId, String type) {
        MenuTabFragment fragment = new MenuTabFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_RESTAURANT_ID, restaurantId);
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurantId = getArguments().getLong(ARG_RESTAURANT_ID);
            type = getArguments().getString(ARG_TYPE);
        }
        dbHelper = new DatabaseHelper(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_tab, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        fab = view.findViewById(R.id.fab);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MenuItemAdapter(new ArrayList<>(), requireContext(), this);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FoodFormActivity.class);
            intent.putExtra("restaurant_id", restaurantId);
            intent.putExtra("type", type);
            formLauncher.launch(intent);
        });

        loadMenuItems();
        return view;
    }

    private void loadMenuItems() {
        List<Food> items = dbHelper.getFoodsByRestaurantAndType(restaurantId, type);
        adapter.updateItems(items);
    }

    @Override
    public void onMenuItemClick(Food food) {
        Intent intent = new Intent(getContext(), FoodDetailsActivity.class);
        intent.putExtra("food", food);
        startActivity(intent);
    }

    @Override
    public void onMenuItemLongClick(Food food, View view) {
        PopupMenu popup = new PopupMenu(requireContext(), view);
        popup.getMenuInflater().inflate(R.menu.context_menu_food, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_edit) {
                Intent intent = new Intent(getContext(), FoodFormActivity.class);
                intent.putExtra("restaurant_id", restaurantId);
                intent.putExtra("type", type);
                intent.putExtra("food", food);
                formLauncher.launch(intent);
                return true;
            }
            return false;
        });

        popup.show();
    }

    public void filterMenuItems(String query) {
        if (adapter != null) {
            adapter.filterItems(query);
        }
    }
}
