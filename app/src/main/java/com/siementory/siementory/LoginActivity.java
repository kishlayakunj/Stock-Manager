package com.siementory.siementory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    EditText etSigninEmail, etSigninPassword;
    TextView tvCreateAccount;
    Button btLogin;
    String emailID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etSigninEmail=(EditText)findViewById(R.id.etSigninEmail);
        etSigninPassword=(EditText)findViewById(R.id.etSigninPassword);
        mAuth= FirebaseAuth.getInstance();
        findViewById(R.id.tvCreateAccount).setOnClickListener(this);
        findViewById(R.id.btSignin).setOnClickListener(this);
    }




    private void loginUser(){
        final String email = etSigninEmail.getText().toString().trim();
        String password = etSigninPassword.getText().toString().trim();



        if(email.isEmpty()){
            etSigninEmail.setError("Email is required");
            etSigninEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etSigninEmail.setError("Please enter a valid email");
            etSigninEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etSigninPassword.setError("Choose a password");
            etSigninPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            etSigninPassword.setError("Password should be atleast 6 characters");
            etSigninPassword.requestFocus();
            return;
        }




        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    emailID=email;

                    Toast.makeText(getApplicationContext(),"log in successfull",Toast.LENGTH_SHORT).show();


                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference().child("users");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds:dataSnapshot.getChildren()
                                    ) {
                                        if(ds.child("email").getValue().toString().equals(email)){
                                            String userID=ds.getKey();
                                            Log.d("USER ID : ", userID);
                                            Intent intent = new Intent(LoginActivity.this,ChooseProjectActivity.class);
                                            intent.putExtra("EMAIL_ID", emailID);
                                            intent.putExtra("USER_ID", userID);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        }
                                        else continue;


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });












                }
                else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                }


            }
        });


    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvCreateAccount:
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
                break;

            case R.id.btSignin:
                loginUser();
                break;
        }
    }



}
