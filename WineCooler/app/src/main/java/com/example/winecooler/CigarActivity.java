package com.example.winecooler;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONObject;


public class CigarActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Switch switch1;
    static TextView tempTextView;
    static int temp;

    static TextView humTextView;
    static int hum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MqttHelper mqttHelper = new MqttHelper(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cigar);


        tempTextView =  findViewById(R.id.tempTextView);

        switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(this);

        humTextView =  findViewById(R.id.humTextView);



    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton , boolean b){

        if (switch1.isChecked()){
            tempTextView.setText(this.temp+" degree");
            humTextView.setText(this.temp +"%");
        }

        else {
            tempTextView.setText("Temperature");
            humTextView.setText("Humidity");
        }

    }

    public static void receiveData(String msg){
        //System.out.println(msg);
        temp=Integer.valueOf(msg);
        //push notification
//       tempTextView.setText(msg);
//       humTextView.setText(msg);
    }
}


