package com.example.dcdcconvertersdesign.model;

import android.os.Bundle;

public class AdvancedModel {
    private double inductance;
    private double inductanceCritical;
    private double inputCurrent;
    private double outputCurrent;
    private double inductorCurrent;
    private double switchCurrent;
    private double diodeCurrent;
    private double outputVoltage;
    private double frequency;
    private double deltaInductorCurrent;
    private double deltaCapacitorVoltage;
    private double inductorCurrentRMS;
    private boolean isCCM;
    private int flag;
    private int flagReverse;
    public void retrieveDataFromConverterActivity(Bundle bundle) {
        inductance = bundle.getDouble("Inductance");
        inductanceCritical = bundle.getDouble("Inductance_Crit");
        inputCurrent = bundle.getDouble("Input_Current");
        outputCurrent = bundle.getDouble("Output_Current");
        inductorCurrent = bundle.getDouble("Inductor_Current");
        switchCurrent = bundle.getDouble("Switch_Current");
        diodeCurrent = bundle.getDouble("Diode_Current");
        outputVoltage = bundle.getDouble("Output_Voltage");
        frequency = bundle.getDouble("Frequency");
        deltaInductorCurrent = bundle.getDouble("DeltaIL");
        deltaCapacitorVoltage = bundle.getDouble("DeltaVC");
        inductorCurrentRMS = bundle.getDouble("ILrms");
        isCCM = bundle.getBoolean("is_ccm");
        flag = bundle.getInt("Flag");
        flagReverse = bundle.getInt("Reverse_Flag");
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

    public double getOutputVoltage() {
        return outputVoltage;
    }

    public double getFrequency() {
        return frequency;
    }

    public double getDeltaInductorCurrent() { return deltaInductorCurrent; }

    public double getDeltaCapacitorVoltage() {
        return deltaCapacitorVoltage;
    }

    public double getInductorCurrentRMS() {
        return inductorCurrentRMS;
    }

    public boolean getIsCCM() {
        return isCCM;
    }

    public int getFlag() { return flag; }

    public int getFlagReverse() { return flagReverse; }
}
