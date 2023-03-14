package com.example.dcdcconvertersdesign.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.interfaces.SimulationParametersControllerInterface;
import com.example.dcdcconvertersdesign.views.SimulationView;
import com.example.dcdcconvertersdesign.models.SimulationParametersModel;
import com.example.dcdcconvertersdesign.views.SimulationParametersView;

public class SimulationParametersController implements SimulationParametersControllerInterface {
    private final SimulationParametersView view;
    private final SimulationParametersModel model;

    private double timeStep;

    private final String TAG = "SimulationParameters";

    public SimulationParametersController(SimulationParametersView view) {
        this.view = view;
        this.model = new SimulationParametersModel();
    }

    public void onCreateController(Bundle bundle) {
        if(bundle != null) {
            double frequency = bundle.getDouble("Frequency");
            view.updateFrequency(frequency);

            timeStep = model.calculateTimeStep(frequency);
            view.updateTimeStep(timeStep);
        } else {
            Log.d(TAG, "Bundle in SimulationParametersController is null");
        }
    }

    public void handleMaxTimeInput(String maxTimeStr) {
        if (!maxTimeStr.isEmpty()) {
            double maxTime = Double.parseDouble(maxTimeStr) / 1000;
            model.calculateSimulationParameters(maxTime, timeStep);

            // Update the view by calling the corresponding methods
            double maxTimeRecommended = model.getMaxTimeRecommended();
            double memoryCalculated = model.getMemoryCalculated();
            view.showSimulationTexts(memoryCalculated, maxTimeRecommended);
            view.handleSimulationButtons(this, maxTime, timeStep);
        }
    }

    public void navigateToSimulation(double maxTime, double timeStep, String receivedID){
        Intent intentSimulation = new Intent(view, SimulationView.class);
        Bundle bundle = new Bundle();

        // Copy all extras from the previous activity
        bundle.putAll(view.getIntent().getExtras());
        String MAX_TIME_KEY = "Max_Time";
        String TIME_STEP_KEY = "Time_Step";
        String RECEIVED_ID_KEY = "Received_ID";
        bundle.putDouble(MAX_TIME_KEY, maxTime);
        bundle.putDouble(TIME_STEP_KEY, timeStep);
        bundle.putString(RECEIVED_ID_KEY, receivedID);

        intentSimulation.putExtras(bundle);
        view.startActivity(intentSimulation);
    }
}
