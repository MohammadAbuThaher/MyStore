package com.example.assignment1;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPrefManager {
    public boolean isFirstRun() {
        return !sharedPreferences.getBoolean("hasRun", false);
    }

    public void setFirstRunDone() {
        editor.putBoolean("hasRun", true);
        editor.apply();
    }


    private static final String PREF_NAME = "PlayTradePrefs";
    private static final String PRODUCTS_KEY = "products";
    private static final String CART_KEY = "cart";
    private static final String ORDERS_KEY = "orders"; // ✅ جديد

    private static final String USER_NAME_KEY = "user_name";
    private static final String USER_PHONE_KEY = "user_phone";
    private static final String USER_ADDRESS_KEY = "user_address";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    // Save the product list
    public void saveProductList(ArrayList<Product> productList) {
        String json = gson.toJson(productList);
        editor.putString(PRODUCTS_KEY, json);
        editor.apply();
    }

    public ArrayList<Product> getProductList() {
        String json = sharedPreferences.getString(PRODUCTS_KEY, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        ArrayList<Product> list = gson.fromJson(json, type);
        return (list != null) ? list : new ArrayList<>();
    }

    public void saveCart(ArrayList<Product> cartList) {
        String json = gson.toJson(cartList);
        editor.putString(CART_KEY, json);
        editor.apply();
    }

    public ArrayList<Product> getCart() {
        String json = sharedPreferences.getString(CART_KEY, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        ArrayList<Product> list = gson.fromJson(json, type);
        return (list != null) ? list : new ArrayList<>();
    }


    public void clearAllOrders() {
        editor.remove("orders");
        editor.apply();
    }

    public void clearCart() {
        editor.remove(CART_KEY);
        editor.apply();
    }
    public void removeFromCart(String productId) {
        ArrayList<Product> cart = getCart();
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId().equals(productId)) {
                cart.remove(i);
                break;
            }
        }
        saveCart(cart); // Update the saved cart
    }


    public void saveUserInfo(String name, String phone, String address) {
        editor.putString(USER_NAME_KEY, name);
        editor.putString(USER_PHONE_KEY, phone);
        editor.putString(USER_ADDRESS_KEY, address);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME_KEY, "Guest");
    }

    public String getUserPhone() {
        return sharedPreferences.getString(USER_PHONE_KEY, "0000000000");
    }

    public String getUserAddress() {
        return sharedPreferences.getString(USER_ADDRESS_KEY, "Unknown");
    }

    //  Save a new order
    public void saveOrder(ArrayList<Product> order) {
        ArrayList<ArrayList<Product>> allOrders = getAllOrders();
        allOrders.add(order);
        String json = gson.toJson(allOrders);
        editor.putString(ORDERS_KEY, json);
        editor.apply();
    }

    //  Retrieve all orders
    public ArrayList<ArrayList<Product>> getAllOrders() {
        String json = sharedPreferences.getString(ORDERS_KEY, null);
        Type type = new TypeToken<ArrayList<ArrayList<Product>>>() {}.getType();
        ArrayList<ArrayList<Product>> list = gson.fromJson(json, type);
        return (list != null) ? list : new ArrayList<>();
    }
}
