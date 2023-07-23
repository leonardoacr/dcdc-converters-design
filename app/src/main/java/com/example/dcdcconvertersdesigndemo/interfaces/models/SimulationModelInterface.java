package com.example.dcdcconvertersdesign.interfaces.models;

import android.os.Bundle;

import com.example.dcdcconvertersdesign.utils.simulationutils.GraphUtils;
import com.github.mikephil.charting.charts.LineChart;

public interface SimulationModelInterface {
    void retrieveSimulationData(Bundle bundle);
    void solveConverter(double outputVoltage, double inputVoltage, double dutyCycleIdeal,
                        double inductance, double capacitance, double resistance,
                        double frequency, double timeStep, int numStep, int flag);
    void handleOutputVoltage(GraphUtils graphUtils, LineChart chart);
    void handleOutputCurrent(GraphUtils graphUtils, LineChart chart, int flag);
    void handleInputCurrent(GraphUtils graphUtils, LineChart chart, int flag);
    void handleDiodeCurrent(GraphUtils graphUtils, LineChart chart, int flag);
    void handleSwitchCurrent(GraphUtils graphUtils, LineChart chart, int flag);
}
