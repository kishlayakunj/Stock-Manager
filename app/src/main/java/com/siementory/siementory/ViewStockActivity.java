package com.siementory.siementory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewStockActivity extends AppCompatActivity implements View.OnClickListener {
    String projectID, userID;
    Button btViewAllStock,btViewByName;
    EditText etViewByName;
    ImageView ivSearch;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



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
        setContentView(R.layout.activity_view_stock);
        findViewById(R.id.btViewAllStock).setOnClickListener(this);
        etViewByName = (EditText) findViewById(R.id.etViewByName);
        ivSearch=(ImageView) findViewById(R.id.ivSearch);
        ivSearch.setOnClickListener(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("projects").child(projectID).child("stock_details");





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btViewAllStock:
                final Intent intent = new Intent(ViewStockActivity.this,DisplayAllStockActivity.class);
                intent.putExtra("USER_ID", userID);
                intent.putExtra("PROJECT_ID", projectID);
                startActivity(intent);
                break;

            case R.id.ivSearch:
                final String stockName = etViewByName.getText().toString().trim();

                if(stockName.isEmpty()){
                    etViewByName.setError("Stock name cannot be empty");
                    etViewByName.requestFocus();
                }


                else { databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int flag=0;
                        for (DataSnapshot ds:dataSnapshot.getChildren()
                                ) {
                            if(ds.child("stock_name").getValue().toString().equalsIgnoreCase(stockName)){
                                flag=1;
                                Intent intent1 = new Intent(ViewStockActivity.this, ViewSpecificStockActivity.class);
                                intent1.putExtra("USER_ID", userID);
                                intent1.putExtra("STOCK_NAME", stockName);
                                intent1.putExtra("PROJECT_ID", projectID);
                                startActivity(intent1);
                                break;


                            }
                            else continue;
                            }
                            if(flag==0){
                                    etViewByName.setError("Stock does not exist");
                                    etViewByName.requestFocus();

                            // else {
                            //   etViewByName.setError("Stock does not exist");
                            //  etViewByName.requestFocus();

                            //Toast.makeText(getApplicationContext(),"You fucked up kid",Toast.LENGTH_LONG).show();
                            //  }



                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });}
                break;
        }
    }
}
