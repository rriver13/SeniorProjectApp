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

public class Login extends AppCompatActivity {
    EditText usrEmail, usrPassword;
    Button Login_button;
    TextView Register_link;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usrEmail = findViewById(R.id.Email);
        usrPassword = findViewById(R.id.Password);
        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        Login_button = findViewById(R.id.login_button);
        Register_link = findViewById(R.id.register_link);

        Login_button.setOnClickListener(v -> {

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

            //THIS AUTHENTICATES THE USERS

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Logging in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else {
                    Toast.makeText(Login.this, "!ERROR!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}