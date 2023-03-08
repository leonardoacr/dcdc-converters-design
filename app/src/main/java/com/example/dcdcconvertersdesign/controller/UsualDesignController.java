package com.example.dcdcconvertersdesign.controller;

import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.model.ConverterModel;
import com.example.dcdcconvertersdesign.convertersutils.ConverterData;
import com.example.dcdcconvertersdesign.model.UsualDesignModel;
import com.example.dcdcconvertersdesign.view.ConverterActivity;
import com.example.dcdcconvertersdesign.view.UsualDesignActivity;

public class UsualDesignController {
    private double outputPower, rippleInductorCurrent, rippleCapacitorVoltage, outputVoltage,
            inputVoltage, frequency, efficiency;
    private double dutyCycle, resistance, capacitance, inductance;
    private int flag;
    private final UsualDesignActivity view;
    private final UsualDesignModel model;

    private final String TAG = "UsualDesignController";

    public UsualDesignController(UsualDesignActivity view, UsualDesignModel model) {
        this.view = view;
        this.model = model;
    }

    public void onExampleClicked() {
        view.updateUIComponents(24, 12, 100, 20,
                1, 50, 90);
    }

    public void onBuckConverterClicked() {
        flag = 1;
        if (view.isEmpty()) {
            return;
        }

        // Get input variables from Edit Text
        getInputData();

        // Converter equations
        ConverterData data = ConverterModel.buckCalculations(inputVoltage,
                outputVoltage, outputPower, rippleInductorCurrent, rippleCapacitorVoltage,
                frequency, efficiency);

        // Save flag
        data.setFlag(flag);
        getConverterData(data);

        if(verifyInputErrorsBuck(view, inputVoltage, outputVoltage, dutyCycle, efficiency,
                resistance, capacitance, inductance)) {
            return;
        }

        navigateToConverter(data);
    }

    public void onBoostConverterClicked() {
        flag = 2;
        if (view.isEmpty()) {
            return;
        }

        // Get input variables from Edit Text
        getInputData();

        // Converter equations
        ConverterData data = ConverterModel.boostCalculations(inputVoltage,
                outputVoltage, outputPower, rippleInductorCurrent, rippleCapacitorVoltage,
                frequency, efficiency);

        // Save flag
        data.setFlag(flag);
        getConverterData(data);

        if(verifyInputErrorsBoost(view, inputVoltage, outputVoltage, dutyCycle, efficiency,
                resistance, capacitance, inductance)) {
            return;
        }

        navigateToConverter(data);
    }

    public void onBuckBoostConverterClicked() {
        flag = 3;
        if (view.isEmpty()) {
            return;
        }

        // Get input variables from Edit Text
        getInputData();

        // Converter equations
        ConverterData data = ConverterModel.buckBoostCalculations(inputVoltage,
                outputVoltage, outputPower, rippleInductorCurrent, rippleCapacitorVoltage,
                frequency, efficiency);

        // Save flag
        data.setFlag(flag);
        getConverterData(data);

        if(verifyInputErrorsBuckBoost(view, inputVoltage, outputVoltage, dutyCycle, efficiency,
                resistance, capacitance, inductance)) {
            return;
        }

        navigateToConverter(data);
    }

    private void getConverterData(ConverterData data) {
        dutyCycle = data.getDutyCycle();
        resistance = data.getResistance();
        capacitance = data.getCapacitance();
        inductance = data.getInductance();
    }

    private void getInputData() {
        inputVoltage = view.getInputVoltage();
        outputVoltage = view.getOutputVoltage();
        outputPower = view.getOutputPower();
        rippleInductorCurrent = view.getRippleInductorCurrent();
        rippleCapacitorVoltage = view.getRippleCapacitorVoltage();
        frequency = view.getFrequency();
        efficiency = view.getEfficiency();
    }

    public void navigateToConverter(ConverterData converterData) {
        Intent intent = new Intent(view, ConverterActivity.class);
        Bundle bundle = model.sendDataToConverterActivity(converterData);
        intent.putExtras(bundle);
        view.startActivity(intent);
    }
}
