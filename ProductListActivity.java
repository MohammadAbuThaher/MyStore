package com.example.assignment1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    ListView productListView;
    SharedPrefManager prefManager;
    ArrayList<Product> productList;
    ArrayList<Product> filteredList;
    ProductAdapter adapter;

    EditText searchInput;
    Spinner categorySpinner;
    TextView userInfoText;
    RadioGroup radioGroup;
    RadioButton radioNew, radioUsed;
    CheckBox checkFreeShipping, checkWarranty;
    Switch switchDiscount;
    Button btnShowAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        productListView = findViewById(R.id.productListView);
        searchInput = findViewById(R.id.searchInput);
        categorySpinner = findViewById(R.id.categorySpinner);
        userInfoText = findViewById(R.id.userInfoText);
        radioGroup = findViewById(R.id.radioGroup);
        radioNew = findViewById(R.id.radioNew);
        radioUsed = findViewById(R.id.radioUsed);
        checkFreeShipping = findViewById(R.id.checkFreeShipping);
        checkWarranty = findViewById(R.id.checkWarranty);
        switchDiscount = findViewById(R.id.switchDiscount);
        btnShowAll = findViewById(R.id.btnShowAll);

        prefManager = new SharedPrefManager(this);

        String userName = prefManager.getUserName();
        String userAddress = prefManager.getUserAddress();
        userInfoText.setText("User: " + userName + " | Address: " + userAddress);

        productList = prefManager.getProductList();

        if (productList == null || productList.isEmpty()) {
            productList = ProductDefaults.getDefaults();
            prefManager.saveProductList(productList);
        } else {
            ArrayList<Product> defaults = ProductDefaults.getDefaults();
            for (Product def : defaults) {
                boolean exists = false;
                for (Product p : productList) {
                    if (p.getId().equals(def.getId())) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    productList.add(def);
                }
            }
            prefManager.saveProductList(productList);
        }

        filteredList = new ArrayList<>(productList);
        adapter = new ProductAdapter(this, filteredList, prefManager);
        productListView.setAdapter(adapter);

        String[] categories = {"All", "Console", "Game", "Accessory"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        View.OnClickListener filterListener = v -> filterProducts();
        radioNew.setOnClickListener(filterListener);
        radioUsed.setOnClickListener(filterListener);
        checkFreeShipping.setOnClickListener(filterListener);
        checkWarranty.setOnClickListener(filterListener);
        switchDiscount.setOnClickListener(filterListener);

        btnShowAll.setOnClickListener(v -> {
            searchInput.setText("");
            categorySpinner.setSelection(0);
            radioGroup.clearCheck();
            checkFreeShipping.setChecked(false);
            checkWarranty.setChecked(false);
            switchDiscount.setChecked(false);
            filterProducts();
        });

        filterProducts();
    }

    private void filterProducts() {
        String searchText = searchInput.getText().toString().toLowerCase().trim();
        String selectedCategory = categorySpinner.getSelectedItem().toString();
        String selectedCondition = radioNew.isChecked() ? "New" : radioUsed.isChecked() ? "Used" : "";

        boolean filterFreeShipping = checkFreeShipping.isChecked();
        boolean filterWarranty = checkWarranty.isChecked();
        boolean onlyDiscounted = switchDiscount.isChecked();

        filteredList.clear();

        for (Product p : productList) {
            boolean matchesName = searchText.isEmpty() || p.getName().toLowerCase().contains(searchText);
            boolean matchesCategory = selectedCategory.equals("All") || p.getCategory().equalsIgnoreCase(selectedCategory);
            boolean matchesCondition = selectedCondition.isEmpty() || p.getCondition().equalsIgnoreCase(selectedCondition);
            boolean matchesShipping = !filterFreeShipping || p.isFreeShipping();
            boolean matchesWarranty = !filterWarranty || p.isWarrantyIncluded();
            boolean matchesDiscount = !onlyDiscounted || p.getPrice() < 30.0;

            if (matchesName && matchesCategory && matchesCondition && matchesShipping && matchesWarranty && matchesDiscount) {
                filteredList.add(p);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
