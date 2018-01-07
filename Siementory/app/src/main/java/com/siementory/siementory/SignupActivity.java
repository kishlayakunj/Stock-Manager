package com.siementory.siementory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    EditText etSignupEmail, etSignupPassword, etSignupConfirmPassword;
    Button btSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etSignupEmail = (EditText) findViewById(R.id.etSignupEmail);
        etSignupPassword = (EditText) findViewById(R.id.etSignupPassword);
        etSignupConfirmPassword = (EditText) findViewById(R.id.etSignupConfirmPassword);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btSignup).setOnClickListener(this);
    }

    private void registerUser(){

        String email = etSignupEmail.getText().toString().trim();
        String choosePassword = etSignupPassword.getText().toString().trim();
        String confirmPassword = etSignupConfirmPassword.getText().toString().trim();



        if(email.isEmpty()){
            etSignupEmail.setError("Email is required");
            etSignupEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etSignupEmail.setError("Please enter a valid email");
            etSignupEmail.requestFocus();
            return;
        }

        if(choosePassword.isEmpty()){
            etSignupPassword.setError("Choose a password");
            etSignupPassword.requestFocus();
            return;
        }

        if(choosePassword.length()<6){
            etSignupPassword.setError("Password should be atleast 6 characters");
            etSignupPassword.requestFocus();
            return;
        }


        if(confirmPassword.isEmpty()){
            etSignupConfirmPassword.setError("Choose a password");
            etSignupConfirmPassword.requestFocus();
            return;
        }

        if(confirmPassword.length()<6){
            etSignupConfirmPassword.setError("Password should be atleast 6 characters");
            etSignupConfirmPassword.requestFocus();
            return;
        }

        if(!(confirmPassword.equals(choosePassword))){
            etSignupConfirmPassword.setError("Passwords do not match");
            etSignupConfirmPassword.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(email,confirmPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"User registration successfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"You are already registered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btSignup:
                registerUser();
                break;
        }

    }
}
