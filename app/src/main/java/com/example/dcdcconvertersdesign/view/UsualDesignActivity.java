package com.example.dcdcconvertersdesign.view;

import static com.example.dcdcconvertersdesign.helpers.Helpers.showToast;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.dcdcconvertersdesign.R;
import com.example.dcdcconvertersdesign.controller.UsualDesignController;
import com.example.dcdcconvertersdesign.helpers.Helpers;

public class UsualDesignActivity extends AppCompatActivity {
    // UI-related variables
    private EditText inputVoltageEditText, outputVoltageEditText, outputPowerEditText,
            rippleInductorCurrentEditText, rippleCapacitorVoltageEditText,
            frequencyEditText, efficiencyEditText;
    private Button buckBtn, boostBtn, buckBoostBtn, exampleBtn;

    // Calculation-related variables

    private UsualDesignController controller;

    // Flags and constants
    private static final String TAG = "UsualDesign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usual_design);
        Helpers.setMainActionBar(this);

        // Set up the controller
        controller = new UsualDesignController(this);

        // Set up the UI
        setUIComponents();

        // Example button listener
        exampleBtn.setOnClickListener(v -> controller.onExampleClicked());

        // Buck Converter
        buckBtn.setOnClickListener(v -> controller.onBuckConverterClicked());

        // Boost Converter
        boostBtn.setOnClickListener(v -> controller.onBoostConverterClicked());
//        boostBtn.setOnClickListener(v -> {
//            flag = 2;
//            if (isEmpty()) {
//                return;
//            }
//
////            getInputs();
////            CalculateConverterVariables.boostCalculations();
//
//            if (!(inputVoltage < outputVoltage && isValid())) {
//                verifyInputErrorsBoost(this);
//                return;
//            }
//
//            navigateToBoostConverter();
//        });

        // Buck-Boost Calculations
//        buckBoostBtn.setOnClickListener(v -> {
//            flag = 3;
//            if (isEmpty()) {
//                return;
//            }
//
////            getInputs();
////            CalculateConverterVariables.buckBoostCalculations();
//
//            if (!(inputVoltage != outputVoltage && isValid())) {
//                verifyInputErrorsBuckBoost(this);
//                return;
//            }
//
//            navigateToBuckBoostConverter();
//        });
    }

    public void setUIComponents() {
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

    public void updateUIComponents(double inputVoltage, double outputVoltage, double outputPower,
                                   double rippleInductorCurrent, double rippleCapacitorVoltage,
                                   double frequency, double efficiency) {
        inputVoltageEditText.setText(String.valueOf(inputVoltage));
        outputVoltageEditText.setText(String.valueOf(outputVoltage));
        outputPowerEditText.setText(String.valueOf(outputPower));
        efficiencyEditText.setText(String.valueOf(efficiency));
        frequencyEditText.setText(String.valueOf(frequency));
        rippleInductorCurrentEditText.setText(String.valueOf(rippleInductorCurrent));
        rippleCapacitorVoltageEditText.setText(String.valueOf(rippleCapacitorVoltage));
    }

    public boolean isEmpty() {
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

    public Double getInputVoltage() {
        return Double.parseDouble(inputVoltageEditText.getText().toString());
    }

    public Double getOutputVoltage() {
        return Double.parseDouble(outputVoltageEditText.getText().toString());
    }

    public Double getOutputPower() {
        return Double.parseDouble(outputPowerEditText.getText().toString());
    }

    public Double getRippleInductorCurrent() {
        return Double.parseDouble(rippleInductorCurrentEditText.getText().toString());
    }

    public Double getRippleCapacitorVoltage() {
        return Double.parseDouble(rippleCapacitorVoltageEditText.getText().toString());
    }

    public Double getFrequency() {
        return Double.parseDouble(frequencyEditText.getText().toString()) * 1000;
    }

    public Double getEfficiency() {
        return Double.parseDouble(efficiencyEditText.getText().toString());
    }
}