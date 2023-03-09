package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dcdcconvertersdesign.utils.convertersutils.CalculateConverterVariablesReverse;
import com.example.dcdcconvertersdesign.helpers.Helpers;
import com.example.dcdcconvertersdesign.views.ConverterReverseActivity;

public class ReverseDesign extends AppCompatActivity {
    public int flag;
    public static double inputVoltage, outputVoltage, outputPower, rippleInductorCurrent,
        rippleCapacitorVoltage, frequency, switchCurrent, diodeCurrent,  inductorCurrentRMS,
            efficiency, inductorCurrentMax, inductorCurrentMin, inductorCurrent, outputVoltageMax,
            outputVoltageMin;
    public static double inputCurrent, outputCurrent, deltaInductorCurrent, deltaCapacitorVoltage;
    public static double dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance, inductanceCritical;

    public static boolean isCCM;
    private EditText inductanceEditText, resistanceEditText, capacitanceEditText, inputVoltageEditText,
            outputVoltageEditText, frequencyEditText, efficiencyEditText;
    private Button buckBtn, boostBtn, buckBoostBtn, exampleBtn;

    String TAG = "ReverseDesign";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse_design);
        Helpers.setMainActionBar(this);
        createObjects();

        // Example button listener
        exampleBtn.setOnClickListener(v -> {
            inputVoltageEditText.setText("24");
            outputVoltageEditText.setText("12");
            inductanceEditText.setText("71.11");
            capacitanceEditText.setText("17.36");
            resistanceEditText.setText("1.44");
            frequencyEditText.setText("50");
            efficiencyEditText.setText("90");
        });

        buckBtn.setOnClickListener(v -> {
            flag = 1;
            if (isEmpty()) {
                Helpers.showToast(this, "Error! Something is empty");
                return;
            }

            InputValues();
            CalculateConverterVariablesReverse.buckCalculations();
            Log.d(TAG, "HEY" + dutyCycle);
            if (inputVoltage > outputVoltage && dutyCycle <= 0.95 && dutyCycle >= 0.05) {
                sendDataToConvertersR();
            }
            if (inputVoltage < outputVoltage) {
                Helpers.showToast(this, "Error! Output bigger than Input");
            }
            if (inputVoltage == outputVoltage) {
                Helpers.showToast(this, "Error! Input and Output are equal");
            }
            if((dutyCycle > 0.95 || dutyCycle < 0.05) && inputVoltage > outputVoltage) {
                Helpers.showToast(this, "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)");
            }
        });

        boostBtn.setOnClickListener(v -> {
            flag = 2;
            if (isEmpty()) {
                Helpers.showToast(this, "Error! Something is empty");
                return;
            }
            InputValues();
            CalculateConverterVariablesReverse.boostCalculations();

            if(inputVoltage < outputVoltage && dutyCycle <= 0.95 && dutyCycle >= 0.05) {
                sendDataToConvertersR();
            }
            if (inputVoltage == outputVoltage) {
                Toast.makeText(getApplicationContext(), "Error! Input and Output are equal", Toast.LENGTH_SHORT).show();
            }
            if(inputVoltage > outputVoltage) {
                Toast.makeText(getApplicationContext(), "Error! Input bigger than Output", Toast.LENGTH_SHORT ).show();
            }
            if((dutyCycle > 0.95 || dutyCycle < 0.05) && inputVoltage < outputVoltage) {
                Toast.makeText(getApplicationContext(), "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)", Toast.LENGTH_SHORT).show();
            }
        });

        buckBoostBtn.setOnClickListener(v -> {
            flag = 3;
            if (isEmpty()) {
                Helpers.showToast(this, "Error! Something is empty");
                return;
            }
            InputValues();
            CalculateConverterVariablesReverse.buckBoostCalculations();

            if (inputVoltage != outputVoltage && dutyCycle <= 0.95 && dutyCycle >= 0.05) {
                sendDataToConvertersR();
            }
            if (inputVoltage == outputVoltage) {
                Helpers.showToast(this, "Error! Input and Output are equal");
            }
            if((dutyCycle > 0.95 || dutyCycle < 0.05) && inputVoltage != outputVoltage) {
                Helpers.showToast(this, "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)");
            }
        });

    }

    public void createObjects() {
        // Input Values
        inputVoltageEditText = findViewById(R.id.input_voltage_reverse);
        outputVoltageEditText = findViewById(R.id.output_voltage);
        frequencyEditText = findViewById(R.id.frequency_reverse);
        inductanceEditText = findViewById(R.id.inductance_reverse);
        resistanceEditText = findViewById(R.id.resistance_reverse);
        capacitanceEditText = findViewById(R.id.capacitance_reverse);
        efficiencyEditText = findViewById(R.id.efficiency_reverse);

        // Buttons
        buckBtn = findViewById(R.id.buck_btn_reverse);
        boostBtn = findViewById(R.id.boost_btn_reverse);
        buckBoostBtn = findViewById(R.id.buck_boost_btn_reverse);
        exampleBtn = findViewById(R.id.example_reverse_design_btn);
    }

    public boolean isEmpty(){
        return inputVoltageEditText.getText().toString().isEmpty() ||
                outputVoltageEditText.getText().toString().isEmpty() ||
                inductanceEditText.getText().toString().isEmpty() ||
                resistanceEditText.getText().toString().isEmpty() ||
                capacitanceEditText.getText().toString().isEmpty() ||
                frequencyEditText.getText().toString().isEmpty();
    }

    public void InputValues() {
        inputVoltage = Double.parseDouble(inputVoltageEditText.getText().toString());
        outputVoltage = Double.parseDouble(outputVoltageEditText.getText().toString());
        resistance = Double.parseDouble(resistanceEditText.getText().toString());
        inductance = Double.parseDouble(inductanceEditText.getText().toString()) / 1e6;
        capacitance = Double.parseDouble(capacitanceEditText.getText().toString()) / 1e6;
        frequency = Double.parseDouble(frequencyEditText.getText().toString()) * 1e3;
        efficiency = Double.parseDouble(efficiencyEditText.getText().toString());
    }

    public void sendDataToConvertersR() {
        Intent intent = new Intent(getApplicationContext(), ConverterReverseActivity.class);
        Bundle data = new Bundle();

        // Sending Data to Write Values
        data.putDouble("Duty_Cycle", dutyCycle);
        data.putDouble("Duty_Cycle_Ideal", dutyCycleIdeal);
        data.putDouble("Inductance_Crit", inductanceCritical);
        data.putDouble("Ripplevc", rippleCapacitorVoltage);
        data.putDouble("Rippleil", rippleInductorCurrent);
        data.putDouble("Output_Power", outputPower);
        data.putDouble("Inductance", inductance);
        data.putDouble("Resistance", resistance);
        data.putDouble("Capacitance", capacitance);

        data.putBoolean("is_ccm", isCCM);
        data.putInt("Flag", flag);

        // Sending data to Advanced
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

        intent.putExtras(data);

        startActivity(intent);
    }
}
