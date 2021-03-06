package com.example.winecooler;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class MqttHelper {
    public MqttAndroidClient mqttAndroidClient;

    final String serverUri = "tcp://212.98.137.194:1883";
    final String clientId = "MDP25-CCE";
    final String subscriptionTopic = "winecooler";
    final String username = "iotleb";
    final String password = "iotleb";
    JSONObject myjson = new JSONObject();

//    static JSONArray wtemp= new JSONArray();
//    static JSONArray ctemp= new JSONArray();
//    static JSONArray chum= new JSONArray();
//    static JSONArray whum= new JSONArray();

//    static ArrayList<Double> wtemp = new ArrayList<>();
//    static ArrayList < Double> ctemp = new ArrayList<>();
//    static ArrayList < Double> whum = new ArrayList<>();
//    static ArrayList < Double> chum = new ArrayList<>();

    public MqttHelper(Context context){
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("Mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {
                Log.w("Mqtt", "connection lost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
               Log.w("Mqtt", " Getting the Data ....Loading");

                String payload = new String(mqttMessage.getPayload());
//                Parse the payload in Json Format
                JSONObject contObj = new JSONObject(payload);  //7340
                myjson = contObj;
                Log.w("Mqtt",String.valueOf(contObj));

                GraphActivity.receiveData(contObj);
                CigarActivity.receiveData(contObj);
                WineActivity.receiveData(contObj);

//                if ( wtemp == null && ctemp == null && whum == null && chum== null) {
//                    System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
//                    Log.w("Mqtt", " Adding the Graph ------------------------");

//                    wtemp.add(((Double.parseDouble((String) contObj.get("WineTemp")))));
//                    ctemp.add((Double.parseDouble((String) contObj.get("CigTemp"))));
//                    whum.add(((Double.parseDouble((String) contObj.get("WineHum")))));
//                    chum.add((Double.parseDouble((String)contObj.get("CigHum"))));
//
//                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//                    System.out.println(whum);
//                    System.out.println(whum);
//                    System.out.println(chum);
//                    System.out.println(ctemp);
//                    System.out.println(wtemp);
//                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//


                //}


            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                Log.w("Mqtt", "msg delivered");
            }
        });
        connect();
    }

    private void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                }
            });

        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }

    private void subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt","Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Subscribed failed");
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exception");
            ex.printStackTrace();
        }
    }
}

/* public void dataPublisher(String dataPublish){
        byte[] dataByte = new byte[0];
        try {
            System.out.println("data:" + dataPublish);
            dataByte = dataPublish.getBytes();
            mqttAndroidClient.publish(subscriptionTopic,dataByte,2,false);
            System.out.println("MSG PUBLISHED SUCCESSFULLY");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/