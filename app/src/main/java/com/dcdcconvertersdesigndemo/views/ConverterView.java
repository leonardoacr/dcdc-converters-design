package com.dcdcconvertersdesigndemo.views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dcdcconvertersdesigndemo.R;
import com.dcdcconvertersdesigndemo.controllers.ConverterController;
import com.dcdcconvertersdesigndemo.helpers.Helpers;
import com.dcdcconvertersdesigndemo.interfaces.views.ConverterViewInterface;

public class ConverterView extends AppCompatActivity implements ConverterViewInterface {
    // Private variables
    private TextView dutyCycleTextView;
    private TextView resistanceTextView;
    private TextView capacitanceTextView;
    private TextView inductanceTextView;

    // Public variables
    public TextView dutyCycleText;
    public TextView resistanceText;
    public TextView inductanceText;
    public TextView capacitanceText;
    public TextView modeWarning;
    public TextView converterTitle;
    public TextView modeTextView;
    public ImageView converterFigure;
    public Button simulationBtn;
    public Button advancedBtn;
//    String TAG = "Converter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converters);
        Helpers.setMinActionBar(this);

        // Set UI
        setUIComponents();

        // Retrieve data from past activity
        Bundle bundle = getIntent().getExtras();

        // Set up controller
        ConverterController controller = new ConverterController(this);
        controller.onCreateController(bundle);

        // Simulation
        simulationBtn.setOnClickListener(v -> controller.onSimulationClicked(bundle));

        // Advanced
        advancedBtn.setOnClickListener(v -> controller.onAdvancedClicked(bundle));
    }

    public void setUIComponents()
    {
        // Values
        dutyCycleTextView = findViewById(R.id.duty_cycle);
        resistanceTextView = findViewById(R.id.resistance);
        capacitanceTextView = findViewById(R.id.capacitance);
        inductanceTextView = findViewById(R.id.inductance);

        // Texts
        dutyCycleText = findViewById(R.id.duty_cycle_text);
        resistanceText = findViewById(R.id.resistance_text);
        inductanceText = findViewById(R.id.inductance_text);
        capacitanceText = findViewById(R.id.capacitance_text);
        modeWarning = findViewById(R.id.mode_warning);
        converterTitle = findViewById(R.id.converter_title);
        modeTextView = findViewById(R.id.mode);

        // Image
        converterFigure = findViewById(R.id.converter_image);

        // Buttons
        simulationBtn = findViewById(R.id.simulation_btn);
        advancedBtn = findViewById(R.id.advanced_btn);
    }

    public void setResources(int flag) {
        // Set Title and Image
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

    public void updateUIValues(double dutyCycle, double resistance, double capacitance, double inductance) {
        // Duty Cycle
        String dutyCycleValueText = getString(R.string.duty_cycle_value, (int)(dutyCycle*100));
        dutyCycleTextView.setText(dutyCycleValueText);

        // Resistance
        resistanceTextView.setText(Helpers.formatValue(resistance, "Ω"));

        // Capacitance
        capacitanceTextView.setText(Helpers.formatValue(capacitance, "F"));

        // Inductance
        inductanceTextView.setText(Helpers.formatValue(inductance, "H"));
    }

    public void updateUITexts(boolean isCCM) {
        dutyCycleText.setText(R.string.duty_cycle_text);
        resistanceText.setText(R.string.resistance);
        capacitanceText.setText(R.string.capacitance);
        inductanceText.setText(R.string.inductance);
        modeTextView.setText(isCCM ? "Continuous Conduction Mode\n (CCM)" :
                "Discontinuous Conduction Mode\n (DCM)");
    }

    public void setModeWarning() {
        modeWarning.setText(R.string.mode_warning_simulation);
    }
}
