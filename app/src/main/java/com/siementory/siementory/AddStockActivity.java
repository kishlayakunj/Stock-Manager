package com.siementory.siementory;

import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddStockActivity extends AppCompatActivity implements View.OnClickListener {

    String projectID, userID;

    private DatabaseReference mDatabase;
    Button btAddMore, btStockDone;
    int count=0;
    EditText etStockName_new,etStockID_new, etStockQuantity_new, etCPI_new;






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
        setContentView(R.layout.activity_add_stock);
        findViewById(R.id.btAddMore).setOnClickListener(this);
        findViewById(R.id.btStockDone).setOnClickListener(this);
        etStockName_new = (EditText) findViewById(R.id.etStockName_new);
        //etStockID_new = (EditText) findViewById(R.id.etStockID_new);
        etStockQuantity_new = (EditText) findViewById(R.id.etStockQuantity_new);
        etCPI_new = (EditText) findViewById(R.id.etCPI_new);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("projects").child(projectID);

        //databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://siementory-a0f2f.firebaseio.com/projects");
        Log.d("Project ID screen 2", projectID.toString());

    }

    private void addStock(){
       final String stockName = etStockName_new.getText().toString().trim();
   //    final String stockID = etStockID_new.getText().toString().trim();
       final String stockQuantity = etStockQuantity_new.getText().toString().trim();
       final String cpi = etCPI_new.getText().toString().trim();

       final int totalPrice;

        if(stockName.isEmpty()){
            etCPI_new.setError("Stock name is required");
            etCPI_new.requestFocus();
            return;
        }


        if(stockQuantity.isEmpty()){
            etStockQuantity_new.setError("Please enter quantity");
            etStockQuantity_new.requestFocus();
            return;
        }

        if(cpi.isEmpty()){
            etCPI_new.setError("Please enter cost per item");
            etCPI_new.requestFocus();
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("projects").child(projectID);
        mDatabase.child("stock_details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              /*  if(!dataSnapshot.hasChildren()){

                    DatabaseReference newProject = mDatabase.child("stock_details").push();
                    newProject.child("stock_name").setValue(stockName);
                    newProject.child("stock_quantity").setValue(stockQuantity);
                    newProject.child("cpi").setValue(cpi);

                }*/
                //else {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    int Flag=0;
                    if (ds.child("stock_name").getValue().toString().equalsIgnoreCase(stockName)) {
                        String stockID = ds.getKey().toString();
                        String currentQuantity = ds.child("stock_quantity").getValue().toString();
                        updateStock(stockID, currentQuantity, stockQuantity);
                        Flag=1;
                        count++;

                        break;

                    }
                    if(Flag==0)
                    {
                        continue;

                    }


                }

                if(count==0){

                    DatabaseReference newProject = mDatabase.child("stock_details").push();
                    newProject.child("stock_name").setValue(stockName);
                    newProject.child("stock_quantity").setValue(stockQuantity);
                    newProject.child("cpi").setValue(cpi);
                }

            //}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }

    private void updateStock(String stockID, String currentQuantity, String stockQuantity){
        int temp1 = Integer.parseInt(currentQuantity);
        int temp2 = Integer.parseInt(stockQuantity);
        int temp3 = temp1 + temp2;
        String updatedQuantity = String.valueOf(temp3);

        mDatabase.child("stock_details").child(stockID).child("stock_quantity").setValue(updatedQuantity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btAddMore:
                addStock();
                Toast.makeText(getApplicationContext(),"Stock added successfully!", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent(AddStockActivity.this, AddStockActivity.class);
                Intent intent = getIntent();
                intent.putExtra("USER_ID", userID);
                intent.putExtra("PROJECT_ID", projectID);
                finish();
                startActivity(intent);
                break;

            case R.id.btStockDone:
                addStock();
                Intent intent1 = new Intent(getBaseContext(), ProjectActivity.class);
                intent1.putExtra("USER_ID", userID);
                intent1.putExtra("PROJECT_ID", projectID);
                finish();
                startActivity(intent1);
                Toast.makeText(getApplicationContext(),"Stock added successfully!", Toast.LENGTH_LONG).show();

                break;
        }

    }
}
