package com.example.restaurantes;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.restaurantes.model.Food;
import java.util.Locale;

public class FoodDetailsActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView descriptionTextView;
    private TextView priceTextView;
    private TextView typeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        // Inicializar vistas
        nameTextView = findViewById(R.id.nameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        priceTextView = findViewById(R.id.priceTextView);
        typeTextView = findViewById(R.id.typeTextView);

        // Configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Obtener alimento del intent
        Food food = (Food) getIntent().getSerializableExtra("food");
        if (food != null) {
            setTitle(food.getName());
            nameTextView.setText(food.getName());
            descriptionTextView.setText(food.getDescription());
            priceTextView.setText(String.format(Locale.getDefault(), "$%.2f", food.getPrice()));
            typeTextView.setText(food.getType());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
