package com.example.restaurantes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.restaurantes.db.DatabaseHelper;
import com.example.restaurantes.model.Restaurant;
import com.google.android.material.textfield.TextInputEditText;

public class RestaurantFormActivity extends AppCompatActivity {

    private TextInputEditText nameEditText;
    private TextInputEditText descriptionEditText;
    private TextInputEditText typeEditText;
    private Button saveButton;
    private Button deleteButton;
    private DatabaseHelper dbHelper;
    private Restaurant restaurant;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_form);

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        typeEditText = findViewById(R.id.typeEditText);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);

        dbHelper = new DatabaseHelper(this);

        // Check if we're editing an existing restaurant
        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");
        if (restaurant != null) {
            isEditing = true;
            // Fill the form with existing data
            nameEditText.setText(restaurant.getName());
            descriptionEditText.setText(restaurant.getDescription());
            typeEditText.setText(restaurant.getType());
            deleteButton.setVisibility(View.VISIBLE);
        }

        // Setup save button
        saveButton.setOnClickListener(v -> saveRestaurant());

        // Setup delete button
        deleteButton.setOnClickListener(v -> deleteRestaurant());
    }

    private void saveRestaurant() {
        String name = nameEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String type = typeEditText.getText().toString().trim();

        // Validate inputs
        if (name.isEmpty() || description.isEmpty() || type.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isEditing) {
            // Update existing restaurant
            restaurant.setName(name);
            restaurant.setDescription(description);
            restaurant.setType(type);
            dbHelper.updateRestaurant(restaurant);
            Toast.makeText(this, "Restaurante actualizado exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            // Create new restaurant
            Restaurant newRestaurant = new Restaurant(name, description, type);
            long id = dbHelper.insertRestaurant(newRestaurant);
            if (id != -1) {
                Toast.makeText(this, "Restaurante agregado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al agregar el restaurante", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Return to the list activity
        Intent intent = new Intent(this, RestaurantListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void deleteRestaurant() {
        if (restaurant != null) {
            dbHelper.deleteRestaurant(restaurant.getId());
            Toast.makeText(this, "Restaurante eliminado exitosamente", Toast.LENGTH_SHORT).show();

            // Return to the list activity
            Intent intent = new Intent(this, RestaurantListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
