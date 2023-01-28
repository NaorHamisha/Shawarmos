package com.example.shawarmos.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shawarmos.R;

import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLogin = findViewById(R.id.login_button);

        mLogin.setOnClickListener(view -> {
            String username = mUsername.getText().toString();
            String password = mPassword.getText().toString();

            // Implement authentication logic here
            // For example, send a request to a server to check if the user exists and the password is correct
        });
    }
}