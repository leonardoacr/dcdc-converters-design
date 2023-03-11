package com.example.dcdcconvertersdesign.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.views.SimulationParametersView;
import com.example.dcdcconvertersdesign.views.AdvancedView;
import com.example.dcdcconvertersdesign.models.ConverterModel;
import com.example.dcdcconvertersdesign.views.ConverterView;

public class ConverterController {
    private final ConverterView view;
    private final ConverterModel model;
    private final String TAG = "ConverterController";

    public ConverterController(ConverterView view, ConverterModel model) {
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
        Intent intent = new Intent(view, AdvancedView.class);
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
        Intent intent = new Intent(view, SimulationParametersView.class);
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
        view.updateUIValues(dutyCycle, resistance, capacitance, inductance);
        view.updateUITexts(isCCM);
    }
}
