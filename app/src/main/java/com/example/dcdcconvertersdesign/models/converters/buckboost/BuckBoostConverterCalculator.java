package com.example.dcdcconvertersdesign.models.converters.buckboost;

import com.example.dcdcconvertersdesign.utils.convertersutils.ConverterData;

public class BuckBoostConverterCalculator {
    private static double dutyCycle;
    private static double dutyCycleIdeal;
    private static double inductance;
    private static double inductanceCritical;
    private static double inputCurrent;
    private static double inductorCurrentMax;
    private static double inductorCurrentMin;
    private static double deltaInductorCurrent;

    public static ConverterData calculateBuckBoostVariables(double inputVoltage, double outputVoltage,
                                                      double outputPower, double rippleInductorCurrent,
                                                      double rippleCapacitorVoltage, double frequency,
                                                      double efficiency) {
        // Pre-global-variables
        double resistance = Math.pow(outputVoltage, 2) / outputPower;
        double outputCurrent = outputPower / outputVoltage;
        inputCurrent = outputPower / inputVoltage;
        double switchCurrent = inputCurrent;
        double inductorCurrent = outputCurrent + inputCurrent;
        double outputVoltageMax = outputVoltage + outputVoltage * (rippleCapacitorVoltage / 200);
        double outputVoltageMin = outputVoltage - outputVoltage * (rippleCapacitorVoltage / 200);
        double deltaCapacitorVoltage = outputVoltageMax - outputVoltageMin;

        boolean isCCM = checkBuckBoostConductionMode(outputVoltage, inputVoltage, efficiency,
                rippleInductorCurrent, frequency, inductorCurrent);

        double inductorCurrentRMS;
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
        double capacitance = (outputCurrent * dutyCycle) / (deltaCapacitorVoltage * frequency);
        // create a ConverterData object
        ConverterData data = new ConverterData();

        // set data using the setData() method
        data.setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, switchCurrent,
                outputCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
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
}
