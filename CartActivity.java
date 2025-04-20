package com.example.assignment1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ListView cartListView;
    TextView userInfoText, totalText;
    Button checkoutButton, clearCartButton;
    SharedPrefManager prefManager;
    ArrayList<Product> cart;
    ArrayList<String> itemStrings;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cartListView);
        userInfoText = findViewById(R.id.userInfoText);
        totalText = findViewById(R.id.totalText);
        checkoutButton = findViewById(R.id.checkoutButton);

        prefManager = new SharedPrefManager(this);
        cart = prefManager.getCart();

        itemStrings = new ArrayList<>();
        for (Product p : cart) {
            itemStrings.add(p.getName() + " - $" + p.getPrice());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemStrings);
        cartListView.setAdapter(adapter);

        String userName = prefManager.getUserName();
        String userAddress = prefManager.getUserAddress();
        userInfoText.setText("User: " + userName + "\nAddress: " + userAddress);

        updateTotal();




        checkoutButton.setOnClickListener(view -> {
            double finalTotal = calculateTotal();

            new AlertDialog.Builder(CartActivity.this)
                    .setTitle("Confirm Checkout")
                    .setMessage("Are you sure you want to checkout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        prefManager.saveOrder(new ArrayList<>(cart));
                        prefManager.clearCart();
                        cart.clear();
                        itemStrings.clear();
                        adapter.notifyDataSetChanged();
                        updateTotal();

                        Toast.makeText(CartActivity.this, "Paid $" + String.format("%.2f", finalTotal), Toast.LENGTH_LONG).show();
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });



    }

    private double calculateTotal() {
        double total = 0;
        for (Product p : cart) {
            total += p.getPrice();
        }
        return total;
    }

    private void updateTotal() {
        totalText.setText("Total: $" + String.format("%.2f", calculateTotal()));
    }
}
