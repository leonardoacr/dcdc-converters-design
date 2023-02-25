package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class SimulationDefinitions extends AppCompatActivity {
    private static final String TAG = "Simulation";
    private double maxTime, switchingFrequencyRecovered;
    TextView switchingFrequency, timeStep, timeStepText;
    TextView requiredMemoryText, requiredMemory,
            maxTimeRecommended, maxTimeRecommendedText;
    EditText  maxTimeEditText;
    Button outputVoltageBtn, outputCurrentBtn, inputCurrentBtn, switchCurrentBtn, diodeCurrentBtn,
            inductorCurrentBtn, capacitorCurrentBtn;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_definitions);
        createObjects();
        // retrieve extras from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            switchingFrequencyRecovered = extras.getDouble("Frequency");

            // Converter
            converterCalculations();
        }
    }
    private void converterCalculations() {
        switchingFrequency.setText(String.format(Locale.US, "%.2f", switchingFrequencyRecovered));

        double timeStepCalculated = calculateTimeStep(switchingFrequencyRecovered);
        String formattedTimeStep = formatTimeStep(timeStepCalculated);

        // Set formatted time step
        timeStep.setText(formattedTimeStep);

        maxTimeEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String maxTimeStr = v.getText().toString();
                if (!maxTimeStr.isEmpty()) {
                    handleMaxTimeEditText(v.getText().toString(), timeStepCalculated);

                    // Show texts
                    handleSimulationTexts();

                    // Handling simulation buttons
                    handleSimulationButtons(maxTime, timeStepCalculated);
                }
                return true;
            }
            return false;
        });
    }

    private double calculateTimeStep(double switchingFrequencyRecovered) {
        return 1 / switchingFrequencyRecovered / 1000;
    }

    private String formatTimeStep(double timeStepCalculated) {
        double coefficient = timeStepCalculated / Math.pow(10, -6);
        String unit;
        if (coefficient >= 1) {
            unit = "u"; // for micro (10^-6)
        } else {
            coefficient *= 1000;
            unit = "n";
        }
        timeStepText.setText("Time Step (" + unit + "s)");

        return String.format(Locale.US, "%.2f", coefficient);
    }

    private void handleMaxTimeEditText(String maxTimeStr, double timeStepCalculated) {
        if (!maxTimeStr.isEmpty()) {
            calculateSimulationParameters(maxTimeStr, timeStepCalculated);
        }
    }

    private void createObjects()
    {
        // Output Values
        switchingFrequency = (TextView) findViewById(R.id.switchingFrequency);
        timeStep = (TextView) findViewById(R.id.timeStep);
        timeStepText = (TextView) findViewById(R.id.timeStepText);
        maxTimeRecommended = (TextView) findViewById(R.id.maxTimeRecommended);

        // Input Text
        requiredMemoryText = (TextView) findViewById(R.id.requiredMemoryText);
        maxTimeRecommendedText = (TextView) findViewById(R.id.maxTimeRecommendedText);

        // Input Values
        maxTimeEditText = (EditText) findViewById(R.id.maxTime);
        requiredMemory = (TextView) findViewById(R.id.requiredMemory);

        // Buttons
        outputVoltageBtn = (Button) findViewById(R.id.outputVoltage);
        outputCurrentBtn = (Button) findViewById(R.id.outputCurrent);
        inputCurrentBtn = (Button) findViewById(R.id.inputCurrent);
        switchCurrentBtn = (Button) findViewById(R.id.switchCurrent);
        diodeCurrentBtn = (Button) findViewById(R.id.diodeCurrent);
        inductorCurrentBtn = (Button) findViewById(R.id.inductorCurrent);
        capacitorCurrentBtn = (Button) findViewById(R.id.capacitorCurrent);
    }

    private void sendDataSimulation(double maxTime, double timeStepCalculated, String receivedID){
        Intent intentSimulation = new Intent(
                SimulationDefinitions.this, Simulation.class);
        Bundle simulationData = new Bundle();
        // copy all extras from the previous activity
        simulationData.putAll(getIntent().getExtras());
        simulationData.putDouble("Max_Time", maxTime);
        simulationData.putDouble("Time_Step", timeStepCalculated);
        simulationData.putString("Received_ID", receivedID);
        intentSimulation.putExtras(simulationData);
        startActivity(intentSimulation);
    }

    private void calculateSimulationParameters(String maxTimeStr, double timeStepCalculated){
        maxTime = Double.parseDouble(maxTimeStr) / 1000;
        double maxMemoryAllowed = 10; // MB
        double requiredMemoryByByteCSV = 16.0 / 1048576.0;
        double arraysQuantity = 2;
        double numStepCalculated = (maxTime / timeStepCalculated);
        double memoryCalculated = numStepCalculated*requiredMemoryByByteCSV*arraysQuantity;
        Log.d(TAG, "memoryCalculated: " + memoryCalculated);

        String memoryString = getString(R.string.required_memory_text,
                memoryCalculated);
        requiredMemory.setText(memoryString);
        requiredMemoryText.setText(R.string.required_memory_info);

        double maxTimeRecommendedCalculated = timeStepCalculated*maxMemoryAllowed
                /(requiredMemoryByByteCSV*arraysQuantity);

        maxTimeRecommended.setText(String.format(Locale.US, "%.2f",
                maxTimeRecommendedCalculated*1000));

        maxTimeRecommendedText.setText(R.string.max_time_recommended_info);
    }

    private void handleSimulationTexts() {
        ((TextView) findViewById(R.id.simulationOptions)).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.maxTimeText3)).setVisibility(View.VISIBLE);
    }

    private void handleSimulationButtons(double maxTime, double timeStepCalculated) {
        // Show buttons
        outputVoltageBtn.setVisibility(View.VISIBLE);
        outputCurrentBtn.setVisibility(View.VISIBLE);
        inputCurrentBtn.setVisibility(View.VISIBLE);
        diodeCurrentBtn.setVisibility(View.VISIBLE);
        inductorCurrentBtn.setVisibility(View.VISIBLE);
        capacitorCurrentBtn.setVisibility(View.VISIBLE);
        switchCurrentBtn.setVisibility(View.VISIBLE);

        outputVoltageBtn.setOnClickListener(view -> {
            String receivedID = "outputVoltage";
            Log.d(TAG, "Sending ID: " + receivedID);
            // Send to the simulation activity
            sendDataSimulation(maxTime, timeStepCalculated, receivedID);
        });

        inputCurrentBtn.setOnClickListener(view -> {
            String receivedID = "inputCurrent";
            Log.d(TAG, "Sending ID: " + receivedID);
            // Send to the simulation activity
            sendDataSimulation(maxTime, timeStepCalculated, receivedID);
        });

        switchCurrentBtn.setOnClickListener(view -> {
            String receivedID = "switchCurrent";
            Log.d(TAG, "Sending ID: " + receivedID);
            // Send to the simulation activity
            sendDataSimulation(maxTime, timeStepCalculated, receivedID);
        });

        outputCurrentBtn.setOnClickListener(view -> {
            String receivedID = "outputCurrent";
            Log.d(TAG, "Sending ID: " + receivedID);
            // Send to the simulation activity
            sendDataSimulation(maxTime, timeStepCalculated, receivedID);
        });

        diodeCurrentBtn.setOnClickListener(view -> {
            String receivedID = "diodeCurrent";
            Log.d(TAG, "Sending ID: " + receivedID);
            // Send to the simulation activity
            sendDataSimulation(maxTime, timeStepCalculated, receivedID);
        });

        inductorCurrentBtn.setOnClickListener(view -> {
            String receivedID = "inductorCurrent";
            Log.d(TAG, "Sending ID: " + receivedID);
            // Send to the simulation activity
            sendDataSimulation(maxTime, timeStepCalculated, receivedID);
        });

        capacitorCurrentBtn.setOnClickListener(view -> {
            String receivedID = "capacitorCurrent";
            Log.d(TAG, "Sending ID: " + receivedID);
            // Send to the simulation activity
            sendDataSimulation(maxTime, timeStepCalculated, receivedID);
        });
    }
}

