package com.example.dcdcconvertersdesign.interfaces.views;

import com.example.dcdcconvertersdesign.utils.simulationutils.GraphUtils;
import com.github.mikephil.charting.charts.LineChart;

public interface SimulationViewInterface {
    LineChart initChart();
    void handleDialogButtons(LineChart chart, GraphUtils graphUtils);
    void alertBox(String alertString);
}
