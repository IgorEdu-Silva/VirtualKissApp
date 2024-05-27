package com.example.virtualkissapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class ReceiveMessage extends AppCompatActivity {

    private CaptureManager capture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_message);

        DecoratedBarcodeView barcodeScannerView = findViewById(R.id.barcode_scanner);
        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();

        barcodeScannerView.decodeSingle(result -> showMessageDialog(result.getText()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    private void showMessageDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Cantada:")
                .setNegativeButton("Próximo", (dialog, which) -> sendResponseToEmitter("Próximo"))
                .setNeutralButton("Vale um celinho", (dialog, which) -> sendResponseToEmitter("Vale um celinho"))
                .setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void sendResponseToEmitter(String response) {
        Intent intent = new Intent("com.example.virtualkissapp.RESPONSE_ACTION");
        intent.putExtra("response", response);
        sendBroadcast(intent);
    }

}