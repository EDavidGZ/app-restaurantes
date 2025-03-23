package com.example.restaurantes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restaurantes.R;
import com.example.restaurantes.adapter.MenuPagerAdapter;
import com.example.restaurantes.db.DatabaseHelper;
import com.example.restaurantes.model.Restaurant;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RestaurantDetailFragment extends Fragment {

    private static final String ARG_RESTAURANT = "restaurant";
    private Restaurant restaurant;
    private DatabaseHelper dbHelper;
    private ViewPager2 viewPager;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView categoryTextView;
    private ImageView restaurantImageView;
    private SearchView searchView;
    private TabLayout tabLayout;

    public static RestaurantDetailFragment newInstance(Restaurant restaurant) {
        RestaurantDetailFragment fragment = new RestaurantDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESTAURANT, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurant = (Restaurant) getArguments().getSerializable(ARG_RESTAURANT);
        }
        dbHelper = new DatabaseHelper(requireContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);

        // Initialize views
        nameTextView = view.findViewById(R.id.nameTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        categoryTextView = view.findViewById(R.id.categoryTextView);
        restaurantImageView = view.findViewById(R.id.restaurantImageView);
        searchView = view.findViewById(R.id.searchView);
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        // Set restaurant details
        if (restaurant != null) {
            nameTextView.setText(restaurant.getName());
            descriptionTextView.setText(restaurant.getDescription());
            categoryTextView.setText(restaurant.getType());
            restaurantImageView.setImageResource(R.drawable.restaurant_placeholder);
        }

        // Setup ViewPager and TabLayout
        setupViewPager();

        // Setup search functionality
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Get the current fragment from the ViewPager
                    int currentPosition = viewPager.getCurrentItem();
                    Fragment currentFragment = getChildFragmentManager().findFragmentByTag("f" + currentPosition);
                    if (currentFragment instanceof MenuTabFragment) {
                        ((MenuTabFragment) currentFragment).filterMenuItems(newText);
                    }
                    return true;
                }
            });
        }

        return view;
    }

    private void setupViewPager() {
        MenuPagerAdapter pagerAdapter = new MenuPagerAdapter(requireActivity(), restaurant.getId());
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Food");
                    break;
                case 1:
                    tab.setText("Drinks");
                    break;
                case 2:
                    tab.setText("Complements");
                    break;
            }
        }).attach();
    }

    public ViewPager2 getViewPager() {
        return viewPager;
    }
}
