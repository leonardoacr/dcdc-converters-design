package com.example.dcdcconvertersdesigndemo.interfaces.views;

import com.example.dcdcconvertersdesigndemo.controllers.SimulationParametersController;

public interface SimulationParametersViewInterface {
    void updateFrequency(double frequency);
    void updateTimeStep(double timeStep);
    void showSimulationTexts(double memoryCalculated, double maxTimeRecommended);
    void handleSimulationButtons(SimulationParametersController controller, double maxTime, double timeStep);
    void showProgressBar();
}