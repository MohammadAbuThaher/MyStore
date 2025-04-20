package com.example.assignment1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<Product> {

    private final Activity context;
    private final ArrayList<Product> cartList;
    private final SharedPrefManager prefManager;
    private final ArrayList<Product> allProducts;

    public CartAdapter(Activity context, ArrayList<Product> cartList, SharedPrefManager prefManager, ArrayList<Product> allProducts) {
        super(context, R.layout.cart_row, cartList);
        this.context = context;
        this.cartList = cartList;
        this.prefManager = prefManager;
        this.allProducts = allProducts;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.cart_row, null, true);

        TextView nameText = rowView.findViewById(R.id.cartProductName);
        TextView priceText = rowView.findViewById(R.id.cartProductPrice);
        Button btnRemove = rowView.findViewById(R.id.btnRemove);

        Product product = cartList.get(position);
        nameText.setText(product.getName());
        priceText.setText("Price: $" + product.getPrice());

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increase quantity in main product list
                for (Product p : allProducts) {
                    if (p.getId().equals(product.getId())) {
                        p.setQuantity(p.getQuantity() + 1);
                        break;
                    }
                }

                // Remove from cart
                cartList.remove(product);
                prefManager.saveCart(cartList);
                prefManager.saveProductList(allProducts);

                // Update UI
                notifyDataSetChanged();
            }
        });

        return rowView;
    }
}
