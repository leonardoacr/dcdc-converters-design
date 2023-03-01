package com.example.dcdcconvertersdesign;

import static com.example.dcdcconvertersdesign.convertersutilities.CalculateConverterVariables.*;
import static com.example.dcdcconvertersdesign.convertersutilities.VerifyInputErrors.*;
import static com.example.dcdcconvertersdesign.helpers.Helpers.showToast;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dcdcconvertersdesign.convertersutilities.CalculateConverterVariables;
import com.example.dcdcconvertersdesign.helpers.Helpers;

public class UsualDesign extends AppCompatActivity {
    // UI-related variables
    private EditText inputVoltageEditText, outputVoltageEditText, outputPowerEditText,
            rippleInductorCurrentEditText, rippleCapacitorVoltageEditText,
            frequencyEditText, efficiencyEditText;
    private Button buckBtn, boostBtn, buckBoostBtn, exampleBtn;

    // Calculation-related variables
    public static double inputCurrent, outputCurrent, inductorCurrentMax, inductorCurrentMin,
            outputVoltageMax, outputVoltageMin, deltaInductorCurrent, deltaCapacitorVoltage,
            outputPower, rippleInductorCurrent, rippleCapacitorVoltage, outputVoltage,
            inputVoltage,  frequency, efficiency;
    public static double dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
            inductanceCritical, inductorCurrent, switchCurrent, diodeCurrent,
            inductorCurrentRMS;

    public static boolean isCCM;

    // Flags and constants
    private int flag;
    private static final String TAG = "UsualDesign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usual_design);
        Helpers.setMainActionBar(this);

        createObjects();

        // Example button listener
        exampleBtn.setOnClickListener(v -> {
            inputVoltageEditText.setText("24");
            outputVoltageEditText.setText("12");
            outputPowerEditText.setText("100");
            efficiencyEditText.setText("90");
            frequencyEditText.setText("50");
            rippleInductorCurrentEditText.setText("20");
            rippleCapacitorVoltageEditText.setText("1");
        });

        // Buck Converter
        buckBtn.setOnClickListener(v -> {
            flag = 1;
            if (isEmpty()) {
                return;
            }
            getInputs();
            CalculateConverterVariables.buckCalculations();
            if (inputVoltage > outputVoltage && isValid()) {
                Log.d(TAG, "Flag: " + flag);
                // Sending data
                Intent intent = new Intent(UsualDesign.this, BuckConverter.class);
                sendIntent(intent);
            } else {
                verifyInputErrorsBuck(this);
            }
        });

        // Boost Converter
        boostBtn.setOnClickListener(v -> {
            flag = 2;
            if (isEmpty()) {
                return;
            }
            getInputs();
            CalculateConverterVariables.boostCalculations();
            if (inputVoltage < outputVoltage && isValid()) {
                // Sending data
                Intent intent = new Intent(UsualDesign.this, BoostConverter.class);
                sendIntent(intent);
            } else {
                verifyInputErrorsBoost(this);
            }
        });

        // Buck-Boost Calculations
        buckBoostBtn.setOnClickListener(v -> {
            flag = 3;
            if (isEmpty()) {
                return;
            }
            getInputs();
            CalculateConverterVariables.buckBoostCalculations();
            if (inputVoltage != outputVoltage && isValid()) {
                // Sending data
                Intent intent = new Intent(UsualDesign.this, BuckBoostConverter.class);
                sendIntent(intent);
            } else {
                verifyInputErrorsBuckBoost(this);
            }
        });
    }

    public void createObjects() {
        // Inputs
        inputVoltageEditText = findViewById(R.id.input_voltage);
        outputVoltageEditText = findViewById(R.id.output_voltage);
        outputPowerEditText = findViewById(R.id.output_power);
        rippleInductorCurrentEditText = findViewById(R.id.ripple_inductor_current);
        rippleCapacitorVoltageEditText = findViewById(R.id.ripple_capacitor_voltage);
        frequencyEditText = findViewById(R.id.frequency);
        efficiencyEditText = findViewById(R.id.efficiency);

        // Buttons
        buckBtn = findViewById(R.id.buck_btn);
        boostBtn = findViewById(R.id.boost_btn);
        buckBoostBtn = findViewById(R.id.buck_boost_btn);
        exampleBtn = findViewById(R.id.example_usual_design_btn);
    }

    private boolean isEmpty() {
        EditText[] fields = {inputVoltageEditText, outputVoltageEditText, outputPowerEditText,
                rippleInductorCurrentEditText, rippleCapacitorVoltageEditText, frequencyEditText,
                efficiencyEditText};
        for (EditText field : fields) {
            if (field.getText().toString().isEmpty()) {
                showToast(this, "Error! Something is empty");
                return true;
            }
        }
        return false;
    }

    private boolean isValid() {
        return dutyCycle <= 0.95 && dutyCycle >= 0.05 && resistance > 0 && inductance > 0 &&
                capacitance > 0 && efficiency <= 100;
    }
    private void getInputs() {
        // Convert strings to Double
        inputVoltage = Double.parseDouble(inputVoltageEditText.getText().toString());
        outputVoltage = Double.parseDouble(outputVoltageEditText.getText().toString());
        outputPower = Double.parseDouble(outputPowerEditText.getText().toString());
        rippleInductorCurrent = Double.parseDouble(rippleInductorCurrentEditText.getText().toString());
        rippleCapacitorVoltage = Double.parseDouble(rippleCapacitorVoltageEditText.getText().toString());
        frequency = Double.parseDouble(frequencyEditText.getText().toString()) * 1000;
        efficiency = Double.parseDouble(efficiencyEditText.getText().toString());
    }
    private void setBundleVariables(Bundle data){
        data.putDouble("Duty_Cycle", dutyCycle);
        data.putDouble("Duty_Cycle_Ideal", dutyCycleIdeal);
        data.putDouble("Resistance", resistance);
        data.putDouble("Capacitance", capacitance);
        data.putDouble("Inductance", inductance);
        data.putDouble("Inductance_Crit", inductanceCritical);
        data.putDouble("Input_Current", inputCurrent);
        data.putDouble("Output_Current", outputCurrent);
        data.putDouble("Inductor_Current", inductorCurrent);
        data.putDouble("Switch_Current", switchCurrent);
        data.putDouble("Diode_Current", diodeCurrent);
        data.putDouble("Input_Voltage", inputVoltage);
        data.putDouble("Output_Voltage", outputVoltage);
        data.putDouble("Frequency", frequency);
        data.putDouble("DeltaIL", deltaInductorCurrent);
        data.putDouble("DeltaVC", deltaCapacitorVoltage);
        data.putDouble("ILrms", inductorCurrentRMS);
        data.putBoolean("is_ccm", isCCM);
        data.putInt("Flag", flag);
    }

    private void sendIntent(Intent intent){
        Bundle data = new Bundle();

        setBundleVariables(data);
        intent.putExtras(data);
        startActivity(intent);
    }
}