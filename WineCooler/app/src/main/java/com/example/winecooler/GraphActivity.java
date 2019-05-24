package com.example.winecooler;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class GraphActivity extends AppCompatActivity {

    public static LineGraphSeries<DataPoint> line1;
    public static LineGraphSeries<DataPoint> line2;
    public static LineGraphSeries<DataPoint> line3;
    public static LineGraphSeries<DataPoint> line4;


    public static GraphView graph;
    public static GraphView graph2;
    static boolean state = true;
    static int winetemp;
    static int cigtemp;
    static int cighum;
    static int winehum;
    int j;
    static Thread t = new Thread();
    static Thread x = new Thread();

    static ArrayList<Double> wtemp = new ArrayList<>();
    static ArrayList < Double> ctemp = new ArrayList<>();
    static ArrayList < Double> whum = new ArrayList<>();
    static ArrayList < Double> chum = new ArrayList<>();


    public class LiveReceiver implements Runnable {

        private int var;


        public LiveReceiver(int var) {
            this.var = var;
        }

        public void run() {
            while (state) {
                MqttHelper mqttHelper = new MqttHelper(getApplicationContext());
                try {
                    if (mqttHelper.myjson != null) {
                        receiveData(mqttHelper.myjson);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    System.out.println("rip");
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            state = false;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        graph = findViewById(R.id.graph);
        graph2 = findViewById(R.id.graph2);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Seconds");
        graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.rgb(48, 139, 159));
        graph.getGridLabelRenderer().setVerticalAxisTitle("Temperature (°C)");
        graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.rgb(48, 139, 159));
        graph.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setLabelVerticalWidth(40);


        graph2.getViewport().setScalable(true);
        graph2.getViewport().setScalableY(true);
        graph2.getGridLabelRenderer().setHorizontalAxisTitle("Seconds");
        graph2.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.rgb(48, 139, 159));
        graph2.getGridLabelRenderer().setVerticalAxisTitle("Humidity (%)");
        graph2.getGridLabelRenderer().setVerticalAxisTitleColor(Color.rgb(48, 139, 159));
        graph2.getGridLabelRenderer().setGridColor(Color.WHITE);
        graph2.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);
        graph2.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph2.getGridLabelRenderer().setLabelVerticalWidth(40);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LiveReceiver liveReceiver = new LiveReceiver(6);
        Thread t = new Thread(liveReceiver);
        t.start();

//        MyRunnable myRunnable = new MyRunnable(6);
//        Thread x = new Thread(myRunnable);
//        x.start();

    }

    public static void receiveData(JSONObject msg) throws JSONException {


        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");

//
//        wtemp.add(((Double.valueOf(((String) msg.get("WineTemp"))))));
//        ctemp.add((Double.valueOf((String) msg.get("CigTemp"))));
//        whum.add(((Double.valueOf((String) msg.get("WineHum")))));
//        chum.add((Double.valueOf((String) msg.get("CigHum"))));

        System.out.println("--------------------------------------------------");
        //System.out.println(msg);
        winetemp = Integer.valueOf((String) msg.get("WineTemp"));
        cigtemp= Integer.valueOf((String) msg.get("CigTemp"));
        winehum = Integer.valueOf((String) msg.get("WineHum"));
        cighum= Integer.valueOf((String) msg.get("CigHum"));

        System.out.println("----------------- the value of temp is "+ winetemp + " and the value of humidity is "+ cigtemp);

        wtemp.add((double)winetemp);
        ctemp.add((double)winehum);
        whum.add((double) cigtemp);
        chum.add((double) cighum);

        System.out.println("-----------------------------------------"+ wtemp);
        System.out.println(ctemp);
        System.out.println(whum);
        System.out.println(wtemp);
        System.out.println(wtemp.size());


        int j=0;
        DataPoint[] datapoints1 = new DataPoint[wtemp.size()];
        for(int i=0; i< wtemp.size();i++){
                j+=5;
//                LineGraphSeries<DataPoint> line1 = new LineGraphSeries<>(new DataPoint[] {
//                        new DataPoint(j, wtemp.get(i))});
            datapoints1[i]=new DataPoint(j,wtemp.get(i));

            }
        LineGraphSeries<DataPoint> line1 = new LineGraphSeries<>(datapoints1);
        line1.setColor(Color.parseColor("#9c0000"));
        line1.setDrawDataPoints(true);
        line1.setDataPointsRadius(10);
        line1.setThickness(5);
        graph.addSeries(line1);

        j=0;
        DataPoint[] datapoints2 = new DataPoint[ctemp.size()];
        for(int i=0; i< ctemp.size();i++){
                j+=5;
//                LineGraphSeries<DataPoint> line2 = new LineGraphSeries<>(new DataPoint[] {
//                        new DataPoint(j, ctemp.get(i))});
            datapoints2[i]=new DataPoint(j,ctemp.get(i));

            }
        //        line2.setColor(Color.parseColor("#4bcafc"));

        LineGraphSeries<DataPoint> line2 = new LineGraphSeries<>(datapoints2);
        line2.setDrawDataPoints(true);
        line2.setDataPointsRadius(10);
        line2.setThickness(5);
        graph.addSeries(line2);

        j=0;
        DataPoint[] datapoints3 = new DataPoint[whum.size()];
        for(int i=0; i< whum.size();i++){
            j+=5;

//                LineGraphSeries<DataPoint> line3 = new LineGraphSeries<>(new DataPoint[] {
//                        new DataPoint(j, whum.get(i))});
            datapoints3[i]=new DataPoint(j,whum.get(i));
            }
        LineGraphSeries<DataPoint> line3 = new LineGraphSeries<>(datapoints3);
        line3.setColor(Color.parseColor("#9c0000"));
        line3.setDrawDataPoints(true);
        line3.setDataPointsRadius(10);
        line3.setThickness(5);
        graph2.addSeries(line3);

        j=0;
        DataPoint[] datapoints4 = new DataPoint[chum.size()];
        for(int i=0; i< chum.size();i++){
            j+=5;
//
//                LineGraphSeries<DataPoint> line4 = new LineGraphSeries<>(new DataPoint[] {
//                        new DataPoint(j, whum.get(i))});
            datapoints4[i]=new DataPoint(j,whum.get(i));
            }
        LineGraphSeries<DataPoint> line4 = new LineGraphSeries<>(datapoints4);
        line4.setColor(Color.parseColor("#4bcafc"));
        line4.setDrawDataPoints(true);
        line4.setDataPointsRadius(10);
        line4.setThickness(5);
        graph2.addSeries(line4);


//        LineGraphSeries<DataPoint> line1 = new LineGraphSeries<>(new DataPoint[] {
//                new DataPoint(5, winetemp),
//                new DataPoint(10, winetemp),
//                new DataPoint(15, winetemp),
//                new DataPoint(20, winetemp),
//        });
//        line1.setColor(Color.parseColor("#9c0000"));
//        line1.setDrawDataPoints(true);
//        line1.setDataPointsRadius(10);
//        line1.setThickness(5);
//
//        LineGraphSeries<DataPoint> line2 = new LineGraphSeries<>(new DataPoint[] {
//                new DataPoint(5, cigtemp),
//                new DataPoint(10, cigtemp),
//                new DataPoint(15, cigtemp),
//                new DataPoint(20, cigtemp),
//        });
//
//        line2.setColor(Color.parseColor("#4bcafc"));
//        line2.setDrawDataPoints(true);
//        line2.setDataPointsRadius(10);
//        line2.setThickness(5);
//        graph.addSeries(line1);
//        graph.addSeries(line2);
//
//        LineGraphSeries<DataPoint> line3 = new LineGraphSeries<>(new DataPoint[] {
//                new DataPoint(5, winehum),
//                new DataPoint(10, winehum),
//                new DataPoint(15, winehum),
//                new DataPoint(20, winehum),
//
//        });
//        line3.setColor(Color.parseColor("#9c0000"));
//        line3.setDrawDataPoints(true);
//        line3.setDataPointsRadius(10);
//        line3.setThickness(5);
//
//        LineGraphSeries<DataPoint> line4 = new LineGraphSeries<>(new DataPoint[] {
//                new DataPoint(5, cighum),
//                new DataPoint(10, cighum),
//                new DataPoint(15, cighum),
//                new DataPoint(20, cighum),
//        });
//
//        line4.setColor(Color.parseColor("#4bcafc"));
//        line4.setDrawDataPoints(true);
//        line4.setDataPointsRadius(10);
//        line4.setThickness(5);
//        graph2.addSeries(line3);
//        graph2.addSeries(line4);

    }



//    public static class MyRunnable implements Runnable {
//
//        private int var;
//        public MyRunnable(int var)
//        {
//            this.var = var;
//        }
//
//        public void run() {
//            while(state) {
//                if (graph2 == null){System.out.println("grsph is null and the lines are: "+line1+" "+line2);}
//
//                System.out.println(">>>>>>>>>>>>>>>>>>>>graph is null and the lines are: "+line1+" "+line2);
////                graph.addSeries(line1);
////                graph.addSeries(line2);
////                graph2.addSeries(line3);
////                graph2.addSeries(line4);
//
//                try {
//                    Thread.sleep(1000);
//                }
//                catch (InterruptedException e) { System.out.println("rip 2 "); }
//            }
//        }
//    }

}


//        line2.appendData(new DataPoint(2,10), true, 100);
//        line2.appendData(new DataPoint(1,3), true, 100);
//        line2.appendData(new DataPoint(2,6), true, 100);
//
