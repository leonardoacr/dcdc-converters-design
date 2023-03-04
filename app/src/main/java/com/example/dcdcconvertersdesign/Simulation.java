package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import com.example.dcdcconvertersdesign.helpers.Helpers;
import com.example.dcdcconvertersdesign.simulationutilities.CalculateBoostArrays;
import com.example.dcdcconvertersdesign.simulationutilities.CalculateBuckBoostArrays;
import com.example.dcdcconvertersdesign.simulationutilities.CalculateBuckArrays;
import com.example.dcdcconvertersdesign.simulationutilities.GraphUtils;
import com.example.dcdcconvertersdesign.simulationutilities.LimitsDialog;
import com.example.dcdcconvertersdesign.simulationutilities.SaveDialog;
import com.example.dcdcconvertersdesign.simulationutilities.SolveDiffEquations;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;

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
    private double dutyCycleIdeal;
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
        Helpers.unsetActionBar(this);
        // retrieve extras from the intent
        Bundle simulationData = getIntent().getExtras();
        if (simulationData != null) {
            recoverSimulationData(simulationData);

            numStep = (int) (maxTime / timeStep);

            // Buck Simulation
            if (flag == 1) {
                SolveDiffEquations.buckConverter(outputVoltage, inputVoltage, dutyCycleIdeal,
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
                SolveDiffEquations.boostConverter(outputVoltage, inputVoltage, dutyCycleIdeal,
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
                SolveDiffEquations.buckBoostConverter(outputVoltage, inputVoltage, dutyCycleIdeal,
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
        dutyCycleIdeal = simulationData.getDouble("Duty_Cycle_Ideal");
        inductance = simulationData.getDouble("Inductance");
        capacitance = simulationData.getDouble("Capacitance");
        frequency = simulationData.getDouble("Frequency");
        flag = simulationData.getInt("Flag");
        resistance = simulationData.getDouble("Resistance");

        Log.d(TAG, "Recovering Simulation Data:");
        Log.d(TAG, "Output Voltage: " + outputVoltage);
        Log.d(TAG, "Input Voltage: " + inputVoltage);
        Log.d(TAG, "Duty Cycle: " + dutyCycle);
        Log.d(TAG, "Ideal Duty Cycle: " + dutyCycleIdeal);
        Log.d(TAG, "Inductance: " + inductance);
        Log.d(TAG, "Capacitance: " + capacitance);
        Log.d(TAG, "Frequency: " + frequency);
        Log.d(TAG, "Flag: " + flag);
        Log.d(TAG, "Resistance: " + resistance);

        // Define the parameters
        maxTime = simulationData.getDouble("Max_Time");
        timeStep = simulationData.getDouble("Time_Step");
        receivedID = simulationData.getString("Received_ID");
    }

    private void handleID(String receivedID, int flag) {
//        SimulationParameters.showProgressBar();
        Log.d(TAG, String.valueOf(chart.getData()));

        synchronized (this) {
            while (chart.getData() == null || chart.getData().getEntryCount() != outputVoltageArray.length-1) {
                handleSwitch(receivedID, flag);
            }
        }

//        SimulationParameters.hideProgressBar();
        new Handler().postDelayed(SimulationParameters::hideProgressBar, numStep/200);

        Log.d(TAG, String.valueOf(chart.getData().getEntryCount()));
        Log.d(TAG, String.valueOf(outputVoltageArray.length-1));
        Log.d(TAG, "numStep: " + numStep);
    }


    private void handleSwitch(String receivedID, int flag) {
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
                    outputCurrentArray = CalculateBuckArrays.calculateOutputCurrentArray(
                            outputVoltageArray, resistance);
                }
                if(flag == 2){
                    outputCurrentArray = CalculateBoostArrays.calculateOutputCurrentArray(
                            outputVoltageArray, resistance);
                }
                if(flag == 3){
                    outputCurrentArray = CalculateBuckBoostArrays.calculateOutputCurrentArray(
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
                    inputCurrentArray = CalculateBuckArrays.calculateInputCurrentArray(
                            inductorCurrentArray, sArray);
                }
                if(flag == 2) {
                    inputCurrentArray = inductorCurrentArray;
                }
                if(flag == 3) {
                    inputCurrentArray = CalculateBuckBoostArrays.calculateInputCurrentArray(
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
                    diodeCurrentArray = CalculateBuckArrays.calculateDiodeCurrentArray(
                            inductorCurrentArray, sArray);
                }
                if(flag == 2){
                    diodeCurrentArray = CalculateBoostArrays.calculateDiodeCurrentArray(
                            inductorCurrentArray, sArray);
                }
                if(flag == 3){
                    diodeCurrentArray = CalculateBuckBoostArrays.calculateDiodeCurrentArray(
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
                    switchCurrentArray = CalculateBuckArrays.calculateSwitchCurrentArray(
                            inductorCurrentArray, sArray);
                }
                if(flag == 2){
                    switchCurrentArray = CalculateBoostArrays.calculateSwitchCurrentArray(
                            inductorCurrentArray, sArray);
                }
                if(flag == 3){
                    switchCurrentArray = CalculateBuckBoostArrays.calculateSwitchCurrentArray(
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
                    capacitorCurrentArray = CalculateBuckArrays.calculateCapacitorCurrentArray(
                            outputVoltageArray, inductorCurrentArray, resistance);
                }
                if(flag == 2){
                    capacitorCurrentArray = CalculateBoostArrays.calculateCapacitorCurrentArray(
                            outputVoltageArray, inductorCurrentArray, resistance, sArray);
                }
                if(flag == 3){
                    capacitorCurrentArray = CalculateBuckBoostArrays.calculateCapacitorCurrentArray(
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




