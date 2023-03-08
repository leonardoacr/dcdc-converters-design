package com.example.dcdcconvertersdesign.model;

import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.convertersutils.ConverterData;

public class UsualDesignModel {
    public static final String DUTY_CYCLE_KEY = "Duty_Cycle";
    public static final String DUTY_CYCLE_IDEAL_KEY = "Duty_Cycle_Ideal";
    public static final String RESISTANCE_KEY = "Resistance";
    public static final String CAPACITANCE_KEY = "Capacitance";
    public static final String INDUCTANCE_KEY = "Inductance";
    public static final String INDUCTANCE_CRIT_KEY = "Inductance_Crit";
    public static final String INPUT_CURRENT_KEY = "Input_Current";
    public static final String OUTPUT_CURRENT_KEY = "Output_Current";
    public static final String INDUCTOR_CURRENT_KEY = "Inductor_Current";
    public static final String SWITCH_CURRENT_KEY = "Switch_Current";
    public static final String DIODE_CURRENT_KEY = "Diode_Current";
    public static final String INPUT_VOLTAGE_KEY = "Input_Voltage";
    public static final String OUTPUT_VOLTAGE_KEY = "Output_Voltage";
    public static final String FREQUENCY_KEY = "Frequency";
    public static final String DELTA_IL_KEY = "DeltaIL";
    public static final String DELTA_VC_KEY = "DeltaVC";
    public static final String INDUCTOR_CURRENT_RMS_KEY = "Inductor_Current_RMS";
    public static final String IS_CCM_KEY = "is_ccm";
    public static final String FLAG_KEY = "Flag";

    private String TAG = "UsualDesignModel";
    public Bundle sendDataToConverterActivity(ConverterData converterData){
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

        bundle.putDouble(DUTY_CYCLE_KEY, dutyCycle);
        bundle.putDouble(DUTY_CYCLE_IDEAL_KEY, dutyCycleIdeal);
        bundle.putDouble(RESISTANCE_KEY, resistance);
        bundle.putDouble(CAPACITANCE_KEY, capacitance);
        bundle.putDouble(INDUCTANCE_KEY, inductance);
        bundle.putDouble(INDUCTANCE_CRIT_KEY, inductanceCritical);
        bundle.putDouble(INPUT_CURRENT_KEY, inputCurrent);
        bundle.putDouble(OUTPUT_CURRENT_KEY, outputCurrent);
        bundle.putDouble(INDUCTOR_CURRENT_KEY, inductorCurrent);
        bundle.putDouble(SWITCH_CURRENT_KEY, switchCurrent);
        bundle.putDouble(DIODE_CURRENT_KEY, diodeCurrent);
        bundle.putDouble(INPUT_VOLTAGE_KEY, inputVoltage);
        bundle.putDouble(OUTPUT_VOLTAGE_KEY, outputVoltage);
        bundle.putDouble(FREQUENCY_KEY, frequency);
        bundle.putDouble(DELTA_IL_KEY, deltaInductorCurrent);
        bundle.putDouble(DELTA_VC_KEY, deltaCapacitorVoltage);
        bundle.putDouble(INDUCTOR_CURRENT_RMS_KEY, inductorCurrentRMS);
        bundle.putBoolean(IS_CCM_KEY, isCCM);
        bundle.putInt(FLAG_KEY, flag);
        return bundle;
    }
}
