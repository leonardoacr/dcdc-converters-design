package com.example.dcdcconvertersdesign.controllers;

import android.os.Bundle;

import com.example.dcdcconvertersdesign.models.SnubberDesignModel;
import com.example.dcdcconvertersdesign.views.SnubberDesignView;

public class SnubberDesignController {
    private final SnubberDesignView view;
    private final SnubberDesignModel model;
    public SnubberDesignController(SnubberDesignView view, SnubberDesignModel model) {
        this.view = view;
        this.model = model;
    }

    public void onCreateController(Bundle bundle) {
        model.retrieveDataFromAdvanced(bundle);
        view.setSnubberImage(model.getFlag());
    }
    public void onResultsClicked(boolean btn) {
            if(btn) {
                if (view.isEmpty()) {
                    return;
                }
                // Calculate Snubber equations
                model.snubberEquations(model.getFlag(), model.getOutputVoltage(),
                        model.getInputCurrent(), model.getOutputCurrent(), model.getFrequency(),
                        view.getTimeDelayFall(), view.getTimeDelayOff());

                updateDisplay();
            }
    }
    private void updateDisplay() {
        view.updateDisplayTexts();

        view.updateDisplayValues(model.getCapacitanceSnubber(),
                model.getResistanceSnubber(), model.getPowerSnubber());
    }
}
