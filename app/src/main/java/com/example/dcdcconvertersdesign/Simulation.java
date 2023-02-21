package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Arrays;

import com.example.dcdcconvertersdesign.simulationutilities.GraphUtils;
import com.example.dcdcconvertersdesign.simulationutilities.SolveDiffEquations;
import com.github.mikephil.charting.charts.LineChart;

public class Simulation extends AppCompatActivity {
    // define a tag for logging purposes
    private static final String TAG = "Simulation";
    public static double[] outputVoltageArray;
    public static double[] inductanceCurrentArray;
    public static double[] timeArray;

    private LineChart chart;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        // retrieve extras from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double outputVoltage = extras.getDouble("Output_Voltage");
            double inputVoltage = extras.getDouble("Input_Voltage");
            double dutyCycle = extras.getDouble("Duty_Cycle");
            double inductance = extras.getDouble("Inductance");
            double capacitance = extras.getDouble("Capacitance");
            double resistance = extras.getDouble("Resistance");
            double frequency = extras.getDouble("Frequency");
            double flag = extras.getDouble("Flag");

            // Define the parameters
            double maxTime = 0.004;
            double timeStep = 1 / frequency / 1000; // time step
            int numStep = (int) (maxTime / timeStep);

            Log.d(TAG, "outputVoltage: " + outputVoltage);
            Log.d(TAG, "inputVoltage: " + inputVoltage);
            Log.d(TAG, "dutyCycle: " + dutyCycle);
            Log.d(TAG, "inductance: " + inductance);
            Log.d(TAG, "capacitance: " + capacitance);
            Log.d(TAG, "resistance: " + resistance);
            Log.d(TAG, "frequency: " + frequency);

            // Buck Simulation
            if (flag == 1) {
                SolveDiffEquations.BuckConverter(outputVoltage, inputVoltage, dutyCycle,
                        inductance, capacitance, resistance, frequency, maxTime, timeStep, numStep);

                Log.d(TAG, "inductanceCurrentArray: " + Arrays.toString(inductanceCurrentArray));
                Log.d(TAG, "outputVoltageArray: " + Arrays.toString(outputVoltageArray));
                Log.d(TAG, "timeArray: " + Arrays.toString(timeArray));
                Log.d(TAG, "numSteps: " + numStep);

                chart = findViewById(R.id.chart);
                GraphUtils.plotGraph(timeArray, outputVoltageArray, numStep, "Output Voltage", chart, TAG);
//                plotGraph(timeArray, inductanceCurrentArray, numSteps, "Inductance Current");
            }
        }
    }
}

