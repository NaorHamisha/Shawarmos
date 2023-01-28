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

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mConfirmPassword = findViewById(R.id.confirm_password);
        mSignUp = findViewById(R.id.sign_up_button);

        mSignUp.setOnClickListener(view -> {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            String confirmPassword = mConfirmPassword.getText().toString();

            // Implement sign-up logic here
            // For example, check if the email and password fields are not empty and the password matches the confirm password
            // Then, send a request to a server to create a new user
        });
    }
}
