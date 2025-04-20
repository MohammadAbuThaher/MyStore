package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView welcomeText;
    Button btnViewProducts, btnAddProduct, btnViewCart, btnMyOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcomeText = findViewById(R.id.welcomeText);
        btnViewProducts = findViewById(R.id.btnViewProducts);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnViewCart = findViewById(R.id.btnViewCart);
        btnMyOrders = findViewById(R.id.btnMyOrders);

        SharedPrefManager prefManager = new SharedPrefManager(this);
        String name = prefManager.getUserName();
        welcomeText.setText("Welcome, " + name + "!");

        btnViewProducts.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, ProductListActivity.class)));

        btnAddProduct.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, AddProductActivity.class)));

        btnViewCart.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, CartActivity.class)));

        btnMyOrders.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, MyOrdersActivity.class)));
    }
}
