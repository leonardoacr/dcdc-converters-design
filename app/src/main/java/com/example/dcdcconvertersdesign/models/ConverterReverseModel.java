package com.example.dcdcconvertersdesign.models;

import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.utils.convertersutils.ConverterData;

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
    private static double outputPower;
    private static double frequency;
    private static double efficiency;
    private static double inductorCurrentMax;
    private static double inductorCurrentMin;
    private static double deltaInductorCurrent;
    private static double rippleInductorCurrent;
    private static double outputVoltageMax;
    private static double outputVoltageMin;
    private static double deltaCapacitorVoltage;
    private static double rippleCapacitorVoltage;
    private static double inductorCurrentRMS;
    private static boolean isCCM;
    private static int flag;

    private static final String TAG = "CalculateReverse";
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
        Log.d(TAG, "Hello?? " + isCCM);

        if (isCCM) {
            // CCM mode
            dutyCycleIdeal = outputVoltage / inputVoltage;
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            deltaInductorCurrent = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                    (frequency * inductance);
            rippleInductorCurrent = 100 * deltaInductorCurrent / outputCurrent;
            inductorCurrentRMS = outputCurrent * Math.sqrt(1 + ((1 / 12.0) *
                    Math.pow(deltaInductorCurrent / outputCurrent, 2)));
            deltaCapacitorVoltage = (inputVoltage * (1 - dutyCycle) * dutyCycle) / (16 * inductance * capacitance * Math.pow(frequency, 2));
            rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;
        } else {
            // DCM mode
            dutyCycleIdeal = 2 * outputPower / (deltaInductorCurrent * inputVoltage);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            deltaInductorCurrent = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                    (frequency * inductance);
            rippleInductorCurrent = 100 * deltaInductorCurrent / outputCurrent;
            deltaCapacitorVoltage = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                    (16 * inductance * capacitance * Math.pow(frequency, 2));
            rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;
            double dcm2 = inductorCurrentMax * frequency * inductance / outputVoltage;
            double dcm1 = 1 - dutyCycle - dcm2;
            inductorCurrentRMS = deltaInductorCurrent*Math.sqrt((dutyCycle + dcm1)/3);
        }

        inductanceCritical = dutyCycleIdeal * (inputVoltage - outputVoltage) * resistance / (2 * frequency * outputVoltage);

        Log.d(TAG, "Check variables after the MODE: " +
                "Duty Cycle Ideal = " + dutyCycleIdeal +
                ", Duty Cycle = " + dutyCycle +
                ", Output Current = " + outputCurrent +
                ", Input Current = " + inputCurrent +
                ", Switch Current = " + switchCurrent +
                ", Diode Current = " + diodeCurrent +
                ", Max Inductor Current = " + inductorCurrentMax +
                ", Min Inductor Current = " + inductorCurrentMin +
                ", Delta Inductor Current = " + deltaInductorCurrent +
                ", Delta Capacitor Voltage = " + deltaCapacitorVoltage +
                ", Inductance = " + inductance +
                ", Inductor Current RMS = " + inductorCurrentRMS +
                ", Resistance = " + resistance +
                ", Inductance = " + inductance +
                ", Capacitance = " + capacitance);

        // create a ConverterData object
        ConverterData data = new ConverterData();

        // set data using the setData() method
        data.setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, switchCurrent,
                diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
                inputVoltage, outputVoltage, frequency, outputPower, rippleInductorCurrent,
                rippleCapacitorVoltage);

        Log.d("CalculateVariables", isCCM + " " + inductanceCritical + " " + inductance + " " + dutyCycle);

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

        Log.d(TAG, "Check variables before MODE: " +
                "Resistance = " + resistance +
                ", Output Current = " + outputCurrent +
                ", Input Current = " + inputCurrent +
                ", Inductor Current = " + inductorCurrent +
                ", Max Output Voltage = " + outputVoltageMax +
                ", Min Output Voltage = " + outputVoltageMin +
                ", Delta Capacitor Voltage = " + deltaCapacitorVoltage);

        isCCM = checkBoostConductionMode(inputVoltage, outputVoltage, frequency,
                inductance, efficiency);
        Log.d(TAG, isCCM + " " +
                inductanceCritical + " " + inductance + " " + dutyCycle);

        if(isCCM) {
            dutyCycleIdeal = (outputVoltage - inputVoltage) / outputVoltage;
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            switchCurrent = dutyCycle * inputCurrent;
            diodeCurrent = (1 - dutyCycle) * inputCurrent;
            deltaInductorCurrent = (inputVoltage * dutyCycle) / (frequency * inductance);
            rippleInductorCurrent = 100 * deltaInductorCurrent / inputCurrent;
            inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = inputCurrent - inputCurrent * (rippleInductorCurrent / 200);
        } else {
            // DCM mode
            dutyCycleIdeal = (1 / inputVoltage) * Math.sqrt((2 * inductance * outputVoltage *
                    frequency * (outputVoltage - inputVoltage)) / resistance);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            deltaInductorCurrent = Math.sqrt((2 * outputVoltage * (outputVoltage - inputVoltage)) /
                    (resistance * frequency * inductance));
            rippleInductorCurrent = 100 * deltaInductorCurrent / inputCurrent;
            inductorCurrentMax = deltaInductorCurrent;
            inductorCurrentMin = 0;
            deltaInductorCurrent = inductorCurrentMax;
            switchCurrent = dutyCycle * inputCurrent;
            diodeCurrent = (1 - dutyCycle) * inputCurrent;
            inductorCurrentRMS = inputCurrent * Math.sqrt((1 - dutyCycle) / 3);
        }

        inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        deltaCapacitorVoltage = (outputCurrent * dutyCycle) / (capacitance * frequency);
        rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;
        outputVoltageMax = outputVoltage + outputVoltage * (rippleCapacitorVoltage / 200);
        outputVoltageMin = outputVoltage - outputVoltage * (rippleCapacitorVoltage / 200);

        Log.d(TAG, "Check variables after the MODE: " +
                "Duty Cycle Ideal = " + dutyCycleIdeal +
                ", Duty Cycle = " + dutyCycle +
                ", Output Current = " + outputCurrent +
                ", Input Current = " + inputCurrent +
                ", Switch Current = " + switchCurrent +
                ", Diode Current = " + diodeCurrent +
                ", Max Inductor Current = " + inductorCurrentMax +
                ", Min Inductor Current = " + inductorCurrentMin +
                ", Delta Inductor Current = " + deltaInductorCurrent +
                ", Max Output Voltage = " + outputVoltageMax +
                ", Min Output Voltage = " + outputVoltageMin +
                ", Delta Capacitor Voltage = " + deltaCapacitorVoltage +
                ", Inductance = " + inductance +
                ", Inductor Current RMS = " + inductorCurrentRMS +
                ", Resistance = " + resistance +
                ", Inductance = " + inductance +
                ", Capacitance = " + capacitance);

        // create a ConverterData object
        ConverterData data = new ConverterData();

        // set data using the setData() method
        data.setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, switchCurrent,
                diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
                inputVoltage, outputVoltage, frequency, outputPower, rippleInductorCurrent,
                rippleCapacitorVoltage);

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

        Log.d(TAG, "Check variables before MODE: " +
                "Output Power = " + outputPower +
                ", Output Voltage = " + outputVoltage +
                ", Output Current = " + outputCurrent +
                ", Input Voltage = " + inputVoltage +
                ", Input Current = " + inputCurrent +
                ", Switch Current = " + switchCurrent +
                ", Diode Current = " + diodeCurrent +
                ", Inductor Current = " + inductorCurrent);

        isCCM = checkBuckBoostConductionMode(inputVoltage, outputVoltage, frequency,
                inductance, efficiency, inductorCurrent);
        Log.d(TAG, isCCM + " " +
                inductanceCritical + " " + inductance + " " + dutyCycle);

        if(isCCM) {
            // CCM Mode
            dutyCycleIdeal = (outputVoltage) / (inputVoltage + outputVoltage);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            deltaInductorCurrent = (inputVoltage * dutyCycle) / (frequency * inductance);
            rippleInductorCurrent = (100 * deltaInductorCurrent / inductorCurrent);
            inductorCurrentMax = inductorCurrent + inductorCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = inductorCurrent - inductorCurrent * (rippleInductorCurrent / 200);
            inductorCurrentRMS = Math.sqrt(Math.pow(inductorCurrentMax, 2) +
                    Math.pow(deltaInductorCurrent / 12, 2));
            inductanceCritical = inputVoltage * dutyCycle  / (frequency * inductorCurrent * 2);
        } else {
            // DCM Mode
            deltaInductorCurrent = Math.sqrt(2 * outputCurrent * outputVoltage /
                    (inductance * frequency));
            inductorCurrentMax= deltaInductorCurrent;
            inductorCurrentMin = 0;
            rippleInductorCurrent = 100 * inductorCurrentMax / inductorCurrent;
            dutyCycleIdeal = (outputVoltage / inputVoltage) *
                    Math.sqrt((2 * inductance * frequency) / resistance);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            inductanceCritical = 2 * outputCurrent * outputVoltage /
                    (Math.pow(2 * inductorCurrent, 2) * frequency);
        }

        deltaCapacitorVoltage =  (outputCurrent * dutyCycle) / (capacitance * frequency);
        rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;

        Log.d(TAG, "Check variables after the MODE: " +
                "Duty Cycle Ideal = " + dutyCycleIdeal +
                ", Duty Cycle = " + dutyCycle +
                ", Output Current = " + outputCurrent +
                ", Input Current = " + inputCurrent +
                ", Switch Current = " + switchCurrent +
                ", Diode Current = " + diodeCurrent +
                ", Max Inductor Current = " + inductorCurrentMax +
                ", Min Inductor Current = " + inductorCurrentMin +
                ", Delta Inductor Current = " + deltaInductorCurrent +
                ", Delta Capacitor Voltage = " + deltaCapacitorVoltage +
                ", Inductance = " + inductance +
                ", Inductor Current RMS = " + inductorCurrentRMS +
                ", Resistance = " + resistance +
                ", Inductance = " + inductance +
                ", Capacitance = " + capacitance);

        // create a ConverterData object
        ConverterData data = new ConverterData();

        // set data using the setData() method
        data.setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, switchCurrent,
                diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
                inputVoltage, outputVoltage, frequency, outputPower, rippleInductorCurrent,
                rippleCapacitorVoltage);

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
        String CAPACITANCE_KEY = "Capacitance";
        String INDUCTANCE_KEY = "Inductance";
        String INDUCTANCE_CRITICAL_KEY = "Inductance_Crit";
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

        dutyCycleIdeal = data.getDouble(DUTY_CYCLE_IDEAL_KEY);
        dutyCycle = data.getDouble(DUTY_CYCLE_KEY);
        inductanceCritical = data.getDouble(INDUCTANCE_CRITICAL_KEY);
        rippleInductorCurrent = data.getDouble(RIPPLE_INDUCTOR_CURRENT_KEY);
        rippleCapacitorVoltage = data.getDouble(RIPPLE_CAPACITOR_VOLTAGE_KEY);
        outputPower = data.getDouble(OUTPUT_POWER_KEY);
        inductance = data.getDouble(INDUCTANCE_KEY);
        capacitance = data.getDouble(CAPACITANCE_KEY);
        resistance = data.getDouble(RESISTANCE_KEY);
        isCCM = data.getBoolean(IS_CCM_KEY);
        flag = data.getInt(FLAG_KEY);
        inputCurrent = data.getDouble(INPUT_CURRENT_KEY);
        outputCurrent = data.getDouble(OUTPUT_CURRENT_KEY);
        inductorCurrent = data.getDouble(INDUCTOR_CURRENT_KEY);
        switchCurrent = data.getDouble(SWITCH_CURRENT_KEY);
        diodeCurrent = data.getDouble(DIODE_CURRENT_KEY);
        inputVoltage = data.getDouble(INPUT_VOLTAGE_KEY);
        outputVoltage = data.getDouble(OUTPUT_VOLTAGE_KEY);
        frequency = data.getDouble(FREQUENCY_KEY);
        deltaInductorCurrent = data.getDouble(DELTA_INDUCTOR_CURRENT_KEY);
        deltaCapacitorVoltage = data.getDouble(DELTA_CAPACITOR_VOLTAGE_KEY);
        inductorCurrentRMS = data.getDouble(INDUCTOR_CURRENT_RMS_KEY);
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
