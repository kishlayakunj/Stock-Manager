package com.siementory.siementory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SpecificStockIssueListActivity extends AppCompatActivity {
    String projectID,stockName,stockID, userID;
    private ArrayList<String> issueList = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference, databaseReference2;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

       Bundle extras = getIntent().getExtras();
        if (extras != null) {
            stockName = extras.getString("STOCK_NAME");
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
        setContentView(R.layout.activity_specific_stock_issue_list);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("projects").child(projectID).child("issue_details");
        listView = (ListView) findViewById(R.id.specificStockIssueListView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,issueList);
        listView.setAdapter(arrayAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren()
                        ) {
                                if(ds.child("stock_name").getValue().toString().equals(stockName)) {


                                    issueList.add("Issued to             :  "+ds.child("receiver_name").getValue().toString()+"\n" +"Quantity Issued  :  "+ ds.child("quantity_issued").getValue().toString() + "\n"+"Date of issue      :  "+ds.child("date_of_issue").getValue().toString());
                                    //projects.add(ds.getValue().toString());

                                }
                }
                //Log.d("list", issueList.toString());
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
