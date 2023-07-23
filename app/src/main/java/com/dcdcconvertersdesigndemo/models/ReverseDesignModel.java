package com.example.dcdcconvertersdesigndemo.models;

import android.os.Bundle;

import com.example.dcdcconvertersdesigndemo.interfaces.models.ReverseDesignModelInterface;
import com.example.dcdcconvertersdesigndemo.utils.convertersutils.ConverterData;

public class ReverseDesignModel implements ReverseDesignModelInterface {
    public Bundle sendDataToConverterReverseActivity(ConverterData converterData){
        String DUTY_CYCLE_KEY = "Duty_Cycle";
        String DUTY_CYCLE_IDEAL_KEY = "Duty_Cycle_Ideal";
        String RESISTANCE_KEY = "Resistance";
        String CAPACITANCE_KEY = "Capacitance";
        String INDUCTANCE_KEY = "Inductance";
        String INDUCTANCE_CRITICAL_KEY = "Inductance_Critical";
        String INPUT_CURRENT_KEY = "Input_Current";
        String OUTPUT_CURRENT_KEY = "Output_Current";
        String INDUCTOR_CURRENT_KEY = "Inductor_Current";
        String SWITCH_CURRENT_KEY = "Switch_Current";
        String DIODE_CURRENT_KEY = "Diode_Current";
        String INPUT_VOLTAGE_KEY = "Input_Voltage";
        String OUTPUT_VOLTAGE_KEY = "Output_Voltage";
        String OUTPUT_POWER_KEY = "Output_Power";
        String FREQUENCY_KEY = "Frequency";
        String DELTA_INDUCTOR_CURRENT_KEY = "DeltaIL";
        String RIPPLE_INDUCTOR_CURRENT_KEY = "RippleIL";
        String DELTA_CAPACITOR_VOLTAGE_KEY = "DeltaVC";
        String RIPPLE_CAPACITOR_VOLTAGE_KEY = "RippleVC";
        String INDUCTOR_CURRENT_RMS_KEY = "Inductor_Current_RMS";
        String IS_CCM_KEY = "is_ccm";
        String FLAG_KEY = "Flag";

        Bundle bundle = new Bundle();

        double dutyCycle = converterData.getDutyCycle();
        double dutyCycleIdeal = converterData.getDutyCycleIdeal();
        double resistance = converterData.getResistance();
        double capacitance = converterData.getCapacitance();
        double inductance = converterData.getInductance();
        double inductanceCritical = converterData.getInductanceCritical();
        double rippleCapacitorVoltage = converterData.getRippleCapacitorVoltage();
        double rippleInductorCurrent = converterData.getRippleInductorCurrent();
        double outputPower = converterData.getOutputPower();
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
        bundle.putDouble(INDUCTANCE_CRITICAL_KEY, inductanceCritical);
        bundle.putDouble(RIPPLE_CAPACITOR_VOLTAGE_KEY, rippleCapacitorVoltage);
        bundle.putDouble(RIPPLE_INDUCTOR_CURRENT_KEY, rippleInductorCurrent);
        bundle.putDouble(OUTPUT_POWER_KEY, outputPower);
        bundle.putDouble(INDUCTANCE_KEY, inductance);
        bundle.putDouble(RESISTANCE_KEY, resistance);
        bundle.putDouble(CAPACITANCE_KEY, capacitance);
        bundle.putBoolean(IS_CCM_KEY, isCCM);
        bundle.putInt(FLAG_KEY, flag);
        bundle.putDouble(INPUT_CURRENT_KEY, inputCurrent);
        bundle.putDouble(OUTPUT_CURRENT_KEY, outputCurrent);
        bundle.putDouble(INDUCTOR_CURRENT_KEY, inductorCurrent);
        bundle.putDouble(SWITCH_CURRENT_KEY, switchCurrent);
        bundle.putDouble(DIODE_CURRENT_KEY, diodeCurrent);
        bundle.putDouble(INPUT_VOLTAGE_KEY, inputVoltage);
        bundle.putDouble(OUTPUT_VOLTAGE_KEY, outputVoltage);
        bundle.putDouble(FREQUENCY_KEY, frequency);
        bundle.putDouble(DELTA_INDUCTOR_CURRENT_KEY, deltaInductorCurrent);
        bundle.putDouble(DELTA_CAPACITOR_VOLTAGE_KEY, deltaCapacitorVoltage);
        bundle.putDouble(INDUCTOR_CURRENT_RMS_KEY, inductorCurrentRMS);

        return bundle;
    }
}
