package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inputName, inputPhone, inputAddress;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputName = findViewById(R.id.inputName);
        inputPhone = findViewById(R.id.inputPhone);
        inputAddress = findViewById(R.id.inputAddress);
        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString().trim();
                String phone = inputPhone.getText().toString().trim();
                String address = inputAddress.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    //
                    //      eSave user info to SharedPreferences
                    SharedPrefManager prefManager = new SharedPrefManager(MainActivity.this);
                    prefManager.saveUserInfo(name, phone, address);

                    // Move to HomeActivity
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
