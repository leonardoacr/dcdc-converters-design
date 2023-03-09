package com.example.dcdcconvertersdesign.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.models.ConverterModel;
import com.example.dcdcconvertersdesign.models.ConverterReverseModel;
import com.example.dcdcconvertersdesign.views.AdvancedActivity;
import com.example.dcdcconvertersdesign.views.ConverterActivity;
import com.example.dcdcconvertersdesign.views.ConverterReverseActivity;
import com.example.dcdcconvertersdesign.views.SimulationParametersActivity;

public class ConverterReverseController {
    private final ConverterReverseActivity view;
    private final ConverterReverseModel model;
    private final String TAG = "ConverterReverseController";

    public ConverterReverseController(ConverterReverseActivity view, ConverterReverseModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreateController(Bundle bundle) {
        if(bundle != null) {
            // Retrieve data from the Bundle and set the model variables
            model.retrieveDataFromReverseDesignActivity(bundle);

            // Update view with model data
            updateDisplay();
        } else {
            Log.d(TAG, "Bundle in ConverterReverseController is null");
        }
    }

    public void onAdvancedClicked(Bundle bundle) {
        Log.d(TAG, "ConverterController: " + bundle.getDouble("Duty_Cycle"));
        Intent intent = new Intent(view, AdvancedActivity.class);
        final int flagReverse = 1;
        bundle.putDouble("Flag_Reverse", flagReverse);
        intent.putExtras(bundle);
        view.startActivity(intent);
    }

    public void onSimulationClicked(Bundle bundle) {
        boolean isCCM = model.getIsCCM();

        if(isCCM){
            navigateToSimulation(bundle);
        } else {
            view.setModeWarningReverse();
        }
    }

    private void navigateToSimulation(Bundle bundle) {
        Intent intent = new Intent(view, SimulationParametersActivity.class);
        intent.putExtras(bundle);
        view.startActivity(intent);
    }

    private void updateDisplay() {
        // Set resources in view
        view.setResources(model.getFlag());
        view.updateDisplayValues(model.getOutputPower(), model.getRippleInductorCurrent(),
                model.getRippleCapacitorVoltage(), model.getDutyCycle());
        view.updateDisplayTexts(model.getIsCCM());
    }
}
