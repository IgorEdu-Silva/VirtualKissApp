package com.example.virtualkissapp;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class SendMessage extends AppCompatActivity {

    private final BroadcastReceiver responseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.example.virtualkissapp.RESPONSE_ACTION".equals(intent.getAction())) {
                String response = intent.getStringExtra("response");
                Toast.makeText(SendMessage.this, "Resposta recebida: " + response, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean isReceiverRegistered = false;

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        final EditText messageInput = findViewById(R.id.messageInput);
        final ImageButton generateQRButton = findViewById(R.id.generateQRButton);
        final ImageView qrCodeImageView = findViewById(R.id.qrCodeImageView);
        final ImageButton btnBack = findViewById(R.id.btnBack);

        generateQRButton.setOnClickListener(view -> {
            String text = messageInput.getText().toString();

            if (text.isEmpty()) {
                Toast.makeText(SendMessage.this, "Por favor, insira uma mensagem.", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(SendMessage.this, "Gerando QR Code, por favor aguarde.", Toast.LENGTH_SHORT).show();

            MultiFormatWriter writer = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512);
                Bitmap bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565);
                for (int x = 0; x < 512; x++) {
                    for (int y = 0; y < 512; y++) {
                        bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                    }
                }
                qrCodeImageView.setImageBitmap(bitmap);

                messageInput.setVisibility(View.GONE);
                generateQRButton.setVisibility(View.GONE);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        });

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(SendMessage.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.virtualkissapp.RESPONSE_ACTION");
        registerReceiver(responseReceiver, filter);
        isReceiverRegistered = true;
    }

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onResume() {
        super.onResume();
        if (!isReceiverRegistered) {
            IntentFilter filter = new IntentFilter();
            filter.addAction("com.example.virtualkissapp.RESPONSE_ACTION");
            registerReceiver(responseReceiver, filter);
            isReceiverRegistered = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isReceiverRegistered) {
            unregisterReceiver(responseReceiver);
            isReceiverRegistered = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isReceiverRegistered) {
            unregisterReceiver(responseReceiver);
            isReceiverRegistered = false;
        }
    }
}