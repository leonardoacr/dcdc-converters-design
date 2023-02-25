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
    private static final String TAG = "GraphUtils";
    public static double[] xGlobal;
    public static double[] yGlobal;
    private int numStepGlobal;

    public void loadData(double[] x, double[] y, int numStep, String graphLegend, LineChart chart) {
        // Define the arrays with a size of 10
        numStepGlobal = numStep;
        xGlobal = new double[numStep];
        yGlobal = new double[numStep];

        // create a new data set for the line chart
        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < numStep; i++) {
            xGlobal[i] = x[i]*1000;
            yGlobal[i] = y[i];
            entries.add(new Entry((float) x[i]*1000, (float) y[i]));
        }

        LineDataSet dataSet = new LineDataSet(entries, graphLegend);
        dataSet.setColor(Color.BLUE);
        dataSet.setDrawCircles(false);
        dataSet.setDrawValues(false);

        // add data set to chart
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);

        // set chart attributes
        chart.getDescription().setText(graphLegend);
        chart.getDescription().setTextSize(16f);
        chart.getDescription().setTextColor(Color.BLACK);
    }

    public void plotGraph(LineChart chart, Double xLowerLimit, Double xUpperLimit, Double yLowerLimit, Double yUpperLimit) {
        // set chart attributes
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);
        chart.setPinchZoom(true);
        chart.setBackgroundColor(Color.WHITE);
        chart.getAxisLeft().setEnabled(true); //show y-axis at left
        chart.getAxisRight().setEnabled(false); //hide y-axis at right

        setAxisLimits(chart, xLowerLimit, xUpperLimit, yLowerLimit, yUpperLimit);

        // set up listener to show x and y values on chart
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            TextView xValueTextView, yValueTextView;
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // get x and y values of selected point
                float xVal = e.getX();
                float yVal = e.getY();

                String formattedX = String.format("%.2f", xVal);
                String formattedY = String.format("%.2f", yVal);

                // update TextView objects to show x and y values
                xValueTextView = chart.getRootView().findViewById(R.id.x_value);
                yValueTextView = chart.getRootView().findViewById(R.id.y_value);
                xValueTextView.setText("X: " + formattedX);
                yValueTextView.setText("Y: " + formattedY);
            }
            @Override
            public void onNothingSelected() {
                xValueTextView.setText("");
                yValueTextView.setText("");
            }
        });
    }
    private void setAxisLimits(LineChart chart, Double xLowerLimit, Double xUpperLimit,
                               Double yLowerLimit, Double yUpperLimit){
        if (xLowerLimit == null && xUpperLimit == null
                && yLowerLimit == null && yUpperLimit == null) {
            // set default axis
            // set up X-axis
            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getXAxis().setAxisMinimum(0f);
            chart.getXAxis().setAxisMaximum((float) xGlobal[numStepGlobal - 1]);

            // set up Y-axis
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                double maximumY = Arrays.stream(yGlobal).max().getAsDouble();
                maximumY = (maximumY >= 0) ? maximumY * 1.1 : maximumY * 0.9;

                double minimumY = Arrays.stream(yGlobal).min().getAsDouble();
                minimumY = (minimumY > 0) ? minimumY * 0.9 : (minimumY < 0) ?
                        minimumY * 1.1 : minimumY - 0.5;


                chart.getAxisLeft().setAxisMaximum((float) (maximumY));
                chart.getAxisLeft().setAxisMinimum((float) (minimumY));
            }
            chart.getAxisLeft().setGranularity(0.1f);
        }
        if (xLowerLimit != null || xUpperLimit != null || yLowerLimit != null || yUpperLimit != null) {
            // set axis limits if arguments are not empty:
            if (xLowerLimit != null) {
                chart.getXAxis().setAxisMinimum(xLowerLimit.floatValue());
            }
            if (xUpperLimit != null) {
                chart.getXAxis().setAxisMaximum(xUpperLimit.floatValue());
            }
            if (yLowerLimit != null) {
                chart.getAxisLeft().setAxisMinimum(yLowerLimit.floatValue());
            }
            if (yUpperLimit != null) {
                chart.getAxisLeft().setAxisMaximum(yUpperLimit.floatValue());
            }
        }
    }
}
