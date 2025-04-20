package com.example.assignment1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.UUID;

public class AddProductActivity extends AppCompatActivity {

    EditText inputName, inputCategory, inputPrice, inputQuantity, inputCondition, inputSeller;
    CheckBox checkFreeShipping, checkWarranty;
    Button btnAddProduct;
    SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Bind views
        inputName = findViewById(R.id.inputName);
        inputCategory = findViewById(R.id.inputCategory);
        inputPrice = findViewById(R.id.inputPrice);
        inputQuantity = findViewById(R.id.inputQuantity);
        inputCondition = findViewById(R.id.inputCondition);
        inputSeller = findViewById(R.id.inputSeller);
        checkFreeShipping = findViewById(R.id.checkFreeShipping);
        checkWarranty = findViewById(R.id.checkWarranty);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        prefManager = new SharedPrefManager(this);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Read values
                    String id = UUID.randomUUID().toString();
                    String name = inputName.getText().toString().trim();
                    String category = inputCategory.getText().toString().trim();
                    double price = Double.parseDouble(inputPrice.getText().toString().trim());
                    int quantity = Integer.parseInt(inputQuantity.getText().toString().trim());
                    String condition = inputCondition.getText().toString().trim();
                    String seller = inputSeller.getText().toString().trim();
                    boolean freeShipping = checkFreeShipping.isChecked();
                    boolean warrantyIncluded = checkWarranty.isChecked();

                    // Create product and save
                    Product newProduct = new Product(id, name, category, price, quantity, condition, seller, freeShipping, warrantyIncluded);
                    ArrayList<Product> list = prefManager.getProductList();
                    list.add(newProduct);
                    prefManager.saveProductList(list);

                    Toast.makeText(AddProductActivity.this, "Product added!", Toast.LENGTH_SHORT).show();
                    finish(); // Go back

                } catch (Exception e) {
                    Toast.makeText(AddProductActivity.this, "Please enter valid data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
