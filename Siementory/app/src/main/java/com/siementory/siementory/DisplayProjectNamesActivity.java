package com.siementory.siementory;

import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Context;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;





public class DisplayProjectNamesActivity extends AppCompatActivity {


    TextView textView;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_project_names);

       listView = (ListView) findViewById(R.id.listView);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://siementory-a0f2f.firebaseio.com/projects");


        FirebaseListAdapter <String> firebaseListAdapter = new FirebaseListAdapter<String>(
                this,
                String.class,
                android.R.layout.simple_list_item_1,
                databaseReference

        ) {
            @Override
            protected void populateView(View v, String model, int position) {




                Toast.makeText(getApplicationContext(),model,Toast.LENGTH_LONG).show();
                textView = (TextView) findViewById(android.R.id.text1);
                textView.setText(model);




            }
        };


       listView.setAdapter(firebaseListAdapter);
    }
}


        /*      final ListView projectList = (ListView) findViewById(R.id.listView);
        //projectNameList = new ArrayList<String>();
        //getProjectNames();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();
        databaseReference.child("project").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Userlist = new ArrayList<String>();
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(String.valueOf(dsp.getValue())); //add result into array list

                }
                Log.d("list", Userlist.toString());
                Toast.makeText(getApplicationContext(), "Project  : "+Userlist,  Toast.LENGTH_LONG).show();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Userlist);
        //projectList.setAdapter(arrayAdapter);


        projectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String projectSelected = (String) Userlist.get(position);
                Toast.makeText(getApplicationContext(), "Project Selected : "+projectSelected,   Toast.LENGTH_LONG).show();
            }
        });
    }
*/


