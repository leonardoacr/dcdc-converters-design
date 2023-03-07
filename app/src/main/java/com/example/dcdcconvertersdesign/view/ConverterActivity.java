package com.example.dcdcconvertersdesign.view;

import static com.example.dcdcconvertersdesign.helpers.Helpers.stringFormat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dcdcconvertersdesign.R;
import com.example.dcdcconvertersdesign.controller.ConverterController;
import com.example.dcdcconvertersdesign.convertersutils.ConverterData;
import com.example.dcdcconvertersdesign.helpers.Helpers;

public class ConverterActivity extends AppCompatActivity {
    public TextView dutyCycleTextView, resistanceTextView, capacitanceTextView, inductanceTextView;
    public TextView dutyCycleText, resistanceText, inductanceText, capacitanceText, modeWarning,
            converterTitle, modeTextView;
    public ImageView converterFigure;
    public Button simulationBtn, advancedBtn;
    private ConverterController controller;

    private ConverterData data;

    String TAG = "Converter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converters);
        Helpers.setMinActionBar(this);

        // Set UI
        setUIComponents(this);

        // Set up the controller
        controller = new ConverterController(this);
        Bundle bundle = getIntent().getExtras();
        controller.onCreateController(bundle);

        // Simulation
        simulationBtn.setOnClickListener(v -> controller.onSimulationClicked());

        // Advanced
        advancedBtn.setOnClickListener(v -> controller.onAdvancedClicked());
    }

//    public void navigateToAdvanced(ConverterData converterData) {
//        Intent intent = new Intent(ConverterActivity.this, Advanced.class);
//
//    }

    public void setUIComponents(Activity activity)
    {
        // Values
        dutyCycleTextView = (TextView) activity.findViewById(R.id.duty_cycle);
        resistanceTextView = (TextView) activity.findViewById(R.id.resistance);
        capacitanceTextView = (TextView) activity.findViewById(R.id.capacitance);
        inductanceTextView = (TextView) activity.findViewById(R.id.inductance);

        // Texts
        dutyCycleText = activity.findViewById(R.id.duty_cycle_text);
        resistanceText = activity.findViewById(R.id.resistance_text);
        inductanceText = activity.findViewById(R.id.inductance_text);
        capacitanceText = activity.findViewById(R.id.capacitance_text);
        modeWarning = (TextView) activity.findViewById(R.id.mode_warning);
        converterTitle = (TextView) activity.findViewById(R.id.converter_title);
        modeTextView = (TextView) activity.findViewById(R.id.mode);

        // Image
        converterFigure = (ImageView) activity.findViewById(R.id.converter_image);

        // Buttons
        simulationBtn = (Button) activity.findViewById(R.id.simulation_btn);
        advancedBtn = (Button) activity.findViewById(R.id.advanced_btn);
    }

    public void setResources(boolean isCCM, double dutyCycle, double resistance,
                                   double capacitance, double inductance, int flag) {
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

        // Writing Values
        String formattedUnit;

        formattedUnit = stringFormat(dutyCycle*100) + " %";
        dutyCycleTextView.setText(formattedUnit);

        dutyCycleText.setText(R.string.duty_cycle_text);
        resistanceText.setText(R.string.resistance);
        capacitanceText.setText(R.string.capacitance);
        inductanceText.setText(R.string.inductance);

        modeTextView.setText(isCCM ? "Continuous Conduction Mode\n (CCM)" :
                "Discontinuous Conduction Mode\n (DCM)");

        // Resistance
        if (resistance > 1e6) {
            formattedUnit = stringFormat(resistance / 1e6) + " MΩ";
            resistanceTextView.setText(formattedUnit);
        } else if (resistance > 1e3) {
            formattedUnit = stringFormat(resistance / 1e3) + " kΩ";
            resistanceTextView.setText(formattedUnit);
        } else if (resistance >= 1) {
            formattedUnit = stringFormat(resistance) + " Ω";
            resistanceTextView.setText(formattedUnit);
        } else if (resistance >= 1e-3) {
            formattedUnit = stringFormat(resistance / 1e-3) + " mΩ";
            resistanceTextView.setText(formattedUnit);
        } else if (resistance >= 1e-6) {
            formattedUnit = stringFormat(resistance / 1e-6) + " μΩ";
            resistanceTextView.setText(formattedUnit);
        } else {
            formattedUnit = stringFormat(resistance / 1e-9) + " nΩ";
            resistanceTextView.setText(formattedUnit);
        }

        // Capacitance
        if(capacitance > 1){
            formattedUnit = stringFormat(capacitance) + " F";
            capacitanceTextView.setText(formattedUnit);
        }
        if(capacitance >= 1e-3 && capacitance < 1) {
            formattedUnit = stringFormat(capacitance * 1e3) + " mF";
            capacitanceTextView.setText(formattedUnit);
        }
        if(capacitance < 1e-3 && capacitance >= 1e-6) {
            formattedUnit = stringFormat(capacitance * 1e6) + " μF";
            capacitanceTextView.setText(formattedUnit);
        }
        if(capacitance < 1e-6){
            formattedUnit = stringFormat(capacitance * 1e9) + " nF";
            capacitanceTextView.setText(formattedUnit);
        }

        //Inductance
        if(inductance > 1){
            formattedUnit = stringFormat(inductance) + " H";
            inductanceTextView.setText(formattedUnit);
        }
        if(inductance >= 1e-3 && inductance < 1) {
            formattedUnit = stringFormat(inductance * 1e3) + " mF";
            inductanceTextView.setText(formattedUnit);
        }
        if(inductance < 1e-3 && inductance >= 1e-6) {
            formattedUnit = stringFormat(inductance * 1e6) + " μF";
            inductanceTextView.setText(formattedUnit);
        }
        if(inductance < 1e-6){
            formattedUnit = stringFormat(inductance * 1e9) + " nF";
            inductanceTextView.setText(formattedUnit);
        }
    }
}
