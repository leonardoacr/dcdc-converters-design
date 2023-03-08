package com.example.dcdcconvertersdesign.models;

import android.os.Bundle;

import com.example.dcdcconvertersdesign.simulationutils.CalculateBoostArrays;
import com.example.dcdcconvertersdesign.simulationutils.CalculateBuckArrays;
import com.example.dcdcconvertersdesign.simulationutils.CalculateBuckBoostArrays;
import com.example.dcdcconvertersdesign.simulationutils.GraphUtils;
import com.example.dcdcconvertersdesign.simulationutils.SolveDiffEquations;
import com.github.mikephil.charting.charts.LineChart;

public class SimulationModel {
    private double outputVoltage;
    private double inputVoltage;
    private double dutyCycle;
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
        dutyCycle = bundle.getDouble("Duty_Cycle");
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
                SolveDiffEquations.buckConverter(outputVoltage, inputVoltage, dutyCycleIdeal,
                        inductance, capacitance, resistance, frequency, timeStep, numStep);
                break;
            case 2:
                SolveDiffEquations.boostConverter(outputVoltage, inputVoltage, dutyCycleIdeal,
                        inductance, capacitance, resistance, frequency, timeStep, numStep);
                break;
            case 3:
                SolveDiffEquations.buckBoostConverter(outputVoltage, inputVoltage, dutyCycleIdeal,
                        inductance, capacitance, resistance, frequency, timeStep, numStep);
                break;
        }
    }

    public void handleOutputVoltage(GraphUtils graphUtils, LineChart chart) {
        fileNameKey = "OutputVoltage";
        double[] outputVoltageArray = SolveDiffEquations.getOutputVoltageArray();
        double[] timeArray = SolveDiffEquations.getTimeArray();
        graphUtils.loadDataAndPlotGraph(timeArray, outputVoltageArray, getNumStep(), fileNameKey, chart);
    }

    public void handleOutputCurrent(GraphUtils graphUtils, LineChart chart, int flag) {
        fileNameKey = "OutputCurrent";
        numStep = getNumStep();
        double[] outputCurrentArray = new double[numStep + 1];
        double[] outputVoltageArray = SolveDiffEquations.getOutputVoltageArray();
        double[] timeArray = SolveDiffEquations.getTimeArray();

        if (flag == 1) {
            outputCurrentArray = CalculateBuckArrays.calculateOutputCurrentArray(
                    outputVoltageArray, resistance);
        }
        if (flag == 2) {
            outputCurrentArray = CalculateBoostArrays.calculateOutputCurrentArray(
                    outputVoltageArray, resistance);
        }
        if (flag == 3) {
            outputCurrentArray = CalculateBuckBoostArrays.calculateOutputCurrentArray(
                    outputVoltageArray, resistance);
        }

        graphUtils.loadDataAndPlotGraph(timeArray, outputCurrentArray, numStep, fileNameKey, chart);
    }

    public void handleInputCurrent(GraphUtils graphUtils, LineChart chart, int flag) {
        fileNameKey = "InputCurrent";
        numStep = getNumStep();
        double[] inputCurrentArray = new double[numStep + 1];
        double[] inductorCurrentArray = SolveDiffEquations.getInductorCurrentArray();
        double[] timeArray = SolveDiffEquations.getTimeArray();
        double[] sArray = SolveDiffEquations.getSArray();

        if (flag == 1) {
            inputCurrentArray = CalculateBuckArrays.calculateInputCurrentArray(
                    inductorCurrentArray, sArray);
        }
        if (flag == 2) {
            inputCurrentArray = inductorCurrentArray;
        }
        if (flag == 3) {
            inputCurrentArray = CalculateBuckBoostArrays.calculateInputCurrentArray(
                    inductorCurrentArray, sArray);
        }

        graphUtils.loadDataAndPlotGraph(timeArray, inputCurrentArray, numStep, fileNameKey, chart);
    }

    public void handleDiodeCurrent(GraphUtils graphUtils, LineChart chart, int flag) {
        fileNameKey = "DiodeCurrent";
        numStep = getNumStep();
        double[] diodeCurrentArray = new double[numStep + 1];
        double[] inductorCurrentArray = SolveDiffEquations.getInductorCurrentArray();
        double[] timeArray = SolveDiffEquations.getTimeArray();
        double[] sArray = SolveDiffEquations.getSArray();

        if (flag == 1) {
            diodeCurrentArray = CalculateBuckArrays.calculateDiodeCurrentArray(
                    inductorCurrentArray, sArray);
        }
        if (flag == 2) {
            diodeCurrentArray = CalculateBoostArrays.calculateDiodeCurrentArray(
                    inductorCurrentArray, sArray);
        }
        if (flag == 3) {
            diodeCurrentArray = CalculateBuckBoostArrays.calculateDiodeCurrentArray(
                    inductorCurrentArray, sArray);
        }

        graphUtils.loadDataAndPlotGraph(timeArray, diodeCurrentArray, numStep, fileNameKey, chart);
    }

    public void handleSwitchCurrent(GraphUtils graphUtils, LineChart chart, int flag) {
        fileNameKey = "SwitchCurrent";
        numStep = getNumStep();
        double[] switchCurrentArray = new double[numStep + 1];
        double[] inductorCurrentArray = SolveDiffEquations.getInductorCurrentArray();
        double[] timeArray = SolveDiffEquations.getTimeArray();
        double[] sArray = SolveDiffEquations.getSArray();

        if (flag == 1) {
            switchCurrentArray = CalculateBuckArrays.calculateSwitchCurrentArray(
                    inductorCurrentArray, sArray);
        }
        if (flag == 2) {
            switchCurrentArray = CalculateBoostArrays.calculateSwitchCurrentArray(
                    inductorCurrentArray, sArray);
        }
        if (flag == 3) {
            switchCurrentArray = CalculateBuckBoostArrays.calculateSwitchCurrentArray(
                    inductorCurrentArray, sArray);
        }

        graphUtils.loadDataAndPlotGraph(timeArray, switchCurrentArray, numStep, fileNameKey, chart);
    }

    public void handleInductorCurrent(GraphUtils graphUtils, LineChart chart) {
        fileNameKey = "InductorCurrent";
        numStep = getNumStep();
        double[] inductorCurrentArray = SolveDiffEquations.getInductorCurrentArray();
        double[] timeArray = SolveDiffEquations.getTimeArray();
        graphUtils.loadDataAndPlotGraph(timeArray, inductorCurrentArray, numStep, fileNameKey, chart);
    }

    public void handleCapacitorCurrent(GraphUtils graphUtils, LineChart chart, int flag) {
        fileNameKey = "CapacitorCurrent";
        numStep = getNumStep();
        double[] outputVoltageArray = SolveDiffEquations.getOutputVoltageArray();
        double[] capacitorCurrentArray = new double[numStep + 1];
        double[] inductorCurrentArray = SolveDiffEquations.getInductorCurrentArray();
        double[] timeArray = SolveDiffEquations.getTimeArray();
        double[] sArray = SolveDiffEquations.getSArray();

        if (flag == 1) {
            capacitorCurrentArray = CalculateBuckArrays.calculateCapacitorCurrentArray(
                    outputVoltageArray, inductorCurrentArray, resistance);
        }
        if (flag == 2) {
            capacitorCurrentArray = CalculateBoostArrays.calculateCapacitorCurrentArray(
                    outputVoltageArray, inductorCurrentArray, resistance, sArray);
        }
        if (flag == 3) {
            capacitorCurrentArray = CalculateBuckBoostArrays.calculateCapacitorCurrentArray(
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

    public double getDutyCycle() {
        return dutyCycle;
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
