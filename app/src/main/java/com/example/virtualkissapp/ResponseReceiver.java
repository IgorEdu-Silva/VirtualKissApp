package com.example.virtualkissapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ResponseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.example.virtualkissapp.RESPONSE_ACTION".equals(intent.getAction())) {
            String response = intent.getStringExtra("response");
            Toast.makeText(context, "Resposta recebida: " + response, Toast.LENGTH_SHORT).show();
        }
    }
}
