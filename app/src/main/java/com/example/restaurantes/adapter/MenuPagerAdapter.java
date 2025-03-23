package com.example.restaurantes.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.restaurantes.fragment.MenuTabFragment;

public class MenuPagerAdapter extends FragmentStateAdapter {

    private final long restaurantId;

    public MenuPagerAdapter(@NonNull FragmentActivity fragmentActivity, long restaurantId) {
        super(fragmentActivity);
        this.restaurantId = restaurantId;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String type;
        switch (position) {
            case 0:
                type = "food";
                break;
            case 1:
                type = "drinks";
                break;
            case 2:
                type = "complements";
                break;
            default:
                type = "food";
        }
        return MenuTabFragment.newInstance(type, restaurantId);
    }

    @Override
    public int getItemCount() {
        return 3; // Three tabs: Food, Drinks, and Complements
    }
}
