package com.siementory.siementory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewStockFromListViewActivity extends AppCompatActivity implements View.OnClickListener {
String projectID,stockID,stockName, userID;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    TextView tvStockName, tvStockID, tvStockQuantity, tvStockCPI, tvStockValue;
    Button btStockHistory2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            stockID = extras.getString("STOCK_ID");
            //The key argument here must match that used in the other activity
        }

         extras = getIntent().getExtras();
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
        setContentView(R.layout.activity_view_stock_from_list_view);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("projects").child(projectID).child("stock_details");
        tvStockName = (TextView) findViewById(R.id.tvStockName);
        tvStockID=(TextView) findViewById(R.id.tvValueOne);
        tvStockQuantity=(TextView) findViewById(R.id.tvValueTwo);
        tvStockCPI = (TextView) findViewById(R.id.tvValueThree);
        tvStockValue = (TextView) findViewById(R.id.tvValueFour);
        findViewById(R.id.btStockHistory2).setOnClickListener(this);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    if(ds.getKey().toString().equals(stockID)) {
                        Log.d("MESSAGE",ds.getKey().toString());
                        stockName=ds.child("stock_name").getValue().toString();
                        tvStockName.setText(ds.child("stock_name").getValue().toString());
                        tvStockID.setText(ds.getKey().toString());
                        tvStockQuantity.setText(ds.child("stock_quantity").getValue().toString());
                        tvStockCPI.setText(ds.child("cpi").getValue().toString());

                        int temp1 = Integer.parseInt(tvStockQuantity.getText().toString());
                        int temp2 = Integer.parseInt(tvStockCPI.getText().toString());
                        int temp3 = temp1 * temp2;
                        String totalCost = String.valueOf(temp3);
                        tvStockValue.setText(totalCost);

                    }
                    //else{
                     //   Toast.makeText(getApplicationContext(),"There are no stocks to display",Toast.LENGTH_LONG).show();
                    //}
            }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btStockHistory2:


        Intent intent = new Intent(ViewStockFromListViewActivity.this,SpecificStockIssueListActivity.class);
        intent.putExtra("USER_ID", userID);
        intent.putExtra("STOCK_NAME", stockName);
        intent.putExtra("PROJECT_ID",projectID);
        startActivity(intent);
    }
    }
}
