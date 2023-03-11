package com.example.dcdcconvertersdesign.views;

import static com.example.dcdcconvertersdesign.helpers.Helpers.showToast;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.dcdcconvertersdesign.R;
import com.example.dcdcconvertersdesign.controllers.UsualDesignController;
import com.example.dcdcconvertersdesign.helpers.Helpers;
import com.example.dcdcconvertersdesign.models.UsualDesignModel;

public class UsualDesignView extends AppCompatActivity {
    // UI-related variables
    private EditText inputVoltageEditText;
    private EditText outputVoltageEditText;
    private EditText outputPowerEditText;
    private EditText rippleInductorCurrentEditText;
    private EditText rippleCapacitorVoltageEditText;
    private EditText frequencyEditText;
    private EditText efficiencyEditText;

    private Button buckBtn;
    private Button boostBtn;
    private Button buckBoostBtn;
    private Button exampleBtn;

    // Flags and constants
    private static final String TAG = "UsualDesign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usual_design);
        Helpers.setMainActionBar(this);

        // Set up model and controller
        UsualDesignModel model = new UsualDesignModel();
        UsualDesignController controller = new UsualDesignController(this, model);

        // Set up the UI
        setUIComponents();
        handleButtons(controller);
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

    private void handleButtons(UsualDesignController controller) {
        // Example button listener
        exampleBtn.setOnClickListener(v -> controller.onExampleClicked());

        // Buck Converter
        buckBtn.setOnClickListener(v -> controller.onBuckConverterClicked());

        // Boost Converter
        boostBtn.setOnClickListener(v -> controller.onBoostConverterClicked());

        // Buck Converter
        buckBoostBtn.setOnClickListener(v -> controller.onBuckBoostConverterClicked());
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