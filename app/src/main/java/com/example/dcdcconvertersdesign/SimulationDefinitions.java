package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SimulationDefinitions extends AppCompatActivity {
    private static final String TAG = "Simulation";
    TextView switchingFrequency, timeStep;
    TextView requiredMemoryText, requiredMemory,
            maxTimeRecommended, maxTimeRecommendedText;
    EditText  maxTimeEditText;
    Button outputVoltageBtn, outputCurrentBtn, inputCurrentBtn, diodeCurrentBtn,
            inductorCurrentBtn, capacitorCurrentBtn;

    private void createObjects()
    {
        // Output Values
        switchingFrequency = (TextView) findViewById(R.id.switchingFrequency);
        timeStep = (TextView) findViewById(R.id.timeStep);
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
        diodeCurrentBtn = (Button) findViewById(R.id.diodeCurrent);
        inductorCurrentBtn = (Button) findViewById(R.id.inductorCurrent);
        capacitorCurrentBtn = (Button) findViewById(R.id.capacitorCurrent);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_definitions);
        createObjects();
        // retrieve extras from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double switchingFrequencyRecovered = extras.getDouble("Frequency");
            double flag = extras.getDouble("Flag");

            // Buck Converter
            if (flag == 1) {
                switchingFrequency.setText(String.format("%.2f", switchingFrequencyRecovered));

                double timeStepCalculated = 1 / switchingFrequencyRecovered / 1000;

                double coefficient = timeStepCalculated / Math.pow(10, -6);
                int exponent = -6;
                String formattedString = String.format("%.2fE%d", coefficient, exponent);
                timeStep.setText(formattedString);

                maxTimeEditText.setOnEditorActionListener((v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        String maxTimeStr = v.getText().toString();
                        //                String maxTimeStr = maxTimeEditText.getText().toString();
                        if (!maxTimeStr.isEmpty()) {
                            double maxTime = Double.parseDouble(maxTimeStr) / 1000;
                            double maxMemoryAllowed = 10;
                            double requiredMemoryByByteCSV = 20.0 / 1048576.0;
                            double arraysQuantity = 2;
                            double numStepCalculated = (maxTime / timeStepCalculated);
                            double memoryCalculated = numStepCalculated*requiredMemoryByByteCSV*arraysQuantity;

                            String memoryString = getString(R.string.required_memory_text, memoryCalculated);
                            requiredMemory.setText(memoryString);
                            requiredMemoryText.setText("Required memory (MB)");
                            Log.d(TAG, "INPUTS TESTES: " + memoryCalculated);

                            double maxTimeRecommendedCalculated = timeStepCalculated*maxMemoryAllowed
                                    /(requiredMemoryByByteCSV*arraysQuantity);
                            maxTimeRecommended.setText(String.format("%.2f", maxTimeRecommendedCalculated*1000));
                            maxTimeRecommendedText.setText("Max time recommended (ms)");

                            // Handling simulation buttons
                            // ....
                            //
                        }
                        return true;
                    }
                    return false;
                });
            }
        }
    }
}

