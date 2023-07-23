package com.example.dcdcconvertersdesign.models;

import android.os.Bundle;

import com.example.dcdcconvertersdesign.interfaces.models.SnubberDesignModelInterface;

public class SnubberDesignModel implements SnubberDesignModelInterface {
    private double inputCurrent;
    private double outputVoltage;
    private double outputCurrent;
    private double frequency;
    private int flag;
    private double capacitanceSnubber;
    private double resistanceSnubber;
    private double powerSnubber;

    public void snubberEquations(int flag, double outputVoltage, double inputCurrent,
                                 double outputCurrent, double frequency, double timeDelayFall,
                                 double timeDelayOff) {
        // Buck
        if (flag == 1) {
            capacitanceSnubber = outputCurrent * (timeDelayFall + timeDelayOff) / (2 * outputVoltage);
            resistanceSnubber = outputVoltage / (0.2 * outputCurrent);
            powerSnubber = 0.5 * capacitanceSnubber * Math.pow(outputVoltage, 2) * frequency;
        }
        // Boost
        capacitanceSnubber = inputCurrent * (timeDelayFall + timeDelayOff)/ (2 * outputVoltage);
        if (flag == 2) {
            resistanceSnubber = outputVoltage / (0.2 * inputCurrent);
            powerSnubber = 0.5 * capacitanceSnubber * Math.pow(outputVoltage, 2) * frequency;
        }
        // Buck Boost
        if (flag == 3) {
            resistanceSnubber = outputVoltage / (0.2 * inputCurrent);
            powerSnubber = 0.5 * capacitanceSnubber * Math.pow(outputVoltage, 2) * frequency;
        }
    }

    public void retrieveDataFromAdvanced(Bundle bundle) {
        String INPUT_CURRENT_KEY = "Input_Current";
        String OUTPUT_CURRENT_KEY = "Output_Current";
        String OUTPUT_VOLTAGE_KEY = "Output_Voltage";
        String FLAG_KEY = "Flag";
        inputCurrent = bundle.getDouble(INPUT_CURRENT_KEY);
        outputCurrent = bundle.getDouble(OUTPUT_CURRENT_KEY);
        outputVoltage = bundle.getDouble(OUTPUT_VOLTAGE_KEY);
        frequency = bundle.getDouble(INPUT_CURRENT_KEY);
        flag = bundle.getInt(FLAG_KEY);
    }

    public double getInputCurrent() {
        return inputCurrent;
    }

    public double getOutputVoltage() {
        return outputVoltage;
    }

    public double getOutputCurrent() {
        return outputCurrent;
    }

    public double getFrequency() {
        return frequency;
    }

    public int getFlag() {
        return flag;
    }

    public double getCapacitanceSnubber() {
        return capacitanceSnubber;
    }

    public double getResistanceSnubber() {
        return resistanceSnubber;
    }

    public double getPowerSnubber() {
        return powerSnubber;
    }
}
