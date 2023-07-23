package com.example.dcdcconvertersdesigndemo.controllers;

import android.content.Intent;
import android.os.Bundle;

import com.example.dcdcconvertersdesigndemo.interfaces.controllers.InductorDesignControllerInterface;
import com.example.dcdcconvertersdesigndemo.models.InductorDesignModel;
import com.example.dcdcconvertersdesigndemo.views.InductorDesignView;
import com.example.dcdcconvertersdesigndemo.views.InductorDesignTableView;

public class InductorDesignController implements InductorDesignControllerInterface,
             InductorDesignModel.ModelListener {
    private final InductorDesignView view;
    private final InductorDesignModel model;

    public InductorDesignController(InductorDesignView view) {
        this.view = view;
        this.model = new InductorDesignModel();
    }

    public void onCreateController(Bundle bundle) {
        model.retrieveDataFromAdvanced(bundle);
    }

    public void onResultsClicked() {
        if (view.isEmpty()) {
            return;
        }

        model.inductorDesignEquations(view.getJmax(), view.getBmax(), view.getKu(),
                model.getInductance(), model.getDeltaInductorCurrent(),
                model.getInductorCurrentRMS(), model.getFrequency());

        updateUI();
    }

    public void onTableClicked() {
        Intent intent = new Intent(view, InductorDesignTableView.class);
        view.startActivity(intent);
    }

    private void updateUI() {
        view.updateUITexts();
        view.updateUIValues(model.getAirGap(), model.getAreaPercentage(), model.getTurnNumber(),
                model.getAwg(), model.getParallelConductors(), model.getCoreModel());
        view.updateUILayouts();
        view.setTableButtonVisible();
    }

    @Override
    public void onEvent(String message) {
        // handle the event
        // For example, display a toast message:
        view.setCoreError(message);
    }
}
