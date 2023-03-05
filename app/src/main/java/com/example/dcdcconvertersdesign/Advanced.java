package com.example.dcdcconvertersdesign;

import static com.example.dcdcconvertersdesign.helpers.Helpers.stringFormat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.dcdcconvertersdesign.helpers.Helpers;

public class Advanced extends AppCompatActivity {

    private double inductance, inductanceCritical, inputCurrent, outputCurrent, switchCurrent, diodeCurrent,
            inductorCurrent, outputVoltage, frequency, deltaInductorCurrent, deltaCapacitorVoltage,
            inductorCurrentRMS;

    private int flag, flagReverse;
    private TextView inductanceCriticalTextView, inputCurrentTextView, outputCurrentTextView,
            inductorCurrentTextView, switchCurrentTextView, diodeCurrentTextView,
            deltaInductorCurrentTextView, deltaCapacitorVoltageTextView, obsinductor;
    private Button snubberDesignBtn, inductorDesignBtn;

    private final String TAG = "Advanced";

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        Helpers.setMinActionBar(this);
        createObjects();

        Bundle data2 = getIntent().getExtras();

        if (data2 != null) {
            // Recovering data
            recoveringDataFromConvertersR(data2);

            // Writing Values
            formatAndSetValues();

            if(flagReverse == 1){
                obsinductor.setText("For the reverse mode, the designed inductor shows the ideal component for the design");
            }

            // Snubber Project
            snubberDesignBtn.setOnClickListener(v -> {
                // Sending data to Snubber Project
                Intent intentSnubber = new Intent(Advanced.this, SnubberDesign.class);
                Bundle data3 = new Bundle();

                sendingDataToSnubberDesign(data3);

                intentSnubber.putExtras(data3);
                startActivity(intentSnubber);
            });

            // Inductor Project
            inductorDesignBtn.setOnClickListener(v -> {

                // Sending data to Inductor Project
                Intent intentInductor = new Intent(Advanced.this, InductorDesign.class);
                sendingDataToInductorProject(intentInductor);
            });
        }
    }

    private void createObjects() {
        inductanceCriticalTextView = (TextView) findViewById(R.id.inductance_critical_advanced);
        inputCurrentTextView = (TextView) findViewById(R.id.input_current_advanced);
        outputCurrentTextView = (TextView) findViewById(R.id.output_current_advanced);
        inductorCurrentTextView = (TextView) findViewById(R.id.inductor_current_advanced);
        switchCurrentTextView = (TextView) findViewById(R.id.switch_current_advanced);
        diodeCurrentTextView = (TextView) findViewById(R.id.diode_current_advanced);
        deltaInductorCurrentTextView = (TextView) findViewById(R.id.delta_inductor_current_advanced);
        deltaCapacitorVoltageTextView = (TextView) findViewById(R.id.delta_capacitor_voltage_advanced);
        obsinductor = findViewById(R.id.obsinductor);
        // Buttons
        snubberDesignBtn = (Button) findViewById(R.id.snubber_design_btn_advanced);
        inductorDesignBtn = (Button) findViewById(R.id.inductor_design_btn);
    }

    private void recoveringDataFromConvertersR(Bundle data2) {
        inductance = data2.getDouble("Inductance");
        inductanceCritical = data2.getDouble("Inductance_Crit");
        inputCurrent = data2.getDouble("Input_Current");
        outputCurrent = data2.getDouble("Output_Current");
        inductorCurrent = data2.getDouble("Inductor_Current");
        switchCurrent = data2.getDouble("Switch_Current");
        diodeCurrent = data2.getDouble("Diode_Current");
        outputVoltage = data2.getDouble("Output_Voltage");
        frequency = data2.getDouble("Frequency");
        deltaInductorCurrent = data2.getDouble("DeltaIL");
        deltaCapacitorVoltage = data2.getDouble("DeltaVC");
        inductorCurrentRMS = data2.getDouble("ILrms");
        flag = data2.getInt("Flag");
        flagReverse = data2.getInt("Reverse_Flag");
    }

    private void sendingDataToSnubberDesign(Bundle data3){
        data3.putDouble("Output_Current", outputCurrent);
        data3.putDouble("Input_Current", inputCurrent);
        data3.putDouble("Output_Voltage", outputVoltage);
        data3.putDouble("Frequency", frequency);
        data3.putInt("Flag", flag);
        Log.d(TAG, "Flag advanced: " + flag);
    }

    private void sendingDataToInductorProject(Intent intentInductor) {
        Bundle data4 = new Bundle();

        data4.putDouble("Inductance", inductance);
        data4.putDouble("ILrms", inductorCurrentRMS);
        data4.putDouble("DeltaIL", deltaInductorCurrent);
        data4.putDouble("Frequency", frequency);

        intentInductor.putExtras(data4);
        startActivity(intentInductor);
    }

    private void formatAndSetValues() {
        String formattedUnit;
        // Inductance Critical
        if (inductanceCritical >= 1e-3) {
            formattedUnit = stringFormat(inductanceCritical * 1e3) + " mH";
            inductanceCriticalTextView.setText(formattedUnit);
        }
        else if (inductanceCritical >= 1e-6) {
            formattedUnit = stringFormat(inductanceCritical * 1e6) + " μH";
            inductanceCriticalTextView.setText(formattedUnit);
        }
        else {
            formattedUnit = stringFormat(inductanceCritical * 1e9) + " nH";
            inductanceCriticalTextView.setText(formattedUnit);
        }

        // Input Current
        if (inputCurrent >= 1) {
            formattedUnit = stringFormat(inputCurrent) + " A";
            inputCurrentTextView.setText(formattedUnit);
        }
        else if (inputCurrent >= 1e-3) {
            formattedUnit = stringFormat(inputCurrent * 1e3) + " mA";
            inputCurrentTextView.setText(formattedUnit);
        }
        else {
            formattedUnit = stringFormat(inputCurrent * 1e6) + " μA";
            inputCurrentTextView.setText(formattedUnit);
        }

        // Output Current
        if (outputCurrent >= 1) {
            formattedUnit = stringFormat(outputCurrent) + " A";
            outputCurrentTextView.setText(formattedUnit);
        }
        else if (outputCurrent >= 1e-3) {
            formattedUnit = stringFormat(outputCurrent * 1e3) + " mA";
            outputCurrentTextView.setText(formattedUnit);
        }
        else {
            formattedUnit = stringFormat(outputCurrent * 1e6) + " μA";
            outputCurrentTextView.setText(formattedUnit);
        }

        // Inductor Current
        if (inductorCurrent >= 1) {
            formattedUnit = stringFormat(inductorCurrent) + " A";
            inductorCurrentTextView.setText(formattedUnit);
        }
        else if (inductorCurrent >= 1e-3) {
            formattedUnit = stringFormat(inductorCurrent * 1e3) + " mA";
            inductorCurrentTextView.setText(formattedUnit);
        }
        else {
            formattedUnit = stringFormat(inductorCurrent * 1e6) + " μA";
            inductorCurrentTextView.setText(formattedUnit);
        }

        // Switch Current
        if (switchCurrent >= 1) {
            formattedUnit = stringFormat(switchCurrent) + " A";
            switchCurrentTextView.setText(formattedUnit);
        }
        else if (switchCurrent >= 1e-3) {
            formattedUnit = stringFormat(switchCurrent * 1e3) + " mA";
            switchCurrentTextView.setText(formattedUnit);
        }
        else {
            formattedUnit = stringFormat(switchCurrent * 1e6) + " μA";
            switchCurrentTextView.setText(formattedUnit);
        }

        // Diode Current
        if (diodeCurrent >= 1) {
            formattedUnit = stringFormat(diodeCurrent) + " A";
            diodeCurrentTextView.setText(formattedUnit);
        }
        else if (diodeCurrent >= 1e-3) {
            formattedUnit = stringFormat(diodeCurrent * 1e3) + " mA";
            diodeCurrentTextView.setText(formattedUnit);
        }
        else {
            formattedUnit = stringFormat(diodeCurrent * 1e6) + " μA";
            diodeCurrentTextView.setText(formattedUnit);
        }

        // Delta Inductor Current
        if (deltaInductorCurrent >= 1) {
            formattedUnit = stringFormat(deltaInductorCurrent) + " A";
            deltaInductorCurrentTextView.setText(formattedUnit);
        }
        else if (deltaInductorCurrent >= 1e-3) {
            formattedUnit = stringFormat(deltaInductorCurrent * 1e3) + " mA";
            deltaInductorCurrentTextView.setText(formattedUnit);
        }
        else {
            formattedUnit = stringFormat(deltaInductorCurrent * 1e6) + " μA";
            deltaInductorCurrentTextView.setText(formattedUnit);
        }

        // Delta Capacitor Voltage
        if (deltaCapacitorVoltage >= 1) {
            formattedUnit = stringFormat(deltaCapacitorVoltage) + " V";
            deltaCapacitorVoltageTextView.setText(formattedUnit);
        }
        else if (deltaCapacitorVoltage >= 1e-3) {
            formattedUnit = stringFormat(deltaCapacitorVoltage * 1e3) + " mV";
            deltaCapacitorVoltageTextView.setText(formattedUnit);
        }
        else {
            formattedUnit = stringFormat(deltaCapacitorVoltage * 1e6) + " μV";
            deltaCapacitorVoltageTextView.setText(formattedUnit);
        }
        }
}
