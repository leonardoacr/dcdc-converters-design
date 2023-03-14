package com.example.dcdcconvertersdesign.controllers;

import android.os.Bundle;

import com.example.dcdcconvertersdesign.interfaces.SnubberDesignControllerInterface;
import com.example.dcdcconvertersdesign.models.SnubberDesignModel;
import com.example.dcdcconvertersdesign.views.SnubberDesignView;

import java.util.Objects;

public class SnubberDesignController implements SnubberDesignControllerInterface {
    private final SnubberDesignView view;
    private final SnubberDesignModel model;

    public SnubberDesignController(SnubberDesignView view) {
        this.view = view;
        this.model = new SnubberDesignModel();
    }

    public void onCreateController(Bundle bundle) {
        model.retrieveDataFromAdvanced(bundle);
        view.setSnubberImage(model.getFlag());
    }

    public void onResultsClicked(boolean btn) {
        if (btn) {
            if (view.isEmpty()) {
                return;
            }
            // Calculate Snubber equations
            model.snubberEquations(model.getFlag(), model.getOutputVoltage(),
                    model.getInputCurrent(), model.getOutputCurrent(), model.getFrequency(),
                    view.getTimeDelayFall(), view.getTimeDelayOff());

            updateUI("SHOW");
        } else {
            updateUI("HIDE");
        }
    }

    public void updateUI(String state) {
        view.updateUITexts();
        view.updateUIValues(model.getCapacitanceSnubber(),
                model.getResistanceSnubber(), model.getPowerSnubber());

        if(Objects.equals(state, "SHOW")) {
            view.showResults();
        } else if (Objects.equals(state, "HIDE")) {
            view.hideResults();
        }
    }
}
