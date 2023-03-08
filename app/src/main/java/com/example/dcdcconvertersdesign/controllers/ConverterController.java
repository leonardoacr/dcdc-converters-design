package com.example.dcdcconvertersdesign.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.views.SimulationParametersActivity;
import com.example.dcdcconvertersdesign.views.AdvancedActivity;
import com.example.dcdcconvertersdesign.models.ConverterModel;
import com.example.dcdcconvertersdesign.views.ConverterActivity;

public class ConverterController {
    private final ConverterActivity view;
    private final ConverterModel model;
    private final String TAG = "ConverterController";

    public ConverterController(ConverterActivity view, ConverterModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreateController(Bundle bundle) {
       if(bundle != null) {
           // Retrieve data from the Bundle and set the model variables
           model.retrieveDataFromUsualDesignActivity(bundle);

           // Update view with model data
           updateDisplay();
       } else {
           Log.d(TAG, "Bundle in ConverterController is null");
       }
    }

    public void onAdvancedClicked(Bundle bundle) {
        Log.d(TAG, "ConverterController: " + bundle.getDouble("Duty_Cycle"));
        Intent intent = new Intent(view, AdvancedActivity.class);
        intent.putExtras(bundle);
        view.startActivity(intent);
    }

    public void onSimulationClicked(Bundle bundle) {
        boolean isCCM = model.getIsCCM();

        if(isCCM){
            navigateToSimulation(bundle);
        } else {
            view.setModeWarning();
        }
    }

    private void navigateToSimulation(Bundle bundle) {
        Intent intent = new Intent(view, SimulationParametersActivity.class);
        intent.putExtras(bundle);
        view.startActivity(intent);
    }

    private void updateDisplay() {
        // Set resources in view
        boolean isCCM = model.getIsCCM();
        double dutyCycle = model.getDutyCycle();
        double resistance = model.getResistance();
        double capacitance = model.getCapacitance();
        double inductance = model.getInductance();
        int flag = model.getFlag();
        view.setResources(flag);
        view.updateDisplayValues(dutyCycle, resistance, capacitance, inductance);
        view.updateDisplayTexts(isCCM);
    }
}
