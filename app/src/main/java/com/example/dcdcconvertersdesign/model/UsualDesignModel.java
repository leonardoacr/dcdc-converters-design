package com.example.dcdcconvertersdesign.model;

import android.os.Bundle;

import com.example.dcdcconvertersdesign.convertersutils.ConverterData;

public class UsualDesignModel {
    public Bundle setBundleVariables(ConverterData converterData){
        Bundle bundle = new Bundle();
        double dutyCycle = converterData.getDutyCycle();
        double dutyCycleIdeal = converterData.getDutyCycleIdeal();
        double resistance = converterData.getResistance();
        double capacitance = converterData.getCapacitance();
        double inductance = converterData.getInductance();
        double inductanceCritical = converterData.getInductanceCritical();
        double inputCurrent = converterData.getInputCurrent();
        double outputCurrent = converterData.getOutputCurrent();
        double inductorCurrent = converterData.getInductorCurrent();
        double switchCurrent = converterData.getSwitchCurrent();
        double diodeCurrent = converterData.getDiodeCurrent();
        double deltaInductorCurrent = converterData.getDeltaInductorCurrent();
        double deltaCapacitorVoltage = converterData.getDeltaCapacitorVoltage();
        double inductorCurrentRMS = converterData.getInductorCurrentRMS();
        boolean isCCM = converterData.getIsCCM();
        double inputVoltage = converterData.getInputVoltage();
        double outputVoltage = converterData.getOutputVoltage();
        double frequency = converterData.getFrequency();
        int flag = converterData.getFlag();

        bundle.putDouble("Duty_Cycle", dutyCycle);
        bundle.putDouble("Duty_Cycle_Ideal", dutyCycleIdeal);
        bundle.putDouble("Resistance", resistance);
        bundle.putDouble("Capacitance", capacitance);
        bundle.putDouble("Inductance", inductance);
        bundle.putDouble("Inductance_Crit", inductanceCritical);
        bundle.putDouble("Input_Current", inputCurrent);
        bundle.putDouble("Output_Current", outputCurrent);
        bundle.putDouble("Inductor_Current", inductorCurrent);
        bundle.putDouble("Switch_Current", switchCurrent);
        bundle.putDouble("Diode_Current", diodeCurrent);
        bundle.putDouble("Input_Voltage", inputVoltage);
        bundle.putDouble("Output_Voltage", outputVoltage);
        bundle.putDouble("Frequency", frequency);
        bundle.putDouble("DeltaIL", deltaInductorCurrent);
        bundle.putDouble("DeltaVC", deltaCapacitorVoltage);
        bundle.putDouble("ILrms", inductorCurrentRMS);
        bundle.putBoolean("is_ccm", isCCM);
        bundle.putInt("Flag", flag);
        return bundle;
    }
}
