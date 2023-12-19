package com.example.myhivemq;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputEditText username = findViewById(R.id.inputUsername);
        TextInputEditText password = findViewById(R.id.inputPassword);
        Button btnLogin = findViewById(R.id.button4);

        btnLogin.setOnClickListener(v -> {
            if (username.getText().toString().equals("client") && password.getText().toString().equals("Tranconghai2017")) {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Login failure");
                builder.setIcon(R.drawable.ic_baseline_error_24);
                builder.setMessage("Username or password is incorrect");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();
            }
        });
    }
}