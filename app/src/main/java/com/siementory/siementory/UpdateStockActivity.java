package com.siementory.siementory;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateStockActivity extends AppCompatActivity {
    String projectID, stockName, updatedQuantity, stockID, userID;
    private static int TIME_OUT = 1000;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            projectID = extras.getString("PROJECT_ID");
            //The key argument here must match that used in the other activity
        }


        extras = getIntent().getExtras();
        if (extras != null) {
            stockName = extras.getString("STOCK_NAME");
            //The key argument here must match that used in the other activity
        }

        extras = getIntent().getExtras();
        if (extras != null) {
            updatedQuantity = extras.getString("UPDATED_QUANTITY");
            //The key argument here must match that used in the other activity
        }

        extras = getIntent().getExtras();
        if (extras != null) {
            stockID = extras.getString("STOCK_ID");
            //The key argument here must match that used in the other activity
        }
        extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getString("USER_ID");
            //The key argument here must match that used in the other activity
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("projects").child(projectID);
        databaseReference.child("stock_details").child(stockID).child("stock_quantity").setValue(updatedQuantity);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(UpdateStockActivity.this, ProjectActivity.class);
                i.putExtra("USER_ID", userID);
                i.putExtra("PROJECT_ID", projectID);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }
}
