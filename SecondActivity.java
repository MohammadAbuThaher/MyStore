package com.example.assignment1;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        welcomeText = findViewById(R.id.welcomeText);


        String name = getIntent().getStringExtra("username");


        welcomeText.setText("Welcome, " + name + " 👋");
    }
}
