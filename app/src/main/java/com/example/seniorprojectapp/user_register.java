package com.example.seniorprojectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class user_register extends AppCompatActivity implements View.OnClickListener {

    private TextView header, user_register;
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextPhone;
    private ProgressBar progressBar;
    private FirebaseAuth rAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        rAuth = FirebaseAuth.getInstance();

        header = findViewById(R.id.header);
        header.setOnClickListener(this);

        user_register = (Button) findViewById(R.id.register_button);
        user_register.setOnClickListener(this);

        editTextFullName = findViewById(R.id.fullName);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextPhone = findViewById(R.id.phone);

        progressBar = findViewById(R.id.progressBar2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header:
            startActivity(new Intent(this, MainActivity.class));
            break;

            case R.id.register_button:
                register_users();
                break;
        }

    }

    private void register_users() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (fullName.isEmpty()){
            editTextFullName.setError("Full Name is Required!");
            editTextFullName.requestFocus();
            return;
        }

        if (email.isEmpty()){
            editTextEmail.setError("An Email is Required!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Provide A Valid Email!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("A Password is Required!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            editTextPassword.setError("Min Password Length is 6 Characters!");
            editTextPassword.requestFocus();
            return;
        }

        if (phone.isEmpty()){
            editTextPhone.setError("Enter Phone # ");
            editTextPhone.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        rAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    sUsers user = new sUsers(fullName, phone, email);

//This registers the user to the Firebase Database
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(com.example.seniorprojectapp.user_register.this, "This User Has Been Registered", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);

                            }else {
                                Toast.makeText(com.example.seniorprojectapp.user_register.this, "Failed to Register the User. Try Again!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }else {
                    Toast.makeText(com.example.seniorprojectapp.user_register.this, "Failed to Register the User. Try Again!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}