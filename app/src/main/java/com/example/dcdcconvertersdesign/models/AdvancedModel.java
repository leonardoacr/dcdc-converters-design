package com.example.dcdcconvertersdesign.models;

import android.os.Bundle;

import com.example.dcdcconvertersdesign.interfaces.models.AdvancedModelInterface;

public class AdvancedModel implements AdvancedModelInterface {
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

    public static final String INDUCTANCE_KEY = "Inductance";
    public static final String INDUCTANCE_CRITICAL_KEY = "Inductance_Critical";
    public static final String INPUT_CURRENT_KEY = "Input_Current";
    public static final String OUTPUT_CURRENT_KEY = "Output_Current";
    public static final String INDUCTOR_CURRENT_KEY = "Inductor_Current";
    public static final String SWITCH_CURRENT_KEY = "Switch_Current";
    public static final String DIODE_CURRENT_KEY = "Diode_Current";
    public static final String OUTPUT_VOLTAGE_KEY = "Output_Voltage";
    public static final String FREQUENCY_KEY = "Frequency";
    public static final String DELTA_INDUCTOR_CURRENT_KEY = "DeltaIL";
    public static final String DELTA_CAPACITOR_VOLTAGE_KEY = "DeltaVC";
    public static final String INDUCTOR_CURRENT_RMS_KEY = "Inductor_Current_RMS";
    public static final String IS_CCM_KEY = "is_ccm";
    public static final String FLAG_KEY = "Flag";
    public static final String REVERSE_FLAG_KEY = "Reverse_Flag";

    public void retrieveDataFromConverterActivity(Bundle bundle) {
        inductance = bundle.getDouble(INDUCTANCE_KEY);
        inductanceCritical = bundle.getDouble(INDUCTANCE_CRITICAL_KEY);
        inputCurrent = bundle.getDouble(INPUT_CURRENT_KEY);
        outputCurrent = bundle.getDouble(OUTPUT_CURRENT_KEY);
        inductorCurrent = bundle.getDouble(INDUCTOR_CURRENT_KEY);
        switchCurrent = bundle.getDouble(SWITCH_CURRENT_KEY);
        diodeCurrent = bundle.getDouble(DIODE_CURRENT_KEY);
        outputVoltage = bundle.getDouble(OUTPUT_VOLTAGE_KEY);
        frequency = bundle.getDouble(FREQUENCY_KEY);
        deltaInductorCurrent = bundle.getDouble(DELTA_INDUCTOR_CURRENT_KEY);
        deltaCapacitorVoltage = bundle.getDouble(DELTA_CAPACITOR_VOLTAGE_KEY);
        inductorCurrentRMS = bundle.getDouble(INDUCTOR_CURRENT_RMS_KEY);
        isCCM = bundle.getBoolean(IS_CCM_KEY);
        flag = bundle.getInt(FLAG_KEY);
        flagReverse = bundle.getInt(REVERSE_FLAG_KEY);
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
