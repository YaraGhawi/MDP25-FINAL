package com.example.winecooler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;


public class WineActivity extends AppCompatActivity  {

    //Switch switch1;
    static TextView tempTextView;
    static int temp;

    static TextView humTextView;
    static int hum;

    static Thread t = new Thread();
    static Thread x = new Thread();

    static boolean state=true;

    public class LiveReceiver implements Runnable {

        private int var;

        public LiveReceiver(int var) {
            this.var = var;
        }

        public void run() {
           while(state){
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
                catch (InterruptedException e) { System.out.println("rip"); }
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


        LiveReceiver liveReceiver = new LiveReceiver(6);
        Thread t = new Thread(liveReceiver);
        t.start();

        MyRunnable myRunnable = new MyRunnable(6);
        Thread x = new Thread(myRunnable);
        x.start();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            state = false;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public static void receiveData(JSONObject msg) throws JSONException {
        System.out.println(msg);

        temp = Integer.valueOf((String) msg.get("WineTemp"));
        hum = Integer.valueOf((String) msg.get("WineHum"));
        System.out.println("----------------- the value of temp is "+temp + " and the value of humidity is "+ hum);
    }

    public static class MyRunnable implements Runnable {

        private int var;
        public MyRunnable(int var)
        {
            this.var = var;
        }

        public void run() {
            while(state) {



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

