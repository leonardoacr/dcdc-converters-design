package com.example.dcdcconvertersdesign.models;

import android.os.Bundle;

import com.example.dcdcconvertersdesign.interfaces.models.SimulationModelInterface;
import com.example.dcdcconvertersdesign.models.converters.boost.BoostConverterArrays;
import com.example.dcdcconvertersdesign.models.converters.boost.BoostConverterSimulator;
import com.example.dcdcconvertersdesign.models.converters.buck.BuckConverterArrays;
import com.example.dcdcconvertersdesign.models.converters.buck.BuckConverterSimulator;
import com.example.dcdcconvertersdesign.models.converters.buckboost.BuckBoostConverterArrays;
import com.example.dcdcconvertersdesign.models.converters.buckboost.BuckBoostConverterSimulator;
import com.example.dcdcconvertersdesign.utils.simulationutils.GraphUtils;
import com.github.mikephil.charting.charts.LineChart;

public class SimulationModel implements SimulationModelInterface {
    private double outputVoltage;
    private double inputVoltage;
    private double dutyCycleIdeal;
    private double inductance;
    private double capacitance;
    private double resistance;
    private double frequency;
    private double timeStep;
    private int numStep;
    private int flag;
    private double maxTime;
    private String receivedID;
    private String fileNameKey;

    public void retrieveSimulationData(Bundle bundle) {
        outputVoltage = bundle.getDouble("Output_Voltage");
        inputVoltage = bundle.getDouble("Input_Voltage");
        dutyCycleIdeal = bundle.getDouble("Duty_Cycle_Ideal");
        inductance = bundle.getDouble("Inductance");
        capacitance = bundle.getDouble("Capacitance");
        frequency = bundle.getDouble("Frequency");
        flag = bundle.getInt("Flag");
        resistance = bundle.getDouble("Resistance");

        // Define the parameters
        maxTime = bundle.getDouble("Max_Time");
        timeStep = bundle.getDouble("Time_Step");
        receivedID = bundle.getString("Received_ID");
    }
    public void solveConverter(double outputVoltage, double inputVoltage, double dutyCycleIdeal,
                               double inductance, double capacitance, double resistance,
                               double frequency, double timeStep, int numStep, int flag) {
        switch (flag) {
            case 1:
                BuckConverterSimulator.solveDiffEquations(outputVoltage, inputVoltage, dutyCycleIdeal,
                        inductance, capacitance, resistance, frequency, timeStep, numStep);
                break;
            case 2:
                BoostConverterSimulator.solveDiffEquations(outputVoltage, inputVoltage, dutyCycleIdeal,
                        inductance, capacitance, resistance, frequency, timeStep, numStep);
                break;
            case 3:
                BuckBoostConverterSimulator.solveDiffEquations(outputVoltage, inputVoltage, dutyCycleIdeal,
                        inductance, capacitance, resistance, frequency, timeStep, numStep);
                break;
        }
    }

    public void handleOutputVoltage(GraphUtils graphUtils, LineChart chart) {
        fileNameKey = "OutputVoltage";
        double[] outputVoltageArray = new double[numStep + 1];
        double[] timeArray = new double[numStep + 1];

        if (flag == 1) {
            outputVoltageArray = BuckConverterSimulator.getOutputVoltageArray();
            timeArray = BuckConverterSimulator.getTimeArray();
        }
        if (flag == 2) {
            outputVoltageArray = BoostConverterSimulator.getOutputVoltageArray();
            timeArray = BoostConverterSimulator.getTimeArray();
        }
        if (flag == 3) {
            outputVoltageArray = BuckBoostConverterSimulator.getOutputVoltageArray();
            timeArray = BuckBoostConverterSimulator.getTimeArray();
        }

        graphUtils.loadDataAndPlotGraph(timeArray, outputVoltageArray, getNumStep(), fileNameKey, chart);
    }

    public void handleOutputCurrent(GraphUtils graphUtils, LineChart chart, int flag) {
        fileNameKey = "OutputCurrent";
        numStep = getNumStep();
        double[] outputCurrentArray = new double[numStep + 1];
        double[] outputVoltageArray;
        double[] timeArray = new double[numStep + 1];

        if (flag == 1) {
            outputVoltageArray = BuckConverterSimulator.getOutputVoltageArray();
            timeArray = BuckConverterSimulator.getTimeArray();
            outputCurrentArray = BuckConverterArrays.calculateOutputCurrentArray(
                    outputVoltageArray, resistance);
        }
        if (flag == 2) {
            outputVoltageArray = BoostConverterSimulator.getOutputVoltageArray();
            timeArray = BoostConverterSimulator.getTimeArray();
            outputCurrentArray = BoostConverterArrays.calculateOutputCurrentArray(
                    outputVoltageArray, resistance);
        }
        if (flag == 3) {
            outputVoltageArray = BuckBoostConverterSimulator.getOutputVoltageArray();
            timeArray = BuckBoostConverterSimulator.getTimeArray();
            outputCurrentArray = BuckBoostConverterArrays.calculateOutputCurrentArray(
                    outputVoltageArray, resistance);
        }

        graphUtils.loadDataAndPlotGraph(timeArray, outputCurrentArray, numStep, fileNameKey, chart);
    }

    public void handleInputCurrent(GraphUtils graphUtils, LineChart chart, int flag) {
        fileNameKey = "InputCurrent";
        numStep = getNumStep();
        double[] inputCurrentArray = new double[numStep + 1];
        double[] timeArray = new double[numStep + 1];
        double[] inductorCurrentArray;
        double[] sArray;

        if (flag == 1) {
            inductorCurrentArray = BuckConverterSimulator.getInductorCurrentArray();
            sArray = BuckConverterSimulator.getSArray();
            timeArray = BuckConverterSimulator.getTimeArray();
            inputCurrentArray = BuckConverterArrays.calculateInputCurrentArray(
                    inductorCurrentArray, sArray);
        }
        if (flag == 2) {
            inductorCurrentArray = BoostConverterSimulator.getInductorCurrentArray();
            timeArray = BoostConverterSimulator.getTimeArray();
            inputCurrentArray = inductorCurrentArray;
        }
        if (flag == 3) {
            inductorCurrentArray = BuckBoostConverterSimulator.getInductorCurrentArray();
            sArray = BuckBoostConverterSimulator.getSArray();
            timeArray = BuckBoostConverterSimulator.getTimeArray();
            inputCurrentArray = BuckBoostConverterArrays.calculateInputCurrentArray(
                    inductorCurrentArray, sArray);
        }

        graphUtils.loadDataAndPlotGraph(timeArray, inputCurrentArray, numStep, fileNameKey, chart);
    }

    public void handleDiodeCurrent(GraphUtils graphUtils, LineChart chart, int flag) {
        fileNameKey = "DiodeCurrent";
        numStep = getNumStep();
        double[] timeArray = new double[numStep + 1];
        double[] diodeCurrentArray = new double[numStep + 1];
        double[] inductorCurrentArray;
        double[] sArray;

        if (flag == 1) {
            inductorCurrentArray = BuckConverterSimulator.getInductorCurrentArray();
            sArray = BuckConverterSimulator.getSArray();
            timeArray = BuckConverterSimulator.getTimeArray();
            diodeCurrentArray = BuckConverterArrays.calculateDiodeCurrentArray(
                    inductorCurrentArray, sArray);
        }
        if (flag == 2) {
            inductorCurrentArray = BoostConverterSimulator.getInductorCurrentArray();
            timeArray = BoostConverterSimulator.getTimeArray();
            sArray = BoostConverterSimulator.getSArray();
            diodeCurrentArray = BoostConverterArrays.calculateDiodeCurrentArray(
                    inductorCurrentArray, sArray);
        }
        if (flag == 3) {
            inductorCurrentArray = BuckBoostConverterSimulator.getInductorCurrentArray();
            timeArray = BuckBoostConverterSimulator.getTimeArray();
            sArray = BuckBoostConverterSimulator.getSArray();
            diodeCurrentArray = BuckBoostConverterArrays.calculateDiodeCurrentArray(
                    inductorCurrentArray, sArray);
        }

        graphUtils.loadDataAndPlotGraph(timeArray, diodeCurrentArray, numStep, fileNameKey, chart);
    }

    public void handleSwitchCurrent(GraphUtils graphUtils, LineChart chart, int flag) {
        fileNameKey = "SwitchCurrent";
        numStep = getNumStep();
        double[] switchCurrentArray = new double[numStep + 1];
        double[] inductorCurrentArray;
        double[] timeArray = new double[numStep + 1];
        double[] sArray;

        if (flag == 1) {
            inductorCurrentArray = BuckConverterSimulator.getInductorCurrentArray();
            sArray = BuckConverterSimulator.getSArray();
            timeArray = BuckConverterSimulator.getTimeArray();
            switchCurrentArray = BuckConverterArrays.calculateSwitchCurrentArray(
                    inductorCurrentArray, sArray);
        }
        if (flag == 2) {
            inductorCurrentArray = BoostConverterSimulator.getInductorCurrentArray();
            sArray = BoostConverterSimulator.getSArray();
            timeArray = BoostConverterSimulator.getTimeArray();
            switchCurrentArray = BoostConverterArrays.calculateSwitchCurrentArray(
                    inductorCurrentArray, sArray);
        }
        if (flag == 3) {
            inductorCurrentArray = BuckBoostConverterSimulator.getInductorCurrentArray();
            sArray = BuckBoostConverterSimulator.getSArray();
            timeArray = BuckBoostConverterSimulator.getTimeArray();
            switchCurrentArray = BuckBoostConverterArrays.calculateSwitchCurrentArray(
                    inductorCurrentArray, sArray);
        }

        graphUtils.loadDataAndPlotGraph(timeArray, switchCurrentArray, numStep, fileNameKey, chart);
    }

    public void handleInductorCurrent(GraphUtils graphUtils, LineChart chart) {
        fileNameKey = "InductorCurrent";
        numStep = getNumStep();
        double[] inductorCurrentArray = new double[numStep + 1];
        double[] timeArray = new double[numStep + 1];

        if (flag == 1) {
            inductorCurrentArray = BuckConverterSimulator.getInductorCurrentArray();
            timeArray = BuckConverterSimulator.getTimeArray();
        }
        if (flag == 2) {
            inductorCurrentArray = BoostConverterSimulator.getInductorCurrentArray();
            timeArray = BoostConverterSimulator.getTimeArray();
        }
        if (flag == 3) {
            inductorCurrentArray = BuckBoostConverterSimulator.getInductorCurrentArray();
            timeArray = BuckBoostConverterSimulator.getTimeArray();
        }

        graphUtils.loadDataAndPlotGraph(timeArray, inductorCurrentArray, numStep, fileNameKey, chart);
    }

    public void handleCapacitorCurrent(GraphUtils graphUtils, LineChart chart, int flag) {
        fileNameKey = "CapacitorCurrent";
        numStep = getNumStep();
        double[] capacitorCurrentArray = new double[numStep + 1];
        double[] timeArray = new double[numStep + 1];
        double[] outputVoltageArray;
        double[] inductorCurrentArray;
        double[] sArray;

        if (flag == 1) {
            outputVoltageArray = BuckConverterSimulator.getOutputVoltageArray();
            inductorCurrentArray = BuckConverterSimulator.getInductorCurrentArray();
            timeArray = BuckConverterSimulator.getTimeArray();
            capacitorCurrentArray = BuckConverterArrays.calculateCapacitorCurrentArray(
                    outputVoltageArray, inductorCurrentArray, resistance);
        }
        if (flag == 2) {
            outputVoltageArray = BoostConverterSimulator.getOutputVoltageArray();
            inductorCurrentArray = BoostConverterSimulator.getInductorCurrentArray();
            sArray = BoostConverterSimulator.getSArray();
            timeArray = BoostConverterSimulator.getTimeArray();
            capacitorCurrentArray = BoostConverterArrays.calculateCapacitorCurrentArray(
                    outputVoltageArray, inductorCurrentArray, resistance, sArray);
        }
        if (flag == 3) {
            outputVoltageArray = BuckBoostConverterSimulator.getOutputVoltageArray();
            inductorCurrentArray = BuckBoostConverterSimulator.getInductorCurrentArray();
            sArray = BuckBoostConverterSimulator.getSArray();
            timeArray = BuckBoostConverterSimulator.getTimeArray();
            capacitorCurrentArray = BuckBoostConverterArrays.calculateCapacitorCurrentArray(
                    outputVoltageArray, inductorCurrentArray, resistance, sArray);
        }

        graphUtils.loadDataAndPlotGraph(timeArray, capacitorCurrentArray, numStep, fileNameKey, chart);
    }

    public double getOutputVoltage() {
        return outputVoltage;
    }

    public double getInputVoltage() {
        return inputVoltage;
    }

    public double getDutyCycleIdeal() {
        return dutyCycleIdeal;
    }

    public double getInductance() {
        return inductance;
    }

    public double getCapacitance() {
        return capacitance;
    }

    public double getResistance() {
        return resistance;
    }

    public double getFrequency() {
        return frequency;
    }

    public double getTimeStep() {
        return timeStep;
    }

    public int getNumStep() {
        return numStep;
    }

    public void setNumStep(double maxTime, double timeStep) {
        this.numStep = (int) (maxTime / timeStep);
    }

    public int getFlag() {
        return flag;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public String getReceivedID() {
        return receivedID;
    }

    public String getFileNameKey() {
        return fileNameKey;
    }
}
