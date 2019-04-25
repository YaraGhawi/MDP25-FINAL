package com.example.winecooler;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class WineActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Switch switch1;
    TextView tempTextView;
    int temp=13;
    Switch switch2;
    TextView humTextView;
    int hum=80;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine);


        tempTextView =  findViewById(R.id.tempTextView);
        tempTextView.setBackgroundColor(Color.rgb(224,224,224));
        tempTextView.setTextColor(Color.rgb(64,64,64));
        switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(this);
        humTextView =  findViewById(R.id.humTextView);

        humTextView.setBackgroundColor(Color.rgb(224,224,224));
        humTextView.setTextColor(Color.rgb(64,64,64));
        switch2 = findViewById(R.id.switch2);
        switch2.setOnCheckedChangeListener(this);

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton , boolean b){

        if (switch1.isChecked()){

            tempTextView.setText(temp+" degree");
        }
        else {
            tempTextView.setText("");
        }


        if (switch2.isChecked()){

            humTextView.setText(hum+" %");
        }
        else {
            humTextView.setText("");
        }

    }
}
