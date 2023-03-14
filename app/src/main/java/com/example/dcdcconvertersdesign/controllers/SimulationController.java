package com.example.dcdcconvertersdesign.controllers;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import com.example.dcdcconvertersdesign.interfaces.controllers.SimulationControllerInterface;
import com.example.dcdcconvertersdesign.models.SimulationModel;
import com.example.dcdcconvertersdesign.models.converters.boost.BoostConverterSimulator;
import com.example.dcdcconvertersdesign.models.converters.buck.BuckConverterSimulator;
import com.example.dcdcconvertersdesign.models.converters.buckboost.BuckBoostConverterSimulator;
import com.example.dcdcconvertersdesign.utils.simulationutils.FileSaver;
import com.example.dcdcconvertersdesign.utils.simulationutils.GraphUtils;
import com.example.dcdcconvertersdesign.utils.simulationutils.LimitsDialog;
import com.example.dcdcconvertersdesign.utils.simulationutils.SaveDialog;
import com.example.dcdcconvertersdesign.views.SimulationView;
import com.example.dcdcconvertersdesign.views.SimulationParametersView;
import com.github.mikephil.charting.charts.LineChart;

import java.util.Objects;

public class SimulationController implements SimulationControllerInterface {
    private final SimulationView view;
    private final SimulationModel model;
//    private final String TAG = "SimulationController";

    public SimulationController(SimulationView view) {
        this.view = view;
        this.model = new SimulationModel();
    }

    public void onCreateController(Bundle bundle) {
        if (bundle != null) {
            model.retrieveSimulationData(bundle);
            model.setNumStep(model.getMaxTime(), model.getTimeStep());
            model.solveConverter(model.getOutputVoltage(), model.getInputVoltage(),
                    model.getDutyCycleIdeal(), model.getInductance(), model.getCapacitance(),
                    model.getResistance(), model.getFrequency(), model.getTimeStep(),
                    model.getNumStep(), model.getFlag());

            LineChart chart = view.initChart();
            GraphUtils graphUtils = new GraphUtils();

            //        Log.d(TAG, String.valueOf(chart.getData()));

            synchronized (this) {
                switch (model.getFlag()) {
                    case 1:
                        while (chart.getData() == null || chart.getData().getEntryCount() !=
                                BuckConverterSimulator.getOutputVoltageArray().length - 1) {
                            handleID(model.getReceivedID(), model.getFlag(), chart, graphUtils);
                        }
                        break;
                    case 2:
                        while (chart.getData() == null || chart.getData().getEntryCount() !=
                                BoostConverterSimulator.getOutputVoltageArray().length - 1) {
                            handleID(model.getReceivedID(), model.getFlag(), chart, graphUtils);
                        }
                        break;
                    case 3:
                        while (chart.getData() == null || chart.getData().getEntryCount() !=
                                BuckBoostConverterSimulator.getOutputVoltageArray().length - 1) {
                            handleID(model.getReceivedID(), model.getFlag(), chart, graphUtils);
                        }
                        break;
                }
            }

            long delayToHideProgressBar = model.getNumStep() / 200;
            new Handler().postDelayed(SimulationParametersView::hideProgressBar,
                    delayToHideProgressBar);

            view.handleDialogButtons(chart, graphUtils);
        }
    }

    private void handleID(String receivedID, int flag, LineChart chart, GraphUtils graphUtils) {
        switch (receivedID) {
            case "outputVoltage":
                model.handleOutputVoltage(graphUtils, chart);
                break;

            case "outputCurrent":
                model.handleOutputCurrent(graphUtils, chart, flag);
                break;

            case "inputCurrent":
                model.handleInputCurrent(graphUtils, chart, flag);
                break;

            case "diodeCurrent":
                model.handleDiodeCurrent(graphUtils, chart, flag);
                break;

            case "switchCurrent":
                model.handleSwitchCurrent(graphUtils, chart, flag);
                break;

            case "inductorCurrent":
                model.handleInductorCurrent(graphUtils, chart);
                break;

            case "capacitorCurrent":
                model.handleCapacitorCurrent(graphUtils, chart, flag);
                break;

            default:
                // receivedID is not recognized
                break;
        }
    }

    public void handleOptionsButton(LineChart chart, GraphUtils graphUtils) {
        // Create a new instance of the LimitsDialogFragment
        LimitsDialog dialog = new LimitsDialog();

        dialog.setListener((xLowerLimit, xUpperLimit, yLowerLimit, yUpperLimit) ->
                graphUtils.plotGraph(chart, xLowerLimit, xUpperLimit, yLowerLimit, yUpperLimit));
        // Show the dialog
        dialog.show(view.getSupportFragmentManager(), "LimitsDialog");
    }

    public void handleSaveGraphButton(LineChart chart) {
        // Create a new instance of the LimitsDialogFragment
        SaveDialog dialog = new SaveDialog();

        dialog.setListener((saveKey) -> {
            String folderName = "/DCDCConvertersDesign";
            String directoryPath = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS) + folderName;
            String fileNameKey = model.getFileNameKey();

            if (Objects.equals(saveKey, "PNG")) {
                FileSaver.savePNG(directoryPath, chart, fileNameKey, view);
            }
            if (Objects.equals(saveKey, "CSV")) {
                FileSaver.saveCSV(directoryPath, fileNameKey, view);
            }
        });
        // Show the dialog
        dialog.show(view.getSupportFragmentManager(), "SaveDialog");
    }
}
