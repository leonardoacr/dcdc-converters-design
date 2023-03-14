package com.example.dcdcconvertersdesign.interfaces;

import android.os.Bundle;

public interface SimulationParametersControllerInterface {
    void onCreateController(Bundle bundle);
    void handleMaxTimeInput(String maxTimeStr);
    void navigateToSimulation(double maxTime, double timeStep, String receivedID);
}
