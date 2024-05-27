package com.example.virtualkissapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = findViewById(R.id.sendButton);
        Button receiveButton = findViewById(R.id.receiveButton);

        sendButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SendMessage.class)));

        receiveButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ReceiveMessage.class)));
    }
}