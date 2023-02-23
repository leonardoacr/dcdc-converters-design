package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

import com.example.dcdcconvertersdesign.simulationutilities.CalculateBuckVariables;
import com.example.dcdcconvertersdesign.simulationutilities.GraphUtils;
import com.example.dcdcconvertersdesign.simulationutilities.LimitsDialog;
import com.example.dcdcconvertersdesign.simulationutilities.SolveDiffEquations;
import com.github.mikephil.charting.charts.LineChart;

public class Simulation extends AppCompatActivity {
    // define a tag for logging purposes
    private static final String TAG = "Simulation";
    public static double[] outputVoltageArray;
    public static double[] inductorCurrentArray;
    public static double[] outputCurrentArray;
    public static double[] inputCurrentArray;
    public static double[] diodeCurrentArray;
    public static double[] capacitorCurrentArray;
    public static double[] timeArray;
    public static double[] sArray;

    // Declare x and y limits as instance variables
    private Double xLowerLimit;
    private Double xUpperLimit;
    private Double yLowerLimit;
    private Double yUpperLimit;

    private LineChart chart;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        // retrieve extras from the intent
        Bundle simulationData = getIntent().getExtras();
        if (simulationData != null) {
            double outputVoltage = simulationData.getDouble("Output_Voltage");
            double inputVoltage = simulationData.getDouble("Input_Voltage");
            double dutyCycle = simulationData.getDouble("Duty_Cycle");
            double inductance = simulationData.getDouble("Inductance");
            double capacitance = simulationData.getDouble("Capacitance");
            double resistance = simulationData.getDouble("Resistance");
            double frequency = simulationData.getDouble("Frequency");
            double flag = simulationData.getDouble("Flag");

            // Define the parameters
            double maxTime = simulationData.getDouble("Max_Time");
            double timeStep = simulationData.getDouble("Time_Step");
            String receivedID = simulationData.getString("Received_ID");

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

                Log.d(TAG, "inductorCurrentArray: " + Arrays.toString(inductorCurrentArray));
                Log.d(TAG, "outputVoltageArray: " + Arrays.toString(outputVoltageArray));
                Log.d(TAG, "timeArray: " + Arrays.toString(timeArray));
                Log.d(TAG, "numSteps: " + numStep);
                Log.d(TAG, "Received ID: " + receivedID);

                chart = findViewById(R.id.chart);

                // Handling ID received from Simulation Definitions to decide the variable
                GraphUtils graphUtils = new GraphUtils();

                TextView xLabel = findViewById(R.id.x_label);
                xLabel.setText("Time (ms)");

                switch (receivedID) {
                    case "outputVoltage":
                        graphUtils.loadData(timeArray, outputVoltageArray,
                                numStep, "Output Voltage", chart);
                        graphUtils.plotGraph(chart, null, null, null, null);
                        break;
                    case "outputCurrent":
                        outputCurrentArray = CalculateBuckVariables.calculateOutputCurrentArray(
                                outputVoltageArray, resistance);

                        graphUtils.loadData(timeArray, outputCurrentArray, numStep,
                                "Output Current",
                                chart);
                        graphUtils.plotGraph(chart, null, null, null, null);
                        break;
                    case "inputCurrent":
                        inputCurrentArray = CalculateBuckVariables.calculateInputCurrentArray(
                                inductorCurrentArray, sArray);

                        graphUtils.loadData(timeArray, inputCurrentArray, numStep,
                                "Input Current", chart);
                        graphUtils.plotGraph(chart, null, null, null, null);
                        break;
                    case "diodeCurrent":
                        diodeCurrentArray = CalculateBuckVariables.calculateDiodeCurrentArray(
                                inductorCurrentArray, sArray);

                        graphUtils.loadData(timeArray, diodeCurrentArray, numStep,
                                "Diode Current", chart);
                        graphUtils.plotGraph(chart, null, null, null, null);
                        break;
                    case "inductorCurrent":
                        graphUtils.loadData(timeArray, inductorCurrentArray, numStep,
                                "Inductor Current", chart);
                        graphUtils.plotGraph(chart, null, null, null, null);
                        break;
                    case "capacitorCurrent":
                        capacitorCurrentArray = CalculateBuckVariables.calculateCapacitorCurrentArray(
                                outputVoltageArray, inductorCurrentArray, resistance);

                        graphUtils.loadData(timeArray, capacitorCurrentArray, numStep,
                                "Capacitor Current", chart);
                        graphUtils.plotGraph(chart, null, null, null, null);
                        break;
                    default:
                        // Handle case when receivedID is not recognized
                        Log.d(TAG, "received ID not recognized");
                        break;
                }

                // Handling Limits button and open dialog:
                Button optionsButton = findViewById(R.id.options_button);

                // Set an onClickListener for the options_button
                optionsButton.setOnClickListener(v -> {
                    // Create a new instance of the LimitsDialogFragment
                    LimitsDialog dialog = new LimitsDialog();

                    dialog.setListener((xLowerLimit, xUpperLimit, yLowerLimit, yUpperLimit) -> {
                        Log.d(TAG, "X Lower Limit: " + xLowerLimit);
                        Log.d(TAG, "X Upper Limit: " + xUpperLimit);
                        Log.d(TAG, "Y Lower Limit: " + yLowerLimit);
                        Log.d(TAG, "Y Upper Limit: " + yUpperLimit);
                        graphUtils.plotGraph(chart, xLowerLimit, xUpperLimit, yLowerLimit, yUpperLimit);
                    });
                    // Show the dialog
                    dialog.show(getSupportFragmentManager(), "LimitsDialog");
                });
            }
        }
    }
}




