package com.example.dcdcconvertersdesign.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.view.AdvancedActivity;
import com.example.dcdcconvertersdesign.model.ConverterModel;
import com.example.dcdcconvertersdesign.view.ConverterActivity;

public class ConverterController {
    private final ConverterActivity view;

    private final String TAG = "ConverterController";

    public ConverterController(ConverterActivity view) {
        this.view = view;
    }

    public void onCreateController(Bundle bundle) {
       if(bundle != null) {
           // Create an instance of ConverterModel
           ConverterModel model = new ConverterModel();

           // Retrieve data from the Bundle and set the model variables
           model.retrieveDataFromUsualDesignActivity(bundle);

           // Update view with model data
           updateDisplay(model);
       } else {
           Log.d(TAG, "Bundle in ConverterController is null");
       }
    }

    public void onAdvancedClicked(Bundle bundle) {
        Intent intent = new Intent(view, AdvancedActivity.class);
        intent.putExtras(bundle);
        view.startActivity(intent);
    }

    public void onSimulationClicked() {
//        view.navigateToSimulation(data);
    }

    private void updateDisplay(ConverterModel model) {
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
