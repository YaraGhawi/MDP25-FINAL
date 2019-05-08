package com.example.winecooler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static java.util.Objects.isNull;


public class WineActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

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
//        MqttHelper mqttHelper = new MqttHelper(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine);


        tempTextView = findViewById(R.id.tempTextView);

        switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(this);
        humTextView = findViewById(R.id.humTextView);

        LiveReceiver liveReceiver = new LiveReceiver(6);
        Thread t = new Thread(liveReceiver);
        t.start();

//        try {
//            System.out.println("i am here");
//            System.out.println(mqttHelper.myjson);
//            receiveData(mqttHelper.myjson);
//        }
//        catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

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

    public static void receiveData(JSONObject msg) throws JSONException {
        System.out.println(msg);

        temp = Integer.valueOf((String) msg.get("WineTemp"));
        hum = Integer.valueOf((String) msg.get("WineHum"));

    }


    public class MyRunnable implements Runnable {

        private int var;


        public MyRunnable(int var) {
            this.var = var;
        }

        public void run() {
            while(true) {
                tempTextView.setText(temp + " degree");
                humTextView.setText(hum + "%");



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

