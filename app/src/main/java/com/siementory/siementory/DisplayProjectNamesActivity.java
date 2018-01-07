package com.siementory.siementory;

import android.app.ListActivity;
import android.app.ListFragment;
import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;



import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;






public class DisplayProjectNamesActivity extends AppCompatActivity {

    private ArrayList<String> projects = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listView;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userID = extras.getString("USER_ID");
            //The key argument here must match that used in the other activity
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_project_names);
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("users").child(userID).child("projects");
        listView = (ListView) findViewById(R.id.list_projects);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,projects);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arrayAdapter, View view, int position, long id) {
                // String clickedProject = listView.getSelectedItem().toString();
                String projectID =(listView.getItemAtPosition(position).toString());
                projectID = projectID.substring(projectID.lastIndexOf("-L"));
                Log.d("Clicked Project",projectID);
                Intent intent1 = new Intent(DisplayProjectNamesActivity.this,ProjectActivity.class);
                intent1.putExtra("PROJECT_ID",projectID);
                intent1.putExtra("USER_ID", userID);
                startActivity(intent1);

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren()
                        ) {

                    projects.add("Project Name  :  "+ds.child("project_name").getValue().toString() +"\n" +"Supervisor       :  "+ ds.child("supervisor_name").getValue().toString()+"\n"+"Project UID      :  "+ds.getKey().toString());
                    //projects.add(ds.getValue().toString());

                }
                Log.d("list", projects.toString());
                arrayAdapter.notifyDataSetChanged();




                //Toast.makeText(getApplicationContext(),dataSnapshot.getValue().toString(),Toast.LENGTH_LONG).show();

                /*
                for (DataSnapshot ds: dataSnapshot.getChildren()
                     ) {
                    Project project = ds.getValue(Project.class);
                    Log.d( "onDataChange: ", ds.toString());
                    projects.add(project.projectName);
                    projects.add(project.supervisorName);
                    arrayAdapter.notifyDataSetChanged();

                }
                */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




}




