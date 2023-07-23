package com.example.dcdcconvertersdesigndemo.models;

import android.os.Bundle;

import com.example.dcdcconvertersdesigndemo.interfaces.models.ConverterReverseModelInterface;
import com.example.dcdcconvertersdesigndemo.utils.convertersutils.ConverterData;

public class ConverterReverseModel implements ConverterReverseModelInterface {
    private static double dutyCycle;
    private static double dutyCycleIdeal;
    private static double resistance;
    private static double inductanceCritical;
    private static double inputCurrent;
    private static double outputCurrent;
    private static double inductorCurrent;
    private static double switchCurrent;
    private static double diodeCurrent;
    private static double outputPower;
    private static double inductorCurrentMax;
    private static double deltaInductorCurrent;
    private static double rippleInductorCurrent;
    private static double deltaCapacitorVoltage;
    private static double rippleCapacitorVoltage;
    private static double inductorCurrentRMS;
    private static boolean isCCM;
    private static int flag;

//    private static final String TAG = "CalculateReverse";
    public static ConverterData buckCalculations(double inputVoltage, double outputVoltage,
                                                 double resistance, double inductance, double capacitance,
                                                 double frequency, double efficiency) {
        // Pre-global-variables
        outputPower = Math.pow(outputVoltage, 2) / resistance;
        outputCurrent = outputVoltage / resistance;
        inputCurrent = outputPower / inputVoltage;
        inductorCurrent = outputCurrent;
        switchCurrent = inputCurrent;
        diodeCurrent =  outputCurrent - inputCurrent;

        isCCM = checkBuckConductionMode(inputVoltage, outputVoltage, frequency,
                inductance, efficiency);

        if (isCCM) {
            // CCM mode
            dutyCycleIdeal = outputVoltage / inputVoltage;
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            deltaInductorCurrent = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                    (frequency * inductance);
            rippleInductorCurrent = 100 * deltaInductorCurrent / outputCurrent;
            inductorCurrentRMS = outputCurrent * Math.sqrt(1 + ((1 / 12.0) *
                    Math.pow(deltaInductorCurrent / outputCurrent, 2)));
            deltaCapacitorVoltage = (inputVoltage * (1 - dutyCycle) * dutyCycle) / (8 * inductance * capacitance * Math.pow(frequency, 2));
            rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;
        } else {
            // DCM mode
            dutyCycleIdeal = 2 * outputPower / (deltaInductorCurrent * inputVoltage);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            deltaInductorCurrent = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                    (frequency * inductance);
            rippleInductorCurrent = 100 * deltaInductorCurrent / outputCurrent;
            deltaCapacitorVoltage = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                    (8 * inductance * capacitance * Math.pow(frequency, 2));
            rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;
            double dcm2 = inductorCurrentMax * frequency * inductance / outputVoltage;
            double dcm1 = 1 - dutyCycle - dcm2;
            inductorCurrentRMS = deltaInductorCurrent*Math.sqrt((dutyCycle + dcm1)/3);
        }

        inductanceCritical = dutyCycleIdeal * (inputVoltage - outputVoltage) * resistance / (2 * frequency * outputVoltage);

        // create a ConverterData object
        ConverterData data = new ConverterData();

        // set data using the setData() method
        data.setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, switchCurrent,
                diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
                inputVoltage, outputVoltage, frequency, outputPower, rippleInductorCurrent,
                rippleCapacitorVoltage, efficiency);

        // return the data object
        return data;
    }

    private static boolean checkBuckConductionMode(double inputVoltage, double outputVoltage,
                                                   double frequency, double inductance,
                                                   double efficiency) {
        dutyCycleIdeal = outputVoltage / inputVoltage;
        dutyCycle = dutyCycleIdeal / (efficiency / 100);
        inductanceCritical = dutyCycleIdeal * (inputVoltage - outputVoltage) * resistance / (2 * frequency * outputVoltage);
        return inductanceCritical <= inductance;
    }

    public static ConverterData boostCalculations(double inputVoltage, double outputVoltage,
                                                 double resistance, double inductance, double capacitance,
                                                 double frequency, double efficiency) {
        // Pre-global-variables
        outputPower = (Math.pow(outputVoltage, 2)) / resistance;
        outputCurrent = outputPower / outputVoltage;
        inputCurrent = outputPower / inputVoltage;
        inductorCurrent = inputCurrent;

        isCCM = checkBoostConductionMode(inputVoltage, outputVoltage, frequency,
                inductance, efficiency);

        if(isCCM) {
            dutyCycleIdeal = (outputVoltage - inputVoltage) / outputVoltage;
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            switchCurrent = dutyCycle * inputCurrent;
            diodeCurrent = (1 - dutyCycle) * inputCurrent;
            deltaInductorCurrent = (inputVoltage * dutyCycle) / (frequency * inductance);
            rippleInductorCurrent = 100 * deltaInductorCurrent / inputCurrent;
            inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
        } else {
            // DCM mode
            dutyCycleIdeal = (1 / inputVoltage) * Math.sqrt((2 * inductance * outputVoltage *
                    frequency * (outputVoltage - inputVoltage)) / resistance);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            deltaInductorCurrent = Math.sqrt((2 * outputVoltage * (outputVoltage - inputVoltage)) /
                    (resistance * frequency * inductance));
            rippleInductorCurrent = 100 * deltaInductorCurrent / inputCurrent;
            inductorCurrentMax = deltaInductorCurrent;
            deltaInductorCurrent = inductorCurrentMax;
            switchCurrent = dutyCycle * inputCurrent;
            diodeCurrent = (1 - dutyCycle) * inputCurrent;
            inductorCurrentRMS = inputCurrent * Math.sqrt((1 - dutyCycle) / 3);
        }

        inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        deltaCapacitorVoltage = (outputCurrent * dutyCycle) / (capacitance * frequency);
        rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;
//        double outputVoltageMax = outputVoltage + outputVoltage * (rippleCapacitorVoltage / 200);
//        double outputVoltageMin = outputVoltage - outputVoltage * (rippleCapacitorVoltage / 200);

        // create a ConverterData object
        ConverterData data = new ConverterData();

        // set data using the setData() method
        data.setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, switchCurrent,
                diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
                inputVoltage, outputVoltage, frequency, outputPower, rippleInductorCurrent,
                rippleCapacitorVoltage, efficiency);

        // return the data object
        return data;
    }

    private static boolean checkBoostConductionMode(double inputVoltage, double outputVoltage,
                                                   double frequency, double inductance,
                                                   double efficiency) {
        dutyCycleIdeal = (outputVoltage - inputVoltage) / outputVoltage;
        dutyCycle = dutyCycleIdeal / (efficiency / 100);
        inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        return inductanceCritical <= inductance;
    }

    public static ConverterData buckBoostCalculations(double inputVoltage, double outputVoltage,
                                                 double resistance, double inductance, double capacitance,
                                                 double frequency, double efficiency) {
        // Pre-global-variables
        outputPower = (Math.pow(outputVoltage, 2)) / resistance;
        outputCurrent = outputVoltage / resistance;
        inputCurrent = outputPower / inputVoltage;
        switchCurrent = inputCurrent;
        diodeCurrent = outputCurrent;
        inductorCurrent = outputCurrent + inputCurrent;

        isCCM = checkBuckBoostConductionMode(inputVoltage, outputVoltage, frequency,
                inductance, efficiency, inductorCurrent);

        if(isCCM) {
            // CCM Mode
            dutyCycleIdeal = (outputVoltage) / (inputVoltage + outputVoltage);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            deltaInductorCurrent = (inputVoltage * dutyCycle) / (frequency * inductance);
            rippleInductorCurrent = (100 * deltaInductorCurrent / inductorCurrent);
            inductorCurrentMax = inductorCurrent + inductorCurrent * (rippleInductorCurrent / 200);
            inductorCurrentRMS = Math.sqrt(Math.pow(inductorCurrentMax, 2) +
                    Math.pow(deltaInductorCurrent / 12, 2));
            inductanceCritical = inputVoltage * dutyCycle  / (frequency * inductorCurrent * 2);
        } else {
            // DCM Mode
            deltaInductorCurrent = Math.sqrt(2 * outputCurrent * outputVoltage /
                    (inductance * frequency));
            inductorCurrentMax= deltaInductorCurrent;
            rippleInductorCurrent = 100 * inductorCurrentMax / inductorCurrent;
            dutyCycleIdeal = (outputVoltage / inputVoltage) *
                    Math.sqrt((2 * inductance * frequency) / resistance);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            inductanceCritical = 2 * outputCurrent * outputVoltage /
                    (Math.pow(2 * inductorCurrent, 2) * frequency);
        }

        deltaCapacitorVoltage =  (outputCurrent * dutyCycle) / (capacitance * frequency);
        rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;

        // create a ConverterData object
        ConverterData data = new ConverterData();

        // set data using the setData() method
        data.setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, switchCurrent,
                diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
                inputVoltage, outputVoltage, frequency, outputPower, rippleInductorCurrent,
                rippleCapacitorVoltage, efficiency);

        // return the data object
        return data;
    }

    private static boolean checkBuckBoostConductionMode(double inputVoltage, double outputVoltage,
                                                   double frequency, double inductance,
                                                   double efficiency, double inductorCurrent) {
        dutyCycleIdeal = (outputVoltage) / (inputVoltage + outputVoltage);
        dutyCycle = ((outputVoltage) / (inputVoltage + outputVoltage)) / (efficiency / 100);
        inductanceCritical = inputVoltage * dutyCycle  / (frequency * inductorCurrent * 2);
        return inductanceCritical <= inductance;
    }

    public void retrieveDataFromReverseDesignActivity(Bundle data) {
        String DUTY_CYCLE_KEY = "Duty_Cycle";
        String DUTY_CYCLE_IDEAL_KEY = "Duty_Cycle_Ideal";
        String RESISTANCE_KEY = "Resistance";
        String INDUCTANCE_CRITICAL_KEY = "Inductance_Critical";
        String INPUT_CURRENT_KEY = "Input_Current";
        String OUTPUT_CURRENT_KEY = "Output_Current";
        String INDUCTOR_CURRENT_KEY = "Inductor_Current";
        String SWITCH_CURRENT_KEY = "Switch_Current";
        String DIODE_CURRENT_KEY = "Diode_Current";
        String OUTPUT_POWER_KEY = "Output_Power";
        String DELTA_INDUCTOR_CURRENT_KEY = "DeltaIL";
        String RIPPLE_INDUCTOR_CURRENT_KEY = "RippleIL";
        String DELTA_CAPACITOR_VOLTAGE_KEY = "DeltaVC";
        String RIPPLE_CAPACITOR_VOLTAGE_KEY = "RippleVC";
        String INDUCTOR_CURRENT_RMS_KEY = "Inductor_Current_RMS";
        String IS_CCM_KEY = "is_ccm";
        String FLAG_KEY = "Flag";

        dutyCycleIdeal = data.getDouble(DUTY_CYCLE_IDEAL_KEY);
        dutyCycle = data.getDouble(DUTY_CYCLE_KEY);
        inductanceCritical = data.getDouble(INDUCTANCE_CRITICAL_KEY);
        rippleInductorCurrent = data.getDouble(RIPPLE_INDUCTOR_CURRENT_KEY);
        rippleCapacitorVoltage = data.getDouble(RIPPLE_CAPACITOR_VOLTAGE_KEY);
        outputPower = data.getDouble(OUTPUT_POWER_KEY);
        resistance = data.getDouble(RESISTANCE_KEY);
        isCCM = data.getBoolean(IS_CCM_KEY);
        flag = data.getInt(FLAG_KEY);
        inputCurrent = data.getDouble(INPUT_CURRENT_KEY);
        outputCurrent = data.getDouble(OUTPUT_CURRENT_KEY);
        inductorCurrent = data.getDouble(INDUCTOR_CURRENT_KEY);
        switchCurrent = data.getDouble(SWITCH_CURRENT_KEY);
        diodeCurrent = data.getDouble(DIODE_CURRENT_KEY);
        deltaInductorCurrent = data.getDouble(DELTA_INDUCTOR_CURRENT_KEY);
        deltaCapacitorVoltage = data.getDouble(DELTA_CAPACITOR_VOLTAGE_KEY);
        inductorCurrentRMS = data.getDouble(INDUCTOR_CURRENT_RMS_KEY);
    }

    public double getDutyCycle() {
        return dutyCycle;
    }
    public double getRippleInductorCurrent() {
        return rippleInductorCurrent;
    }
    public double getRippleCapacitorVoltage() {
        return rippleCapacitorVoltage;
    }
    public double getOutputPower() { return outputPower; }
    public boolean getIsCCM() {
        return isCCM;
    }
    public int getFlag() { return flag; }
}
