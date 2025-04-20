package com.example.assignment1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private final Activity context;
    private List<Product> products;
    private final SharedPrefManager prefManager;

    public ProductAdapter(Activity context, List<Product> products, SharedPrefManager prefManager) {
        super(context, R.layout.product_row, products);
        this.context = context;
        this.products = products;
        this.prefManager = prefManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.product_row, null, true);

        TextView nameText = rowView.findViewById(R.id.productName);
        TextView detailsText = rowView.findViewById(R.id.productDetails);
        TextView priceText = rowView.findViewById(R.id.productPrice);
        TextView quantityText = rowView.findViewById(R.id.productQuantity);
        Button btnAddToCart = rowView.findViewById(R.id.btnAddToCart);

        Product product = products.get(position);

        nameText.setText(product.getName());
        priceText.setText("Price: $" + product.getPrice());
        quantityText.setText("Available: " + product.getQuantity());

        StringBuilder detailBuilder = new StringBuilder();
        detailBuilder.append("Condition: ").append(product.getCondition());
        detailBuilder.append(" | Seller: ").append(product.getSeller());

        if (product.isFreeShipping()) {
            detailBuilder.append(" | Free Shipping");
        }

        if (product.isWarrantyIncluded()) {
            detailBuilder.append(" | Warranty Included");
        }

        detailsText.setText(detailBuilder.toString());

        btnAddToCart.setOnClickListener(v -> {
            if (product.getQuantity() > 0) {
                product.setQuantity(product.getQuantity() - 1);
                prefManager.saveProductList((ArrayList<Product>) prefManager.getProductList());
                ArrayList<Product> cart = prefManager.getCart();
                cart.add(product);
                prefManager.saveCart(cart);
                quantityText.setText("Available: " + product.getQuantity());
                Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Out of stock!", Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }

    public void updateList(List<Product> newProducts) {
        this.products = newProducts;
        clear();
        addAll(newProducts);
        notifyDataSetChanged();
    }

}
