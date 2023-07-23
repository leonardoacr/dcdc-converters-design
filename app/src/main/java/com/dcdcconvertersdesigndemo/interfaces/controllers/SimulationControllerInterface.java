package com.example.dcdcconvertersdesigndemo.interfaces.controllers;

import android.os.Bundle;

import com.example.dcdcconvertersdesigndemo.utils.simulationutils.GraphUtils;
import com.github.mikephil.charting.charts.LineChart;

public interface SimulationControllerInterface {
    void onCreateController(Bundle bundle);
    void handleOptionsButton(LineChart chart, GraphUtils graphUtils);
    void handleSaveGraphButton(LineChart chart);
}
