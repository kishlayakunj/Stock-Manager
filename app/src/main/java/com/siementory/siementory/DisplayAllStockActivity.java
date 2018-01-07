package com.siementory.siementory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayAllStockActivity extends AppCompatActivity {
    String projectID, userID;

    private ArrayList<String> stocks = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ListView listView;

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
        setContentView(R.layout.activity_display_all_stock);
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("projects").child(projectID).child("stock_details");
        listView = (ListView) findViewById(R.id.list_stocks);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,stocks);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arrayAdapter, View view, int position, long id) {
                // String clickedProject = listView.getSelectedItem().toString();
                String stockID =(listView.getItemAtPosition(position).toString());
                stockID = stockID.substring(stockID.lastIndexOf("-L"));
                Log.d("Clicked Stock",stockID);
                Intent intent1 = new Intent(DisplayAllStockActivity.this,ViewStockFromListViewActivity.class);
                intent1.putExtra("USER_ID", userID);
                intent1.putExtra("STOCK_ID",stockID);
                intent1.putExtra("PROJECT_ID",projectID);
                startActivity(intent1);


            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren()
                        ) {

                    stocks.add("Stock Name  :  "+ds.child("stock_name").getValue().toString() +"\n" +"Quantity         :  "+ ds.child("stock_quantity").getValue().toString()+"\n"+"Stock ID         :  "+ds.getKey().toString());
                    //projects.add(ds.getValue().toString());

                }
                Log.d("list", stocks.toString());
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
