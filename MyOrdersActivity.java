package com.example.assignment1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {

    ListView ordersListView;
    TextView userInfoText;
    Button btnClearOrders;
    SharedPrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        ordersListView = findViewById(R.id.ordersListView);
        userInfoText = findViewById(R.id.userInfoText);
        btnClearOrders = findViewById(R.id.btnClearOrders);

        prefManager = new SharedPrefManager(this);

        String name = prefManager.getUserName();
        String address = prefManager.getUserAddress();
        userInfoText.setText("User: " + name + "\nAddress: " + address);

        loadOrders();

        btnClearOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefManager.clearAllOrders();
                Toast.makeText(MyOrdersActivity.this, "All orders cleared", Toast.LENGTH_SHORT).show();
                loadOrders();
            }
        });
    }

    private void loadOrders() {
        ArrayList<ArrayList<Product>> allOrders = prefManager.getAllOrders();
        ArrayList<String> displayList = new ArrayList<>();

        int orderNumber = 1;
        for (ArrayList<Product> order : allOrders) {
            displayList.add("Order #" + orderNumber);
            for (Product p : order) {
                displayList.add(" - " + p.getName() + " ($" + p.getPrice() + ")");
            }
            orderNumber++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        ordersListView.setAdapter(adapter);
    }
}
