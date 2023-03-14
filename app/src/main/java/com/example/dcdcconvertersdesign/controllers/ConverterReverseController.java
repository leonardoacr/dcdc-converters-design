package com.example.dcdcconvertersdesign.controllers;

import android.content.Intent;
import android.os.Bundle;

import com.example.dcdcconvertersdesign.interfaces.controllers.ConverterControllerInterface;
import com.example.dcdcconvertersdesign.models.ConverterReverseModel;
import com.example.dcdcconvertersdesign.views.AdvancedView;
import com.example.dcdcconvertersdesign.views.ConverterReverseView;
import com.example.dcdcconvertersdesign.views.SimulationParametersView;

public class ConverterReverseController implements ConverterControllerInterface {
    private final ConverterReverseView view;
    private final ConverterReverseModel model;
//    private final String TAG = "ConverterReverseController";

    public ConverterReverseController(ConverterReverseView view, ConverterReverseModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreateController(Bundle bundle) {
        if(bundle != null) {
            // Retrieve data from the Bundle and set the model variables
            model.retrieveDataFromReverseDesignActivity(bundle);

            // Update view with model data
            updateDisplay();
        }
    }

    public void onAdvancedClicked(Bundle bundle) {
        Intent intent = new Intent(view, AdvancedView.class);
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
        Intent intent = new Intent(view, SimulationParametersView.class);
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
