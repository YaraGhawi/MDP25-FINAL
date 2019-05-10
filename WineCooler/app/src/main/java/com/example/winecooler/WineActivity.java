package com.example.winecooler;

import android.app.ActionBar;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;


public class WineActivity extends AppCompatActivity  {

    //Switch switch1;
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        tempTextView = findViewById(R.id.tempTextView);
        humTextView = findViewById(R.id.humTextView);


//        switch1 = findViewById(R.id.switch1);
//        switch1.setOnCheckedChangeListener(this);

        LiveReceiver liveReceiver = new LiveReceiver(6);
        Thread t = new Thread(liveReceiver);
        t.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//        if (switch1.isChecked()) {
//
//            MyRunnable myRunnable = new MyRunnable(6);
//            Thread t = new Thread(myRunnable);
//            t.start();
//        }
//        else {
//            tempTextView.setText("Temperature");
//            humTextView.setText("Humidity");
//        }
//    }

    public static void receiveData(JSONObject msg) throws JSONException {
        System.out.println(msg);

        MyRunnable myRunnable = new MyRunnable(6);
        Thread t = new Thread(myRunnable);
        t.start();

        temp = Integer.valueOf((String) msg.get("WineTemp"));
        hum = Integer.valueOf((String) msg.get("WineHum"));
    }

    public static class MyRunnable implements Runnable {

        private int var;
        public MyRunnable(int var)
        {
            this.var = var;
        }
        public void run() {
            while(true) {
                tempTextView.setText(temp + " °C");
                humTextView.setText(hum + " %");

                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) { System.out.println("rip 2 "); }
            }
        }
    }


}


//    public static void receiveData(String msg) throws JSONException, ParseException {
//        /*System.out.println(msg);
//        JSONArray JA= new JSONArray(msg);
//        System.out.println("---------------------------------"+msg);
//        for (int i=0; i< JA.length(); i++){
//            JSONObject JO =(JSONObject) JA.get(i);
//            temp=Integer.valueOf((Integer) JO.get("WineTemp"));
//            hum=Integer.valueOf((Integer) JO.get("WineHum"));*/
//            System.out.println(msg);
//            // JSONArray JA= new JSONArray(msg);
//            System.out.println("---------------------------------");
//            JSONParser parser = new JSONParser();
//            JSONObject JO = (JSONObject) parser.parse(msg);
//            System.out.println(JO);
//
//            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" + JO.get("WineHum") + "<<<<<<<<<<<<");
//            hum = Integer.valueOf((Integer) JO.get("WineHum"));
//            temp = Integer.valueOf((Integer) JO.get("WineTemp"));
//
//            //System.out.println(temp);
//
//        //}
//    }

