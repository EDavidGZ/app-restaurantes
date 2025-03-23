package com.example.restaurantes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.restaurantes.model.Restaurant;
import com.example.restaurantes.model.Food;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "RestaurantDB";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_RESTAURANTS = "restaurants";
    private static final String TABLE_FOODS = "foods";

    // Common column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_RESTAURANT_ID = "restaurant_id";

    // Create table queries
    private static final String CREATE_TABLE_RESTAURANTS = "CREATE TABLE " + TABLE_RESTAURANTS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_TYPE + " TEXT"
            + ")";

    private static final String CREATE_TABLE_FOODS = "CREATE TABLE " + TABLE_FOODS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_PRICE + " REAL,"
            + COLUMN_TYPE + " TEXT,"
            + COLUMN_RESTAURANT_ID + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_RESTAURANT_ID + ") REFERENCES " + TABLE_RESTAURANTS + "(" + COLUMN_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESTAURANTS);
        db.execSQL(CREATE_TABLE_FOODS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        onCreate(db);
    }

    // Restaurant operations
    public long insertRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, restaurant.getName());
        values.put(COLUMN_DESCRIPTION, restaurant.getDescription());
        values.put(COLUMN_TYPE, restaurant.getType());
        return db.insert(TABLE_RESTAURANTS, null, values);
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Restaurant restaurant = new Restaurant(
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
                );
                restaurant.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                restaurants.add(restaurant);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return restaurants;
    }

    // Food operations
    public long insertFood(Food food, long restaurantId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_DESCRIPTION, food.getDescription());
        values.put(COLUMN_PRICE, food.getPrice());
        values.put(COLUMN_TYPE, food.getType());
        values.put(COLUMN_RESTAURANT_ID, restaurantId);
        return db.insert(TABLE_FOODS, null, values);
    }

    public List<Food> getFoodsByRestaurantAndType(long restaurantId, String type) {
        List<Food> foods = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FOODS + " WHERE " + COLUMN_RESTAURANT_ID + " = ? AND " + COLUMN_TYPE + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(restaurantId), type});

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food(
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TYPE))
                );
                foods.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return foods;
    }
}
