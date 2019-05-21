package com.example.winecooler;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;



public class GraphActivity extends AppCompatActivity {

//    public static LineGraphSeries<DataPoint> line1 = new LineGraphSeries<DataPoint>();
//    public static LineGraphSeries<DataPoint> line2 = new LineGraphSeries<DataPoint>();

    public static GraphView graph;
    public static GraphView graph2;
    static boolean state = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//////////////////// Temperature

        graph = findViewById(R.id.graph);
        LineGraphSeries<DataPoint> line1 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 4),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 1)
        });
        line1.setColor(Color.parseColor("#9c0000"));
        line1.setDrawDataPoints(true);
        line1.setDataPointsRadius(10);
        line1.setThickness(5);

        LineGraphSeries<DataPoint> line2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 3),
                new DataPoint(1, 7),
                new DataPoint(2, 8),
                new DataPoint(3, 5),
                new DataPoint(4, 5),
                new DataPoint(5, 9),
                new DataPoint(6, 1)
        });
        line2.setColor(Color.parseColor("#4bcafc"));
        line2.setDrawDataPoints(true);
        line2.setDataPointsRadius(10);
        line2.setThickness(5);
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
        graph.addSeries(line1);
        graph.addSeries(line2);



///////// Humidity
        graph2 = findViewById(R.id.graph2);

        LineGraphSeries<DataPoint> line3 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 4),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 1)
        });
        line3.setColor(Color.parseColor("#9c0000"));
        line3.setDrawDataPoints(true);
        line3.setDataPointsRadius(10);
        line3.setThickness(5);

        LineGraphSeries<DataPoint> line4 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 3),
                new DataPoint(1, 7),
                new DataPoint(2, 8),
                new DataPoint(3, 5),
                new DataPoint(4, 5),
                new DataPoint(5, 9),
                new DataPoint(6, 1)
        });
        line4.setColor(Color.parseColor("#4bcafc"));
        line4.setDrawDataPoints(true);
        line4.setDataPointsRadius(10);
        line4.setThickness(5);
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
        graph2.addSeries(line1);
        graph2.addSeries(line2);


    }
//    public static void updateSeries(){
//        graph.addSeries(series);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            state = false;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


//        line2.appendData(new DataPoint(2,10), true, 100);
//        line2.appendData(new DataPoint(1,3), true, 100);
//        line2.appendData(new DataPoint(2,6), true, 100);
//
