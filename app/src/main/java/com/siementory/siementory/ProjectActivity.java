package com.siementory.siementory;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class ProjectActivity extends AppCompatActivity implements View.OnClickListener {
    String projectID, userID;
    Button  btAddStock,btRegister,btViewStock,btSignout, btScarceStock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            projectID = extras.getString("PROJECT_ID");
            //The key argument here must match that used in the other activity
        }

         extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getString("USER_ID");
            //The key argument here must match that used in the other activity
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        findViewById(R.id.btAddStock).setOnClickListener(this);
        findViewById(R.id.btRegister).setOnClickListener(this);
        findViewById(R.id.btViewStock).setOnClickListener(this);
        findViewById(R.id.btSignout).setOnClickListener(this);
        findViewById(R.id.btScarceStock).setOnClickListener(this);
        Log.d("Project ID screen 1", projectID.toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btAddStock:
                Intent intent = new Intent(ProjectActivity.this,AddStockActivity.class);
                intent.putExtra("USER_ID",userID);
                intent.putExtra("PROJECT_ID", projectID);
                startActivity(intent);
                break;

            case R.id.btViewStock:
               // Intent intent2 =new Intent(ProjectActivity.this,)
                Intent intent1 = new Intent(ProjectActivity.this, ViewStockActivity.class);
                intent1.putExtra("USER_ID",userID);
                intent1.putExtra("PROJECT_ID", projectID);
                startActivity(intent1);
                break;

            case R.id.btRegister:
                Intent intent2 = new Intent(ProjectActivity.this,RegisterActivity.class);
                intent2.putExtra("USER_ID",userID);
                intent2.putExtra("PROJECT_ID", projectID);
                startActivity(intent2);
                break;

            case R.id.btSignout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(),"You are signed out successfully",Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(ProjectActivity.this, LoginActivity.class);
                startActivity(intent3);
                finish();
                break;


            case R.id.btScarceStock:
                Intent intent4 = new Intent(ProjectActivity.this, ScarceStockActivity.class);
                intent4.putExtra("USER_ID",userID);
                intent4.putExtra("PROJECT_ID", projectID);

                startActivity(intent4);
                break;
        }
    }
}
