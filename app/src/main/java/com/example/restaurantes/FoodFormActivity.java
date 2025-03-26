package com.example.restaurantes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.restaurantes.db.DatabaseHelper;
import com.example.restaurantes.model.Food;
import com.google.android.material.textfield.TextInputEditText;

public class FoodFormActivity extends AppCompatActivity {

    private TextInputEditText nameEditText;
    private TextInputEditText descriptionEditText;
    private TextInputEditText priceEditText;
    private TextInputEditText typeEditText;
    private Button saveButton;
    private Button deleteButton;
    private DatabaseHelper dbHelper;
    private Food food;
    private long restaurantId;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_form);

        // Inicializar vistas
        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);
        typeEditText = findViewById(R.id.typeEditText);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Inicializar base de datos
        dbHelper = new DatabaseHelper(this);

        // Obtener datos del intent
        restaurantId = getIntent().getLongExtra("restaurant_id", -1);
        type = getIntent().getStringExtra("type");
        food = (Food) getIntent().getSerializableExtra("food");

        // Configurar tipo
        typeEditText.setText(type);

        // Si es edición, cargar datos
        if (food != null) {
            setTitle("Editar Alimento");
            nameEditText.setText(food.getName());
            descriptionEditText.setText(food.getDescription());
            priceEditText.setText(String.valueOf(food.getPrice()));
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            setTitle("Nuevo Alimento");
        }

        // Configurar botones
        saveButton.setOnClickListener(v -> saveFood());
        deleteButton.setOnClickListener(v -> deleteFood());
    }

    private void saveFood() {
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Precio inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (food == null) {
            // Crear nuevo alimento
            food = new Food();
            food.setRestaurantId(restaurantId);
            food.setType(type);
        }

        food.setName(name);
        food.setDescription(description);
        food.setPrice(price);

        long result = food.getId() > 0
                ? dbHelper.updateFood(food)
                : dbHelper.insertFood(food);

        if (result > 0) {
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "Error al guardar el alimento", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteFood() {
        if (food != null && food.getId() > 0) {
            int result = dbHelper.deleteFood(food.getId());
            if (result > 0) {
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Error al eliminar el alimento", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
