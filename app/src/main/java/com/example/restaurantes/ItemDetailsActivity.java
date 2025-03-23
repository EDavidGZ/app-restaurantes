package com.example.restaurantes;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Get views
        ImageView imageView = findViewById(R.id.imageView);
        TextView nameTextView = findViewById(R.id.textName);
        TextView priceTextView = findViewById(R.id.textPrice);
        TextView descriptionTextView = findViewById(R.id.textDescription);

        // Get data from intent
        String itemName = getIntent().getStringExtra("item_name");
        String itemDescription = getIntent().getStringExtra("item_description");
        double itemPrice = getIntent().getDoubleExtra("item_price", 0.0);
        int imageResourceId = getIntent().getIntExtra("item_image", 0);

        // Set data to views
        imageView.setImageResource(imageResourceId);
        nameTextView.setText(itemName);
        priceTextView.setText(String.format("$%.2f", itemPrice));
        descriptionTextView.setText(itemDescription);

        // Set toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(itemName);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
