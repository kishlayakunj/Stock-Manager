package com.siementory.siementory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ScarceStockActivity extends AppCompatActivity {
    String projectID, userID;
    ListView listView;
    private ArrayList<String> scarceList = new ArrayList<>();
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
        setContentView(R.layout.activity_scarce_stock);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("projects").child(projectID);
        listView = (ListView) findViewById(R.id.ScarceStockListView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,scarceList);
        listView.setAdapter(arrayAdapter);
        databaseReference.child("stock_details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()
                        ) {
                       int scarceValue  = Integer.parseInt(ds.child("stock_quantity").getValue().toString());
                    if (scarceValue<100) {
                        scarceList.add("Stock Name  :  "+ds.child("stock_name").getValue().toString()+"\n"+"Quantity         :  "+ds.child("stock_quantity").getValue().toString());
                        //break;


                    } //else continue;


                }
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
