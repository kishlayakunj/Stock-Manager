package com.siementory.siementory;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChooseProjectActivity extends AppCompatActivity implements View.OnClickListener {

    Button btNewProject;
    Button btExistingProject;
    Button btSignout;
    ListView listView;
    List Userlist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_project);
        findViewById(R.id.btNewProject).setOnClickListener(this);
        findViewById(R.id.btExistingProject).setOnClickListener(this);
        findViewById(R.id.btSignout).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btNewProject:
                Intent intent = new Intent(ChooseProjectActivity.this, NewProjectDetailsActivity.class);
                startActivity(intent);
                break;

            case R.id.btExistingProject:
                //listView=(ListView)findViewById(R.id.listView);
                Intent intent1 = new Intent(ChooseProjectActivity.this, DisplayProjectNamesActivity.class);
                startActivity(intent1);
                break;

            case R.id.btSignout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(),"You are signed out successfully",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(ChooseProjectActivity.this, LoginActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
