package com.siementory.siementory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IssueNewStockActivity extends AppCompatActivity implements View.OnClickListener {
    String projectID, stockName, stockID, userID;
    private FirebaseDatabase firebaseDatabase, firebaseDatabase2;
    private DatabaseReference databaseReference, databaseReference2;
    TextView tvStockName, tvQuantityPresent;
    EditText etRecieverName,etIssueQuantity,etDOI;
    Button btIssue;
    String getPresentQuantity, updatedQuantity;
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
            userID = extras.getString("USER_ID");
            //The key argument here must match that used in the other activity
        }





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_new_stock);

        tvStockName = (TextView) findViewById(R.id.tvStockName);
        tvQuantityPresent = (TextView) findViewById(R.id.tvQuantityExisting);
        etRecieverName = (EditText) findViewById(R.id.etRecieverName);
        etIssueQuantity = (EditText) findViewById(R.id.etIssueQuantity);
        etDOI = (EditText) findViewById(R.id.etDOI);
        findViewById(R.id.btIssue).setOnClickListener(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("projects").child(projectID);
        databaseReference.child("stock_details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()
                        ) {
                    if (ds.child("stock_name").getValue().toString().equalsIgnoreCase(stockName)) {
                        stockID = (ds.getKey());
                        tvStockName.setText(ds.child("stock_name").getValue().toString());
                        stockName = tvStockName.getText().toString();
                        tvQuantityPresent.setText(ds.child("stock_quantity").getValue().toString());
                        getPresentQuantity = tvQuantityPresent.getText().toString();
                        break;


                    } else continue;


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void createIssueNode(){
        String recieverName = etRecieverName.getText().toString().trim();
        final String issueQuantity = etIssueQuantity.getText().toString().trim();
        String doi = etDOI.getText().toString().trim();

        if (recieverName.isEmpty()) {
            etRecieverName.setError("Receiver name is required");
            etRecieverName.requestFocus();
        }

        if (issueQuantity.isEmpty()) {
            etIssueQuantity.setError("Please enter quantity");
            etIssueQuantity.requestFocus();
        }
        if (doi.isEmpty()) {
            etDOI.setError("Please enter a valid date");
            etDOI.requestFocus();
        }

        int temp1 = Integer.parseInt(getPresentQuantity);
        int temp2 = Integer.parseInt(issueQuantity);
        if(temp1>=temp2)
        {
            Toast.makeText(getApplicationContext(),"Stock issued successfully!", Toast.LENGTH_LONG).show();
            int temp3 = temp1 - temp2;
            updatedQuantity = String.valueOf(temp3);
            firebaseDatabase2 = FirebaseDatabase.getInstance();
            databaseReference2 = firebaseDatabase.getReference().child("users").child(userID).child("projects").child(projectID);

            databaseReference2.child("stock_details").child(stockID).child("stock_quantity").setValue(updatedQuantity);
            DatabaseReference issueReference = databaseReference2.child("issue_details").push();
            issueReference.child("stock_name").setValue(stockName);
            issueReference.child("receiver_name").setValue(recieverName);
            issueReference.child("quantity_issued").setValue(issueQuantity);
            issueReference.child("date_of_issue").setValue(doi);

           Intent intent1 = new Intent(IssueNewStockActivity.this, RegisterActivity.class);
            intent1.putExtra("USER_ID",userID);
            intent1.putExtra("PROJECT_ID", projectID);
            //intent1.putExtra("UPDATED_QUANTITY",updatedQuantity);
            //intent.putExtra("STOCK_NAME",stockName);
            //intent.putExtra("STOCK_ID",stockID);
            startActivity(intent1);
            finish();


        }
        else {
            updatedQuantity=getPresentQuantity;
            databaseReference.child("stock_details").child(stockID).child("stock_quantity").setValue(updatedQuantity);
            Toast.makeText(getApplicationContext(),"Please enter issue quantity less than stock quantity", Toast.LENGTH_LONG).show();
            //Intent intent = new Intent(IssueNewStockActivity.this, IssueNewStockActivity.class);
            Intent intent = getIntent();
            intent.putExtra("USER_ID",userID);
            intent.putExtra("PROJECT_ID", projectID);
            finish();
            startActivity(intent);
        }



    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btIssue:
                createIssueNode();
                break;
        }
    }
}
