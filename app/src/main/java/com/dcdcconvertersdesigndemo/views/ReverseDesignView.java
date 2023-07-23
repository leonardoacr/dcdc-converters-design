package com.example.dcdcconvertersdesigndemo.views;

import static com.example.dcdcconvertersdesigndemo.helpers.Helpers.showToast;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;

import com.example.dcdcconvertersdesigndemo.R;
import com.example.dcdcconvertersdesigndemo.controllers.ReverseDesignController;
import com.example.dcdcconvertersdesigndemo.interfaces.views.ReverseDesignViewInterface;
import com.example.dcdcconvertersdesigndemo.helpers.Helpers;

public class ReverseDesignView extends AppCompatActivity implements ReverseDesignViewInterface {
    private EditText inductanceEditText;
    private EditText resistanceEditText;
    private EditText capacitanceEditText;
    private EditText inputVoltageEditText;
    private EditText outputVoltageEditText;
    private EditText frequencyEditText;
    private EditText efficiencyEditText;

    private Button buckBtn;
    private Button boostBtn;
    private Button buckBoostBtn;
    private Button exampleBtn;


//    String TAG = "ReverseDesign";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse_design);
        Helpers.setMainActionBar(this);

        ReverseDesignController controller = new ReverseDesignController(this);

        // Set up the UI
        setUIComponents();
        handleButtons(controller);
    }

    public void setUIComponents() {
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

    public void updateUIComponents(double inputVoltage, double outputVoltage,
                                   double resistance, double inductance, double capacitance,
                                   double frequency, double efficiency) {
        inputVoltageEditText.setText(String.valueOf(inputVoltage));
        outputVoltageEditText.setText(String.valueOf(outputVoltage));
        resistanceEditText.setText(String.valueOf(resistance));
        inductanceEditText.setText(String.valueOf(inductance));
        capacitanceEditText.setText(String.valueOf(capacitance));
        frequencyEditText.setText(String.valueOf(frequency));
        efficiencyEditText.setText(String.valueOf(efficiency));
    }

    private void handleButtons(ReverseDesignController controller) {
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
        EditText[] fields = {inputVoltageEditText, outputVoltageEditText, inductanceEditText,
                resistanceEditText, capacitanceEditText, frequencyEditText, efficiencyEditText};
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

    public Double getResistance() {
        return Double.parseDouble(resistanceEditText.getText().toString());
    }

    public Double getInductance() {
        return Double.parseDouble(inductanceEditText.getText().toString()) / 1e6;
    }

    public Double getCapacitance() {
        return Double.parseDouble(capacitanceEditText.getText().toString()) / 1e6;
    }

    public Double getFrequency() {
        return Double.parseDouble(frequencyEditText.getText().toString()) * 1e3;
    }

    public Double getEfficiency() {
        return Double.parseDouble(efficiencyEditText.getText().toString());
    }
}
