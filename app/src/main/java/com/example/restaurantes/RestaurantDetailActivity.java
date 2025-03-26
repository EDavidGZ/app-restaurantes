package com.example.restaurantes;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.restaurantes.adapter.MenuPagerAdapter;
import com.example.restaurantes.model.Restaurant;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RestaurantDetailActivity extends AppCompatActivity {

    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        // Obtener el restaurante pasado como extra
        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");

        // Configurar la toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(restaurant.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Configurar ViewPager y TabLayout
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        MenuPagerAdapter pagerAdapter = new MenuPagerAdapter(this, restaurant);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Comida");
                    break;
                case 1:
                    tab.setText("Bebidas");
                    break;
                case 2:
                    tab.setText("Complementos");
                    break;
            }
        }).attach();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
