package com.example.dcdcconvertersdesign.models;

import android.os.Bundle;

import com.example.dcdcconvertersdesign.interfaces.models.ConverterModelInterface;

public class ConverterModel implements ConverterModelInterface {
    private static double dutyCycle;
    private static double resistance;
    private static double capacitance;
    private static double inductance;
    private static boolean isCCM;
    private static int flag;

    public void retrieveDataFromUsualDesignActivity(Bundle bundle) {
        String DUTY_CYCLE_KEY = "Duty_Cycle";
        String RESISTANCE_KEY = "Resistance";
        String CAPACITANCE_KEY = "Capacitance";
        String INDUCTANCE_KEY = "Inductance";
        String IS_CCM_KEY = "is_ccm";
        String FLAG_KEY = "Flag";

        dutyCycle = bundle.getDouble(DUTY_CYCLE_KEY);
        resistance = bundle.getDouble(RESISTANCE_KEY);
        capacitance = bundle.getDouble(CAPACITANCE_KEY);
        inductance = bundle.getDouble(INDUCTANCE_KEY);
        isCCM = bundle.getBoolean(IS_CCM_KEY);
        flag = bundle.getInt(FLAG_KEY);
    }

    public double getDutyCycle() { return dutyCycle; }
    public double getResistance() {
        return resistance;
    }
    public double getCapacitance() {
        return capacitance;
    }
    public double getInductance() {
        return inductance;
    }
    public boolean getIsCCM() { return isCCM; }
    public int getFlag() {
        return flag;
    }
}
