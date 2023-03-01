package com.example.dcdcconvertersdesign;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dcdcconvertersdesign.helpers.Helpers;

public class ReverseDesign extends AppCompatActivity {
    public int flag;
    private double inputVoltage, outputVoltage, outputPower, rippleInductorCurrent,
        rippleCapacitorVoltage, frequency, switchCurrent, diodeCurrent,
        inductorCurrentRMS;
    private double inputCurrent, outputCurrent, deltaInductorCurrent, deltaCapacitorVoltage;
    private double dutyCycle, resistance, capacitance, inductance, inductanceCritical;
    private EditText inductanceEditText, resistanceEditText, capacitanceEditText, inputVoltageEditText,
            outputVoltageEditText, frequencyEditText;
    private Button buckBtn, boostBtn, buckBoostBtn, exampleBtn;

    String TAG = "ReverseDesign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse_engineering);
        Helpers.setMainActionBar(this);
        createObjects();


        // Example button listener
        exampleBtn.setOnClickListener(v -> {
            inputVoltageEditText.setText("24");
            outputVoltageEditText.setText("12");
            inductanceEditText.setText("1");
            capacitanceEditText.setText("500");
            resistanceEditText.setText("12");
            frequencyEditText.setText("20");
        });

        buckBtn.setOnClickListener(v -> {

            if (isEmpty()) {
                Helpers.showToast(this, "Error! Something is empty");
                return;
            }

            InputValues();
            buckCalculations();

            if (inputVoltage > outputVoltage && dutyCycle <= 0.95 && dutyCycle >= 0.05) {
                sendDataToConvertersR();
            }
            if (inputVoltage < outputVoltage) {
                Helpers.showToast(this, "Error! Output bigger than Input");
            }
            if (inputVoltage == outputVoltage) {
                Helpers.showToast(this, "Error! Input and Output are equal");
            }
            if((dutyCycle > 0.95 || dutyCycle < 0.05) && inputVoltage != outputVoltage && inputVoltage > outputVoltage) {
                Helpers.showToast(this, "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)");
            }
        });

        boostBtn.setOnClickListener(v -> {

            if (isEmpty()) {
                Helpers.showToast(this, "Error! Something is empty");
                return;
            }
            InputValues();
            boostCalculations();

            if(inputVoltage < outputVoltage && dutyCycle <= 0.95 && dutyCycle >= 0.05) {
                sendDataToConvertersR();
            }
            if (inputVoltage == outputVoltage) {
                Toast.makeText(getApplicationContext(), "Error! Input and Output are equal", Toast.LENGTH_SHORT).show();
            }
            if(inputVoltage > outputVoltage) {
                Toast.makeText(getApplicationContext(), "Error! Input bigger than Output", Toast.LENGTH_SHORT ).show();
            }
            if((dutyCycle > 0.95 || dutyCycle < 0.05) && inputVoltage != outputVoltage && inputVoltage < outputVoltage) {
                Toast.makeText(getApplicationContext(), "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)", Toast.LENGTH_SHORT).show();
            }
        });

        buckBoostBtn.setOnClickListener(v -> {

            if (isEmpty()) {
                Helpers.showToast(this, "Error! Something is empty");
                return;
            }
            InputValues();
            buckBoostCalculations();

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
        inductance = Double.parseDouble(inductanceEditText.getText().toString()) / 1e3;
        capacitance = Double.parseDouble(capacitanceEditText.getText().toString()) / 1e6;
        frequency = Double.parseDouble(frequencyEditText.getText().toString()) * 1e3;
    }

    public void buckCalculations() {
        dutyCycle = outputVoltage / inputVoltage;
        outputPower = Math.pow(outputVoltage, 2) / resistance;
        outputCurrent = outputVoltage / resistance;
        inputCurrent = dutyCycle * outputCurrent;
        switchCurrent = dutyCycle * outputCurrent;
        diodeCurrent = (1 - dutyCycle) * outputCurrent;
        deltaInductorCurrent = (inputVoltage * (1 - dutyCycle) * dutyCycle) / (frequency * inductance);
        deltaCapacitorVoltage = (inputVoltage * (1 - dutyCycle) * dutyCycle) / (8 * inductance * capacitance * Math.pow(frequency, 2));
        rippleInductorCurrent = 100 * deltaInductorCurrent / outputCurrent;
        rippleCapacitorVoltage = 100 * deltaInductorCurrent / outputVoltage;
        inductanceCritical = (inputVoltage * (1 - dutyCycle) * dutyCycle) / (2 * frequency * outputCurrent);
        flag = 1;
    }

    public void boostCalculations() {
        dutyCycle = (outputVoltage - inputVoltage) / outputVoltage;
        outputPower = (Math.pow(outputVoltage, 2)) / resistance;
        outputCurrent = outputVoltage / resistance;
        inputCurrent = outputCurrent / (1 - dutyCycle);
        switchCurrent = dutyCycle * inputCurrent;
        diodeCurrent = (1 - dutyCycle) * inputCurrent;
        inductorCurrentRMS = inputCurrent;
        deltaInductorCurrent = (inputVoltage * dutyCycle) / (frequency * inductance);
        deltaCapacitorVoltage = (outputCurrent * dutyCycle) / (capacitance * frequency);
        rippleInductorCurrent = 100 * deltaInductorCurrent / inputCurrent;
        rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;
        inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        flag = 2;
    }

    public void buckBoostCalculations() {
        dutyCycle = (outputVoltage) / (inputVoltage + outputVoltage);
        outputPower = (Math.pow(outputVoltage, 2)) / resistance;
        outputCurrent = outputVoltage / resistance;
        inputCurrent = outputCurrent / (1 - dutyCycle);
        switchCurrent = dutyCycle * inputCurrent;
        diodeCurrent = (1 - dutyCycle) * inputCurrent;
        deltaInductorCurrent = (inputVoltage * dutyCycle) / (frequency * inductance);
        deltaCapacitorVoltage = (outputCurrent * dutyCycle) / (capacitance * frequency);
        rippleInductorCurrent = 100 * deltaInductorCurrent / inputCurrent;
        rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;
        inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        flag = 3;
    }

    public void sendDataToConvertersR() {
        Intent intent = new Intent(getApplicationContext(), ConvertersReverse.class);
        Bundle data = new Bundle();

        // Sending Data to Write Values
        data.putDouble("Duty_Cycle", dutyCycle);
        data.putDouble("Inductance_Crit", inductanceCritical);
        data.putDouble("Ripplevc", rippleCapacitorVoltage);
        data.putDouble("Rippleil", rippleInductorCurrent);
        data.putDouble("Output_Power", outputPower);
        data.putDouble("Inductance", inductance);
        data.putInt("Flag", flag);

        // Sending data to Advanced
        data.putDouble("Input_Current", inputCurrent);
        data.putDouble("Output_Current", outputCurrent);
        data.putDouble("Switch_Current", switchCurrent);
        data.putDouble("Diode_Current", diodeCurrent);
        data.putDouble("Output_Voltage", outputVoltage);
        data.putDouble("Frequency", frequency);
        data.putDouble("DeltaIL", deltaInductorCurrent);
        data.putDouble("DeltaVC", deltaCapacitorVoltage);
        data.putDouble("ILrms", inductorCurrentRMS);

        intent.putExtras(data);

        startActivity(intent);
    }
}
