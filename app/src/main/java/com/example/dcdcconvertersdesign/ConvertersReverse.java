package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dcdcconvertersdesign.helpers.Helpers;

import java.util.Locale;

public class ConvertersReverse extends AppCompatActivity {
    private double inputVoltage;
    private double outputVoltage;
    private double outputPower;
    private double rippleInductorCurrent;
    private double rippleCapacitorVoltage;
    private double frequency;
    private double inductorCurrent;
    private double switchCurrent;
    private double diodeCurrent;
    private double inductorCurrentRMS;

    private double inputCurrent;
    private double outputCurrent;
    private double deltaInductorCurrent;
    private double deltaCapacitorVoltage;

    private double dutyCycleIdeal;
    private double dutyCycle;
    private double inductance;
    private double resistance;
    private double capacitance;
    private double inductanceCritical;
    private boolean isCCM;
    private int flag;
    private TextView converterTitle;
    private TextView outputPowerText;
    private TextView rippleInductorCurrentText;
    private TextView rippleCapacitorVoltageText;
    private TextView dutyCycleText;
    private TextView outputPowerTextView;
    private TextView rippleInductorCurrentTextView;
    private TextView rippleCapacitorVoltageTextView;
    private TextView dutyCycleTextView;
    private TextView modeText;
    private TextView modeWarningReverse;

    private ImageView converterFigure;

    private Button advancedBtn;
    private Button simulationBtn;

    private final String TAG = "ConvertersR";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converters_reverse);
        Helpers.setMinActionBar(this);
        createObjects();
        Bundle data = getIntent().getExtras();

        if(data != null) {
            // Recovering data Converters
            recoveringData(data);
            formatAndSetValues();
            setConverterInfo();

            // Simulation
            simulationBtn.setOnClickListener(v -> {
                // Continuous Conduction Mode (CCM)
                if(isCCM){
                    sendDataToSimulation();
                } else {
                    modeWarningReverse.setText("Simulation not available for these parameters");
                }
            });

            // advancedBtn
            advancedBtn.setOnClickListener(v -> {
                // Sending data to advancedBtn
                sendDataToAdvanced();
            });
        }
    }

    private void createObjects() {
        // Texts
        converterTitle = findViewById(R.id.converter_title_reverse);
        outputPowerText = findViewById(R.id.output_power_text_reverse);
        rippleInductorCurrentText = findViewById(R.id.ripple_inductor_current_text_reverse);
        rippleCapacitorVoltageText = findViewById(R.id.ripple_capacitor_voltage_text_reverse);
        dutyCycleText = findViewById(R.id.duty_cycle_text_reverse);
        modeText = findViewById(R.id.mode_reverse);
        modeWarningReverse = findViewById(R.id.mode_warning_reverse);

        // Values
        outputPowerTextView = findViewById(R.id.output_power_reverse);
        rippleInductorCurrentTextView = findViewById(R.id.ripple_inductor_current_reverse);
        rippleCapacitorVoltageTextView = findViewById(R.id.ripple_capacitor_voltage_reverse);
        dutyCycleTextView = findViewById(R.id.duty_cycle_reverse);

        // Button
        advancedBtn = findViewById(R.id.advanced_btn_reverse);
        simulationBtn = findViewById(R.id.simulation_btn_reverse);

        // Image
        converterFigure = findViewById(R.id.converter_image_reverse);
    }

    private void formatAndSetValues() {
        setLabels();
        setFormattedValues();
    }

    @SuppressLint("SetTextI18n")
    private void setLabels() {
        // Texts
        outputPowerText.setText("Power Output");
        rippleInductorCurrentText.setText("Ind. Current Ripple");
        rippleCapacitorVoltageText.setText("Cap. Voltage Ripple");
        dutyCycleText.setText("Duty Cycle");
        modeText.setText(isCCM ? "Continuous Conduction Mode\n (CCM)" :
                "Discontinuous Conduction Mode\n (DCM)");
    }

    public void setConverterInfo() {
        if(flag == 1) {
            converterTitle.setText(R.string.buckText);
            converterFigure.setImageResource(R.drawable.buck);
        }
        if(flag == 2) {
            converterTitle.setText(R.string.boostText);
            converterFigure.setImageResource(R.drawable.boost);
        }
        if(flag == 3) {
            converterTitle.setText(R.string.buckBoostText);
            converterFigure.setImageResource(R.drawable.buck_boost);
        }

    }

    private void setFormattedValues() {
        setFormattedPower();
        setFormattedRippleInductorCurrent();
        setFormattedRippleCapacitorVoltage();
        setFormattedDutyCycle();
    }

    private void setFormattedPower() {
        String unit;
        if (outputPower > 1e6) {
            unit = String.format(Locale.US, "%.2f MW", outputPower / 1e6);
        } else if (outputPower > 1e3) {
            unit = String.format(Locale.US, "%.2f kW", outputPower / 1e3);
        } else if (outputPower > 1) {
            unit = String.format(Locale.US, "%.2f W", outputPower);
        } else if (outputPower > 1e-3) {
            unit = String.format(Locale.US, "%.2f mW", outputPower / 1e-3);
        } else {
            unit = String.format(Locale.US, "%.2f uW", outputPower / 1e-6);
        }
        outputPowerTextView.setText(unit);
    }

    private void setFormattedRippleInductorCurrent() {
        String unit = String.format(Locale.US, "%.2f %%", rippleInductorCurrent);
        rippleInductorCurrentTextView.setText(unit);
    }

    private void setFormattedRippleCapacitorVoltage() {
        String unit = String.format(Locale.US, "%.2f %%", rippleCapacitorVoltage);
        rippleCapacitorVoltageTextView.setText(unit);
    }

    private void setFormattedDutyCycle() {
        String unit = String.format(Locale.US, "%.2f %%", dutyCycle * 100);
        dutyCycleTextView.setText(unit);
    }

    private void recoveringData(Bundle data) {
        // Values
        dutyCycleIdeal = data.getDouble("Duty_Cycle_Ideal");
        dutyCycle = data.getDouble("Duty_Cycle");
        inductanceCritical = data.getDouble("Inductance_Crit");
        rippleInductorCurrent = data.getDouble("Rippleil");
        rippleCapacitorVoltage = data.getDouble("Ripplevc");
        outputPower = data.getDouble("Output_Power");
        inductance = data.getDouble("Inductance");
        capacitance = data.getDouble("Capacitance");
        resistance = data.getDouble("Resistance");

        isCCM = data.getBoolean("is_ccm");

        flag = data.getInt("Flag");

        // Advanced
        inputCurrent = data.getDouble("Input_Current");
        outputCurrent = data.getDouble("Output_Current");
        inductorCurrent = data.getDouble("Inductor_Current");
        switchCurrent = data.getDouble("Switch_Current");
        diodeCurrent = data.getDouble("Diode_Current");
        inputVoltage = data.getDouble("Input_Voltage");
        outputVoltage = data.getDouble("Output_Voltage");
        frequency = data.getDouble("Frequency");
        deltaInductorCurrent = data.getDouble("DeltaIL");
        deltaCapacitorVoltage = data.getDouble("DeltaVC");
        inductorCurrentRMS = data.getDouble("ILrms");
    }

    private void sendDataToAdvanced() {
        Intent intentAdvanced = new Intent( getApplicationContext(), Advanced.class);
        Bundle data2 = new Bundle();
        data2.putDouble("Inductance", inductance);
        data2.putDouble("Inductance_Crit", inductanceCritical);
        data2.putDouble("Input_Current", inputCurrent);
        data2.putDouble("Output_Current", outputCurrent);
        data2.putDouble("Inductor_Current", inductorCurrent);
        data2.putDouble("Switch_Current", switchCurrent);
        data2.putDouble("Diode_Current", diodeCurrent);
        data2.putDouble("Output_Voltage", outputVoltage);
        data2.putDouble("Frequency", frequency);
        data2.putDouble("DeltaIL", deltaInductorCurrent);
        data2.putDouble("DeltaVC", deltaCapacitorVoltage);
        data2.putDouble("ILrms", inductorCurrentRMS);
        data2.putInt("Flag", flag);
        final int flagReverse = 1;
        data2.putDouble("Flag_Reverse", flagReverse);
        intentAdvanced.putExtras(data2);
        startActivity( intentAdvanced );
    }

    private void sendDataToSimulation() {
        Intent intentSimulationDefinitions = new Intent(
                ConvertersReverse.this, SimulationParameters.class);
        Bundle simulationData = new Bundle();
        simulationData.putDouble("Output_Voltage", outputVoltage);
        simulationData.putDouble("Input_Voltage", inputVoltage);
        simulationData.putDouble("Duty_Cycle", dutyCycle);
        simulationData.putDouble("Duty_Cycle_Ideal", dutyCycleIdeal);
        simulationData.putDouble("Inductance", inductance);
        simulationData.putDouble("Capacitance", capacitance);
        simulationData.putDouble("Resistance", resistance);
        simulationData.putDouble("Frequency", frequency);
        simulationData.putInt("Flag", flag);
        intentSimulationDefinitions.putExtras(simulationData);
        startActivity(intentSimulationDefinitions);
    }
}
