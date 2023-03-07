package com.example.dcdcconvertersdesign.controller;

import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.model.ConverterModel;
import com.example.dcdcconvertersdesign.view.ConverterActivity;

public class ConverterController {
    private ConverterActivity view;

    private String TAG = "ConverterController";

    public ConverterController(ConverterActivity view) {
        this.view = view;
    }

    public void onCreateController(Bundle bundle) {
       if(bundle != null) {
           // Create an instance of ConverterModel
           ConverterModel model = new ConverterModel();

           // Retrieve data from the Bundle and set the model variables
           model.getBundleVariables(bundle);
           // Set resources in view
           boolean isCCM = model.getIsCCM();
           double dutyCycle = model.getDutyCycle();
           double resistance = model.getResistance();
           double capacitance = model.getCapacitance();
           double inductance = model.getInductance();
           int flag = model.getFlag();
           view.setResources(isCCM, dutyCycle, resistance, capacitance, inductance, flag);
           Log.d(TAG, "Bundle is not null but variables: " + isCCM + " " + dutyCycle + " " + flag);
       } else {
           Log.d(TAG, "Bundle in ConverterController is null");
       }
    }

    public void onAdvancedClicked() {
//        view.navigateToAdvanced();
    }

    public void onSimulationClicked() {
//        view.navigateToSimulation(data);
    }
}
