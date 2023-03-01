package com.example.dcdcconvertersdesign;

import static com.example.dcdcconvertersdesign.helpers.Helpers.stringFormat;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dcdcconvertersdesign.helpers.Helpers;

import java.util.Locale;

public class ConvertersReverse extends AppCompatActivity {
    private double outputVoltage;
    private double outputPower;
    private double rippleInductorCurrent;
    private double rippleCapacitorVoltage;
    private double frequency;
    private double switchCurrent;
    private double diodeCurrent;
    private double inductorCurrentRMS;

    private double inputCurrent;
    private double outputCurrent;
    private double deltaInductorCurrent;
    private double deltaCapacitorVoltage;

    private double dutyCycle;
    private double inductance;
    private double inductanceCritical;

    private int flag;
    private int flagReverse = 1;

    private TextView converterTitle;
    private TextView outputPowerText;
    private TextView rippleInductorCurrentText;
    private TextView rippleCapacitorVoltageText;
    private TextView dutyCycleText;
    private TextView outputPowerTextView;
    private TextView rippleInductorCurrentTextView;
    private TextView rippleCapacitorVoltageTextView;
    private TextView dutyCycleTextView;

    private ImageView converterImage;

    private Button advancedBtn;


    private String TAG = "ConvertersR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converters__r);
        Helpers.setMinActionBar(this);
        createObjects();
        Bundle data = getIntent().getExtras();

        if(data != null) {
            // Recovering data Converters
            recoveringData(data);
            formatAndSetValues();
            setConverterInfo();

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

        // Values
        outputPowerTextView = findViewById(R.id.output_power_reverse);
        rippleInductorCurrentTextView = findViewById(R.id.ripple_inductor_current_reverse);
        rippleCapacitorVoltageTextView = findViewById(R.id.ripple_capacitor_voltage_reverse);
        dutyCycleTextView = findViewById(R.id.duty_cycle_reverse);

        // Button
        advancedBtn = findViewById(R.id.advanced_btn_reverse);

        // Image
        converterImage = findViewById(R.id.converter_image_reverse);
    }

    private void recoveringData(Bundle data) {
        // Values
        dutyCycle = data.getDouble("Duty_Cycle");
        inductanceCritical = data.getDouble("Inductance_Crit");
        rippleInductorCurrent = data.getDouble("Rippleil");
        rippleCapacitorVoltage = data.getDouble("Ripplevc");
        outputPower = data.getDouble("Output_Power");
        inductance = data.getDouble("Inductance");

        flag = data.getInt("Flag");

        // Advanced
        inputCurrent = data.getDouble("Input_Current");
        outputCurrent = data.getDouble("Output_Current");
        switchCurrent = data.getDouble("Switch_Current");
        diodeCurrent = data.getDouble("Diode_Current");
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
        data2.putDouble("Switch_Current", switchCurrent);
        data2.putDouble("Diode_Current", diodeCurrent);
        data2.putDouble("Output_Voltage", outputVoltage);
        data2.putDouble("Frequency", frequency);
        data2.putDouble("DeltaIL", deltaInductorCurrent);
        data2.putDouble("DeltaVC", deltaCapacitorVoltage);
        data2.putDouble("ILrms", inductorCurrentRMS);
        data2.putDouble("Flag", flag);
        data2.putDouble("Flag_Reverse", flagReverse);

        intentAdvanced.putExtras(data2);

        startActivity( intentAdvanced );
    }

    private void formatAndSetValues() {
        setLabels();
        setFormattedValues();
    }

    private void setLabels() {
        // Texts
        outputPowerText.setText("Power Output");
        rippleInductorCurrentText.setText("Ind. Current Ripple");
        rippleCapacitorVoltageText.setText("Cap. Voltage Ripple");
        dutyCycleText.setText("Duty Cycle");
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

    private void setConverterInfo() {
        // Image and converter name
        if(flag == 1){
            converterImage.setImageResource(R.drawable.buck);
            converterTitle.setText("Buck converter");
        }
        if(flag == 2){
            converterImage.setImageResource(R.drawable.boost);
            converterTitle.setText("Boost converter");
        }
        if(flag == 3){
            converterImage.setImageResource(R.drawable.buck_boost);
            converterTitle.setText("Buck-Boost converter");
        }
    }
}
