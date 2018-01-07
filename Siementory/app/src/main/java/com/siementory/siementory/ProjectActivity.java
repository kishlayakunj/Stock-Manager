package com.siementory.siementory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProjectActivity extends AppCompatActivity implements View.OnClickListener {

    Button  btAddStock,btRegister,btViewStock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        findViewById(R.id.btAddStock).setOnClickListener(this);
        findViewById(R.id.btRegister).setOnClickListener(this);
        findViewById(R.id.btViewStock).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btAddStock:
                Intent intent = new Intent(ProjectActivity.this,NewProjectDetailsActivity.class);
                startActivity(intent);
                break;

            case R.id.btViewStock:
               // Intent intent2 =new Intent(ProjectActivity.this,)
                break;

            case R.id.btRegister:
                Intent intent1 = new Intent(ProjectActivity.this,RegisterActivity.class);
                startActivity(intent1);
                break;


        }
    }
}
