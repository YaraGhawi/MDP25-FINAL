package com.example.winecooler;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button wineButton= (Button)findViewById(R.id.wineButton);
        Button cigarButton= (Button)findViewById(R.id.cigarButton);
        Button MoreButton= (Button)findViewById(R.id.MoreButton);


        wineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WineActivity.state = true;
                startActivity(new Intent(MainActivity.this, WineActivity.class));
            }
        });

        cigarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CigarActivity.state = true;
                startActivity(new Intent(MainActivity.this, CigarActivity.class));
            }
        });

        MoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GraphActivity.state = true;
                startActivity(new Intent(MainActivity.this, GraphActivity.class));
            }
        });
    }
}
