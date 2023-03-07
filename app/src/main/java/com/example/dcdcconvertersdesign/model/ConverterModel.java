package com.example.dcdcconvertersdesign.model;

import android.os.Bundle;

public class ConverterModel {
    private double dutyCycle;
    private double dutyCycleIdeal;
    private double resistance;
    private double capacitance;
    private double inductance;
    private double inductanceCritical;
    private double inputCurrent;
    private double outputCurrent;
    private double inductorCurrent;
    private double switchCurrent;
    private double diodeCurrent;
    private double inputVoltage;
    private double outputVoltage;
    private double frequency;
    private double deltaInductorCurrent;
    private double rippleCapacitorVoltage;
    private double inductorCurrentRMS;
    private boolean isCCM;
    private int flag;

    public void getBundleVariables(Bundle bundle) {
        dutyCycle = bundle.getDouble("Duty_Cycle");
        dutyCycleIdeal = bundle.getDouble("Duty_Cycle_Ideal");
        resistance = bundle.getDouble("Resistance");
        capacitance = bundle.getDouble("Capacitance");
        inductance = bundle.getDouble("Inductance");
        inductanceCritical = bundle.getDouble("Inductance_Crit");
        inputCurrent = bundle.getDouble("Input_Current");
        outputCurrent = bundle.getDouble("Output_Current");
        inductorCurrent = bundle.getDouble("Inductor_Current");
        switchCurrent = bundle.getDouble("Switch_Current");
        diodeCurrent = bundle.getDouble("Diode_Current");
        inputVoltage = bundle.getDouble("Input_Voltage");
        outputVoltage = bundle.getDouble("Output_Voltage");
        frequency = bundle.getDouble("Frequency");
        deltaInductorCurrent = bundle.getDouble("DeltaIL");
        rippleCapacitorVoltage = bundle.getDouble("DeltaVC");
        inductorCurrentRMS = bundle.getDouble("ILrms");
        isCCM = bundle.getBoolean("is_ccm");
        flag = bundle.getInt("Flag");
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

    public double getDeltaInductorCurrent() {
        return deltaInductorCurrent;
    }

    public double getRippleCapacitorVoltage() {
        return rippleCapacitorVoltage;
    }

    public double getInductorCurrentRMS() {
        return inductorCurrentRMS;
    }

    public boolean getIsCCM() {
        return isCCM;
    }

    public int getFlag() {
        return flag;
    }
}
