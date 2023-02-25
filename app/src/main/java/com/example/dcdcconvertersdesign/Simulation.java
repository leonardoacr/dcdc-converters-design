package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.example.dcdcconvertersdesign.simulationutilities.CalculateBoostVariables;
import com.example.dcdcconvertersdesign.simulationutilities.CalculateBuckBoostVariables;
import com.example.dcdcconvertersdesign.simulationutilities.CalculateBuckVariables;
import com.example.dcdcconvertersdesign.simulationutilities.GraphUtils;
import com.example.dcdcconvertersdesign.simulationutilities.LimitsDialog;
import com.example.dcdcconvertersdesign.simulationutilities.SaveDialog;
import com.example.dcdcconvertersdesign.simulationutilities.SolveDiffEquations;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

public class Simulation extends AppCompatActivity {
    // define a tag for logging purposes
    private static final String TAG = "Simulation";
    public static double[] outputVoltageArray;
    public static double[] inductorCurrentArray;
    public static double[] outputCurrentArray;
    public static double[] inputCurrentArray;
    public static double[] diodeCurrentArray;
    public static double[] switchCurrentArray;
    public static double[] capacitorCurrentArray;
    public static double[] timeArray;
    public static double[] sArray;

    private LineChart chart;
    private final GraphUtils graphUtils = new GraphUtils();

    private int numStep;
    private int flag;

    private double outputVoltage;
    private double inputVoltage;
    private double dutyCycle;
    private double inductance;
    private double capacitance;
    private double resistance;
    private double frequency;
    private double maxTime;
    private double timeStep;

    private String receivedID;
    private String fileNameKey;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);

        // retrieve extras from the intent
        Bundle simulationData = getIntent().getExtras();
        if (simulationData != null) {
            recoverSimulationData(simulationData);

            numStep = (int) (maxTime / timeStep);

            Log.d(TAG, "inductorCurrentArray: " + Arrays.toString(inductorCurrentArray));
            Log.d(TAG, "outputVoltageArray: " + Arrays.toString(outputVoltageArray));
            Log.d(TAG, "timeArray: " + Arrays.toString(timeArray));
            Log.d(TAG, "numSteps: " + numStep);
            Log.d(TAG, "Received ID: " + receivedID);

            // Buck Simulation
            if (flag == 1) {
                SolveDiffEquations.buckConverter(outputVoltage, inputVoltage, dutyCycle,
                        inductance, capacitance, resistance, frequency, timeStep, numStep);

                chart = findViewById(R.id.chart);

                TextView xLabel = findViewById(R.id.x_label);
                xLabel.setText("Time (ms)");

                // Handling ID received from Simulation Definitions Buttons
                handleID(receivedID, flag);

                handleDialogButtons();
            }

            // Boost Simulation
            if (flag == 2) {
                SolveDiffEquations.boostConverter(outputVoltage, inputVoltage, dutyCycle,
                        inductance, capacitance, resistance, frequency, timeStep, numStep);

                chart = findViewById(R.id.chart);

                TextView xLabel = findViewById(R.id.x_label);
                xLabel.setText("Time (ms)");

                // Handling ID received from Simulation Definitions Buttons
                handleID(receivedID, flag);

                handleDialogButtons();
            }

            // Buck Boost Simulation
            if (flag == 3) {
                SolveDiffEquations.buckBoostConverter(outputVoltage, inputVoltage, dutyCycle,
                        inductance, capacitance, resistance, frequency, timeStep, numStep);

                chart = findViewById(R.id.chart);

                TextView xLabel = findViewById(R.id.x_label);
                xLabel.setText("Time (ms)");

                // Handling ID received from Simulation Definitions Buttons
                handleID(receivedID, flag);

                handleDialogButtons();
            }
        }
    }

    private void recoverSimulationData(Bundle simulationData) {
        outputVoltage = simulationData.getDouble("Output_Voltage");
        inputVoltage = simulationData.getDouble("Input_Voltage");
        dutyCycle = simulationData.getDouble("Duty_Cycle");
        inductance = simulationData.getDouble("Inductance");
        capacitance = simulationData.getDouble("Capacitance");
        frequency = simulationData.getDouble("Frequency");
        flag = simulationData.getInt("Flag");
        resistance = simulationData.getDouble("Resistance");

        // Define the parameters
        maxTime = simulationData.getDouble("Max_Time");
        timeStep = simulationData.getDouble("Time_Step");
        receivedID = simulationData.getString("Received_ID");

        Log.d(TAG, "outputVoltage: " + outputVoltage);
        Log.d(TAG, "inputVoltage: " + inputVoltage);
        Log.d(TAG, "dutyCycle: " + dutyCycle);
        Log.d(TAG, "inductance: " + inductance);
        Log.d(TAG, "capacitance: " + capacitance);
        Log.d(TAG, "resistance: " + resistance);
        Log.d(TAG, "frequency: " + frequency);
    }
    private void handleID(String receivedID, int flag) {
        switch (receivedID) {
            case "outputVoltage":
                fileNameKey = "OutputVoltage";
                graphUtils.loadData(timeArray, outputVoltageArray,
                        numStep, fileNameKey, chart);
                graphUtils.plotGraph(chart, null, null,
                        null, null);
                break;

            case "outputCurrent":
                fileNameKey = "OutputCurrent";
                if(flag == 1){
                    outputCurrentArray = CalculateBuckVariables.calculateOutputCurrentArray(
                            outputVoltageArray, resistance);
                }
                if(flag == 2){
                    outputCurrentArray = CalculateBoostVariables.calculateOutputCurrentArray(
                            outputVoltageArray, resistance);
                }
                if(flag == 3){
                    outputCurrentArray = CalculateBuckBoostVariables.calculateOutputCurrentArray(
                            outputVoltageArray, resistance);
                }

                graphUtils.loadData(timeArray, outputCurrentArray, numStep,
                        fileNameKey,
                        chart);
                graphUtils.plotGraph(chart, null, null,
                        null, null);
                break;

            case "inputCurrent":
                fileNameKey = "InputCurrent";
                if(flag == 1) {
                    inputCurrentArray = CalculateBuckVariables.calculateInputCurrentArray(
                            inductorCurrentArray, sArray);
                }
                if(flag == 2) {
                    inputCurrentArray = inductorCurrentArray;
                }
                if(flag == 3) {
                    inputCurrentArray = CalculateBuckBoostVariables.calculateInputCurrentArray(
                            inductorCurrentArray, sArray);
                }

                graphUtils.loadData(timeArray, inputCurrentArray, numStep,
                        fileNameKey, chart);
                graphUtils.plotGraph(chart, null, null,
                        null, null);
                break;

            case "diodeCurrent":
                fileNameKey = "DiodeCurrent";
                if(flag == 1){
                    diodeCurrentArray = CalculateBuckVariables.calculateDiodeCurrentArray(
                            inductorCurrentArray, sArray);
                }
                if(flag == 2){
                    diodeCurrentArray = CalculateBoostVariables.calculateDiodeCurrentArray(
                            inductorCurrentArray, sArray);
                }
                if(flag == 3){
                    diodeCurrentArray = CalculateBuckBoostVariables.calculateDiodeCurrentArray(
                            inductorCurrentArray, sArray);
                }

                graphUtils.loadData(timeArray, diodeCurrentArray, numStep,
                        fileNameKey, chart);
                graphUtils.plotGraph(chart, null, null,
                        null, null);
                break;

            case "switchCurrent":
                fileNameKey = "SwitchCurrent";
                if(flag == 1){
                    switchCurrentArray = inputCurrentArray;
                }
                if(flag == 2){
                    switchCurrentArray = CalculateBoostVariables.calculateSwitchCurrentArray(
                            inductorCurrentArray, sArray);
                }
                if(flag == 3){
                    switchCurrentArray = CalculateBuckBoostVariables.calculateSwitchCurrentArray(
                            inductorCurrentArray, sArray);
                }

                graphUtils.loadData(timeArray, switchCurrentArray, numStep,
                        fileNameKey, chart);
                graphUtils.plotGraph(chart, null, null,
                        null, null);
                break;

            case "inductorCurrent":
                fileNameKey = "InductorCurrent";
                graphUtils.loadData(timeArray, inductorCurrentArray, numStep,
                        fileNameKey, chart);
                graphUtils.plotGraph(chart, null, null,
                        null, null);
                break;

            case "capacitorCurrent":
                fileNameKey = "CapacitorCurrent";
                if(flag == 1){
                    capacitorCurrentArray = CalculateBuckVariables.calculateCapacitorCurrentArray(
                            outputVoltageArray, inductorCurrentArray, resistance);
                }
                if(flag == 2){
                    capacitorCurrentArray = CalculateBoostVariables.calculateCapacitorCurrentArray(
                            outputVoltageArray, inductorCurrentArray, resistance, sArray);
                }
                if(flag == 3){
                    capacitorCurrentArray = CalculateBuckBoostVariables.calculateCapacitorCurrentArray(
                            outputVoltageArray, inductorCurrentArray, resistance, sArray);
                }

                graphUtils.loadData(timeArray, capacitorCurrentArray, numStep,
                        fileNameKey, chart);
                graphUtils.plotGraph(chart, null, null,
            null, null);
                break;

            default:
                // Handle case when receivedID is not recognized
                Log.d(TAG, "received ID not recognized");
                break;
        }
    }

    private void handleDialogButtons() {
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

        // Handling Limits button and open dialog:
        Button saveGraphButton = findViewById(R.id.save_button);

        // Set an onClickListener for the options_button
        saveGraphButton.setOnClickListener(v -> {
            // Create a new instance of the LimitsDialogFragment
            SaveDialog dialog = new SaveDialog();

            dialog.setListener((saveKey) -> {
                Log.d(TAG, "Save Graph Key: " + saveKey);
                String folderName = "/DCDCConvertersDesign";
                String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + folderName;
                if (Objects.equals(saveKey, "PNG")) {
                    savePNG(directoryPath);
                }
                if (Objects.equals(saveKey, "CSV")) {
                    saveCSV(directoryPath);
                }
            });
            // Show the dialog
            dialog.show(getSupportFragmentManager(), "SaveDialog");
        });
    }
    private void alertBox(String alertString) {
        // create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // set the message to display in the dialog box
        builder.setMessage(alertString);
        // set a listener for when the user clicks outside of the dialog box to close it
        builder.setOnCancelListener(dialog1 -> {
            // do nothing, the dialog will just close
        });
        // create the dialog box and show it
        AlertDialog alert = builder.create();
        alert.show();
    }
    private String generateCSVFromChartData() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < GraphUtils.xGlobal.length; i++) {
            stringBuilder.append(GraphUtils.xGlobal[i]).append(",").append(GraphUtils.yGlobal[i]).append("\n");
        }
        return stringBuilder.toString();
    }
    private void savePNG(String directoryPath) {
        // Save the chart to PNG
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String fileName = fileNameKey + ".png";
        File file = new File(directoryPath, fileName);
        int count = 1;
        while (file.exists()) {
            fileName = fileNameKey + " (" + count + ").png";
            file = new File(directoryPath, fileName);
            count++;
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            Bitmap chartBitmap = chart.getChartBitmap();
            chartBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            Log.d(TAG, "File directory: " + directoryPath);
            Log.d(TAG, "File created: " + file.exists());
            alertBox("File saved to " + directoryPath);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save file. ERROR: " + e);
            alertBox("Failed to save file. ERROR: " + e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void saveCSV(String directoryPath) {
        // Save the chart to CSV
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
        String fileName = fileNameKey + ".csv";
        File file = new File(directory, fileName);
        int count = 1;
        while (file.exists()) {
            fileName = fileNameKey + count + ".csv";
            file = new File(directory, fileName);
            count++;
        }

        String csv = generateCSVFromChartData(); // This method generates the CSV data

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(csv.getBytes());
            outputStream.close();
            Log.d(TAG, "File directory: " + directoryPath);
            Log.d(TAG, "File created: " + file.exists());
            alertBox("File saved to " + directoryPath);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save file. ERROR: " + e);
            alertBox("Failed to save file. ERROR: " + e);
        }
    }
}




