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
            Restaurant pujol = new Restaurant("Pujol", "Cocina contemporánea mexicana", "fine_dining");
            long pujolId = dbHelper.insertRestaurant(pujol);

            // Insertar items del menú de Pujol
            dbHelper.insertFood(new Food("Taco Omakase", 1200.0, "Degustación de tacos con ingredientes de temporada", "food"), pujolId);
            dbHelper.insertFood(new Food("Mole Madre", 850.0, "Mole tradicional con mole nuevo", "food"), pujolId);
            dbHelper.insertFood(new Food("Margarita de Mango", 180.0, "Margarita con mango fresco y chile", "drinks"), pujolId);
            dbHelper.insertFood(new Food("Mezcal Flight", 450.0, "Degustación de diferentes mezcales", "drinks"), pujolId);
            dbHelper.insertFood(new Food("Salsas", 0.0, "Variedad de salsas caseras", "complements"), pujolId);
            dbHelper.insertFood(new Food("Tortillas", 0.0, "Tortillas hechas a mano", "complements"), pujolId);

            Restaurant quintonil = new Restaurant("Quintonil", "Cocina mexicana moderna", "fine_dining");
            long quintonilId = dbHelper.insertRestaurant(quintonil);

            // Insertar items del menú de Quintonil
            dbHelper.insertFood(new Food("Pescado Tikin Xic", 850.0, "Pescado asado en hoja de plátano", "food"), quintonilId);
            dbHelper.insertFood(new Food("Cochinita Pibil", 750.0, "Cochinita pibil tradicional", "food"), quintonilId);
            dbHelper.insertFood(new Food("Paloma", 140.0, "Paloma con tequila reposado", "drinks"), quintonilId);
            dbHelper.insertFood(new Food("Michelada", 120.0, "Michelada con cerveza artesanal", "drinks"), quintonilId);
            dbHelper.insertFood(new Food("Salsas", 0.0, "Salsas de la casa", "complements"), quintonilId);
            dbHelper.insertFood(new Food("Tortillas", 0.0, "Tortillas de maíz", "complements"), quintonilId);

            // Insertar Rosetta con su menú
            Restaurant rosetta = new Restaurant("Rosetta", "Cocina italiana con toques mexicanos", "italian");
            long rosettaId = dbHelper.insertRestaurant(rosetta);
            dbHelper.insertFood(new Food("Risotto de Hongos", 680.0, "Risotto con hongos silvestres y queso parmesano", "food"), rosettaId);
            dbHelper.insertFood(new Food("Pasta al Pesto", 580.0, "Pasta fresca con pesto de albahaca", "food"), rosettaId);
            dbHelper.insertFood(new Food("Vino Tinto", 280.0, "Vino tinto de la casa", "drinks"), rosettaId);
            dbHelper.insertFood(new Food("Spritz", 180.0, "Spritz italiano tradicional", "drinks"), rosettaId);
            dbHelper.insertFood(new Food("Pan de la Casa", 0.0, "Pan artesanal", "complements"), rosettaId);
            dbHelper.insertFood(new Food("Aceite de Oliva", 0.0, "Aceite de oliva extra virgen", "complements"), rosettaId);

            // Insertar Maximo Bistrot con su menú
            Restaurant maximo = new Restaurant("Maximo Bistrot", "Cocina francesa contemporánea", "french");
            long maximoId = dbHelper.insertRestaurant(maximo);
            dbHelper.insertFood(new Food("Sopa de Cebolla", 320.0, "Sopa de cebolla gratinada", "food"), maximoId);
            dbHelper.insertFood(new Food("Steak Frites", 850.0, "Filete con papas fritas", "food"), maximoId);
            dbHelper.insertFood(new Food("Vino Francés", 320.0, "Vino tinto francés", "drinks"), maximoId);
            dbHelper.insertFood(new Food("Pastis", 120.0, "Pastis francés", "drinks"), maximoId);
            dbHelper.insertFood(new Food("Pan Francés", 0.0, "Pan baguette", "complements"), maximoId);
            dbHelper.insertFood(new Food("Mantequilla", 0.0, "Mantequilla de la casa", "complements"), maximoId);

            // Insertar Sud 777 con su menú
            Restaurant sud777 = new Restaurant("Sud 777", "1 estrella Michelin - Cocina mexicana con enfoque en vegetales", "fine_dining");
            long sud777Id = dbHelper.insertRestaurant(sud777);
            dbHelper.insertFood(new Food("Ensalada de Quinoa", 420.0, "Ensalada de quinoa con vegetales", "food"), sud777Id);
            dbHelper.insertFood(new Food("Risotto de Verduras", 580.0, "Risotto con verduras de temporada", "food"), sud777Id);
            dbHelper.insertFood(new Food("Jugo Verde", 120.0, "Jugo verde detox", "drinks"), sud777Id);
            dbHelper.insertFood(new Food("Té de Hierbas", 80.0, "Té de hierbas orgánico", "drinks"), sud777Id);
            dbHelper.insertFood(new Food("Pan Integral", 0.0, "Pan integral artesanal", "complements"), sud777Id);
            dbHelper.insertFood(new Food("Aceite de Oliva", 0.0, "Aceite de oliva extra virgen", "complements"), sud777Id);

            // Insertar Esquina Común con su menú
            Restaurant esquina = new Restaurant("Esquina Común", "1 estrella Michelin - Cocina de temporada creativa", "casual_dining");
            long esquinaId = dbHelper.insertRestaurant(esquina);
            dbHelper.insertFood(new Food("Tacos de Pescado", 280.0, "Tacos de pescado fresco", "food"), esquinaId);
            dbHelper.insertFood(new Food("Enchiladas Verdes", 320.0, "Enchiladas verdes con pollo", "food"), esquinaId);
            dbHelper.insertFood(new Food("Cerveza Artesanal", 120.0, "Cerveza artesanal local", "drinks"), esquinaId);
            dbHelper.insertFood(new Food("Agua de Jamaica", 60.0, "Agua de jamaica natural", "drinks"), esquinaId);
            dbHelper.insertFood(new Food("Salsas", 0.0, "Salsas de la casa", "complements"), esquinaId);
            dbHelper.insertFood(new Food("Tortillas", 0.0, "Tortillas hechas a mano", "complements"), esquinaId);

            // Insertar Cana con su menú
            Restaurant cana = new Restaurant("Cana", "1 estrella Michelin - Cocina internacional con toque parisino", "fine_dining");
            long canaId = dbHelper.insertRestaurant(cana);
            dbHelper.insertFood(new Food("Sopa de Cebolla", 320.0, "Sopa de cebolla gratinada", "food"), canaId);
            dbHelper.insertFood(new Food("Entrecôte", 850.0, "Entrecôte a la parrilla", "food"), canaId);
            dbHelper.insertFood(new Food("Vino Tinto", 320.0, "Vino tinto de la casa", "drinks"), canaId);
            dbHelper.insertFood(new Food("Café Francés", 80.0, "Café francés", "drinks"), canaId);
            dbHelper.insertFood(new Food("Pan Francés", 0.0, "Pan baguette", "complements"), canaId);
            dbHelper.insertFood(new Food("Mantequilla", 0.0, "Mantequilla de la casa", "complements"), canaId);

            // Insertar Tencüi con su menú
            Restaurant tencui = new Restaurant("Tencüi", "1 estrella Michelin - Cocina gourmet enfocada en hongos", "fine_dining");
            long tencuiId = dbHelper.insertRestaurant(tencui);
            dbHelper.insertFood(new Food("Risotto de Hongos", 680.0, "Risotto con hongos silvestres", "food"), tencuiId);
            dbHelper.insertFood(new Food("Sopa de Hongos", 420.0, "Sopa cremosa de hongos", "food"), tencuiId);
            dbHelper.insertFood(new Food("Vino Blanco", 280.0, "Vino blanco de la casa", "drinks"), tencuiId);
            dbHelper.insertFood(new Food("Té de Hongos", 120.0, "Té de hongos medicinales", "drinks"), tencuiId);
            dbHelper.insertFood(new Food("Pan de Hongos", 0.0, "Pan con hongos", "complements"), tencuiId);
            dbHelper.insertFood(new Food("Aceite de Hongos", 0.0, "Aceite aromatizado con hongos", "complements"), tencuiId);

            // Insertar El Califa de León con su menú
            Restaurant califa = new Restaurant("El Califa de León", "1 estrella Michelin - Tacos de res y cerdo", "casual_dining");
            long califaId = dbHelper.insertRestaurant(califa);
            dbHelper.insertFood(new Food("Taco de Res", 45.0, "Taco de res a la parrilla", "food"), califaId);
            dbHelper.insertFood(new Food("Taco de Cerdo", 40.0, "Taco de cerdo a la parrilla", "food"), califaId);
            dbHelper.insertFood(new Food("Cerveza", 60.0, "Cerveza clara", "drinks"), califaId);
            dbHelper.insertFood(new Food("Agua de Horchata", 30.0, "Agua de horchata", "drinks"), califaId);
            dbHelper.insertFood(new Food("Salsas", 0.0, "Salsas de la casa", "complements"), califaId);
            dbHelper.insertFood(new Food("Cebollas", 0.0, "Cebollas asadas", "complements"), califaId);

            // Insertar Contramar con su menú
            Restaurant contramar = new Restaurant("Contramar", "Mariscos y pescados", "seafood");
            long contramarId = dbHelper.insertRestaurant(contramar);
            dbHelper.insertFood(new Food("Pescado a la Veracruzana", 580.0, "Pescado en salsa veracruzana", "food"), contramarId);
            dbHelper.insertFood(new Food("Ceviche", 420.0, "Ceviche de pescado", "food"), contramarId);
            dbHelper.insertFood(new Food("Vino Blanco", 280.0, "Vino blanco de la casa", "drinks"), contramarId);
            dbHelper.insertFood(new Food("Michelada", 120.0, "Michelada especial", "drinks"), contramarId);
            dbHelper.insertFood(new Food("Tostadas", 0.0, "Tostadas de maíz", "complements"), contramarId);
            dbHelper.insertFood(new Food("Salsas", 0.0, "Salsas de la casa", "complements"), contramarId);
        }

        // Configurar adaptador
        adapter = new RestaurantAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        // Load restaurants from database
        loadRestaurants();

        // Configurar FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            // TODO: Implementar acción del FAB
            Toast.makeText(this, "FAB clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadRestaurants() {
        // Get restaurants from database
        List<Restaurant> restaurants = dbHelper.getAllRestaurants();
        adapter.updateRestaurants(restaurants);
    }

    @Override
    public void onRestaurantClick(Restaurant restaurant) {
        // Ocultar RecyclerView y FAB
        recyclerView.setVisibility(View.GONE);
        findViewById(R.id.fab).setVisibility(View.GONE);

        // Crear y mostrar el fragmento de detalle
        RestaurantDetailFragment detailFragment = RestaurantDetailFragment.newInstance(restaurant);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, detailFragment)
                .addToBackStack(null)
                .commit();

        // Actualizar el título de la toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(restaurant.getName());
        }
    }

    @Override
    public void onRestaurantLongClick(Restaurant restaurant, View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.context_menu_restaurant, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_food) {
                openMenuTab(restaurant, "food");
            } else if (itemId == R.id.action_drinks) {
                openMenuTab(restaurant, "drinks");
            } else if (itemId == R.id.action_complements) {
                openMenuTab(restaurant, "complements");
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
}
