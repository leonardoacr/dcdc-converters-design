package com.dcdcconvertersdesigndemo.interfaces.views;

import com.dcdcconvertersdesigndemo.utils.simulationutils.GraphUtils;
import com.github.mikephil.charting.charts.LineChart;

public interface SimulationViewInterface {
    LineChart initChart();
    void handleDialogButtons(LineChart chart, GraphUtils graphUtils);
    void alertBox(String alertString);
}
