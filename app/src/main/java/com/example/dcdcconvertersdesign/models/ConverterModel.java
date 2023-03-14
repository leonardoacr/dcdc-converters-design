package com.example.dcdcconvertersdesign.models;

import android.os.Bundle;
import android.util.Log;

import com.example.dcdcconvertersdesign.interfaces.models.ConverterModelInterface;
import com.example.dcdcconvertersdesign.utils.convertersutils.ConverterData;

public class ConverterModel implements ConverterModelInterface {
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
    private static double inductorCurrentMax;
    private static double inductorCurrentMin;
    private static double deltaInductorCurrent;
    private static double outputVoltageMax;
    private static double outputVoltageMin;
    private static double deltaCapacitorVoltage;
    private static double inductorCurrentRMS;
    private static boolean isCCM;
    private static int flag;

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
            inductorCurrentRMS = inductorCurrent * Math.sqrt(1 + ((1 / 12.0) *
                    Math.pow(deltaInductorCurrent / inductorCurrent, 2)));

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
                8 * inductance * deltaCapacitorVoltage * Math.pow(frequency, 2));

        // create a ConverterData object
        ConverterData data = new ConverterData();

        // set data using the setData() method
        data.setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, switchCurrent,
                diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
                inputVoltage, outputVoltage, frequency, outputPower, rippleInductorCurrent,
                rippleCapacitorVoltage, efficiency);

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
            inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = inputCurrent - inputCurrent * (rippleInductorCurrent / 200);
            deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
            inductorCurrentRMS = inductorCurrent * Math.sqrt(1 + ((1 / 12.0) *
                    Math.pow(deltaInductorCurrent / inductorCurrent, 2)));
            inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
            inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) /
                    (2 * outputPower * frequency);
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
            inductorCurrentRMS = inductorCurrent * Math.sqrt((1 - dutyCycle) / 3);
        }
        capacitance = outputCurrent * dutyCycle / (frequency * deltaCapacitorVoltage);

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
                rippleCapacitorVoltage, efficiency);

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
                rippleInductorCurrent, frequency, inductorCurrent);

        if(isCCM) {
            // CCM Mode
            dutyCycleIdeal = (outputVoltage) / (inputVoltage + outputVoltage);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            inductorCurrentMax = inductorCurrent + inductorCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = inductorCurrent - inductorCurrent * (rippleInductorCurrent / 200);
            deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
            inductorCurrentRMS = inductorCurrent * Math.sqrt(1 + ((1 / 12.0) *
                    Math.pow(deltaInductorCurrent / inductorCurrent, 2)));
            inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
            inductanceCritical = inputVoltage * dutyCycle  / (frequency * inductorCurrent * 2);
        } else {
            // DCM Mode
            inductorCurrentMax = inductorCurrent * (rippleInductorCurrent / 100);
            inductorCurrentMin = 0;
            deltaInductorCurrent = inductorCurrentMax;
            inductance = 2 * outputCurrent * outputVoltage / (Math.pow(deltaInductorCurrent, 2) *
                    frequency);
            inductanceCritical = 2 * outputCurrent * outputVoltage /
                    (Math.pow(2 * inductorCurrent, 2) * frequency);
            dutyCycleIdeal = (outputVoltage / inputVoltage) *
                    Math.sqrt((2 * inductance * frequency) / resistance);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            inductorCurrentRMS = inductorCurrent * Math.sqrt((1 - dutyCycle) / 3);
        }
        capacitance = (outputCurrent * dutyCycle) / (deltaCapacitorVoltage * frequency);
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

    private static boolean checkBuckBoostConductionMode(double outputVoltage, double inputVoltage,
                                                        double efficiency, double rippleInductorCurrent,
                                                        double frequency, double inductorCurrent) {
        dutyCycleIdeal = (outputVoltage) / (inputVoltage + outputVoltage);
        dutyCycle = ((outputVoltage) / (inputVoltage + outputVoltage)) / (efficiency / 100);
        inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
        inductorCurrentMin = inputCurrent - inputCurrent * (rippleInductorCurrent / 200);
        deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
        inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
        inductanceCritical = inputVoltage * dutyCycle  / (frequency * inductorCurrent * 2);

        return inductanceCritical <= inductance;
    }

    public void retrieveDataFromUsualDesignActivity(Bundle bundle) {
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
        String DELTA_INDUCTOR_CURRENT_KEY = "DeltaIL";
        String DELTA_CAPACITOR_VOLTAGE_KEY = "DeltaVC";
        String INDUCTOR_CURRENT_RMS_KEY = "Inductor_Current_RMS";
        String IS_CCM_KEY = "is_ccm";
        String FLAG_KEY = "Flag";

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
