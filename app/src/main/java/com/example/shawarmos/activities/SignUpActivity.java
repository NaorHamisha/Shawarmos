package com.example.shawarmos.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shawarmos.R;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail = findViewById(R.id.register_activty_username_et);
        mPassword = findViewById(R.id.register_activity_password_et);
        mConfirmPassword = findViewById(R.id.confirm_password);
        mSignUp = findViewById(R.id.register_activiry_btn);

        mSignUp.setOnClickListener(view -> {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            String confirmPassword = mConfirmPassword.getText().toString();

        });
    }
}
