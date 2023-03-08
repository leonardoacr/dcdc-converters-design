package com.example.dcdcconvertersdesign.model;

import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.convertersutils.ConverterData;

public class ConverterModel {
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
    private static double outputVoltageMax;
    private static double outputVoltageMin;
    private static double deltaCapacitorVoltage;
    private static double rippleCapacitorVoltage;
    private static double inductorCurrentRMS;
    private static boolean isCCM;
    private static int flag;

    private final String DUTY_CYCLE_KEY = "Duty_Cycle";
    private final String DUTY_CYCLE_IDEAL_KEY = "Duty_Cycle_Ideal";
    private final String RESISTANCE_KEY = "Resistance";
    private final String CAPACITANCE_KEY = "Capacitance";
    private final String INDUCTANCE_KEY = "Inductance";
    private final String INDUCTANCE_CRITICAL_KEY = "Inductance_Crit";
    private final String INPUT_CURRENT_KEY = "Input_Current";
    private final String OUTPUT_CURRENT_KEY = "Output_Current";
    private final String INDUCTOR_CURRENT_KEY = "Inductor_Current";
    private final String SWITCH_CURRENT_KEY = "Switch_Current";
    private final String DIODE_CURRENT_KEY = "Diode_Current";
    private final String INPUT_VOLTAGE_KEY = "Input_Voltage";
    private final String OUTPUT_VOLTAGE_KEY = "Output_Voltage";
    private final String FREQUENCY_KEY = "Frequency";
    private final String DELTA_INDUCTOR_CURRENT_KEY = "DeltaIL";
    private final String DELTA_CAPACITOR_VOLTAGE_KEY = "DeltaVC";
    private final String INDUCTOR_CURRENT_RMS_KEY = "Inductor_Current_RMS";
    private final String IS_CCM_KEY = "is_ccm";
    private final String FLAG_KEY = "Flag";
    public static ConverterData buckCalculations(double inputVoltage, double outputVoltage, double outputPower,
                                                 double rippleInductorCurrent, double rippleCapacitorVoltage,
                                                 double frequency, double efficiency) {
        // Pre-global-variables
        resistance = Math.pow(outputVoltage, 2) / outputPower;
        outputCurrent = outputPower / outputVoltage;
        inputCurrent = outputPower / inputVoltage;
        inductorCurrent = outputCurrent;
        switchCurrent = inputCurrent;
        diodeCurrent = outputCurrent - inputCurrent;
        outputVoltageMax = outputVoltage + outputVoltage * rippleCapacitorVoltage / 200.0;
        outputVoltageMin = outputVoltage - outputVoltage * rippleCapacitorVoltage / 200.0;
        deltaCapacitorVoltage = outputVoltageMax - outputVoltageMin;

        isCCM = checkBuckConductionMode(outputVoltage, inputVoltage, efficiency,
                rippleInductorCurrent, frequency);

        if (isCCM) {
            // CCM mode
            dutyCycleIdeal = outputVoltage / inputVoltage;
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            inductorCurrentMax = outputCurrent + outputCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = outputCurrent - outputCurrent * (rippleInductorCurrent / 200);
            deltaInductorCurrent = (inductorCurrentMax - inductorCurrentMin);
            inductorCurrentRMS = outputCurrent * Math.sqrt(1 + ((1 / 12.0) *
                    Math.pow(deltaInductorCurrent / outputCurrent, 2)));

            inductance = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                    (frequency * deltaInductorCurrent);
        } else {
            // DCM mode
            inductorCurrentMax = outputCurrent * (rippleInductorCurrent / 100);
            inductorCurrentMin = 0;
            deltaInductorCurrent = inductorCurrentMax;
            dutyCycleIdeal = 2 * outputPower / (deltaInductorCurrent * inputVoltage);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            inductance = dutyCycle * (inputVoltage - outputVoltage) /
                    (frequency * inductorCurrentMax);
            double dcm2 = inductorCurrentMax * frequency * inductance / outputVoltage;
            double dcm1 = 1 - dutyCycle - dcm2;
            inductorCurrentRMS = deltaInductorCurrent*Math.sqrt((dutyCycle + dcm1)/3);
        }

        capacitance = (inputVoltage * (1 - dutyCycle) * dutyCycle) / (
                16 * inductance * deltaCapacitorVoltage * Math.pow(frequency, 2));

        // create a ConverterData object
        ConverterData data = new ConverterData();

        // set data using the setData() method
        setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, switchCurrent,
                diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
                inputVoltage, outputVoltage, frequency);

        Log.d("CalculateVariables", isCCM + " " + inductanceCritical + " " + inductance + " " + dutyCycle);

        // return the data object
        return data;
    }

    private static boolean checkBuckConductionMode(double outputVoltage, double inputVoltage,
                                            double efficiency, double rippleInductorCurrent, double frequency) {
        dutyCycleIdeal = outputVoltage / inputVoltage;
        dutyCycle = dutyCycleIdeal / (efficiency / 100);
        inductorCurrentMax = outputCurrent + outputCurrent * (rippleInductorCurrent / 200);
        inductorCurrentMin = outputCurrent - outputCurrent * (rippleInductorCurrent / 200);
        deltaInductorCurrent = (inductorCurrentMax - inductorCurrentMin);
        inductance = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                (frequency * deltaInductorCurrent);
        inductanceCritical = dutyCycleIdeal * (inputVoltage - outputVoltage) * resistance /
                (2 * frequency * outputVoltage);
        return inductanceCritical <= inductance;
    }

    public static ConverterData boostCalculations(double inputVoltage, double outputVoltage, double outputPower,
                        double rippleInductorCurrent, double rippleCapacitorVoltage,
                        double frequency, double efficiency) {
        // Pre-global-variables
        resistance = Math.pow(outputVoltage, 2) / outputPower;
        outputCurrent = outputPower / outputVoltage;
        inputCurrent = outputPower / inputVoltage;
        inductorCurrent = inputCurrent;
        outputVoltageMax = outputVoltage + outputVoltage * (rippleCapacitorVoltage / 200);
        outputVoltageMin = outputVoltage - outputVoltage * (rippleCapacitorVoltage / 200);
        deltaCapacitorVoltage = outputVoltageMax - outputVoltageMin;
        String TAG = "CalculateVariables";
        Log.d(TAG, "Check variables before MODE: " +
                "Resistance = " + resistance +
                ", Output Current = " + outputCurrent +
                ", Input Current = " + inputCurrent +
                ", Max Output Voltage = " + outputVoltageMax +
                ", Min Output Voltage = " + outputVoltageMin +
                ", Delta Capacitor Voltage = " + deltaCapacitorVoltage);

        isCCM = checkBoostConductionMode(outputVoltage, inputVoltage, efficiency,
                rippleInductorCurrent, frequency, outputPower);
        Log.d(TAG, isCCM + " " +
                inductanceCritical + " " + inductance + " " + dutyCycle);

        if(isCCM) {
            dutyCycleIdeal = (outputVoltage - inputVoltage) / outputVoltage;
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            switchCurrent = dutyCycle * inputCurrent;
            diodeCurrent = (1 - dutyCycle) * inputCurrent;
            inductorCurrentRMS = inputCurrent;
            inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = inputCurrent - inputCurrent * (rippleInductorCurrent / 200);
            deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
            inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
            inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        } else {
            // DCM mode
            inductorCurrentMax = inputCurrent * (rippleInductorCurrent / 100);
            inductorCurrentMin = 0;
            deltaInductorCurrent = inductorCurrentMax;
            inductance = 2 * outputVoltage * (outputVoltage - inputVoltage) /
                    (resistance * frequency * Math.pow(deltaInductorCurrent, 2));
            dutyCycleIdeal = (1 / inputVoltage) * Math.sqrt((2 * inductance * outputVoltage *
                    frequency * (outputVoltage - inputVoltage)) / resistance);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            switchCurrent = dutyCycle * inputCurrent;
            diodeCurrent = (1 - dutyCycle) * inputCurrent;
            inductorCurrentRMS = inputCurrent * Math.sqrt((1 - dutyCycle) / 3);
        }
        capacitance = (outputCurrent * dutyCycle) * 2 / (deltaCapacitorVoltage * frequency);

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
        setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, switchCurrent,
                diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
                inputVoltage, outputVoltage, frequency);

        // return the data object
        return data;
    }

    private static boolean checkBoostConductionMode(double outputVoltage, double inputVoltage,
                                                    double efficiency, double rippleInductorCurrent,
                                                    double frequency, double outputPower) {
        dutyCycleIdeal = (outputVoltage - inputVoltage) / outputVoltage;
        dutyCycle = dutyCycleIdeal / (efficiency / 100);
        inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
        inductorCurrentMin = inputCurrent - inputCurrent * (rippleInductorCurrent / 200);
        deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
        inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
        inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        return inductanceCritical <= inductance;
    }

    public static ConverterData buckBoostCalculations(double inputVoltage, double outputVoltage,
                                                      double outputPower, double rippleInductorCurrent,
                                                      double rippleCapacitorVoltage, double frequency,
                                                      double efficiency) {
        // Pre-global-variables
        resistance = Math.pow(outputVoltage, 2) / outputPower;
        outputCurrent = outputPower / outputVoltage;
        inputCurrent = outputPower / inputVoltage;
        switchCurrent = inputCurrent;
        diodeCurrent = outputCurrent;
        inductorCurrent = outputCurrent + inputCurrent;
        outputVoltageMax = outputVoltage + outputVoltage * (rippleCapacitorVoltage / 200);
        outputVoltageMin = outputVoltage - outputVoltage * (rippleCapacitorVoltage / 200);
        deltaCapacitorVoltage = outputVoltageMax - outputVoltageMin;

        isCCM = checkBuckBoostConductionMode(outputVoltage, inputVoltage, efficiency,
                rippleInductorCurrent, frequency, outputPower);

        if(isCCM) {
            // CCM Mode
            dutyCycleIdeal = (outputVoltage) / (inputVoltage + outputVoltage);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            inductorCurrentMax = inductorCurrent + inductorCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = inductorCurrent - inductorCurrent * (rippleInductorCurrent / 200);
            deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
            inductorCurrentRMS = Math.sqrt(Math.pow(inductorCurrentMax, 2) +
                    Math.pow(deltaInductorCurrent / 12, 2));
            inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
            inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) /
                    (2 * outputPower * frequency);
        } else {
            // DCM Mode
            inductorCurrentMax = inductorCurrent * (rippleInductorCurrent / 100);
            inductorCurrentMin = 0;
            deltaInductorCurrent = inductorCurrentMax;
            inductance = 2 * outputCurrent * outputVoltage / (Math.pow(deltaInductorCurrent, 2) *
                    frequency);
            dutyCycleIdeal = (outputVoltage / inputVoltage) *
                    Math.sqrt((2 * inductance * frequency) / resistance);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
        }
        capacitance = (outputCurrent * dutyCycle) * 2 / (deltaCapacitorVoltage * frequency);

        // create a ConverterData object
        ConverterData data = new ConverterData();

        // set data using the setData() method
        setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, switchCurrent,
                diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
                inputVoltage, outputVoltage, frequency);

        // return the data object
        return data;
    }

    private static boolean checkBuckBoostConductionMode(double outputVoltage, double inputVoltage,
                                                        double efficiency, double rippleInductorCurrent,
                                                        double frequency, double outputPower) {
        dutyCycleIdeal = (outputVoltage) / (inputVoltage + outputVoltage);
        dutyCycle = ((outputVoltage) / (inputVoltage + outputVoltage)) / (efficiency / 100);
        inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
        inductorCurrentMin = inputCurrent - inputCurrent * (rippleInductorCurrent / 200);
        deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
        inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
        inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        return inductanceCritical <= inductance;
    }

    public static void setData(ConverterData data, double dutyCycle, double dutyCycleIdeal,
                               double resistance, double capacitance, double inductance,
                               double inductanceCritical, double inputCurrent, double outputCurrent,
                               double inductorCurrent, double switchCurrent, double diodeCurrent,
                               double deltaInductorCurrent, double deltaCapacitorVoltage,
                               double inductorCurrentRMS, boolean isCCM, double inputVoltage,
                               double outputVoltage, double frequency) {
        data.setDutyCycle(dutyCycle);
        data.setDutyCycleIdeal(dutyCycleIdeal);
        data.setResistance(resistance);
        data.setCapacitance(capacitance);
        data.setInductance(inductance);
        data.setInductanceCritical(inductanceCritical);
        data.setInputCurrent(inputCurrent);
        data.setOutputCurrent(outputCurrent);
        data.setInductorCurrent(inductorCurrent);
        data.setSwitchCurrent(switchCurrent);
        data.setDiodeCurrent(diodeCurrent);
        data.setDeltaInductorCurrent(deltaInductorCurrent);
        data.setDeltaCapacitorVoltage(deltaCapacitorVoltage);
        data.setInductorCurrentRMS(inductorCurrentRMS);
        data.setIsCCM(isCCM);
        data.setInputVoltage(inputVoltage);
        data.setOutputVoltage(outputVoltage);
        data.setFrequency(frequency);
    }

    public void retrieveDataFromUsualDesignActivity(Bundle bundle) {
        dutyCycle = bundle.getDouble(DUTY_CYCLE_KEY);
        dutyCycleIdeal = bundle.getDouble(DUTY_CYCLE_IDEAL_KEY);
        resistance = bundle.getDouble(RESISTANCE_KEY);
        capacitance = bundle.getDouble(CAPACITANCE_KEY);
        inductance = bundle.getDouble(INDUCTANCE_KEY);
        inductanceCritical = bundle.getDouble(INDUCTANCE_CRITICAL_KEY);
        inputCurrent = bundle.getDouble(INPUT_CURRENT_KEY);
        outputCurrent = bundle.getDouble(OUTPUT_CURRENT_KEY);
        inductorCurrent = bundle.getDouble(INDUCTOR_CURRENT_KEY);
        switchCurrent = bundle.getDouble(SWITCH_CURRENT_KEY);
        diodeCurrent = bundle.getDouble(DIODE_CURRENT_KEY);
        inputVoltage = bundle.getDouble(INPUT_VOLTAGE_KEY);
        outputVoltage = bundle.getDouble(OUTPUT_VOLTAGE_KEY);
        frequency = bundle.getDouble(FREQUENCY_KEY);
        deltaInductorCurrent = bundle.getDouble(DELTA_INDUCTOR_CURRENT_KEY);
        deltaCapacitorVoltage = bundle.getDouble(DELTA_CAPACITOR_VOLTAGE_KEY);
        inductorCurrentRMS = bundle.getDouble(INDUCTOR_CURRENT_RMS_KEY);
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
