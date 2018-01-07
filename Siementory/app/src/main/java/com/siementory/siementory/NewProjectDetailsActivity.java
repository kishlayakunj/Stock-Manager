package com.siementory.siementory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class NewProjectDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Button btCreateProject,btSignout;
    EditText etProjectName_new,etSupervisorName_new,etDateOfCommencement_new,etProjectPassword_new;


    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project_details);
        findViewById(R.id.btCreateProject).setOnClickListener(this);
        findViewById(R.id.btSignout).setOnClickListener(this);

        etProjectName_new=(EditText)findViewById(R.id.etProjectName_new);
        etSupervisorName_new=(EditText)findViewById(R.id.etSupervisorName_new);
        etDateOfCommencement_new=(EditText)findViewById(R.id.etDateOfCommencement_new);
        etProjectPassword_new=(EditText)findViewById(R.id.etProjectPassword_new);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("project");
    }


    private void createProject(){
        String projectName = etProjectName_new.getText().toString().trim();
        String supervisorName = etSupervisorName_new.getText().toString().trim();
        String dateOfCommencement = etDateOfCommencement_new.getText().toString().trim();
        String projectPassword = etProjectPassword_new.getText().toString().trim();

        if(projectName.isEmpty()){
            etProjectPassword_new.setError("Project name is required");
            etProjectPassword_new.requestFocus();
            return;
        }

        if(supervisorName.isEmpty()){
            etSupervisorName_new.setError("Supervisor name is required");
            etSupervisorName_new.requestFocus();
            return;
        }


        if(dateOfCommencement.isEmpty()){
            etDateOfCommencement_new.setError("Please enter date");
            etDateOfCommencement_new.requestFocus();
            return;
        }

        if(projectPassword.isEmpty()){
            etProjectPassword_new.setError("Please Enter Pin (0-9)");
            etProjectPassword_new.requestFocus();
            return;
        }

        if(projectPassword.length()!=4){
            etProjectPassword_new.setError("Pin should be of 4 digits");
            etProjectPassword_new.requestFocus();
            return;
        }


        DatabaseReference newProject = mDatabase.push();

        newProject.child("project_name").setValue(projectName);
        newProject.child("supervisor_name").setValue(supervisorName);
        newProject.child("date").setValue(dateOfCommencement);
        newProject.child("pin").setValue(projectPassword);



    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btCreateProject:
                createProject();
                Toast.makeText(getApplicationContext(),"Project Created Successfully!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewProjectDetailsActivity.this,ProjectActivity.class);
                startActivity(intent);


                break;

            case R.id.btSignout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(),"You are signed out successfully",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(NewProjectDetailsActivity.this, LoginActivity.class);
                startActivity(intent2);
                break;
        }


    }
}

