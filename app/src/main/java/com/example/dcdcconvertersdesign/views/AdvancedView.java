package com.example.dcdcconvertersdesign.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.TextView;

import com.example.dcdcconvertersdesign.R;
import com.example.dcdcconvertersdesign.controllers.AdvancedController;
import com.example.dcdcconvertersdesign.helpers.Helpers;
import com.example.dcdcconvertersdesign.interfaces.views.AdvancedViewInterface;

public class AdvancedView extends AppCompatActivity implements AdvancedViewInterface {
    // TextViews
    private TextView inductanceCriticalTextView;
    private TextView inputCurrentTextView;
    private TextView outputCurrentTextView;
    private TextView inductorCurrentTextView;
    private TextView switchCurrentTextView;
    private TextView diodeCurrentTextView;
    private TextView deltaInductorCurrentTextView;
    private TextView deltaCapacitorVoltageTextView;
    private TextView inductorReverseNote;

    // Buttons
    private Button snubberDesignBtn;
    private Button inductorDesignBtn;

    private AdvancedController controller;

//    private final String TAG = "Advanced";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        Helpers.setMinActionBar(this);
        setUIComponents();

        // Retrieve data from past activity
        Bundle bundle = getIntent().getExtras();

        // Set up controller
        controller = new AdvancedController(this);

        // Initiating controller and setting resources
        controller.onCreateController(bundle);

        // Snubber Project
        snubberDesignBtn.setOnClickListener(v -> controller.onSnubberDesignClicked());

        // Inductor Project
        inductorDesignBtn.setOnClickListener(v -> controller.onInductorDesignClicked());
    }

    private void setUIComponents() {
        inductanceCriticalTextView = findViewById(R.id.inductance_critical_advanced);
        inputCurrentTextView = findViewById(R.id.input_current_advanced);
        outputCurrentTextView = findViewById(R.id.output_current_advanced);
        inductorCurrentTextView = findViewById(R.id.inductor_current_advanced);
        switchCurrentTextView = findViewById(R.id.switch_current_advanced);
        diodeCurrentTextView = findViewById(R.id.diode_current_advanced);
        deltaInductorCurrentTextView = findViewById(R.id.delta_inductor_current_advanced);
        deltaCapacitorVoltageTextView = findViewById(R.id.delta_capacitor_voltage_advanced);
        inductorReverseNote = findViewById(R.id.inductor_reverse_note);

        // Buttons
        snubberDesignBtn = findViewById(R.id.snubber_design_btn_advanced);
        inductorDesignBtn = findViewById(R.id.inductor_design_btn);
    }

    public void setInductorReverseNote(int flagReverse) {
        if(flagReverse == 1) {
            inductorReverseNote.setText(getString(R.string.reverse_mode_inductor_note));}
    }

    public void updateDisplayValues(double inductanceCritical, double inputCurrent, double outputCurrent,
                                     double inductorCurrent, double switchCurrent, double diodeCurrent,
                                     double deltaInductorCurrent, double deltaCapacitorVoltage) {
        inductanceCriticalTextView.setText(Helpers.formatValue(inductanceCritical, "H"));
        inputCurrentTextView.setText(Helpers.formatValue(inputCurrent, "A"));
        outputCurrentTextView.setText(Helpers.formatValue(outputCurrent, "A"));
        inductorCurrentTextView.setText(Helpers.formatValue(inductorCurrent, "A"));
        switchCurrentTextView.setText(Helpers.formatValue(switchCurrent, "A"));
        diodeCurrentTextView.setText(Helpers.formatValue(diodeCurrent, "A"));
        deltaInductorCurrentTextView.setText(Helpers.formatValue(deltaInductorCurrent, "A"));
        deltaCapacitorVoltageTextView.setText(Helpers.formatValue(deltaCapacitorVoltage, "V"));

    }
}
