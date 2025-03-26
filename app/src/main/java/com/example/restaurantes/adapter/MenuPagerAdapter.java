package com.example.restaurantes.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.restaurantes.fragment.MenuTabFragment;
import com.example.restaurantes.model.Restaurant;

public class MenuPagerAdapter extends FragmentStateAdapter {

    private Restaurant restaurant;

    public MenuPagerAdapter(@NonNull FragmentActivity fragmentActivity, Restaurant restaurant) {
        super(fragmentActivity);
        this.restaurant = restaurant;
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
                break;
        }
        return MenuTabFragment.newInstance(restaurant.getId(), type);
    }

    @Override
    public int getItemCount() {
        return 3; // Comida, Bebidas, Complementos
    }
}
