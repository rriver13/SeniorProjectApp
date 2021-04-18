package com.example.seniorprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText usrFullName, usrEmail, usrPassword, usrPhone;
    Button Create_button;
    TextView Login_link;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usrFullName = findViewById(R.id.fullName);
        usrEmail = findViewById(R.id.Email);
        usrPassword = findViewById(R.id.Password);
        usrPhone = findViewById(R.id.Phone);
        Create_button = findViewById(R.id.create_account_button);
        Login_link = findViewById(R.id.login_link);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //This is if a user already had an account

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        Create_button.setOnClickListener(v -> {
            String email = usrEmail.getText().toString().trim();
            String password = usrPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                usrEmail.setError("Your Email is Required ");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                usrPassword.setError("A Password is Required ");
                return;
            }
            if (password.length() < 7) {
                usrPassword.setError("Your Password Must be >= 7 Characters");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            //THIS FUNCTION REGISTERS THE USER TO THE FIREBASE DATABASE

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Register.this, "User is Created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else {
                    Toast.makeText(Register.this, "!ERROR!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            });

        });
    }}