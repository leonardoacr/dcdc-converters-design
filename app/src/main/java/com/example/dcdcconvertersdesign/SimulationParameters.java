package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dcdcconvertersdesign.helpers.Helpers;

public class SimulationParameters extends AppCompatActivity {
    private static final String TAG = "Simulation";
    private double maxTime, switchingFrequencyRecovered, switchingFrequencyValue, memoryCalculated,
            maxTimeRecommendedCalculated;
    private String unit;
    private TextView switchingFrequency, timeStep, timeStepText;
    private TextView requiredMemoryText, requiredMemory, requiredMemoryBigText,
            maxTimeRecommended, maxTimeRecommendedText;
    private EditText  maxTimeEditText;
    private Button outputVoltageBtn, outputCurrentBtn, inputCurrentBtn, switchCurrentBtn, diodeCurrentBtn,
            inductorCurrentBtn, capacitorCurrentBtn;

    private RelativeLayout layout16, layout17;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_parameters);
        Helpers.setMinActionBar(this);
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
        String formattedFrequency;
        formattedFrequency = formatSwitchingFrequency();
        switchingFrequency.setText(formattedFrequency);

        double timeStepCalculated = calculateTimeStep(switchingFrequencyRecovered);
        String formattedTimeStep = formatTimeStep(timeStepCalculated);
        timeStep.setText(formattedTimeStep);

        maxTimeEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String maxTimeStr = v.getText().toString();
                if (!maxTimeStr.isEmpty()) {
                    calculateSimulationParameters(maxTimeStr, timeStepCalculated);
                    // Show texts
                    handleSimulationTexts(memoryCalculated);

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
            unit = "μ"; // for micro (10^-6)
        } else {
            coefficient *= 1000;
            unit = "n";
        }
        timeStepText.setText("Time Step");
        return Helpers.stringFormat(coefficient) + " " + unit + "s";
    }

    private String formatSwitchingFrequency(){
        switchingFrequencyValue = switchingFrequencyRecovered;
        if (switchingFrequencyValue >= 1000 && switchingFrequencyValue < 1000000) {
            switchingFrequencyValue /= 1000;
            unit = "kHz";
        } else if (switchingFrequencyValue >= 1000000) {
            switchingFrequencyValue /= 1000000;
            unit = "MHz";
        } else {
            unit = "Hz";
        }
        return Helpers.stringFormat(switchingFrequencyValue) + " " + unit;
    }

    private void createObjects()
    {
        // Output Values
        switchingFrequency = (TextView) findViewById(R.id.switching_frequency_simulation);
        timeStep = (TextView) findViewById(R.id.time_step_simulation);
        timeStepText = (TextView) findViewById(R.id.time_step_text_simulation);
        maxTimeRecommended = (TextView) findViewById(R.id.max_time_recommended_simulation);

        // Output Text
        requiredMemoryText = (TextView) findViewById(R.id.required_memory_text);
        requiredMemoryBigText = (TextView) findViewById(R.id.max_time_text_3);
        maxTimeRecommendedText = (TextView) findViewById(R.id.max_time_recommended_text_simulation);

        // Input Values
        maxTimeEditText = (EditText) findViewById(R.id.max_time_simulation);
        requiredMemory = (TextView) findViewById(R.id.required_memory);

        // Buttons
        outputVoltageBtn = (Button) findViewById(R.id.output_voltage_btn_simulation);
        outputCurrentBtn = (Button) findViewById(R.id.output_current_btn_simulation);
        inputCurrentBtn = (Button) findViewById(R.id.input_current_btn_simulation);
        switchCurrentBtn = (Button) findViewById(R.id.switch_current_btn_simulation);
        diodeCurrentBtn = (Button) findViewById(R.id.diode_current_btn_simulation);
        inductorCurrentBtn = (Button) findViewById(R.id.inductor_current_btn_simulation);
        capacitorCurrentBtn = (Button) findViewById(R.id.capacitor_current_btn_simulation);

        // Relative Layout
        layout16 = (RelativeLayout) findViewById(R.id.main_container_16);
        layout17 = (RelativeLayout) findViewById(R.id.main_container_17);

    }

    private void sendDataSimulation(double maxTime, double timeStepCalculated, String receivedID){
        Intent intentSimulation = new Intent(
                SimulationParameters.this, Simulation.class);
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

        memoryCalculated = numStepCalculated*requiredMemoryByByteCSV*arraysQuantity;

        maxTimeRecommendedCalculated = (timeStepCalculated*maxMemoryAllowed
                /(requiredMemoryByByteCSV*arraysQuantity))*1000;
    }

    private void handleSimulationTexts(double memoryCalculated) {
        String memoryString = Helpers.stringFormat(memoryCalculated) + " MB";
        requiredMemory.setText(memoryString);
        requiredMemoryText.setText(R.string.required_memory_info);

        ((TextView) findViewById(R.id.simulation_options)).setVisibility(View.VISIBLE);

        requiredMemoryBigText.setVisibility(View.VISIBLE);
        if(memoryCalculated > 10.05) {
            requiredMemoryBigText.setText(R.string.required_memory_big_text);
            requiredMemoryBigText.setTextColor(Color.RED);

            String maxTimeFormattedString = Helpers.stringFormat(maxTimeRecommendedCalculated) + " ms";
            maxTimeRecommended.setText(maxTimeFormattedString);
            maxTimeRecommendedText.setText(R.string.max_time_recommended_info);
            layout17.setVisibility(View.VISIBLE);
        } else {
            requiredMemoryBigText.setText("");
        }

        layout16.setVisibility(View.VISIBLE);
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

