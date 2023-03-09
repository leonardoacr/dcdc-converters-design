package com.example.dcdcconvertersdesign.models;

import android.os.Bundle;

public class ConverterReverseModel {
    private static double dutyCycle;
    private static double dutyCycleIdeal;
    private static double resistance;
    private static double capacitance;
    private static double inductance;
    private static double inductanceCritical;
    private static double inputCurrent;
    private static double outputCurrent;
    private static double inductorCurrent;
    private static double switchCurrent;
    private static double diodeCurrent;
    private static double inputVoltage;
    private static double outputVoltage;
    private static double frequency;
    private static double inductorCurrentMax;
    private static double inductorCurrentMin;
    private static double deltaInductorCurrent;
    private static double rippleInductorCurrent;
    private static double outputVoltageMax;
    private static double outputVoltageMin;
    private static double deltaCapacitorVoltage;
    private static double rippleCapacitorVoltage;
    private static double inductorCurrentRMS;
    private static double outputPower;
    private static boolean isCCM;
    private static int flag;

    public void retrieveDataFromReverseDesignActivity(Bundle data) {
        // Values
        dutyCycleIdeal = data.getDouble("Duty_Cycle_Ideal");
        dutyCycle = data.getDouble("Duty_Cycle");
        inductanceCritical = data.getDouble("Inductance_Crit");
        rippleInductorCurrent = data.getDouble("Rippleil");
        rippleCapacitorVoltage = data.getDouble("Ripplevc");
        outputPower = data.getDouble("Output_Power");
        inductance = data.getDouble("Inductance");
        capacitance = data.getDouble("Capacitance");
        resistance = data.getDouble("Resistance");

        isCCM = data.getBoolean("is_ccm");

        flag = data.getInt("Flag");

        // Advanced
        inputCurrent = data.getDouble("Input_Current");
        outputCurrent = data.getDouble("Output_Current");
        inductorCurrent = data.getDouble("Inductor_Current");
        switchCurrent = data.getDouble("Switch_Current");
        diodeCurrent = data.getDouble("Diode_Current");
        inputVoltage = data.getDouble("Input_Voltage");
        outputVoltage = data.getDouble("Output_Voltage");
        frequency = data.getDouble("Frequency");
        deltaInductorCurrent = data.getDouble("DeltaIL");
        deltaCapacitorVoltage = data.getDouble("DeltaVC");
        inductorCurrentRMS = data.getDouble("ILrms");
    }

    public double getDutyCycle() {
        return dutyCycle;
    }

    public double getDutyCycleIdeal() {
        return dutyCycleIdeal;
    }

    public double getResistance() {
        return resistance;
    }

    public double getCapacitance() {
        return capacitance;
    }

    public double getInductance() {
        return inductance;
    }

    public double getInductanceCritical() {
        return inductanceCritical;
    }

    public double getInputCurrent() {
        return inputCurrent;
    }

    public double getOutputCurrent() {
        return outputCurrent;
    }

    public double getInductorCurrent() {
        return inductorCurrent;
    }

    public double getSwitchCurrent() {
        return switchCurrent;
    }

    public double getDiodeCurrent() {
        return diodeCurrent;
    }

    public double getInputVoltage() {
        return inputVoltage;
    }

    public double getOutputVoltage() {
        return outputVoltage;
    }

    public double getFrequency() {
        return frequency;
    }

    public double getInductorCurrentMax() {
        return inductorCurrentMax;
    }

    public double getInductorCurrentMin() {
        return inductorCurrentMin;
    }

    public double getDeltaInductorCurrent() {
        return deltaInductorCurrent;
    }

    public double getRippleInductorCurrent() {
        return rippleInductorCurrent;
    }

    public double getOutputVoltageMax() {
        return outputVoltageMax;
    }

    public double getOutputVoltageMin() {
        return outputVoltageMin;
    }

    public double getDeltaCapacitorVoltage() {
        return deltaCapacitorVoltage;
    }

    public double getRippleCapacitorVoltage() {
        return rippleCapacitorVoltage;
    }

    public double getInductorCurrentRMS() {
        return inductorCurrentRMS;
    }

    public double getOutputPower() {
        return outputPower;
    }

    public boolean getIsCCM() {
        return isCCM;
    }

    public int getFlag() {
        return flag;
    }
}
