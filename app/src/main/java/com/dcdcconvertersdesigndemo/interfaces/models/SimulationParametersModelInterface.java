package com.example.dcdcconvertersdesigndemo.interfaces.models;

public interface SimulationParametersModelInterface {
    void calculateSimulationParameters(double maxTime, double timeStep);
    double getMaxTimeRecommended();
    double getMemoryCalculated();
    double calculateTimeStep(double frequency);
}
