package com.example.dcdcconvertersdesign.interfaces;

import android.os.Bundle;

import com.example.dcdcconvertersdesign.utils.simulationutils.GraphUtils;
import com.github.mikephil.charting.charts.LineChart;

public interface SimulationControllerInterface {
    void onCreateController(Bundle bundle);
    void handleOptionsButton(LineChart chart, GraphUtils graphUtils);
    void handleSaveGraphButton(LineChart chart);
}
