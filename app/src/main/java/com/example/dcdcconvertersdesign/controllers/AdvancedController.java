package com.example.dcdcconvertersdesign.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.InductorDesign;
import com.example.dcdcconvertersdesign.views.SnubberDesignActivity;
import com.example.dcdcconvertersdesign.models.AdvancedModel;
import com.example.dcdcconvertersdesign.views.AdvancedActivity;

public class AdvancedController {
    private static final String OUTPUT_CURRENT_KEY = "Output_Current";
    private static final String INPUT_CURRENT_KEY = "Input_Current";
    private static final String OUTPUT_VOLTAGE_KEY = "Output_Voltage";
    private static final String FREQUENCY_KEY = "Frequency";
    private static final String FLAG_KEY = "Flag";
    private static final String INDUCTANCE_KEY = "Inductance";
    private static final String INDUCTOR_CURRENT_RMS_KEY = "Inductor_Current_RMS";
    private static final String DELTA_IL_KEY = "DeltaIL";
    private static final String TAG = "AdvancedController";
    private final AdvancedActivity view;

    private final AdvancedModel model;

    public AdvancedController(AdvancedActivity view, AdvancedModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreateController(Bundle bundle) {
        if(bundle != null) {
            // Retrieve data from bundle
            model.retrieveDataFromConverterActivity(bundle);

            // Update view with model data
            updateDisplay();

            // Set reverse note if necessary
            setReverseNote();
        } else {
            Log.d(TAG, "Bundle in ConverterController is null");
        }
    }

    public void onSnubberDesignClicked() {
        // Sending bundle to Snubber Design
        Intent intent = new Intent(view, SnubberDesignActivity.class);
        Bundle bundle = createSnubberDesignBundle();
        intent.putExtras(bundle);
        view.startActivity(intent);
    }

    public void onInductorDesignClicked() {
        // Sending bundle to Inductor Design
        Intent intent = new Intent(view, InductorDesign.class);
        Bundle bundle = createInductorDesignBundle();
        intent.putExtras(bundle);
        view.startActivity(intent);
    }

    private Bundle createSnubberDesignBundle() {
        double outputCurrent = model.getOutputCurrent();
        double inputCurrent = model.getInputCurrent();
        double outputVoltage = model.getOutputVoltage();
        double frequency = model.getFrequency();
        int flag = model.getFlag();

        Bundle bundle = new Bundle();
        bundle.putDouble(OUTPUT_CURRENT_KEY, outputCurrent);
        bundle.putDouble(INPUT_CURRENT_KEY, inputCurrent);
        bundle.putDouble(OUTPUT_VOLTAGE_KEY, outputVoltage);
        bundle.putDouble(FREQUENCY_KEY, frequency);
        bundle.putInt(FLAG_KEY, flag);
        return bundle;
    }

    private Bundle createInductorDesignBundle() {
        double inductance = model.getInductance();
        double inductorCurrentRMS = model.getInductorCurrentRMS();
        double deltaInductorCurrent = model.getDeltaInductorCurrent();
        double frequency = model.getFrequency();

        Bundle bundle = new Bundle();
        bundle.putDouble(INDUCTANCE_KEY, inductance);
        bundle.putDouble(INDUCTOR_CURRENT_RMS_KEY, inductorCurrentRMS);
        bundle.putDouble(DELTA_IL_KEY, deltaInductorCurrent);
        bundle.putDouble(FREQUENCY_KEY, frequency);
        return bundle;
    }

    private void updateDisplay() {
        double inductanceCritical = model.getInductanceCritical();
        double inputCurrent = model.getInputCurrent();
        double outputCurrent = model.getOutputCurrent();
        double inductorCurrent = model.getInductorCurrent();
        double switchCurrent = model.getSwitchCurrent();
        double diodeCurrent = model.getDiodeCurrent();
        double deltaInductorCurrent = model.getDeltaInductorCurrent();
        double deltaCapacitorVoltage = model.getDeltaCapacitorVoltage();
        view.updateDisplayValues(inductanceCritical, inputCurrent, outputCurrent, inductorCurrent,
                switchCurrent, diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage);
    }

    private void setReverseNote() {
        int flagReverse = model.getFlagReverse();
        view.setInductorReverseNote(flagReverse);
    }
}
