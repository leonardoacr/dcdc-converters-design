package com.example.dcdcconvertersdesigndemo.models;

import com.example.dcdcconvertersdesigndemo.interfaces.models.SimulationParametersModelInterface;

public class SimulationParametersModel implements SimulationParametersModelInterface {
    private double maxTimeRecommended;
    private double memoryCalculated;

    public SimulationParametersModel() {
    }

    public void calculateSimulationParameters(double maxTime, double timeStep) {
        double maxMemoryAllowed = 10; // MB
        double requiredMemoryByByteCSV = 16.0 / 1048576.0;
        double arraysQuantity = 2;
        double numStepCalculated = (maxTime / timeStep);

        memoryCalculated = numStepCalculated * requiredMemoryByByteCSV * arraysQuantity;

        maxTimeRecommended = (timeStep * maxMemoryAllowed
                / (requiredMemoryByByteCSV * arraysQuantity)) * 1000;
    }

    public double getMaxTimeRecommended() {
        return maxTimeRecommended;
    }

    public double getMemoryCalculated() {
        return memoryCalculated;
    }

    public double calculateTimeStep(double frequency) {
        return 1 / frequency / 1000;
    }
}



