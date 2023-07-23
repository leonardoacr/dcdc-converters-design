package com.dcdcconvertersdesigndemo.views;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dcdcconvertersdesigndemo.R;
import com.dcdcconvertersdesigndemo.controllers.ConverterReverseController;
import com.dcdcconvertersdesigndemo.helpers.Helpers;
import com.dcdcconvertersdesigndemo.interfaces.views.ConverterReverseViewInterface;

import java.util.Locale;

public class ConverterReverseView extends AppCompatActivity
        implements ConverterReverseViewInterface {
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

//    private final String TAG = "ConvertersR";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converters_reverse);
        Helpers.setMinActionBar(this);
        // Set UI
        setUIComponents();

        // Retrieve data from past activity
        Bundle bundle = getIntent().getExtras();

        // Set up controller
        ConverterReverseController controller = new ConverterReverseController(this);
        controller.onCreateController(bundle);

        // Simulation
        simulationBtn.setOnClickListener(v -> controller.onSimulationClicked(bundle));

        // Advanced
        advancedBtn.setOnClickListener(v -> controller.onAdvancedClicked(bundle));
    }

    private void setUIComponents() {
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

    public void updateDisplayValues(double outputPower, double rippleInductorCurrent,
                                    double rippleCapacitorVoltage, double dutyCycle) {
        // Output Power
        outputPowerTextView.setText(Helpers.formatValue(outputPower, "W"));

        // Ind. Current Ripple
        setFormattedRippleInductorCurrent(rippleInductorCurrent);

        // Cap. Voltage Ripple
        setFormattedRippleCapacitorVoltage(rippleCapacitorVoltage);

        // Duty Cycle
       setFormattedDutyCycle(dutyCycle);
    }

    @SuppressLint("SetTextI18n")
    public void updateDisplayTexts(boolean isCCM) {
        // Texts
        outputPowerText.setText("Output Power");
        rippleInductorCurrentText.setText("Ind. Current Ripple");
        rippleCapacitorVoltageText.setText("Cap. Voltage Ripple");
        dutyCycleText.setText("Duty Cycle");
        modeText.setText(isCCM ? "Continuous Conduction Mode\n (CCM)" :
                "Discontinuous Conduction Mode\n (DCM)");
    }

    public void setResources(int flag) {
        if (flag == 1) {
            converterTitle.setText(R.string.buckText);
            converterFigure.setImageResource(R.drawable.buck);
        }
        if (flag == 2) {
            converterTitle.setText(R.string.boostText);
            converterFigure.setImageResource(R.drawable.boost);
        }
        if (flag == 3) {
            converterTitle.setText(R.string.buckBoostText);
            converterFigure.setImageResource(R.drawable.buck_boost);
        }
    }

    private void setFormattedRippleInductorCurrent(double rippleInductorCurrent) {
        String unit = String.format(Locale.US, "%.2f %%", rippleInductorCurrent);
        rippleInductorCurrentTextView.setText(unit);
    }

    private void setFormattedRippleCapacitorVoltage(double rippleCapacitorVoltage) {
        String unit = String.format(Locale.US, "%.2f %%", rippleCapacitorVoltage);
        rippleCapacitorVoltageTextView.setText(unit);
    }

    private void setFormattedDutyCycle(double dutyCycle) {
        String unit = String.format(Locale.US, "%.2f %%", dutyCycle * 100);
        dutyCycleTextView.setText(unit);
    }

    public void setModeWarningReverse() {
        modeWarningReverse.setText(R.string.mode_warning_simulation);
    }
}
