package com.example.dcdcconvertersdesign.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dcdcconvertersdesign.models.InductorDesignModel;
import com.example.dcdcconvertersdesign.views.InductorDesignActivity;
import com.example.dcdcconvertersdesign.views.InductorDesignTableActivity;

public class InductorDesignController implements InductorDesignModel.ModelListener {
    private final InductorDesignActivity view;
    private final InductorDesignModel model;

    public InductorDesignController(InductorDesignActivity view, InductorDesignModel model) {
        this.view = view;
        this.model = model;
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
        Intent intent = new Intent(view, InductorDesignTableActivity.class);
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
