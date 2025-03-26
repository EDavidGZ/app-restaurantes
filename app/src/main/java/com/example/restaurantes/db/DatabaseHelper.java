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
    private static final int DATABASE_VERSION = 2;

    // Table names
    private static final String TABLE_RESTAURANTS = "restaurants";
    private static final String TABLE_FOOD = "food";

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

    private static final String CREATE_TABLE_FOOD = "CREATE TABLE " + TABLE_FOOD + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_RESTAURANT_ID + " INTEGER,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_DESCRIPTION + " TEXT,"
            + COLUMN_PRICE + " REAL,"
            + COLUMN_TYPE + " TEXT,"
            + "FOREIGN KEY(" + COLUMN_RESTAURANT_ID + ") REFERENCES " + TABLE_RESTAURANTS + "(" + COLUMN_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESTAURANTS);
        db.execSQL(CREATE_TABLE_FOOD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
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

    public int updateRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, restaurant.getName());
        values.put(COLUMN_DESCRIPTION, restaurant.getDescription());
        values.put(COLUMN_TYPE, restaurant.getType());
        return db.update(TABLE_RESTAURANTS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(restaurant.getId())});
    }

    public void deleteRestaurant(long restaurantId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // First delete all foods associated with this restaurant
        db.delete(TABLE_FOOD, COLUMN_RESTAURANT_ID + " = ?",
                new String[]{String.valueOf(restaurantId)});
        // Then delete the restaurant
        db.delete(TABLE_RESTAURANTS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(restaurantId)});
    }

    // Food operations
    public long insertFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESTAURANT_ID, food.getRestaurantId());
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_DESCRIPTION, food.getDescription());
        values.put(COLUMN_PRICE, food.getPrice());
        values.put(COLUMN_TYPE, food.getType());
        long id = db.insert(TABLE_FOOD, null, values);
        if (id > 0) {
            food.setId(id);
        }
        return id;
    }

    public int updateFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESTAURANT_ID, food.getRestaurantId());
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_DESCRIPTION, food.getDescription());
        values.put(COLUMN_PRICE, food.getPrice());
        values.put(COLUMN_TYPE, food.getType());
        return db.update(TABLE_FOOD, values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(food.getId())});
    }

    public int deleteFood(long foodId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_FOOD,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(foodId)});
    }

    public List<Food> getFoodsByRestaurantAndType(long restaurantId, String type) {
        List<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
            COLUMN_ID,
            COLUMN_RESTAURANT_ID,
            COLUMN_NAME,
            COLUMN_DESCRIPTION,
            COLUMN_PRICE,
            COLUMN_TYPE
        };
        String selection = COLUMN_RESTAURANT_ID + " = ? AND " + COLUMN_TYPE + " = ?";
        String[] selectionArgs = {String.valueOf(restaurantId), type};
        Cursor cursor = db.query(TABLE_FOOD, columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Food food = new Food();
                food.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                food.setRestaurantId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_RESTAURANT_ID)));
                food.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                food.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                food.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
                food.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
                foodList.add(food);
            }
            cursor.close();
        }
        return foodList;
    }
}
