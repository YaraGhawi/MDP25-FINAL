package com.example.winecooler;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.BitSet;
import java.util.Iterator;


public class CigarActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Switch switch1;
    static TextView tempTextView;
    static int temp;

    static TextView humTextView;
    static int hum;

    public class LiveReceiver implements Runnable {

        private int var;

        public LiveReceiver(int var) {
            this.var = var;
        }

        public void run() {
            while(true) {
                MqttHelper mqttHelper = new MqttHelper(getApplicationContext());
                try {
                    if (mqttHelper.myjson != null) {
                        receiveData(mqttHelper.myjson);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) { System.out.println("rip 2 "); }
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MqttHelper mqttHelper = new MqttHelper(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cigar);


        tempTextView =  findViewById(R.id.tempTextView);

        switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(this);

        humTextView =  findViewById(R.id.humTextView);

        LiveReceiver liveReceiver = new LiveReceiver(6);
        Thread t = new Thread(liveReceiver);
        t.start();

//        try {
//            System.out.println("i am here");
//            System.out.println(mqttHelper.myjson);
//            receiveData(mqttHelper.myjson);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton , boolean b){

        if (switch1.isChecked()) {

            MyRunnable myRunnable = new MyRunnable(6);
            Thread t = new Thread(myRunnable);
            t.start();
        }
        else {
            tempTextView.setText("Temperature");
            humTextView.setText("Humidity");
        }

    }

    public static void receiveData(JSONObject msg) throws JSONException{
        System.out.println(msg);

        temp = Integer.valueOf((String)msg.get("CigTemp"));
        hum = Integer.valueOf((String)msg.get("CigHum"));

    }

    public class MyRunnable implements Runnable {

        private int var;


        public MyRunnable(int var) {
            this.var = var;
        }

        public void run() {
            while (true) {
                tempTextView.setText(temp + " degree");
                humTextView.setText(hum + "%");


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("rip 2 ");
                }
            }
        }
    }
}