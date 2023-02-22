package com.example.dcdcconvertersdesign.simulationutilities;

import android.graphics.Color;

import android.os.Build;

import android.util.Log;
import android.widget.TextView;

import com.example.dcdcconvertersdesign.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphUtils {
    public static void plotGraph(double[] x, double[] y, int numStep, String graphLegend, LineChart chart, String TAG) {
        // set chart attributes
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setPinchZoom(true);
        chart.setBackgroundColor(Color.WHITE);

        // create a new data set for the line chart
        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < numStep; i++) {
            entries.add(new Entry((float) x[i], (float) y[i]));

        }
        Log.d(TAG, "teste x: " + Arrays.toString(x));
        Log.d(TAG, "teste y: " + Arrays.toString(y));
        Log.d(TAG, "teste y last position: " + y[18000]);

        LineDataSet dataSet = new LineDataSet(entries, graphLegend);
        dataSet.setColor(Color.BLUE);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);

        // add data set to chart
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        // set up X-axis
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setAxisMinimum(0f);
        chart.getXAxis().setAxisMaximum((float) x[numStep-1]);
        chart.invalidate();

        // set up Y-axis
        chart.getAxisLeft().setAxisMinimum(0f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            chart.getAxisLeft().setAxisMaximum((float) (
                    1.2 * Arrays.stream(y).max().getAsDouble()));
        }
        chart.getAxisLeft().setGranularity(0.1f);

        // set up listener to show x and y values on chart
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // get x and y values of selected point
                float xVal = e.getX();
                float yVal = e.getY();

                String formattedX = String.format("%.4f", xVal);
                String formattedY = String.format("%.4f", yVal);

                // update TextView objects to show x and y values
                TextView xValueTextView = chart.getRootView().findViewById(R.id.x_value);
                TextView yValueTextView = chart.getRootView().findViewById(R.id.y_value);
                xValueTextView.setText("X: " + formattedX);
                yValueTextView.setText("Y: " + formattedY);
                Log.d(TAG, "test xVal: " + xVal);
            }

            @Override
            public void onNothingSelected() {
                // do nothing
            }
        });
    }
}
