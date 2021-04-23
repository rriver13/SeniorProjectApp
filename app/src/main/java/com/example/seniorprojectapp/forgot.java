package com.example.seniorprojectapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPassBttn;
    private ProgressBar progress;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        emailEditText = findViewById(R.id.fEmail);
        resetPassBttn = findViewById(R.id.reset_password_button);
        progress = findViewById(R.id.progressBar3);

        auth = FirebaseAuth.getInstance();

        resetPassBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassBttn();
            }
        });
    }

    private void resetPassBttn(){
        String email = emailEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("An Email is Required!");
            emailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Provide a Valid Email!");
            emailEditText.requestFocus();
            return;
        }

        progress.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(forgot.this, "Check Email To Reset Password!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(forgot.this, "Try Again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}