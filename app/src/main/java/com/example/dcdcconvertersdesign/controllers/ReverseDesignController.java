package com.example.dcdcconvertersdesign.controllers;

import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.verifyInputErrorsBoost;
import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.verifyInputErrorsBuck;
import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.verifyInputErrorsBuckBoost;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.models.ConverterReverseModel;
import com.example.dcdcconvertersdesign.models.ReverseDesignModel;
import com.example.dcdcconvertersdesign.utils.convertersutils.ConverterData;
import com.example.dcdcconvertersdesign.views.ConverterReverseView;
import com.example.dcdcconvertersdesign.views.ReverseDesignView;

public class ReverseDesignController {
    private final ReverseDesignView view;
    private final ReverseDesignModel model;
    private int flag;
    double inputVoltage;
    double outputVoltage;
    double resistance;
    double inductance;
    double capacitance;
    double frequency;
    double efficiency;

    private final String TAG = "ReverseDesignController";

    public ReverseDesignController(ReverseDesignView view) {
        this.view = view;
        this.model = new ReverseDesignModel();
    }

    public void onExampleClicked() {
        view.updateUIComponents(24, 12, 1.44, 71.11,
                17.36, 50, 90);
    }

    public void onBuckConverterClicked() {
        flag = 1;
        if (view.isEmpty()) {
            return;
        }

        getInputData();

        // Converter equations
        ConverterData data = ConverterReverseModel.buckCalculations(inputVoltage, outputVoltage,
                resistance, inductance, capacitance, frequency, efficiency);

        // Save flag
        data.setFlag(flag);
        double dutyCycle = data.getDutyCycle();
        Log.d(TAG, "DUTY: " + dutyCycle);
        Log.d(TAG, "power: " + data.getOutputPower());

        if(verifyInputErrorsBuck(view, inputVoltage, outputVoltage, dutyCycle, efficiency,
                resistance, capacitance, inductance)) {
            return;
        }

        navigateToConverterReverse(data);
    }

    public void onBoostConverterClicked() {
        flag = 2;
        if (view.isEmpty()) {
            return;
        }

        getInputData();

        // Converter equations
        ConverterData data = ConverterReverseModel.boostCalculations(inputVoltage, outputVoltage,
                resistance, inductance, capacitance, frequency, efficiency);

        // Save flag
        data.setFlag(flag);
        double dutyCycle = data.getDutyCycle();

        if(verifyInputErrorsBoost(view, inputVoltage, outputVoltage, dutyCycle, efficiency,
                resistance, capacitance, inductance)) {
            return;
        }

        navigateToConverterReverse(data);
    }

    public void onBuckBoostConverterClicked() {
        flag = 3;
        if (view.isEmpty()) {
            return;
        }

        getInputData();

        // Converter equations
        ConverterData data = ConverterReverseModel.buckBoostCalculations(inputVoltage, outputVoltage,
                resistance, inductance, capacitance, frequency, efficiency);

        // Save flag
        data.setFlag(flag);
        double dutyCycle = data.getDutyCycle();

        if(verifyInputErrorsBuckBoost(view, inputVoltage, outputVoltage, dutyCycle, efficiency,
                resistance, capacitance, inductance)) {
            return;
        }

        navigateToConverterReverse(data);
    }

    public void getInputData() {
        inputVoltage = view.getInputVoltage();
        outputVoltage = view.getOutputVoltage();
        resistance = view.getResistance();
        inductance = view.getInductance();
        capacitance = view.getCapacitance();
        frequency = view.getFrequency();
        efficiency = view.getEfficiency();
    }

    public void navigateToConverterReverse(ConverterData converterData) {
        Intent intent = new Intent(view.getApplicationContext(), ConverterReverseView.class);
        Bundle bundle = model.sendDataToConverterReverseActivity(converterData);
        intent.putExtras(bundle);
        view.startActivity(intent);
    }
}
