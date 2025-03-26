package com.example.restaurantes;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantes.adapter.RestaurantAdapter;
import com.example.restaurantes.fragment.RestaurantDetailFragment;
import com.example.restaurantes.model.Restaurant;
import com.example.restaurantes.model.MenuItemModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.example.restaurantes.db.DatabaseHelper;
import com.example.restaurantes.model.Food;

public class RestaurantListActivity extends AppCompatActivity implements RestaurantAdapter.OnRestaurantClickListener {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<Restaurant> restaurantList;
    private SearchView searchView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        // Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Restaurantes Michelin");
        }

        // Inicializar RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar lista de restaurantes
        restaurantList = new ArrayList<>();

        dbHelper = new DatabaseHelper(this);

        // Verificar si ya existen restaurantes
        List<Restaurant> existingRestaurants = dbHelper.getAllRestaurants();
        if (existingRestaurants.isEmpty()) {
            // Insertar restaurantes en la base de datos solo si no existen
            insertInitialData();
        }

        // Configurar adaptador
        adapter = new RestaurantAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        // Load restaurants from database
        loadRestaurants();

        // Configurar FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // Abrir el formulario para agregar un nuevo restaurante
            Intent intent = new Intent(this, RestaurantFormActivity.class);
            startActivity(intent);
        });
    }

    private void loadRestaurants() {
        // Get restaurants from database
        List<Restaurant> restaurants = dbHelper.getAllRestaurants();
        adapter.updateRestaurants(restaurants);
    }

    @Override
    public void onRestaurantClick(Restaurant restaurant) {
        // Abrir el detalle del restaurante
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra("restaurant", restaurant);
        startActivity(intent);
    }

    @Override
    public void onRestaurantLongClick(Restaurant restaurant, View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.context_menu_restaurant, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_edit) {
                // Abrir el formulario para editar el restaurante
                Intent intent = new Intent(this, RestaurantFormActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            } else if (itemId == R.id.action_food) {
                // Abrir el menú del restaurante
                Intent intent = new Intent(this, RestaurantMenuActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            } else if (itemId == R.id.action_drinks) {
                // Abrir el menú del restaurante
                Intent intent = new Intent(this, RestaurantMenuActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            } else if (itemId == R.id.action_complements) {
                // Abrir el menú del restaurante
                Intent intent = new Intent(this, RestaurantMenuActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivity(intent);
            }
            return true;
        });

        popup.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_restaurant_list, menu);

        // Setup search functionality
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filterRestaurants(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            recyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.fab).setVisibility(View.VISIBLE);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Restaurantes Michelin");
            }
        } else {
            super.onBackPressed();
        }
    }

    private void openMenuTab(Restaurant restaurant, String tab) {
        Intent intent = new Intent(this, RestaurantMenuActivity.class);
        intent.putExtra("restaurant_name", restaurant.getName());
        intent.putExtra("restaurant_description", restaurant.getDescription());
        intent.putExtra("selected_tab", tab);
        startActivity(intent);
    }

    private void insertInitialData() {
        List<Restaurant> restaurants = dbHelper.getAllRestaurants();
        if (restaurants.isEmpty()) {
            // Insertar Pujol
            Restaurant pujol = new Restaurant();
            pujol.setName("Pujol");
            pujol.setDescription("Restaurante de alta cocina mexicana contemporánea");
            pujol.setType("Mexicana");
            long pujolId = dbHelper.insertRestaurant(pujol);

            // Insertar platillos de Pujol
            Food tacoOmakase = new Food();
            tacoOmakase.setName("Taco Omakase");
            tacoOmakase.setDescription("Degustación de tacos con ingredientes de temporada");
            tacoOmakase.setPrice(1200.0);
            tacoOmakase.setType("food");
            tacoOmakase.setRestaurantId(pujolId);
            dbHelper.insertFood(tacoOmakase);

            Food mezcalArtesanal = new Food();
            mezcalArtesanal.setName("Mezcal Artesanal");
            mezcalArtesanal.setDescription("Selección de mezcales artesanales");
            mezcalArtesanal.setPrice(450.0);
            mezcalArtesanal.setType("drinks");
            mezcalArtesanal.setRestaurantId(pujolId);
            dbHelper.insertFood(mezcalArtesanal);

            Food guacamole = new Food();
            guacamole.setName("Guacamole Tradicional");
            guacamole.setDescription("Guacamole con totopos hechos en casa");
            guacamole.setPrice(180.0);
            guacamole.setType("complements");
            guacamole.setRestaurantId(pujolId);
            dbHelper.insertFood(guacamole);

            // Insertar Quintonil
            Restaurant quintonil = new Restaurant();
            quintonil.setName("Quintonil");
            quintonil.setDescription("Cocina mexicana de autor");
            quintonil.setType("Mexicana");
            long quintonilId = dbHelper.insertRestaurant(quintonil);

            // Insertar platillos de Quintonil
            Food tartarRes = new Food();
            tartarRes.setName("Tartar de Res");
            tartarRes.setDescription("Tartar de res con hormiga chicatana");
            tartarRes.setPrice(580.0);
            tartarRes.setType("food");
            tartarRes.setRestaurantId(quintonilId);
            dbHelper.insertFood(tartarRes);

            Food vinoTinto = new Food();
            vinoTinto.setName("Vino Tinto Casa");
            vinoTinto.setDescription("Vino tinto mexicano de la casa");
            vinoTinto.setPrice(320.0);
            vinoTinto.setType("drinks");
            vinoTinto.setRestaurantId(quintonilId);
            dbHelper.insertFood(vinoTinto);

            Food panArtesanal = new Food();
            panArtesanal.setName("Pan Artesanal");
            panArtesanal.setDescription("Selección de panes artesanales");
            panArtesanal.setPrice(150.0);
            panArtesanal.setType("complements");
            panArtesanal.setRestaurantId(quintonilId);
            dbHelper.insertFood(panArtesanal);

            // Insertar otros restaurantes
            Restaurant[] otrosRestaurantes = {
                new Restaurant("El Cardenal", "Restaurante de cocina mexicana tradicional", "Mexicana"),
                new Restaurant("Contramar", "Especialidad en mariscos", "Mariscos"),
                new Restaurant("Sud 777", "Cocina contemporánea", "Internacional")
            };

            for (Restaurant restaurant : otrosRestaurantes) {
                dbHelper.insertRestaurant(restaurant);
            }
        }
    }
}
